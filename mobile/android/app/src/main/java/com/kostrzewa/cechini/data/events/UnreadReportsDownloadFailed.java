package com.kostrzewa.cechini.data.events;

import com.kostrzewa.cechini.model.ReportDTOWithPhotos;

import java.util.ArrayList;
import java.util.List;

public class UnreadReportsDownloadFailed {

    private String text;

    public UnreadReportsDownloadFailed(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
