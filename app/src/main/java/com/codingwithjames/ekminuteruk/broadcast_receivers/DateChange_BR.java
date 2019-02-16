package com.codingwithjames.ekminuteruk.broadcast_receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class DateChange_BR extends BroadcastReceiver {

    private static final String TAG = "DateChange_BR";

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        if (action.equals(Intent.ACTION_DATE_CHANGED)) {
            Log.d(TAG, "onReceive: Date Changed");
            Toast.makeText(context, TAG + " onReceive: Date Changed", Toast.LENGTH_SHORT).show();
        } else if (action.equals(Intent.ACTION_SCREEN_ON)) {
            Log.d(TAG, "onReceive: Screen On");
            Toast.makeText(context, TAG + " onReceive: Screen On", Toast.LENGTH_SHORT).show();
        }

    }
}
