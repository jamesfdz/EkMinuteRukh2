package com.codingwithjames.ekminuteruk.activity;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.codingwithjames.ekminuteruk.adapters.Page_Adapter;
import com.codingwithjames.ekminuteruk.R;
import com.codingwithjames.ekminuteruk.broadcast_receivers.DateChange_BR;
import com.codingwithjames.ekminuteruk.services.ForegroundService;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private static final String CHANNEL_ID = "MAIN_CH_ID";
    private ViewPager mViewPager;
    private Page_Adapter adapter;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //casting all views
        mViewPager = findViewById(R.id.viewPager);
        adapter = new Page_Adapter(getSupportFragmentManager());
        mViewPager.setAdapter(adapter);
        mViewPager.setCurrentItem(0);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Intent foregroundServiceIntent = new Intent(this, ForegroundService.class);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(foregroundServiceIntent);
        } else {
            startService(foregroundServiceIntent);
        }
    }
}
