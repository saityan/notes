package ru.geekbrains.notes;

import java.util.Calendar;

public class Note {
    private String title;
    private String text;
    private String creationDate;
    private String dueDate;

    Note(String title, String text) {
        this.title = title;
        this.text = text;
        this.creationDate = Calendar.getInstance().getTime().toString();
        this.dueDate = "";
    }

    String getTitle() {
        return title;
    }

    void setTitle(String title) {
        this.title = title;
    }

    String getText() {
        return text;
    }

    void setText(String text) {
        this.text = text;
    }

    String getDate() {
        return creationDate;
    }

    String getDueDate() {
        return dueDate;
    }

    void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }
}
