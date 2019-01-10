package com.example.fffatigue.rss.module.lenta;

import com.example.fffatigue.rss.model.Notification;
import com.example.fffatigue.rss.module.base.IBaseView;

import java.util.List;

public interface ILentaView extends IBaseView<ILentaPresenter> {
    void showNotifications(List<Notification> notifications);

    void openNotificationDetailScreen(String notificationId);
}