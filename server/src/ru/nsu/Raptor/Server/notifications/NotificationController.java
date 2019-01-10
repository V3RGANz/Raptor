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
                notificationList.add( it.next().getValue().getNotification() );
            }
        }
        return notificationList;
    }

    public void addNotification(int userID, UtilNotification notification) {
        if(notificationsMap.get(userID) == null){
            notificationsMap.put( userID,new ConcurrentHashMap<>(  ) );
        }
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
        notificationsMap.put( 0, new ConcurrentHashMap<>() );
        Notification notification = new Notification();
        notification.title = "First notification";
        notification.date = new GregorianCalendar( 2014, 12, 21 ).toString();
        notification.message = "To do somethingTo do somethingTo do somethingTo do somethingTo do somethingTo do somethingTo do somethingTo do something";
        notification.sender = "friend";
        notification.id = 0;
        notification.read = false;
        notificationsMap.get( 0 ).put( 0, new UtilNotification( notification ) );
    }

    //endregion
}
