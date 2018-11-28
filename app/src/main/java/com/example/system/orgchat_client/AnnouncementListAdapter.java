package com.example.system.orgchat_client;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

/**
 * Created by System on 28/11/18.
 */

public class AnnouncementListAdapter extends BaseAdapter {

    ArrayList<String> text;
    ArrayList<Bitmap> image;
    Context c;

    AnnouncementListAdapter(Context c,ArrayList<String> text,ArrayList<Bitmap> image){
        this.c = c;
        this.text = text;
        this.image = image;
    }

    @Override
    public int getCount() {
        return text.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return text.size();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        return null;
    }
}
