package com.example.fffatigue.rss.module.lenta;

import android.graphics.Color;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.example.fffatigue.rss.R;
import com.example.fffatigue.rss.common.UseCaseHandler;
import com.example.fffatigue.rss.data.NotificationFakeDataSource;
import com.example.fffatigue.rss.data.NotificationsRepository;

public class LentaActivity extends AppCompatActivity {

    private RelativeLayout incomingLayout;
    private RelativeLayout outgoingLayout;
    private RelativeLayout friendsLayout;
    private IncomingNotificationsFragment incomingNotificationsFragment;
    private OutgoingNotificationsFragment outgoingNotificationsFragment;
    private ILentaPresenter incomingPresenter;
    private ILentaPresenter outgoingPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lenta);

        FragmentManager fragmentManager = getSupportFragmentManager();
        incomingNotificationsFragment = (IncomingNotificationsFragment) fragmentManager.findFragmentById(R.id.fragment_container);

        if ( null == incomingNotificationsFragment){
            incomingNotificationsFragment = IncomingNotificationsFragment.newInstance();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.fragment_container, incomingNotificationsFragment)
                    .commit();
        }

        UseCaseHandler useCaseHandler = UseCaseHandler.getInstance();
        NotificationFakeDataSource notificationFakeDataSource = NotificationFakeDataSource.getInstance();
        NotificationsRepository notificationsRepository = new NotificationsRepository(notificationFakeDataSource);

        GetIncomingNotifications getIncomingNotifications = new GetIncomingNotifications(notificationsRepository);
        GetOutgoingNotifications getOutgoingNotifications = new GetOutgoingNotifications(notificationsRepository);


        outgoingNotificationsFragment = OutgoingNotificationsFragment.newInstance();
        outgoingPresenter = new OutgoingPresenter(outgoingNotificationsFragment, useCaseHandler, getOutgoingNotifications);
        outgoingNotificationsFragment.setPresenter(outgoingPresenter);


        incomingPresenter = new IncomingPresenter(incomingNotificationsFragment, useCaseHandler, getIncomingNotifications);
        incomingNotificationsFragment.setPresenter(incomingPresenter);

        configureView();
    }

    private void configureView() {
        incomingLayout = findViewById(R.id.layout_incoming);
        outgoingLayout = findViewById(R.id.layout_outgoing);
        friendsLayout = findViewById(R.id.layout_friends);

        Button incomingButton = findViewById(R.id.button_incoming);
        incomingButton.setOnClickListener(onIncomingButtonClickListener);

        Button outgoingButton = findViewById(R.id.button_outgoing);
        outgoingButton.setOnClickListener(onOutgoingButtonClickListener);

        Button friendsButton = findViewById(R.id.button_friends);
        friendsButton.setOnClickListener(onFriendsButtonClickListener);
        incomingLayout.setBackgroundColor(Color.BLACK);
    }

    //region private outlets listeners
    private View.OnClickListener onIncomingButtonClickListener = view -> {
        switch (view.getId()){
            case R.id.button_incoming:
                incomingLayout.setBackgroundColor(Color.BLACK);
                outgoingLayout.setBackgroundColor(Color.WHITE);
                friendsLayout.setBackgroundColor(Color.WHITE);
                if ( null != outgoingNotificationsFragment){
                    outgoingNotificationsFragment.setVisible(View.GONE);
                }
                getSupportFragmentManager().popBackStack();
                incomingNotificationsFragment = IncomingNotificationsFragment.newInstance();
                getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, incomingNotificationsFragment).addToBackStack(null).commit();
                incomingPresenter.setView(incomingNotificationsFragment);
                incomingNotificationsFragment.setPresenter(incomingPresenter);

                break;
        }
    };

    private View.OnClickListener onOutgoingButtonClickListener = view -> {
        switch (view.getId()){
            case R.id.button_outgoing:
                incomingLayout.setBackgroundColor(Color.WHITE);
                outgoingLayout.setBackgroundColor(Color.BLACK);
                friendsLayout.setBackgroundColor(Color.WHITE);

                incomingNotificationsFragment.setVisible(View.GONE);
                outgoingNotificationsFragment = OutgoingNotificationsFragment.newInstance();
                getSupportFragmentManager().popBackStack();
                getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, outgoingNotificationsFragment).addToBackStack(null).commit();

                outgoingPresenter.setView(outgoingNotificationsFragment);
                outgoingNotificationsFragment.setPresenter(outgoingPresenter);

                break;
        }
    };
    private View.OnClickListener onFriendsButtonClickListener = view -> {
        switch (view.getId()){
            case R.id.button_friends:
                Log.d("MY_TAG", "On Click FRIENDS");
                incomingLayout.setBackgroundColor(Color.WHITE);
                outgoingLayout.setBackgroundColor(Color.WHITE);
                friendsLayout.setBackgroundColor(Color.BLACK);
                break;
        }
    };
    //endregion
}
