package com.example.system.orgchat_client;

import android.content.Context;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class chatRoom extends AppCompatActivity {

    ListView chatView;
    EditText message;
    Button send;

    ArrayList<String> text;
    ArrayList<Character> direction;

    MessageAdapter message_adapter;

    String admin_name,type;

    void suggestion(Context c){

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

    void compliant(Context c){

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

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

        if(TextUtils.equals(type,"suggestion"))
            suggestion(chatRoom.this);
        else if (TextUtils.equals(type,"compliant"))
            compliant(chatRoom.this);

        text = new ArrayList<String>();
        direction = new ArrayList<Character>();

        message_adapter = new MessageAdapter(chatRoom.this,text,direction);
        chatView.setAdapter(message_adapter);

    }
}
