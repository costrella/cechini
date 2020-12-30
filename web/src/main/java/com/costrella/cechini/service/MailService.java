package com.costrella.cechini.service;

import com.costrella.cechini.domain.*;
import com.costrella.cechini.repository.StoreRepository;
import com.costrella.cechini.repository.WorkerRepository;
import io.github.jhipster.config.JHipsterProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Service for sending emails.
 * <p>
 * We use the {@link Async} annotation to send emails asynchronously.
 */
@Service
public class MailService {

    private final Logger log = LoggerFactory.getLogger(MailService.class);

    private static final String USER = "user";

    private static final String BASE_URL = "baseUrl";

    private static final String CSV_FILE_NAME = "CSV_FILE.csv";

    private final JHipsterProperties jHipsterProperties;

    private final JavaMailSender javaMailSender;

    private final MessageSource messageSource;

    private final SpringTemplateEngine templateEngine;

    private final WorkerRepository workerRepository;

    private final StoreRepository storeRepository;

    public MailService(JHipsterProperties jHipsterProperties, JavaMailSender javaMailSender,
                       MessageSource messageSource, SpringTemplateEngine templateEngine, WorkerRepository workerRepository, StoreRepository storeRepository) {

        this.jHipsterProperties = jHipsterProperties;
        this.javaMailSender = javaMailSender;
        this.messageSource = messageSource;
        this.templateEngine = templateEngine;
        this.workerRepository = workerRepository;
        this.storeRepository = storeRepository;
    }

    public boolean sendEmailWithOrder(Report report) {
        String mail = "michal.kostrzewa89@gmail.com";
        String subject = "Cechini. Zamówienie";
        String content = subject;
        return sendEmail(report, mail, subject, content, true, true);
    }

    public List<String[]> test(Report report) {
        Worker worker = workerRepository.getOne(report.getWorker().getId());
        Store store = storeRepository.getOne(report.getStore().getId());
        List<String[]> dataLines = new ArrayList<>();
        DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            .withZone(ZoneId.systemDefault());

        dataLines.add(new String[]
            {"Odbiorca: ", store.getName() + " " +  store.getAddress()});
        dataLines.add(new String[]
            {"Odbiorca NIP: ", store.getNip()});
        dataLines.add(new String[]
            {"Data wystawienia: ", DATE_TIME_FORMATTER.format(report.getReportDate())});
        dataLines.add(new String[]
            {"Dostawca: ", report.getOrder().getWarehouse().getName()});
        dataLines.add(new String[]
            {"Przedstawiciel handlowy: ", worker.getName() + " " + worker.getSurname(), "tel.:" + worker.getPhone()});
        dataLines.add(new String[]
            {"Lp", "Produkt", "Pojemnosc", "EAN", "Ilosc"});
        int lp = 1;
        for(OrderItem oi : report.getOrder().getOrderItems()){
            dataLines.add(new String[]
                {""+lp, oi.getProduct().getName(), ""+oi.getProduct().getCapacity() + "L", ""+oi.getProduct().getEanPack(), ""+oi.getPackCount()});
            lp ++;
        }

        return dataLines;
    }

    public String escapeSpecialCharacters(String data) {
        if(data == null) return "";
        String escapedData = data.replaceAll("\\R", " ");
        if (data.contains(",") || data.contains("\"") || data.contains("'")) {
            data = data.replace("\"", "\"\"");
            escapedData = "\"" + data + "\"";
        }
        return escapedData;
    }

    public String convertToCSV(String[] data) {
        return Stream.of(data)
            .map(this::escapeSpecialCharacters)
            .collect(Collectors.joining(","));
    }

    public File generateFile(List<String[]> dataLines) throws IOException {
        File csvOutputFile = new File(CSV_FILE_NAME);
//
//        try (FileOutputStream fos = new FileOutputStream(csvOutputFile); //todo utf-8 ?
//             OutputStreamWriter osw = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
//             BufferedWriter writer = new BufferedWriter(osw)) {
//
////            writer.append(line);
//            for (String[] dataLine : dataLines) {
//                String s = convertToCSV(dataLine);
//                writer.write(s);
//            }
//        }

        try (PrintWriter pw = new PrintWriter(csvOutputFile)) {
            dataLines.stream()
                .map(this::convertToCSV)
                .forEach(pw::println);
        }
        if (csvOutputFile.exists()) return csvOutputFile;
        return null;
    }

    public boolean sendEmail(Report report, String to, String subject, String content, boolean isMultipart, boolean isHtml) {
        log.debug("Send email[multipart '{}' and html '{}'] to '{}' with subject '{}' and content={}",
            isMultipart, isHtml, to, subject, content);

        // Prepare message using a Spring helper
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, isMultipart, StandardCharsets.UTF_8.name());
            message.setTo(to);
            message.addCc("misiek_mk@tlen.pl");
            message.setFrom(jHipsterProperties.getMail().getFrom());
            message.setSubject(subject);
            message.setText(content, isHtml);
            if(report.getOrder() != null){
                message.addAttachment("zamowienie_testowe.csv", generateFile(test(report)));
            }
            javaMailSender.send(mimeMessage);
            log.debug("Sent email to User '{}'", to);
            return true;
        } catch (MailException | MessagingException | IOException e) {
            log.warn("Email could not be sent to user '{}'", to, e);
            return false;
        }
    }

    @Async
    public void sendEmailFromTemplate(User user, String templateName, String titleKey) {
        if (user.getEmail() == null) {
            log.debug("Email doesn't exist for user '{}'", user.getLogin());
            return;
        }
        Locale locale = Locale.forLanguageTag(user.getLangKey());
        Context context = new Context(locale);
        context.setVariable(USER, user);
        context.setVariable(BASE_URL, jHipsterProperties.getMail().getBaseUrl());
        String content = templateEngine.process(templateName, context);
        String subject = messageSource.getMessage(titleKey, null, locale);
        sendEmail(null, user.getEmail(), subject, content, false, true);
    }

    @Async
    public void sendActivationEmail(User user) {
        log.debug("Sending activation email to '{}'", user.getEmail());
        sendEmailFromTemplate(user, "mail/activationEmail", "email.activation.title");
    }

    @Async
    public void sendCreationEmail(User user) {
        log.debug("Sending creation email to '{}'", user.getEmail());
        sendEmailFromTemplate(user, "mail/creationEmail", "email.activation.title");
    }

    @Async
    public void sendPasswordResetMail(User user) {
        log.debug("Sending password reset email to '{}'", user.getEmail());
        sendEmailFromTemplate(user, "mail/passwordResetEmail", "email.reset.title");
    }
}