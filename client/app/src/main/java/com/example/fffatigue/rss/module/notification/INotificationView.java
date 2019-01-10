package com.example.fffatigue.rss.module.notification;

import com.example.fffatigue.rss.module.base.IBaseView;

public interface INotificationView extends IBaseView<INotificationPresenter> {
    void setTitle(String title);
    void setFullText(String fullText);
    void setDate(String date);
    void setTo(String to);
    void setFrom(String from);
    void setImageLink(int imageLink);
}