package com.kostrzewa.cechini.data.events;

public class CommentAddedSuccess {

    private String text;

    public CommentAddedSuccess(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
