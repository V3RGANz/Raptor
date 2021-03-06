package ru.nsu.Raptor.Server.notifications;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

public class UtilNotification {

    //region Fields

    private String message;
    private String date;
    private String sender;
    private String title;
    private boolean read = false;
    private final int id;
    static private AtomicInteger MAX_NOTIFICATION_ID = new AtomicInteger( 0 );
    private boolean removed = false;


    //endregion


    public UtilNotification(Notification notification) {
        id = MAX_NOTIFICATION_ID.getAndIncrement();
        this.message = notification.message;
        this.date = notification.date;
        this.sender = notification.sender;
        this.title = notification.title;
    }

    //region Getters

    synchronized Notification getNotification() {
        Notification notification = new Notification();
        notification.date = date;
        notification.message = message;
        notification.sender = sender;
        notification.read = read;
        notification.id = id;
        notification.title = title;
        return notification;
    }

    int getID() {
        return id;
    }

    //endregion

    //region Setters

    synchronized void setRead() {
        read = true;
    }

    synchronized void setRemoved() {
        removed = true;
    }

    //endregion

    synchronized boolean isRemoved() {
        return removed;
    }

}
