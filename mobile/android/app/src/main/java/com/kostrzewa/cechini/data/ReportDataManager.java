package com.kostrzewa.cechini.data;

import com.kostrzewa.cechini.model.ReportDTO;
import com.kostrzewa.cechini.model.ReportsDTO;
import com.kostrzewa.cechini.model.WarehouseDTO;
import com.kostrzewa.cechini.ui.report.data.ReportData;

import java.util.List;

public interface ReportDataManager {

    void send(ReportDTO reportDTO);

    void sendReportNotSent();

    void downloadMyReports(Long workerId);

    void downloadMyReportsByStoreId(Long workerId, Long storeId);
}
