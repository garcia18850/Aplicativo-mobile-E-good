package com.projeto.egoodapp.chat;

public class ChatMessage {
    public static final int TYPE_USER = 1;
    public static final int TYPE_BOT = 2;

    private String text;
    private int type;

    public ChatMessage(String text, int type) {
        this.text = text;
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public int getType() {
        return type;
    }
}
