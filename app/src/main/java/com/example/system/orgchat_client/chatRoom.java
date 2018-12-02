package com.example.system.orgchat_client;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class chatRoom extends AppCompatActivity {

    ListView chatView;
    EditText message;
    Button send;

    ArrayList<String> text;
    ArrayList<Character> direction;

    MessageAdapter message_adapter;

    String admin_name,type;

    void suggestion(){

    }

    void compliant(){

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatroom);

        admin_name = getIntent().getExtras().getString("admin_name");

        type = getIntent().getExtras().getString("type");

        getSupportActionBar().setTitle(admin_name);

        if(type == "suggestion")
            suggestion();
        else if (type == "compliant")
            compliant();

        chatView = (ListView)findViewById(R.id.chatView);
        message = (EditText)findViewById(R.id.suggestion);
        send = (Button)findViewById(R.id.send);

        text = new ArrayList<String>();
        direction = new ArrayList<Character>();

        message_adapter = new MessageAdapter(chatRoom.this,text,direction);
        chatView.setAdapter(message_adapter);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String message = chatRoom.this.message.getText().toString();

                if(message != null){
                    text.add(message);
                    direction.add('R');
                    message_adapter.notifyDataSetChanged();
                    chatRoom.this.message.setText(null);
                    try{
                        Thread.sleep(500);
                        text.add(message);
                        direction.add('L');
                        message_adapter.notifyDataSetChanged();

                    }catch(Exception e){

                    }

                }

            }
        });

    }
}
