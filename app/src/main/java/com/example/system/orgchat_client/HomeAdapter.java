package com.example.system.orgchat_client;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by System on 28/11/18.
 */

public class HomeAdapter extends BaseAdapter {

    Context c;
    public HomeAdapter(Context c){
        this.c = c;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        LayoutInflater layoutInflater = LayoutInflater.from(c);
        View root = layoutInflater.inflate(R.layout.home_adapter_layout,null);

        ImageView icon = (ImageView)root.findViewById(R.id.icon);
        TextView title = (TextView)root.findViewById(R.id.title);

        return null;
    }
}
