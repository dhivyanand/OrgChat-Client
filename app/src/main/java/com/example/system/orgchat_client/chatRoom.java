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

    ArrayList<String> message_data,time;
    ArrayList<Character> direction,message_type;

    MessageAdapter message_adapter;

    String admin_name,type,host_url;

    DisplayMetrics displayMetrics;
    int dpWidth;

    void suggestion_sync(){

    }

    void compliant_sync(){

    }

    void saveDB(String message , String message_type , String message_id , Time time , String from , String to){

    }

    void fetch_messages(){

    }

    void request(String host , String message , Character message_type , String message_id){

    }

    int hash_gen(){

        return 0;
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
        {
            suggestion_sync();
        }
        else if (TextUtils.equals(type,"compliant"))
        {
            compliant_sync();
        }

        message_adapter = new MessageAdapter(chatRoom.this,message_data,direction,type,time);

        message_data = new ArrayList<String>();
        time = new ArrayList<String>();
        direction = new ArrayList<Character>();
        message_type = new ArrayList<Character>();

        chatView.setAdapter(message_adapter);

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

                String msg = message.getText().toString();

                if(!TextUtils.equals(msg,null)){

                    request(null,msg,'T',String.valueOf(hash_gen()));

                }

            }
        });

    }

}
