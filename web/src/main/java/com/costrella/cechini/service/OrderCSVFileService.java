package com.costrella.cechini.service;

import com.costrella.cechini.domain.OrderItem;
import com.costrella.cechini.domain.Report;
import com.costrella.cechini.domain.Store;
import com.costrella.cechini.domain.Worker;
import com.costrella.cechini.repository.StoreRepository;
import com.costrella.cechini.repository.WorkerRepository;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class OrderCSVFileService {
    private static final String CSV_FILE_NAME = "CSV_FILE.csv";
    private final WorkerRepository workerRepository;

    private final StoreRepository storeRepository;
    private final MessageSource messageSource;


    public OrderCSVFileService(WorkerRepository workerRepository, StoreRepository storeRepository, MessageSource messageSource) {
        this.workerRepository = workerRepository;
        this.storeRepository = storeRepository;
        this.messageSource = messageSource;
    }

    public File generateFile(Report report, String langKey) throws IOException {
        Locale locale = Locale.forLanguageTag(langKey);
        List<String[]> dataLines = generateContent(report, locale);
        if (dataLines == null || dataLines.isEmpty()) {
            return null; // No data to write
        }
        File csvOutputFile = new File(CSV_FILE_NAME);

        Writer out = new BufferedWriter(new OutputStreamWriter(
            new FileOutputStream(csvOutputFile), StandardCharsets.UTF_8));
        try {
            for (String[] strings : dataLines) {
                int i = 0;
                for (String s : strings) {
                    out.write(escapeSpecialCharacters(s));
                    if (i != strings.length - 1) {
                        out.write(",");
                    }
                    i++;
                }
                out.write("\n");
            }
        } finally {
            out.close();
        }


//        try (PrintWriter pw = new PrintWriter(csvOutputFile)) {
//            dataLines.stream()
//                .map(this::convertToCSV)
//                .forEach(pw::println);
//        }
        if (csvOutputFile.exists()) return csvOutputFile;
        return null;
    }

    private List<String[]> generateContent(Report report, Locale locale) {
        Worker worker = workerRepository.getOne(report.getWorker().getId());
        Store store = storeRepository.getOne(report.getStore().getId());
        if(store.getNip() == null) {
            return null;
        }
        List<String[]> dataLines = new ArrayList<>();
        DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            .withZone(ZoneId.systemDefault());

        dataLines.add(new String[]
            {messageSource.getMessage("email.attachment.receiver", null, locale), store.getName() + " " + store.getAddress()});
        dataLines.add(new String[]
            {messageSource.getMessage("email.attachment.receiver_nip", null, locale), store.getNip()});
        dataLines.add(new String[]
            {messageSource.getMessage("email.attachment.date", null, locale), DATE_TIME_FORMATTER.format(report.getReportDate()),
                "",
                messageSource.getMessage("email.attachment.order_no", null, locale), report.getOrder().getNumber()
            });
        dataLines.add(new String[]
            {messageSource.getMessage("email.attachment.deliver", null, locale), report.getOrder().getWarehouse().getName()});
        dataLines.add(new String[]
            {messageSource.getMessage("email.attachment.worker", null, locale), worker.getName() + " " + worker.getSurname(), messageSource.getMessage("email.attachment.phone", null, locale) + worker.getPhone()});
        dataLines.add(new String[]
            {messageSource.getMessage("email.attachment.lp", null, locale), messageSource.getMessage("email.attachment.product", null, locale), messageSource.getMessage("email.attachment.ean", null, locale), messageSource.getMessage("email.attachment.count", null, locale)});
        int lp = 1;
        for (OrderItem oi : report.getOrder().getOrderItems()) {
            dataLines.add(new String[]
                {"" + lp, oi.getProduct().getName(), "" + oi.getProduct().getEanPack(), "" + oi.getPackCount()});
            lp++;
        }

        return dataLines;
    }

    private String escapeSpecialCharacters(String data) {
        if (data == null) return "";
        String escapedData = data.replaceAll("\\R", " ");
        if (data.contains(",") || data.contains("\"") || data.contains("'")) {
            data = data.replace("\"", "\"\"");
            escapedData = "\"" + data + "\"";
        }
        return escapedData;
    }

    private String convertToCSV(String[] data) {
        return Stream.of(data)
            .map(this::escapeSpecialCharacters)
            .collect(Collectors.joining(","));
    }
}
