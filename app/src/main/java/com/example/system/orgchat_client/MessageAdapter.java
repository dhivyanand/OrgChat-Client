package com.example.system.orgchat_client;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by System on 29/11/18.
 */

public class MessageAdapter extends BaseAdapter {

    Context c;
    ArrayList<String> message;
    ArrayList<Character> direction;

    View root;
    TextView text;

    MessageAdapter(Context c , ArrayList<String> message , ArrayList<Character> direction){
        this.c = c;
        this.message = message;
        this.direction = direction;
    }

    @Override
    public int getCount() {
        return message.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return message.size();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        LayoutInflater layoutInflater = (LayoutInflater)c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if(direction.get(i) == 'L') {
            root = layoutInflater.inflate(R.layout.chat_message_view_left, null);
            text = (TextView)root.findViewById(R.id.textView);
            text.setText(message.get(i).toString());
        }else{
            root = layoutInflater.inflate(R.layout.chat_message_view_right, null);
            text = (TextView)root.findViewById(R.id.textView);
            text.setText(message.get(i).toString());
        }
        return root;
    }
}
