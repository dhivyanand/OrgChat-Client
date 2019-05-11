package com.example.system.orgchat_client.Activities;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.system.orgchat_client.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        getSupportActionBar().hide();

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run(){
                startActivity(new Intent(SplashActivity.this,MainActivity.class));
                SplashActivity.this.finish();
            }

        },1000);


        //finish();

    }
}
