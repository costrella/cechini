package com.kostrzewa.cechini.data.events;

import com.kostrzewa.cechini.model.ReportDTO;

import java.util.ArrayList;
import java.util.List;

public class MyReportsDownloadSuccess {

    private List<ReportDTO> list = new ArrayList<>();

    public MyReportsDownloadSuccess(List<ReportDTO> list) {
        this.list = list;
    }

    public List<ReportDTO> getList() {
        return list;
    }

    public void setList(List<ReportDTO> list) {
        this.list = list;
    }
}
