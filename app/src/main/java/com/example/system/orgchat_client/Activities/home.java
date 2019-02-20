package com.example.system.orgchat_client.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridView;

import com.example.system.orgchat_client.R;

public class home extends AppCompatActivity {

    GridView grid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        SharedPreferences sharedpreferences = getSharedPreferences("AppSession", Context.MODE_PRIVATE);

        String status = sharedpreferences.getString("status","nil");
        String user = sharedpreferences.getString("user","nil");
        String password = sharedpreferences.getString("password","nil");

        if(status.equals("verified"))
            startActivity(new Intent(home.this,HomeNav.class));
        else
            startActivity(new Intent(home.this,Login.class));

        finish();

    }
}
