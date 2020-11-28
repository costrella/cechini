package com.kostrzewa.cechini.data.events;

public class ReportSentFailed {

    private String text;

    public ReportSentFailed(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
