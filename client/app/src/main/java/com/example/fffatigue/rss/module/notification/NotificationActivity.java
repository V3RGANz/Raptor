package com.example.fffatigue.rss.module.notification;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.fffatigue.rss.R;
import com.example.fffatigue.rss.common.UseCaseHandler;
import com.example.fffatigue.rss.data.NotificationFakeDataSource;
import com.example.fffatigue.rss.data.NotificationsRepository;

import java.util.UUID;

public class NotificationActivity extends AppCompatActivity {
    public static final String EXTRA_NOTIFICATION_ID ="EXTRA_NO_ID";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        FragmentManager fragmentManager = getSupportFragmentManager();

        NotificationFragment notificationFragment = (NotificationFragment) fragmentManager.findFragmentById(R.id.fragment_container);

        if ( null == notificationFragment ){
            notificationFragment = NotificationFragment.newInstance();
            fragmentManager.beginTransaction()
                    .add(R.id.fragment_container, notificationFragment)
                    .commit();
        }
        String notificationIdString = getIntent().getStringExtra(EXTRA_NOTIFICATION_ID);
        UUID notificationId = UUID.fromString(notificationIdString);
        UseCaseHandler useCaseHandler = UseCaseHandler.getInstance();

        NotificationFakeDataSource notificationFakeDataSource = NotificationFakeDataSource.getInstance();
        NotificationsRepository notificationsRepository = new NotificationsRepository(notificationFakeDataSource);
        GetNotification getNotification = new GetNotification(notificationsRepository);

        INotificationPresenter presenter = new NotificationPresenter(notificationFragment, useCaseHandler, notificationId, getNotification);
        notificationFragment.setPresenter(presenter);
    }
}
