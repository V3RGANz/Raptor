package com.example.fffatigue.rss.data;

import com.example.fffatigue.rss.model.Notification;

import java.util.List;
import java.util.UUID;

public interface INotificationDataSource {
    interface ILoadIncomingNotificationsCallback {
        void onIncomingNotificationsLoaded(List<Notification> notifications);
    }
    interface ILoadIncomingNotificationCallback {
        void onIncomingNotificationLoaded(Notification notification);
    }
    interface ILoadOutgoingNotificationsCallback {
        void onOutgoingNotificationsLoaded(List<Notification> notifications);
    }
    interface ILoadOutgoingNotificationCallback {
        void onOutgoingNotificationLoaded(Notification notification);
    }
    interface ILoadNotificationCallback {
        void onNotificationLoaded(Notification notification);
    }
    void getOutgoingNotifications(ILoadOutgoingNotificationsCallback callback);

    void getOutgoingNotification(UUID notificationId, ILoadOutgoingNotificationCallback callback);

    void getIncomingNotifications(ILoadIncomingNotificationsCallback callback);

    void getIncomingNotification(UUID notificationId, ILoadIncomingNotificationCallback callback);

    void getNotification(UUID notificationId, ILoadNotificationCallback callback);

}
