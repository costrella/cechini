package com.kostrzewa.cechini.data.events;

import com.kostrzewa.cechini.model.OrderDTO;
import com.kostrzewa.cechini.model.ReportDTOWithPhotos;

import java.util.ArrayList;
import java.util.List;

public class OneReportSuccess {

    public OneReportSuccess(ReportDTOWithPhotos reportDTOWithPhotos) {
        this.reportDTOWithPhotos = reportDTOWithPhotos;
    }
    private ReportDTOWithPhotos reportDTOWithPhotos;

    public ReportDTOWithPhotos getReportDTOWithPhotos() {
        return reportDTOWithPhotos;
    }

    public void setReportDTOWithPhotos(ReportDTOWithPhotos reportDTOWithPhotos) {
        this.reportDTOWithPhotos = reportDTOWithPhotos;
    }
}
