package com.example.system.orgchat_client.Services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

public class ApplicationBackgroundService extends Service {
    public ApplicationBackgroundService() {
    }

    @Override
    public IBinder onBind(Intent intent) {

        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate(){

        Toast.makeText(this, "Started the Service.", Toast.LENGTH_SHORT).show();

    }
}
