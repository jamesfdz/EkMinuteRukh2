package com.codingwithjames.ekminuteruk.broadcast_receivers;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;
import android.widget.Toast;

import com.codingwithjames.ekminuteruk.R;

import static android.support.v4.content.ContextCompat.getSystemService;

public class DateChange_BR extends BroadcastReceiver {

    private static final String TAG = "DateChange_BR";
    private static final String CHANNEL_ID = "DateChange";

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        if (action.equals(Intent.ACTION_DATE_CHANGED)) {
            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context,
                    CHANNEL_ID)
                    .setSmallIcon(R.mipmap.logo)
                    .setContentTitle("Test")
                    .setContentText("Testing Content")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);

            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

            // notificationId is a unique int for each notification that you must define
            notificationManager.notify(2, mBuilder.build());

        } else if (action.equals(Intent.ACTION_SCREEN_ON)) {
            Log.d(TAG, "onReceive: Screen On");
            Toast.makeText(context, "Screen On", Toast.LENGTH_SHORT).show();

            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context,
                    CHANNEL_ID)
                    .setSmallIcon(R.mipmap.logo)
                    .setContentTitle("Test")
                    .setContentText("Testing Content")
                    .setStyle(new NotificationCompat.BigTextStyle()
                            .bigText("Much longer text that cannot fit one line..."))
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);

            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

            // notificationId is a unique int for each notification that you must define
            notificationManager.notify(3, mBuilder.build());
        }

    }
}
