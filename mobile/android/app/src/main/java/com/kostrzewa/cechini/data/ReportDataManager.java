package com.kostrzewa.cechini.data;

import com.kostrzewa.cechini.model.NoteDTO;
import com.kostrzewa.cechini.model.ReportDTO;
import com.kostrzewa.cechini.model.ReportDTOWithPhotos;
import com.kostrzewa.cechini.model.ReportsDTO;
import com.kostrzewa.cechini.model.WarehouseDTO;
import com.kostrzewa.cechini.ui.report.data.ReportData;

import java.util.List;

public interface ReportDataManager {

    void send(ReportDTO reportDTO);

    void sendReportNotSent();

    void sendCommentsNotSent();

    void downloadMyReports(Long workerId);

    List<ReportDTOWithPhotos> getMyReports();

    void downloadMyReportsByStoreId(Long workerId, Long storeId);

    void addNewComment(NoteDTO noteDTO);
    void setReportReadByWorker(Long reportID);
}
