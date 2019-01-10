package com.example.fffatigue.rss.module.lenta;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fffatigue.rss.R;
import com.example.fffatigue.rss.model.Notification;
import com.example.fffatigue.rss.module.notification.NotificationActivity;

import java.util.List;

public class IncomingNotificationsFragment extends Fragment implements ILentaView {

    public static IncomingNotificationsFragment newInstance(){
        return new IncomingNotificationsFragment();
    }

    //region UI Outlets
    private RecyclerView incomingRecyclerView;
    //endregion

    private ILentaPresenter presenter;


    //region LifeCycle
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_incoming_notifications, container, false);
        configurationView(view);
        return view;
    }

    private void  configurationView(View view ){
        incomingRecyclerView = view.findViewById(R.id.incoming_recycler_view);
        incomingRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.start();
    }
    //endregion

    //region IFeedView
    @Override
    public void setPresenter(ILentaPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showNotifications(List<Notification> notifications) {
        NotificationAdapter notificationAdapter = new NotificationAdapter(notifications);
        incomingRecyclerView.setAdapter(notificationAdapter);
    }

    @Override
    public void openNotificationDetailScreen(String notificationId) {
        Intent intent = new Intent(getContext(), NotificationActivity.class);
        intent.putExtra(NotificationActivity.EXTRA_NOTIFICATION_ID, notificationId);
        startActivity(intent);
    }

    public void setVisible(int visible) {
        incomingRecyclerView.setVisibility(visible);
    }

    //endregion

    //region NotificationHolder
    private  class  NotificationHolder extends RecyclerView.ViewHolder implements  View.OnClickListener{
        private TextView titleTextView;
        private TextView summaryTextView;
        private TextView nameTextView;
        private ImageView imageView;
        private Notification notification;

        NotificationHolder(LayoutInflater inflater, ViewGroup parent ){
            super(inflater.inflate(R.layout.notification_item, parent, false));
            itemView.setOnClickListener(this);
            titleTextView = itemView.findViewById(R.id.notification_title);
            summaryTextView = itemView.findViewById(R.id.notification_summary);
            nameTextView = itemView.findViewById(R.id.name_friend_sender);
            imageView = itemView.findViewById(R.id.avatar_friend_sender_image);
        }

        void bind( Notification notification ){
            this.notification = notification;
            nameTextView.setText(notification.getFrom());
            titleTextView.setText(notification.getTitle());
            summaryTextView.setText(notification.getSummary());
            imageView.setImageResource(notification.getImageLink());
        }

        @Override
        public void onClick(View v) {
            presenter.tapOnNotification(notification);
        }
    }
    //endregion

    //region NotificationAdapter
    private  class  NotificationAdapter extends RecyclerView.Adapter<NotificationHolder> {
        private List<Notification> notifications;

        NotificationAdapter(List<Notification> notifications) {
            this.notifications = notifications;
        }

        @NonNull
        @Override
        public NotificationHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            return new NotificationHolder(inflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull NotificationHolder notificationHolder, int i) {
            Notification notification = notifications.get(i);
            notificationHolder.bind(notification);
        }

        @Override
        public int getItemCount() {
            return notifications.size();
        }
    }
    //endregion

}
