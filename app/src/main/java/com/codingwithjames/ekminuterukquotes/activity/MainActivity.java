package com.codingwithjames.ekminuterukquotes.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.codingwithjames.ekminuterukquotes.adapters.Page_Adapter;
import com.codingwithjames.ekminuterukquotes.R;

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
}
