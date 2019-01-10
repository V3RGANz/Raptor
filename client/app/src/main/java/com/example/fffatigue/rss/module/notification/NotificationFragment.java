package com.example.fffatigue.rss.module.notification;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fffatigue.rss.R;
import com.example.fffatigue.rss.model.Notification;

public class NotificationFragment  extends Fragment implements INotificationView {

    private INotificationPresenter presenter;

    //region UI Outlets
    private TextView notificationTitleTextView;
    private ImageView notificationImageView;
    private TextView notificationDateTextView;
    private TextView notificationFullTextView;
    private TextView notificationToTextView;
    private TextView notificationFromTextView;
    //endregion

    //region Private entities
    private Notification notification;
    //endregion

    public static NotificationFragment newInstance(){
        return new NotificationFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notification, container, false);
        configurationView(view);
        return view;
    }

    private void  configurationView(View view ){
        notificationTitleTextView = view.findViewById(R.id.notification_title);
        notificationImageView = view.findViewById(R.id.from_image);
        notificationDateTextView = view.findViewById(R.id.notification_date);
        notificationFullTextView = view.findViewById(R.id.notification_full_text);
        notificationToTextView = view.findViewById(R.id.notification_to);
        notificationFromTextView = view.findViewById(R.id.notification_from);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.start();
    }

    @Override
    public void setPresenter(INotificationPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void setTitle(String title) {
        this.notificationTitleTextView.setText(title);
    }

    @Override
    public void setFullText(String fullText) {
        this.notificationFullTextView.setText(fullText);
    }

    @Override
    public void setDate(String date) {
        this.notificationDateTextView.setText(date);
    }

    @Override
    public void setTo(String to) {
        this.notificationToTextView.setText(to);

    }

    @Override
    public void setImageLink(int imageLink) {
        this.notificationImageView.setImageResource(imageLink);
    }

    @Override
    public void setFrom(String from) {
        this.notificationFromTextView.setText(from);
    }
}

