package com.example.enchere.value_object;

public class Message {
    private String message;

    public Message(String message) {
        setMessage(message);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
