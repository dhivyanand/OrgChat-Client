package com.example.system.orgchat_client;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import java.io.File;
import java.io.IOException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class chatRoom extends AppCompatActivity {

    ListView chatView;
    EditText message;
    ImageButton camera,attachment;
    Button send;

    ArrayList<String> message_data;
    ArrayList<Character> direction;
    ArrayList<Character> message_type;

    MessageAdapter message_adapter;

    String admin_name,type,host_url;

    DisplayMetrics displayMetrics;
    int dpWidth;

    void suggestion_sync(){

    }


    void saveDB(String message , String message_type , String message_id , Time time , String from , String to){

    }

    void request(String host , String message , String message_type , String message_id , Time time){

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatroom);

        admin_name = getIntent().getExtras().getString("admin_name");
        type = getIntent().getExtras().getString("type");

        getSupportActionBar().setTitle(admin_name);

        chatView = (ListView)findViewById(R.id.chatView);
        message = (EditText)findViewById(R.id.suggestion);
        send = (Button)findViewById(R.id.send);

        camera = (ImageButton)findViewById(R.id.camera);
        attachment = (ImageButton)findViewById(R.id.attachment);

        if(TextUtils.equals(type,"suggestion"))
        {   }
        else if (TextUtils.equals(type,"compliant"))
        {   }

        message_data = new ArrayList<String>();
        direction = new ArrayList<Character>();

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        attachment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

}
