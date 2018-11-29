package com.example.system.orgchat_client;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class Suggestion extends AppCompatActivity {

    ListView chatView;
    EditText suggestion;
    Button send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggestion);

        chatView = (ListView)findViewById(R.id.chatView);
        suggestion = (EditText)findViewById(R.id.suggestion);
        send = (Button)findViewById(R.id.send);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }
}
