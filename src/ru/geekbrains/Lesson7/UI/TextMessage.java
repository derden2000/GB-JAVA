package ru.geekbrains.Lesson7.UI;

import java.time.LocalDateTime;

public class TextMessage {

    private LocalDateTime created;

    private String userFrom;

    private String userTo;

    private String text;

    public TextMessage(String userFrom, String userTo, String text) {
        this.created = LocalDateTime.now();
        this.userFrom = userFrom;
        this.userTo = userTo;
        this.text = text;
    }

    public String getUserFrom() {
        return userFrom;
    }

    public void setUserFrom(String userFrom) {
        this.userFrom = userFrom;
    }

    public String getUserTo() {
        return userTo;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getCreated() {
        return created;
    }
}
