package ru.nsu.Raptor.Server.notifications;

import ru.nsu.Raptor.Server.Exceptions.IllegalOperationException;

public class IncomingNotificationController extends NotificationController {

    public void markReadNotification(int userID, int notificationID) throws IllegalOperationException {
        UtilNotification notification = notificationsMap.get( userID ).get( notificationID );
        if (notification == null) {
            throw new IllegalOperationException( "Notification not found", 404 );
        }
        notification.setRead();
    }

    //region Singleton

    private static IncomingNotificationController ourInstance = new IncomingNotificationController();

    public static IncomingNotificationController getInstance() {
        return ourInstance;
    }

    private IncomingNotificationController() {
    }

    //endregion
}
