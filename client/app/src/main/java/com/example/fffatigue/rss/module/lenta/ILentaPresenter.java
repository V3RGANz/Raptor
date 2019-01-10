package com.example.fffatigue.rss.module.lenta;

import com.example.fffatigue.rss.model.Notification;
import com.example.fffatigue.rss.module.base.IBasePresenter;

public interface ILentaPresenter extends IBasePresenter {
    void tapOnNotification(Notification notification);

    void setView(ILentaView view);
}