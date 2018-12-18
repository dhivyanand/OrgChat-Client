package com.example.system.orgchat_client;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
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

    int screen_height=0, screen_width=0;
    WindowManager wm;
    DisplayMetrics displaymetrics;


    MessageAdapter(Context c , ArrayList<String> message , ArrayList<Character> direction , ArrayList<Character> type , ArrayList<String> time){

        this.c = c;
        this.message = message;
        this.direction = direction;
        this.type = type;
        this.time = time;

    }

    public void img_resize(){
        wm = (WindowManager) c.getSystemService(Context.WINDOW_SERVICE);
        displaymetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(displaymetrics);
        screen_height = displaymetrics.heightPixels;
        screen_width = displaymetrics.widthPixels;

        imgview.setMinimumWidth(screen_width/2);
        imgview.setMaxWidth(screen_width/2);
        imgview.setMinimumHeight(screen_width/2);
        imgview.setMaxHeight(screen_width/2);
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

    @SuppressLint("NewApi")
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        char dir = direction.get(i);
        LayoutInflater layoutInflater = (LayoutInflater)c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        char t = type.get(i);

        if(dir == 'L') {

            if(t == 'T') {

                root = layoutInflater.inflate(R.layout.chat_message_view_left, null);
                text = (TextView) root.findViewById(R.id.textView);
                text.setText(message.get(i));

            } else if(t == 'I'){

                root = layoutInflater.inflate(R.layout.video_left, null);
                imgview = (ImageView) root.findViewById(R.id.imageView);
                Bitmap b = BitmapFactory.decodeResource(c.getResources(),R.mipmap.double_tick_round);
                img_resize();
                imgview.setImageBitmap(Bitmap.createScaledBitmap(b, (screen_width/2)-10, (screen_width/2)-10, false));

            } else if(t == 'F'){

                root = layoutInflater.inflate(R.layout.doc_message_left, null);

            } else if(t == 'V'){

            }


        } else if(dir == 'R'){

            if(t == 'T') {

            } else if(t == 'I'){

                root = layoutInflater.inflate(R.layout.video_right, null);
                imgview = (ImageView) root.findViewById(R.id.imageView);
                Bitmap b = BitmapFactory.decodeResource(c.getResources(),R.mipmap.double_tick_round);
                img_resize();
                imgview.setImageBitmap(Bitmap.createScaledBitmap(b, (screen_width/2)-10, (screen_width/2)-10, false));

            } else if(t == 'F'){

                root = layoutInflater.inflate(R.layout.doc_message_right, null);


            } else if(t == 'V'){

            }

        } else if(dir == 'C'){

        }

        return root;
    }

}
