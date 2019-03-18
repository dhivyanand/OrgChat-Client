package com.example.system.orgchat_client.Receivers;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.example.system.orgchat_client.Services.ApplicationBackgroundService;

public class NetworkStatusReceiver extends BroadcastReceiver {

    public static boolean isMyServiceRunning(Class<?> serviceClass,Context c) {
        ActivityManager manager = (ActivityManager) c.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        try
        {
            Intent appBgSer = new Intent(context,ApplicationBackgroundService.class);

            if (isOnline(context) && !isMyServiceRunning(ApplicationBackgroundService.class, context)) {

                context.startService(appBgSer);

            } else {

                context.stopService(appBgSer);

            }

        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    private boolean isOnline(Context context) {
        try {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            //should check null because in airplane mode it will be null
            return (netInfo != null && netInfo.isConnected());
        } catch (NullPointerException e) {
            e.printStackTrace();
            return false;
        }
    }
}
