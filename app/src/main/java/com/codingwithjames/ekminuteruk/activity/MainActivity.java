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
    private InterstitialAd mInterstitialAd;
    private int mAdCounter = 0;
    private static final String TAG = "MainActivity";
    private DateChange_BR mDateChangeReceiver = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initializing Admobs
        MobileAds.initialize(this, "ca-app-pub-5397309444919460~6110496443");

        //casting all views
        mViewPager = findViewById(R.id.viewPager);
        adapter = new Page_Adapter(getSupportFragmentManager());
        mViewPager.setAdapter(adapter);
        mViewPager.setCurrentItem(0);

        //creating Interstitial admob
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                super.onAdClosed();
                mViewPager.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        });

        //setting page change listener to load Interstitial admob
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                mAdCounter++;
                if (mAdCounter == 5) {
                    mAdCounter = 0;
                    AdRequest request = new AdRequest.Builder()
                            .addTestDevice("CCFA8FF0F89AD93CC8A593BAE17AF056")
                            .build();
                    mInterstitialAd.loadAd(request);
                    if (mInterstitialAd.isLoaded()) {
                        mInterstitialAd.show();
                    } else {
                        Log.d("TAG", "The interstitial wasn't loaded yet.");
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

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
