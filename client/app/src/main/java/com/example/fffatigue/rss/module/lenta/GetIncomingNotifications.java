package com.example.fffatigue.rss.module.lenta;

import com.example.fffatigue.rss.common.UseCase;
import com.example.fffatigue.rss.data.NotificationsRepository;
import com.example.fffatigue.rss.model.Notification;

import java.util.List;

public class GetIncomingNotifications extends UseCase<GetIncomingNotifications.RequestValues, GetIncomingNotifications.ResponseValues> {

    //region Private entities
    private final NotificationsRepository notificationsRepository;
    //endregion

    //region Initialization

    GetIncomingNotifications(NotificationsRepository notificationsRepository) {
        this.notificationsRepository = notificationsRepository;
    }

    //endregion

    //region UseCase
    @Override
    protected void executeUseCase(GetIncomingNotifications.RequestValues requestValues) {
        notificationsRepository.getIncomingNotifications(notifications -> {
            GetIncomingNotifications.ResponseValues responseValues = new GetIncomingNotifications.ResponseValues(notifications);
            getUseCaseCallback().onSuccess(responseValues);

        });
    }
    //endregion

    //region Request and Response
    public static final class RequestValues implements UseCase.RequestValues {
        private boolean fetchFromFakeStore;

        RequestValues(boolean fetchFromFakeStore) {
            this.fetchFromFakeStore = fetchFromFakeStore;
        }

        public boolean isFetchFromFakeStore() {
            return fetchFromFakeStore;
        }
    }

    static final class ResponseValues implements UseCase.ResponseValues {
        private final List<Notification> notifications;

        ResponseValues(List<Notification> notifications) {
            this.notifications = notifications;
        }

        List<Notification> getNotifications() {
            return notifications;
        }
    }
//endregion
}