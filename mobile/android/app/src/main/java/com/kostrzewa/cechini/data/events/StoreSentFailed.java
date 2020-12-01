package com.kostrzewa.cechini.data.events;

public class StoreSentFailed {

    private String text;

    public StoreSentFailed(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
