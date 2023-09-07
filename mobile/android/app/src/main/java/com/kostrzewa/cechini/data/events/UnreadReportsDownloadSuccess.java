package com.kostrzewa.cechini.data.events;

import com.kostrzewa.cechini.model.ReportDTOWithPhotos;

import java.util.ArrayList;
import java.util.List;

public class UnreadReportsDownloadSuccess {

    private List<ReportDTOWithPhotos> list = new ArrayList<>();

    public UnreadReportsDownloadSuccess(List<ReportDTOWithPhotos> list) {
        this.list = list;
    }

    public List<ReportDTOWithPhotos> getList() {
        return list;
    }

    public void setList(List<ReportDTOWithPhotos> list) {
        this.list = list;
    }
}
