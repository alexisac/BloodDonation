package org.example;

import java.io.Serializable;

public class ChatMessage implements Serializable {
    String text;

    public ChatMessage() {}

    public ChatMessage(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
