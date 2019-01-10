package com.example.fffatigue.rss.module.lenta;

import com.example.fffatigue.rss.common.IUseCaseCallback;
import com.example.fffatigue.rss.common.UseCaseHandler;
import com.example.fffatigue.rss.model.Notification;

import java.util.List;

public class OutgoingPresenter implements ILentaPresenter {
    @Override
    public void setView(ILentaView view) {
        this.view = view;
    }

    //region Private entities
    private ILentaView view;
    private UseCaseHandler useCaseHandler;
    //endregion

    //region Use cases
    private GetOutgoingNotifications getOutgoingNotifications;
    //endregion


    public OutgoingPresenter(ILentaView view, UseCaseHandler useCaseHandler, GetOutgoingNotifications getOutgoingNotifications) {
        this.view = view;
        this.useCaseHandler = useCaseHandler;
        this.getOutgoingNotifications = getOutgoingNotifications;
    }

    //region IFeedPresenter
    @Override
    public void start() {
        loadOutgoingNotification();
    }
    //endregion


    @Override
    public void tapOnNotification(Notification notification) {
        view.openNotificationDetailScreen(notification.getUuid().toString());
    }

    //region Private helpers
    private void loadOutgoingNotification(){
        GetOutgoingNotifications.RequestValues requestValues = new GetOutgoingNotifications.RequestValues(true);
        useCaseHandler.execute(getOutgoingNotifications, requestValues, new IUseCaseCallback<GetOutgoingNotifications.ResponseValues>() {
            @Override
            public void onSuccess(GetOutgoingNotifications.ResponseValues response) {
                List<Notification> notifications = response.getNotifications();
                processNotifications(notifications);
            }

            @Override
            public void onError() {

            }
        });
    }
    private void processNotifications(List<Notification> notifications ){
        view.showNotifications(notifications);
    }
    //endregion
}