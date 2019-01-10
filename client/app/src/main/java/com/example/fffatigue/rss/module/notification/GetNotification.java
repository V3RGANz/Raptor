package com.example.fffatigue.rss.module.notification;

import com.example.fffatigue.rss.common.UseCase;
import com.example.fffatigue.rss.data.NotificationsRepository;
import com.example.fffatigue.rss.model.Notification;

import java.util.UUID;

public class GetNotification extends UseCase<GetNotification.RequestValues, GetNotification.ResponseValues> {
    //region Private entities
    private final NotificationsRepository notificationsRepository;
    //endregion

    //region Initialization

    public GetNotification(NotificationsRepository notificationsRepository) {
        this.notificationsRepository = notificationsRepository;
    }

    //endregion

    //region UseCase
    @Override
    protected void executeUseCase(RequestValues requestValues) {
        UUID notificationId = requestValues.getNotificationId();
        notificationsRepository.getNotification(notificationId, notification -> {
            ResponseValues responseValues = new ResponseValues(notification);
            getUseCaseCallback().onSuccess(responseValues);
        });
    }
    //endregion

    //region Request and Response
    static final class RequestValues implements  UseCase.RequestValues{
        private final UUID notificationId;

        RequestValues(UUID notificationId) {
            this.notificationId = notificationId;
        }

        UUID getNotificationId() {
            return notificationId;
        }
    }
    public static final class ResponseValues implements  UseCase.ResponseValues{
        private final Notification notification;

        ResponseValues(Notification notification) {
            this.notification = notification;
        }

        public Notification getNotification() {
            return notification;
        }
    }
    //endregion
}
