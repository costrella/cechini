package com.kostrzewa.cechini.model;

import java.io.Serializable;
import java.util.List;

public class ReportsDTO implements Serializable {

    private List<ReportDTOWithPhotos> reportsDTOS;

    public List<ReportDTOWithPhotos> getReportsDTOS() {
        return reportsDTOS;
    }

    public void setReportsDTOS(List<ReportDTOWithPhotos> reportsDTOS) {
        this.reportsDTOS = reportsDTOS;
    }
}
