package com.costrella.cechini.service.dto;

import java.io.Serializable;
import java.util.List;

public class ReportsDTO implements Serializable {

    private List<ReportDTO> reportsDTOS;

    public List<ReportDTO> getReportsDTOS() {
        return reportsDTOS;
    }

    public void setReportsDTOS(List<ReportDTO> reportsDTOS) {
        this.reportsDTOS = reportsDTOS;
    }
}
