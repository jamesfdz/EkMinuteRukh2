package com.codingwithjames.ekminuteruk;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private Page_Adapter adapter;
    private InterstitialAd mInterstitialAd;
    private int mAdCounter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MobileAds.initialize(this, "ca-app-pub-5397309444919460~6110496443");

        mViewPager = findViewById(R.id.viewPager);
        adapter = new Page_Adapter(getSupportFragmentManager());
        mViewPager.setAdapter(adapter);
        mViewPager.setCurrentItem(0);

        Date currentTime = Calendar.getInstance().getTime();

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

        Log.i("System Time", currentTime.toString());

    }
}
