package com.codingwithjames.ekminuterukquotes.services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.codingwithjames.ekminuterukquotes.broadcast_receivers.DateChange_BR;

public class ForegroundService extends Service {

    private static final String TAG = "ForegroundService";
    private DateChange_BR mDateChangeReceiver = null;

    public ForegroundService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (Build.VERSION.SDK_INT >= 26) {

            //create notification for starting foreground services
            String CHANNEL_ID = "foreground_service_channel";
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                    "Channel for foreground service",
                    NotificationManager.IMPORTANCE_DEFAULT);

            ((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE)).createNotificationChannel(channel);

            Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setContentTitle("Ek Minute Ruk")
                    .setContentText("Please do not destroy this notification, some important " +
                            "background service is going on").build();

            startForeground(1, notification);

            // Create an IntentFilter instance.
            IntentFilter intentFilter = new IntentFilter();

            // Add network connectivity change action.
            intentFilter.addAction(Intent.ACTION_SCREEN_ON);
            intentFilter.addAction(Intent.ACTION_DATE_CHANGED);

            // Set broadcast receiver priority.
            intentFilter.setPriority(100);

            // Create a network change broadcast receiver.
            mDateChangeReceiver = new DateChange_BR();

            // Register the broadcast receiver with the intent filter object.
            registerReceiver(mDateChangeReceiver, intentFilter);

            Log.d(TAG, "Service onCreate: screenOnOffReceiver is registered.");
        } else {
            // Create an IntentFilter instance.
            IntentFilter intentFilter = new IntentFilter();

            // Add network connectivity change action.
            intentFilter.addAction(Intent.ACTION_SCREEN_ON);
            intentFilter.addAction(Intent.ACTION_DATE_CHANGED);

            // Set broadcast receiver priority.
            intentFilter.setPriority(100);

            // Create a network change broadcast receiver.
            mDateChangeReceiver = new DateChange_BR();

            // Register the broadcast receiver with the intent filter object.
            registerReceiver(mDateChangeReceiver, intentFilter);

            Log.d(TAG, "Service onCreate: screenOnOffReceiver is registered.");
        }
    }
}
