package com.kostrzewa.cechini.data.events;

public class OneReportFailed {

    private String text;

    public OneReportFailed(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
