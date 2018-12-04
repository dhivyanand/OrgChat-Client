package com.example.system.orgchat_client;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by System on 29/11/18.
 */

public class MessageAdapter extends BaseAdapter {

    Context c;
    ArrayList<String> message;
    ArrayList<Character> direction;
    ArrayList<Bitmap> image;

    View root;
    TextView text;
    ImageView imgview;
    DisplayMetrics displayMetrics;

    int dpWidth;

    MessageAdapter(Context c , ArrayList<String> message , ArrayList<Character> direction , ArrayList<Bitmap> image){
        this.c = c;
        this.message = message;
        this.direction = direction;
        this.image = image;
        this.displayMetrics =  c.getResources().getDisplayMetrics();
        this.dpWidth = displayMetrics.widthPixels;
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

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        LayoutInflater layoutInflater = (LayoutInflater)c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if(direction.get(i) == 'L') {
            if(message.get(i).toString() != "") {
                root = layoutInflater.inflate(R.layout.chat_message_view_left, null);
                text = (TextView) root.findViewById(R.id.textView);
                text.setText(message.get(i).toString());
            }else{
                try {
                    root = layoutInflater.inflate(R.layout.message_image_left, null);
                    imgview = (ImageView) root.findViewById(R.id.image);
                    imgview.setImageBitmap(Bitmap.createScaledBitmap(image.get(i), dpWidth * 2 / 4, dpWidth * 2 / 4, true));
                    imgview.setMinimumWidth(dpWidth * 1 / 4);
                    imgview.setMaxWidth(dpWidth * 2 / 4);
                    imgview.setMinimumHeight(dpWidth * 1 / 4);
                    imgview.setMaxHeight(dpWidth * 2 / 4);
                }catch(Exception e){
                    Toast.makeText(c, "Please repeat once again." , Toast.LENGTH_SHORT).show();
                }
            }
        }else{
            if(message.get(i).toString() != "") {
                root = layoutInflater.inflate(R.layout.chat_message_view_right, null);
                text = (TextView) root.findViewById(R.id.textView);
                text.setText(message.get(i).toString());
            }else{
                try {
                    root = layoutInflater.inflate(R.layout.message_image_right, null);
                    imgview = (ImageView) root.findViewById(R.id.image);
                    imgview.setImageBitmap(Bitmap.createScaledBitmap(image.get(i), dpWidth * 2 / 4, dpWidth * 2 / 4, true));
                    imgview.setMinimumWidth((int) dpWidth * 2 / 4);
                    imgview.setMaxWidth((int) dpWidth * 2 / 4);
                    imgview.setMinimumHeight(dpWidth * 2 / 4);
                    imgview.setMaxHeight(dpWidth * 2 / 4);
                }catch(Exception e){
                    Toast.makeText(c, "Please repeat once again." , Toast.LENGTH_SHORT).show();
                }
            }
        }
        return root;
    }
}
