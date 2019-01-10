package com.example.fffatigue.rss.fakestore;

import com.example.fffatigue.rss.R;
import com.example.fffatigue.rss.model.Notification;

import java.util.ArrayList;
import java.util.Date;

public class FakeNotificationStore {

    public static ArrayList<Notification> incomingNotifications(){
        Notification notification1 = new Notification("Выпей таблетки", "Нужно выпить красную и синюю таблетки, которые...",
                "Нужно выпить красную и синюю таблетки, которые лежат в верхнем ящике стола", new Date(),
                "Женя", "Кристина");

        Notification notification2 = new Notification("Поешь", "Съешь борщ, он стоит в холодильнике на верхней...",
                "Съешь борщ, он стоит в холодильнике на верхней полочке, не забудь разогреть", new Date(),
                "Женя", "Чмиль");
        Notification notification3= new Notification("Поешь", "Съешь яблоки, они лежат в мешке под...",
                "Съешь яблоки, они лежат в мешке под твоей кроватью, сразу будишь сильный", new Date(),
                "Женя", "Чмиль");
        notification1.setImageLink(R.drawable.imagee);
        notification2.setImageLink(R.drawable.image);
        notification3.setImageLink(R.drawable.img);

        ArrayList<Notification> notifications = new ArrayList<>();
        notifications.add(notification1);
        notifications.add(notification2);
        notifications.add(notification3);
        notifications.add(notification1);
        notifications.add(notification2);
        notifications.add(notification3);
        notifications.add(notification1);
        notifications.add(notification2);
        notifications.add(notification3);
        notifications.add(notification1);
        notifications.add(notification2);
        notifications.add(notification3);
        notifications.add(notification1);
        notifications.add(notification2);
        //notifications.add(notification3);
        return notifications;
    }
    public static ArrayList<Notification> outgoingNotifications(){
        Notification notification1 = new Notification("Экраны!!!!", "Женя, сделай, зкраны...",
                "Женя, сделай, зкраны, что же ты так себя ведешь", new Date(),
                "Женя", "Кристина");

        Notification notification2 = new Notification("Сыр", "Женя, не забудь купить сыр...",
                "Женя, не забудь купить сыр", new Date(),
                "Женя", "Кристина");
        Notification notification3= new Notification("Лекарство", "Женя не забудь принять...",
                "Женя, не забудь принять лекарство", new Date(),
                "Женя", "Чмиль");

        notification1.setImageLink(R.drawable.img);
        notification2.setImageLink(R.drawable.imagee);
        notification3.setImageLink(R.drawable.image);

        ArrayList<Notification> notifications = new ArrayList<>();
        notifications.add(notification1);
        notifications.add(notification2);
        notifications.add(notification3);
        notifications.add(notification1);
        notifications.add(notification2);
        notifications.add(notification3);
        notifications.add(notification1);
        notifications.add(notification2);
        notifications.add(notification3);
        return notifications;
    }
}
