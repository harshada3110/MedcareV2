package com.google.sites.medcare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Home extends AppCompatActivity {

    private BottomNavigationView mMainNav;
    private FrameLayout mMainFrame;
    private HomeFragment homeFragment;
    private NewsFragment newsFragment;
    private NotificationFragment notificationFragment;
    private ReminderFragment reminderFragment;
    private UserFragment userFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mMainFrame = findViewById(R.id.main_frame);
        mMainNav = findViewById(R.id.main_nav);

        homeFragment = new HomeFragment();
        newsFragment = new NewsFragment();
        notificationFragment = new NotificationFragment();
        reminderFragment = new ReminderFragment();
        userFragment = new UserFragment();

        mMainNav.setSelectedItemId(R.id.home_menu);
        setFragment(homeFragment);

        mMainNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()) {
                    case R.id.news_menu:
                        setFragment(newsFragment);
                        return true;

                    case R.id.rem_menu:
                        setFragment(reminderFragment);
                        return true;

                    case R.id.home_menu:
                        setFragment(homeFragment);
                        return true;

                    case R.id.notif_menu:
                        setFragment(notificationFragment);
                        return true;

                    case R.id.user_menu:
                        setFragment(userFragment);
                        return true;

                    default:
                        return false;
                }
            }
        });
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame, fragment);
        fragmentTransaction.commit();
    }
}
