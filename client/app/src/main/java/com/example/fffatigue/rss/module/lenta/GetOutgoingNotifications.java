package com.example.fffatigue.rss.module.lenta;

import com.example.fffatigue.rss.common.UseCase;
import com.example.fffatigue.rss.data.NotificationsRepository;
import com.example.fffatigue.rss.model.Notification;

import java.util.List;

public class GetOutgoingNotifications extends UseCase<GetOutgoingNotifications.RequestValues, GetOutgoingNotifications.ResponseValues> {

    //region Private entities
    private final NotificationsRepository notificationsRepository;
    //endregion

    //region Initialization

    public GetOutgoingNotifications(NotificationsRepository notificationsRepository) {
        this.notificationsRepository = notificationsRepository;
    }

    //endregion

    //region UseCase
    @Override
    protected void executeUseCase(GetOutgoingNotifications.RequestValues requestValues) {
        notificationsRepository.getOutgoingNotifications(notifications -> {
            GetOutgoingNotifications.ResponseValues responseValues = new GetOutgoingNotifications.ResponseValues(notifications);
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