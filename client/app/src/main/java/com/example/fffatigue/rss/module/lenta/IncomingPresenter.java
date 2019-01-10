package com.example.fffatigue.rss.module.lenta;

import com.example.fffatigue.rss.common.IUseCaseCallback;
import com.example.fffatigue.rss.common.UseCaseHandler;
import com.example.fffatigue.rss.model.Notification;

import java.util.List;

public class IncomingPresenter implements ILentaPresenter {
    @Override
    public void setView(ILentaView view) {
        this.view = view;
    }

    //region Private entities
    private ILentaView view;
    private UseCaseHandler useCaseHandler;
    //endregion

    //region Use cases
    private GetIncomingNotifications getIncomingNotifications;
    //endregion


    IncomingPresenter(ILentaView view, UseCaseHandler useCaseHandler, GetIncomingNotifications getIncomingNotifications) {
        this.view = view;
        this.useCaseHandler = useCaseHandler;
        this.getIncomingNotifications = getIncomingNotifications;
    }


    //region IFeedPresenter
    @Override
    public void start() {
        loadIncomingNotification();
    }
    //endregion


    @Override
    public void tapOnNotification(Notification notification) {
        view.openNotificationDetailScreen(notification.getUuid().toString());
    }

    //region Private helpers
    private void loadIncomingNotification(){
        GetIncomingNotifications.RequestValues requestValues = new GetIncomingNotifications.RequestValues(true);
        useCaseHandler.execute(getIncomingNotifications, requestValues, new IUseCaseCallback<GetIncomingNotifications.ResponseValues>() {
            @Override
            public void onSuccess(GetIncomingNotifications.ResponseValues response) {
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