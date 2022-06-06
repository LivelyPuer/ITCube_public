package com.livlypuer.popava.models;

import java.time.LocalDate;

public class EventModel extends BaseModel implements Comparable<EventModel>{
    public static String PATTERN = "yyyy-MM-dd";
    private String title;
    private LocalDate date;
    private String description;
    private boolean isOpen;

    public EventModel(long id, String title, LocalDate date, String description, boolean isOpen) {
        super(id);
        setTitle(title);
        setDate(date);
        setOpen(isOpen);
        setDescription(description);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int compareTo(EventModel o) {
        return this.date.compareTo(o.date);
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }
}

