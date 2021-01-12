package com.costrella.cechini.service;

import com.costrella.cechini.domain.OrderItem;
import com.costrella.cechini.domain.Report;
import com.costrella.cechini.domain.Store;
import com.costrella.cechini.domain.Worker;
import com.costrella.cechini.repository.StoreRepository;
import com.costrella.cechini.repository.WorkerRepository;
import org.springframework.stereotype.Service;

import java.io.*;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class OrderFileService {
    private static final String CSV_FILE_NAME = "CSV_FILE.csv";
    private final WorkerRepository workerRepository;

    private final StoreRepository storeRepository;

    public OrderFileService(WorkerRepository workerRepository, StoreRepository storeRepository) {
        this.workerRepository = workerRepository;
        this.storeRepository = storeRepository;
    }

    public File generateFile(Report report) throws IOException {
        List<String[]> dataLines = generateContent(report);
        File csvOutputFile = new File(CSV_FILE_NAME);

        Writer out = new BufferedWriter(new OutputStreamWriter(
            new FileOutputStream(csvOutputFile), "UTF-8"));
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

    private List<String[]> generateContent(Report report) {
        Worker worker = workerRepository.getOne(report.getWorker().getId());
        Store store = storeRepository.getOne(report.getStore().getId());
        List<String[]> dataLines = new ArrayList<>();
        DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            .withZone(ZoneId.systemDefault());

        dataLines.add(new String[]
            {"Odbiorca:", store.getName() + " " + store.getAddress()});
        dataLines.add(new String[]
            {"Odbiorca NIP:", store.getNip()});
        dataLines.add(new String[]
            {"Data wystawienia:", DATE_TIME_FORMATTER.format(report.getReportDate())});
        dataLines.add(new String[]
            {"Dostawca:", report.getOrder().getWarehouse().getName()});
        dataLines.add(new String[]
            {"Przedstawiciel handlowy:", worker.getName() + " " + worker.getSurname(), "tel:" + worker.getPhone()});
        dataLines.add(new String[]
            {"Lp", "Produkt", "Pojemność", "EAN", "Ilość"});
        int lp = 1;
        for (OrderItem oi : report.getOrder().getOrderItems()) {
            dataLines.add(new String[]
                {"" + lp, oi.getProduct().getName(), "" + oi.getProduct().getCapacity() + "L", "" + oi.getProduct().getEanPack(), "" + oi.getPackCount()});
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
