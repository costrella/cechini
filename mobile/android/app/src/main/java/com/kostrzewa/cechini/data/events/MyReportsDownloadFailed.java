package com.kostrzewa.cechini.data.events;

public class MyReportsDownloadFailed {

    private String text;

    public MyReportsDownloadFailed(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
