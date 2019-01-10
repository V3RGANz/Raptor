package com.example.fffatigue.rss.model;

import java.util.Date;
import java.util.UUID;

public class Notification {
    private UUID uuid;

    private String title;
    private String summary;
    private String fullText;

    private Date date;

    private String to;
    private String from;

    private int imageLink;

    public Notification(String title, String summary, String fullText, Date date, String to, String from) {
        this.uuid = UUID.randomUUID();
        this.title = title;
        this.summary = summary;
        this.fullText = fullText;
        this.date = date;
        this.to = to;
        this.from = from;
    }

    public int getImageLink() {
        return imageLink;
    }

    public void setImageLink(int imageLink) {
        this.imageLink = imageLink;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getFullText() {
        return fullText;
    }

    public void setFullText(String fullText) {
        this.fullText = fullText;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }
}
