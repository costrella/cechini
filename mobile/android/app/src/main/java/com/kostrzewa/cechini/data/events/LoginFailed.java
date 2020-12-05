package com.kostrzewa.cechini.data.events;

public class LoginFailed {

    private String text;

    public LoginFailed(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
