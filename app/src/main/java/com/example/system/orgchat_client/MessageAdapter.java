package com.example.system.orgchat_client;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Blob;
import java.sql.Time;
import java.util.ArrayList;

/**
 * Created by System on 29/11/18.
 */

public class MessageAdapter extends BaseAdapter {

    Context c;
    ArrayList<String> message,time;
    ArrayList<Character> direction,type;

    View root;
    TextView text;
    ImageView imgview;

    MessageAdapter(Context c , ArrayList<String> message , ArrayList<Character> direction , ArrayList<Character> type , ArrayList<String> time){

        this.c = c;
        this.message = message;
        this.direction = direction;
        this.type = type;
        this.time = time;

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

        char dir = direction.get(i);
        LayoutInflater layoutInflater = (LayoutInflater)c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        char t = type.get(i);

        if(dir == 'L') {

            if(t == 'T') {

                root = layoutInflater.inflate(R.layout.chat_message_view_left, viewGroup);
                text = (TextView) root.findViewById(R.id.textView);
                text.setText(message.get(i));

            } else if(t == 'I'){

                root = layoutInflater.inflate(R.layout.image_left, viewGroup);
                imgview = (ImageView) root.findViewById(R.id.imageView);

            } else if(t == 'F'){

            } else if(t == 'V'){

            }


        } else if(dir == 'R'){

        } else if(dir == 'C'){

        }

        return view;
    }
}
