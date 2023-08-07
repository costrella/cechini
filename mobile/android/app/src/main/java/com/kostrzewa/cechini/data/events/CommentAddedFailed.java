package com.kostrzewa.cechini.data.events;

public class CommentAddedFailed {

    private String text;

    public CommentAddedFailed(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
