package com.example.fffatigue.rss.data;

import com.example.fffatigue.rss.fakestore.FakeNotificationStore;
import com.example.fffatigue.rss.model.Notification;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class NotificationFakeDataSource implements INotificationDataSource {
    private static NotificationFakeDataSource sharedInstance;
    private List<Notification> incomingNotifications = FakeNotificationStore.incomingNotifications();
    private List<Notification> outgoingNotifications = FakeNotificationStore.outgoingNotifications();

    public static NotificationFakeDataSource getInstance(){
        if ( null == sharedInstance){
            sharedInstance = new NotificationFakeDataSource();
        }
        return sharedInstance;
    }

    private NotificationFakeDataSource() {
    }

    @Override
    public void getOutgoingNotifications(ILoadOutgoingNotificationsCallback callback) {
        callback.onOutgoingNotificationsLoaded(outgoingNotifications);

    }

    @Override
    public void getOutgoingNotification(UUID notificationId, ILoadOutgoingNotificationCallback callback) {
        Notification notification= outgoingNotifications.stream()
                .filter(a->a.getUuid().compareTo(notificationId) == 0)
                .findFirst()
                .get();
        callback.onOutgoingNotificationLoaded(notification);
    }

    @Override
    public void getIncomingNotifications(ILoadIncomingNotificationsCallback callback) {
        callback.onIncomingNotificationsLoaded(incomingNotifications);
    }

    @Override
    public void getIncomingNotification(UUID notificationId, ILoadIncomingNotificationCallback callback) {
        Notification notification= incomingNotifications.stream()
                .filter(a->a.getUuid().compareTo(notificationId) == 0)
                .findFirst()
                .get();
        callback.onIncomingNotificationLoaded(notification);
    }

    @Override
    public void getNotification(UUID notificationId, ILoadNotificationCallback callback) {
        List<Notification> notifications = new ArrayList<>(incomingNotifications);
        notifications.addAll(outgoingNotifications);
        Notification notification= notifications.stream()
                .filter(a->a.getUuid().compareTo(notificationId) == 0)
                .findFirst()
                .get();
        callback.onNotificationLoaded(notification);
    }
}
