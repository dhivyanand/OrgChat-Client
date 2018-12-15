package com.example.system.orgchat_client;

import android.content.Context;
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

public class Department extends AppCompatActivity {

    ArrayList<String> name,notification;
    ArrayList<Bitmap> image;
    ListView list;
    String type;
    char state; //D 'Department'    S 'Sub Department'

    void populate_list(String domain){
        name.add(domain);
        notification.add("1");
        image.add(((BitmapDrawable)getResources().getDrawable(R.mipmap.ic_launcher)).getBitmap());

        name.add(domain);
        notification.add("1");
        image.add(((BitmapDrawable)getResources().getDrawable(R.mipmap.ic_launcher)).getBitmap());

        name.add(domain);
        notification.add("1");
        image.add(((BitmapDrawable)getResources().getDrawable(R.mipmap.ic_launcher)).getBitmap());

        name.add(domain);
        notification.add("1");
        image.add(((BitmapDrawable)getResources().getDrawable(R.mipmap.ic_launcher)).getBitmap());
    }

    @Override
    public void onBackPressed(){

        if(state == 'D') {
            finish();
        }
        else if (state == 'S'){
            finish();
            startActivity(getIntent());
        }

    }

    void sub_department(final String dept , final Bitmap img){

        //performs sub department collection queries from server

        getSupportActionBar().setTitle("Sub Departments");

        list = (ListView)findViewById(R.id.admin_list);
        list.setVisibility(View.GONE);
        list.setVisibility(View.VISIBLE);

        name = new ArrayList<String>();
        notification = new ArrayList<String>();
        image = new ArrayList<Bitmap>();
        state = 'S';

        populate_list("Sub Department");

        DepartmentAdapter adapter = new DepartmentAdapter(Department.this, name , notification , image);

        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                String name = ((TextView)view.findViewById(R.id.name)).getText().toString();
                Intent chat = new Intent(Department.this,chatRoom.class);
                chat.putExtra("sub_department",dept);
                chat.putExtra("type",type);
                startActivity(chat);

            }
        });

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_department);

        getSupportActionBar().setTitle("Departments");

        type = getIntent().getStringExtra("type");

        list = (ListView)findViewById(R.id.admin_list);

        name = new ArrayList<String>();
        notification = new ArrayList<String>();
        image = new ArrayList<Bitmap>();

        state = 'D';

        populate_list("Department");

        DepartmentAdapter adapter = new DepartmentAdapter(Department.this, name , notification , image);

        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                String name = ((TextView)view.findViewById(R.id.name)).getText().toString();
                sub_department(name , image.get(i));

            }
        });


    }
}
