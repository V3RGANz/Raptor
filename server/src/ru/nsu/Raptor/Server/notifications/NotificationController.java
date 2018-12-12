package ru.nsu.Raptor.Server.notifications;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class NotificationController {
    Map<Integer, Map<Integer, UtilNotification>> notificationsMap = new ConcurrentHashMap<>();

    public List<Notification> getNotifications(int userID) {
        List<Notification> notificationList = new ArrayList<>();
        Map<Integer, UtilNotification> notifications = notificationsMap.get( userID );
        if (notifications != null) {
            Iterator<Map.Entry<Integer, UtilNotification>> it = notifications.entrySet().iterator();
            while (it.hasNext()) {
                if (!it.next().getValue().isRemoved()) {
                    notificationList.add( it.next().getValue().getNotification() );
                } else {
                    it.remove();
                }
            }
        } else {
            throw new IllegalArgumentException();
        }
        return notificationList;
    }

    public void addNotification(int userID, UtilNotification notification) {
        notificationsMap.get( userID ).put( notification.getID(), notification );
    }

    public void deleteNotification(int userID, int notificationID) {
        UtilNotification notification = notificationsMap.get( userID ).remove( notificationID );
        if (notification != null) {
            notification.setRemoved();
        }
    }

    //region Singleton

    private static NotificationController ourInstance = new NotificationController();

    public static NotificationController getInstance() {
        return ourInstance;
    }

    NotificationController() {
    }

    //endregion
}
