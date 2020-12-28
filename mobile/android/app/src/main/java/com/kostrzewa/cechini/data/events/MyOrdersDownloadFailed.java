package com.kostrzewa.cechini.data.events;

public class MyOrdersDownloadFailed {

    private String text;

    public MyOrdersDownloadFailed(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
