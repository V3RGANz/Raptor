package com.example.fffatigue.rss.data;

import java.util.UUID;

public class NotificationsRepository implements INotificationDataSource{

    private INotificationDataSource notificationDataSource;

    public NotificationsRepository(INotificationDataSource notificationDataSource) {
        this.notificationDataSource = notificationDataSource;
    }

    @Override
    public void getOutgoingNotifications(ILoadOutgoingNotificationsCallback callback) {
        if ( null != callback ){
            notificationDataSource.getOutgoingNotifications(callback);
        }
    }

    @Override
    public void getOutgoingNotification(UUID notificationId, ILoadOutgoingNotificationCallback callback) {
        if ( null != callback ){
            notificationDataSource.getOutgoingNotification(notificationId, callback);
        }
    }

    @Override
    public void getIncomingNotifications(ILoadIncomingNotificationsCallback callback) {
        if ( null != callback ){
            notificationDataSource.getIncomingNotifications(callback);
        }

    }

    @Override
    public void getIncomingNotification(UUID notificationId, ILoadIncomingNotificationCallback callback) {
        if ( null != callback ){
            notificationDataSource.getIncomingNotification(notificationId, callback);
        }
    }

    @Override
    public void getNotification(UUID notificationId, ILoadNotificationCallback callback) {
        notificationDataSource.getNotification(notificationId, callback);
    }
}
