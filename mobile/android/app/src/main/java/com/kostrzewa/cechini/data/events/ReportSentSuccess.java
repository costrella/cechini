package com.kostrzewa.cechini.data.events;

public class ReportSentSuccess {

    private String text;

    public ReportSentSuccess(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
