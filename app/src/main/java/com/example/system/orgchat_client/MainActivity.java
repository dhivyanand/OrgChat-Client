package com.example.system.orgchat_client;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    Context c;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        c = MainActivity.this;

        int i=0;
        String[] perms = {"android.permission.WRITE_EXTERNAL_STORAGE","android.permission.CAMERA"};

            if (ContextCompat.checkSelfPermission(MainActivity.this, perms[i])
                    != PackageManager.PERMISSION_GRANTED) {

                int permsRequestCode = 200;
                requestPermissions(perms, permsRequestCode);

            }else {
                startActivity(new Intent(c, home.class));
                finish();
            }

    }
    @Override
    public void onRequestPermissionsResult(int permsRequestCode, String[] permissions, int[] grantResults){

        boolean a = grantResults[0]==PackageManager.PERMISSION_GRANTED;
        boolean b = grantResults[1]==PackageManager.PERMISSION_GRANTED;

        if( a==false || b == false)
            ((AppCompatActivity)c).finish();
        else
            startActivity(new Intent(c, home.class));
            finish();

    }
}
