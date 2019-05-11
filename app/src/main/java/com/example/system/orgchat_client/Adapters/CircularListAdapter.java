package com.example.system.orgchat_client.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.system.orgchat_client.R;

import java.util.ArrayList;

public class CircularListAdapter extends BaseAdapter {

    Context c;
    ArrayList<String> title,date,status;

    public CircularListAdapter(Context c, ArrayList<String> title, ArrayList<String> date, ArrayList<String> status){

        this.c = c;
        this.title = title;
        this.date = date;
        this.status = status;

    }

    @Override
    public int getCount() {
        return title.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        LayoutInflater layoutInflater = (LayoutInflater) c
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View root = layoutInflater.inflate(R.layout.grey_list_row, viewGroup, false);

        TextView content = root.findViewById(R.id.dept);
        TextView d = root.findViewById(R.id.att);

        content.setText(title.get(i));
        d.setText(date.get(i));

        if(status.get(i).equals("unread")){
            d.setTextColor(c.getResources().getColor(android.R.color.holo_green_dark));
        }

        return root;

    }
}
