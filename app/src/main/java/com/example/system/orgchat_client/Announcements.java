package com.example.system.orgchat_client;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class Announcements extends AppCompatActivity {

    ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_announcements);

        list = (ListView)findViewById(R.id.list);

        ArrayList<String> t = new ArrayList<String>();
        t.add("Hello");
        t.add("Hi");
        ArrayList<Bitmap> i = new ArrayList<Bitmap>();
        Bitmap icon = BitmapFactory.decodeResource(Announcements.this.getResources(),
                R.mipmap.menu_announcement_foreground);
        i.add(icon);
        i.add(icon);
        list.setAdapter(new AnnouncementListAdapter(Announcements.this,t,i));

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent notification = new Intent(Announcements.this,Notifications.class);
                notification.putExtra("id","1");
                startActivity(notification);

            }
        });

    }
}
