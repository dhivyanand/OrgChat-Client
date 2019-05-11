package com.example.system.orgchat_client.Activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.system.orgchat_client.R;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    public boolean check_permissions(){

        boolean flag = true;

        if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            flag = false;
        }

        if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            flag = false;
        }

        if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED){
            flag = false;
        }

        if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_NETWORK_STATE) != PackageManager.PERMISSION_GRANTED){
            flag = false;
        }

        if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            flag = false;
        }

        return flag;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedpreferences = getSharedPreferences("AppSession", Context.MODE_PRIVATE);

        String status = sharedpreferences.getString("status","nil");
        String user = sharedpreferences.getString("user","nil");
        String password = sharedpreferences.getString("password","nil");

        if(check_permissions()) {
            if (status.equals("verified"))
                startActivity(new Intent(MainActivity.this, home.class));
            else
                startActivity(new Intent(MainActivity.this, Login.class));
        }else {
            if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},79);
            }

            if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.CAMERA},79);
            }

            if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.INTERNET},79);
            }

            if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_NETWORK_STATE) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.ACCESS_NETWORK_STATE},79);
            }

            if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},79);
            }

            if(check_permissions()){
                if (status.equals("verified"))
                    startActivity(new Intent(MainActivity.this, home.class));
                else
                    startActivity(new Intent(MainActivity.this, Login.class));
            }else{
                finish();
            }
        }

        finish();

    }

}
