package com.example.system.orgchat_client;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class Admin extends AppCompatActivity {

    ArrayList<String> name,notification;
    ArrayList<Bitmap> image;
    ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        list = (ListView)findViewById(R.id.admin_list);

        name = new ArrayList<String>();
        notification = new ArrayList<String>();
        image = new ArrayList<Bitmap>();

        name.add("mr A");
        notification.add("1");
        image.add(((BitmapDrawable)getResources().getDrawable(R.mipmap.ic_launcher)).getBitmap());

        AdminListAdapter adapter = new AdminListAdapter(Admin.this, name , notification , image);

        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String name = ((TextView)view.findViewById(R.id.name)).getText().toString();

                Intent suggestion = new Intent(Admin.this,chatRoom.class);
                suggestion.putExtra("admin_name",name);
                startActivity(suggestion);

            }
        });


    }
}