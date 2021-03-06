package com.costrella.cechini.service;

import com.costrella.cechini.domain.Report;
import com.costrella.cechini.domain.Store;
import com.costrella.cechini.domain.Worker;
import com.costrella.cechini.repository.StoreRepository;
import com.costrella.cechini.repository.WorkerRepository;
import com.costrella.cechini.service.excel.OrderItem;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class OrderExcelFileService {
    private static final String EXCEL_FILE_NAME = "EXCEL_FILE.xls";
    private final WorkerRepository workerRepository;

    private final StoreRepository storeRepository;

    public OrderExcelFileService(WorkerRepository workerRepository, StoreRepository storeRepository) {
        this.workerRepository = workerRepository;
        this.storeRepository = storeRepository;

    }

    public File generateFile(Report report) throws IOException {
        return writeExcel(report);
    }

    public File writeExcel(Report report) throws IOException {
        Worker worker = workerRepository.getOne(report.getWorker().getId());
        Store store = storeRepository.getOne(report.getStore().getId());

        Workbook workbook = new HSSFWorkbook();
        Sheet sheet = workbook.createSheet();
        sheet.setColumnWidth(0, 5000);
        sheet.setColumnWidth(1, 10000);
        sheet.setColumnWidth(2, 3000);
        sheet.setColumnWidth(3, 5000);

        File outputFile = new File(EXCEL_FILE_NAME);
        int rowCount = 0;

        writeOrderNumber(report.getOrder().getNumber(), sheet.createRow(++rowCount));
        writeOdbiorca(store.getName() + ", " + store.getAddress(), sheet.createRow(++rowCount));
        writeOdbiorcaNIP(store.getNip(), sheet.createRow(++rowCount));
        writeDate(report.getReportDate(), sheet.createRow(++rowCount));
        writeWarehouse(report.getOrder().getWarehouse().getName(), sheet.createRow(++rowCount));
        writePH(worker.getSurname() + " " + worker.getName() + ", tel: " + worker.getPhone(), sheet.createRow(++rowCount));
        ++rowCount;
        writeOrderItemHead(sheet.createRow(++rowCount), workbook);
        for (OrderItem aBook : getOrderItemList(report.getOrder().getOrderItems())) {
            Row row = sheet.createRow(++rowCount);
            writeOrderItem(aBook, row, workbook);
        }

        try (FileOutputStream outputStream = new FileOutputStream(outputFile)) {
            workbook.write(outputStream);
        }
        if (outputFile.exists()) return outputFile;
        return null;
    }

    private void writePH(String s, Row row) {
        Cell cell = row.createCell(0);
        cell.setCellValue("Przedstawiciel: ");
        cell = row.createCell(1);
        cell.setCellValue("" + s);
    }

    private void writeWarehouse(String name, Row row) {
        Cell cell = row.createCell(0);
        cell.setCellValue("Dostawca: ");
        cell = row.createCell(1);
        cell.setCellValue("" + name);
    }

    private void writeDate(Instant reportDate, Row row) {
        DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            .withZone(ZoneId.systemDefault());

        Cell cell = row.createCell(0);
        cell.setCellValue("Data wystawienia: ");
        cell = row.createCell(1);
        cell.setCellValue("" + DATE_TIME_FORMATTER.format(reportDate));
    }

    private void writeOdbiorcaNIP(String nip, Row row) {
        Cell cell = row.createCell(0);
        cell.setCellValue("Odbiorca NIP: ");
        cell = row.createCell(1);
        cell.setCellValue("" + nip);
    }

    private void writeOdbiorca(String s, Row row) {
        Cell cell = row.createCell(0);
        cell.setCellValue("Odbiorca: ");
        cell = row.createCell(1);
        cell.setCellValue("" + s);
    }

    private void writeOrderNumber(String number, Row row) {
        Cell cell = row.createCell(0);
        cell.setCellValue("Nr zamówienia: ");
        cell = row.createCell(1);
        cell.setCellValue(number);
    }

    private List<OrderItem> getOrderItemList(Set<com.costrella.cechini.domain.OrderItem> orderItems) {
        List<OrderItem> listBook = new ArrayList<>();
        AtomicInteger i = new AtomicInteger();
        i.getAndIncrement();
        orderItems.forEach(orderItem -> {
            listBook.add(new OrderItem(i.getAndIncrement(), orderItem.getProduct().getName(), orderItem.getProduct().getCapacity(), orderItem.getProduct().getEanPack(), orderItem.getPackCount()));
        });
        return listBook;
    }

    private void writeOrderItemHead(Row row, Workbook workbook) {
        Cell cell = row.createCell(0);
        cell.setCellStyle(border(workbook));
        cell.setCellValue("Lp");

        cell = row.createCell(1);
        cell.setCellStyle(border(workbook));
        cell.setCellValue("Produkt");

        cell = row.createCell(2);
        cell.setCellStyle(border(workbook));
        cell.setCellValue("Pojemność");

        cell = row.createCell(3);
        cell.setCellStyle(border(workbook));
        cell.setCellValue("EAN");

        cell = row.createCell(4);
        cell.setCellStyle(border(workbook));
        cell.setCellValue("Ilość (zgrzewki)");
    }

    private void writeOrderItem(OrderItem orderItem, Row row, Workbook workbook) {
        Cell cell = row.createCell(0);
        cell.setCellValue(orderItem.getLp());

        cell = row.createCell(1);
        cell.setCellStyle(wrap(workbook));
        cell.setCellValue(orderItem.getProductName());

        cell = row.createCell(2);
        cell.setCellValue(""+orderItem.getCapacity() + "L");

        cell = row.createCell(3);
        cell.setCellValue(orderItem.getEan());

        cell = row.createCell(4);
        cell.setCellValue(orderItem.getCount());
    }


    public CellStyle wrap(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        style.setWrapText(true);
        return style;
    }

    public CellStyle border(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        style.setBorderLeft(BorderStyle.valueOf((short) 1));
        style.setBorderRight(BorderStyle.valueOf((short) 1));
        style.setBorderBottom(BorderStyle.valueOf((short) 1));
        style.setBorderTop(BorderStyle.valueOf((short) 1));
        return style;
    }

}
