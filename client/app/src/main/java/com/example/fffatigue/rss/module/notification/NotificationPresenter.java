package com.example.fffatigue.rss.module.notification;

import com.example.fffatigue.rss.common.IUseCaseCallback;
import com.example.fffatigue.rss.common.UseCaseHandler;
import com.example.fffatigue.rss.model.Notification;

import java.util.UUID;

public class NotificationPresenter implements INotificationPresenter{

    private final INotificationView view;
    private final UseCaseHandler useCaseHandler;
    private final UUID notificationId;

    private final GetNotification getNotification;

    public NotificationPresenter(INotificationView view, UseCaseHandler useCaseHandler,
                                 UUID notificationId, GetNotification getNotification) {
        this.view = view;
        this.useCaseHandler = useCaseHandler;
        this.notificationId = notificationId;
        this.getNotification = getNotification;
    }

    @Override
    public void start() {
        loadNotification();
    }
    private void loadNotification(){
        GetNotification.RequestValues requestValues = new GetNotification.RequestValues(notificationId);
        useCaseHandler.execute(getNotification, requestValues, new IUseCaseCallback<GetNotification.ResponseValues>() {
            @Override
            public void onSuccess(GetNotification.ResponseValues response) {
                Notification notification = response.getNotification();
                processNotification(notification);
            }
            @Override
            public void onError() {

            }
        });
    }
    private void processNotification(Notification notification){
        if ( null == notification ){
            return;
        }
        String title = notification.getTitle();
        String fullText = notification.getFullText();
        String date = notification.getDate().toString();
        String to = notification.getTo();
        String from = notification.getFrom();
        int image = notification.getImageLink();
        if ( null != title ){
            view.setTitle(title);
        }
        if( null != fullText ){
            view.setFullText(fullText);
        }
        view.setDate(date);
        view.setTo(to);
        view.setFrom(from);
        view.setImageLink(image);
    }
}