package com.example.system.orgchat_client;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class Suggestion extends AppCompatActivity {

    ListView chatView;
    EditText suggestion;
    Button send;

    ArrayList<String> text;
    ArrayList<Character> direction;

    MessageAdapter message_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggestion);

        chatView = (ListView)findViewById(R.id.chatView);
        suggestion = (EditText)findViewById(R.id.suggestion);
        send = (Button)findViewById(R.id.send);

        text = new ArrayList<String>();
        direction = new ArrayList<Character>();

        message_adapter = new MessageAdapter(Suggestion.this,text,direction);
        chatView.setAdapter(message_adapter);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String message = suggestion.getText().toString();

                if(message != null){
                    text.add(message);
                    direction.add('R');
                    message_adapter.notifyDataSetChanged();
                    suggestion.setText(null);
                }

            }
        });

    }
}
