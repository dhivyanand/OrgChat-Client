package com.example.system.orgchat_client;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

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
    FrameLayout attachment_frame;

    ArrayList<String> message_data,time;
    ArrayList<Character> direction,message_type;

    MessageAdapter message_adapter;

    String admin_name,type,host_url;

    DisplayMetrics displayMetrics;
    int dpWidth , READ_REQUEST_CODE=42;

    void attachment(){

    }

    void photo(){

    }

    void suggestion_sync(){

    }

    void compliant_sync(){

    }

    void saveDB(String message , String message_type , String message_id , Time time , String from , String to){

    }

    void fetch_messages(){

    }

    void populate_message_list(String message , Character messageType , String messageTime){

        message_data.add(message);
        message_type.add(messageType);
        time.add(messageTime);
        direction.add('R');
        message_adapter.notifyDataSetChanged();

    }

    void request(String host , String message , Character message_type , String message_id){

        SimpleDateFormat date,time;

        date = new SimpleDateFormat("dd/MM/yyyy");
        time = new SimpleDateFormat(" HH:mm:ss");

        Date now = new Date();

        populate_message_list(message , message_type, time.format(now));

    }

    String hash_gen(){

        return "hash";
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

        attachment_frame = (FrameLayout)findViewById(R.id.attachment_frame);

        if(TextUtils.equals(type,"suggestion"))
        {
            suggestion_sync();
        }
        else if (TextUtils.equals(type,"compliant"))
        {
            compliant_sync();
        }


        message_data = new ArrayList<String>();
        time = new ArrayList<String>();
        direction = new ArrayList<Character>();
        message_type = new ArrayList<Character>();

        message_adapter = new MessageAdapter(chatRoom.this,message_data,direction,message_type,time);

        chatView.setAdapter(message_adapter);

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        attachment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("image/*");
                startActivityForResult(intent, READ_REQUEST_CODE);

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

    @Override
    public void onActivityResult(int requestCode, int resultCode,
                                 Intent resultData) {

        if (requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK) {

            Uri uri = null;

            if (resultData != null) {

                ContentResolver cr = this.getContentResolver();
                uri = resultData.getData();
                String type = cr.getType(uri);
                Toast.makeText(this, type, Toast.LENGTH_SHORT).show();


            }

        }

    }

}
