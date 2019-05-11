package com.example.system.orgchat_client.Activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.system.orgchat_client.Adapters.CircularListAdapter;
import com.example.system.orgchat_client.R;
import com.example.system.orgchat_client.Services.ApplicationBackgroundService;

import java.util.ArrayList;

public class HomeCircular extends AppCompatActivity {

    public String getUserID(){

        SharedPreferences sharedpreferences = getApplicationContext().getSharedPreferences("AppSession", Context.MODE_PRIVATE);

        return sharedpreferences.getString("user","nil");

    }

    public String getUserPassword(){

        SharedPreferences sharedpreferences = getApplicationContext().getSharedPreferences("AppSession", Context.MODE_PRIVATE);

        return sharedpreferences.getString("password","nil");

    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_circular);

        getSupportActionBar().setTitle("Notice");

        new ApplicationBackgroundService().check_and_sync_circular(getUserID(), getUserPassword() , getApplicationContext());

        ProgressBar progressBar = (ProgressBar)findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);

        ListView circular_list = (ListView)findViewById(R.id.circular_list);

        TextView nocirc = (TextView)findViewById(R.id.nocirc);

        final ArrayList<String> list = new ArrayList<String>();
        final ArrayList<String> date = new ArrayList<String>();
        final ArrayList<String> circular_id = new ArrayList<String>();
        final ArrayList<String> status = new ArrayList<String>();

        final CircularListAdapter adap = new CircularListAdapter(HomeCircular.this,list,date,status);

        circular_list.setAdapter(adap);

        try {

            SQLiteDatabase mydatabase = getApplicationContext().openOrCreateDatabase("org_chat_db", MODE_PRIVATE, null);

            Cursor resultSet = mydatabase.rawQuery("Select TITLE, TIME, CIRCULAR_ID, RW from CIRCULAR",null);

            if(resultSet.moveToFirst()) {

                nocirc.setVisibility(View.INVISIBLE);

                do {

                    list.add(resultSet.getString(0));
                    date.add(resultSet.getString(1));
                    circular_id.add(resultSet.getString(2));
                    if(resultSet.getString(3).equals("NR"))
                        status.add("unread");
                    else
                        status.add("read");

                    adap.notifyDataSetChanged();

                } while (resultSet.moveToNext());

            }

            resultSet.close();
            //mydatabase.close();

        }catch(Exception e){
            //Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
        }

        circular_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent = new Intent(getApplicationContext(),CircularActivity.class);
                intent.putExtra("circular_id",circular_id.get(i));
                startActivity(intent);

                SQLiteDatabase mydatabase = getApplicationContext().openOrCreateDatabase("org_chat_db", MODE_PRIVATE, null);
                mydatabase.execSQL("update CIRCULAR set RW = 'R' where CIRCULAR_ID = '"+ circular_id.get(i) +"' ");

            }
        });

        circular_list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int i, long l) {

                AlertDialog dialog = new AlertDialog.Builder(HomeCircular.this)
                        .setTitle("Delete Item")
                        .setMessage("What do you want to delete this item?")
                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                SQLiteDatabase mydatabase = getApplicationContext().openOrCreateDatabase("org_chat_db", MODE_PRIVATE, null);
                                mydatabase.execSQL("delete from CIRCULAR where CIRCULAR_ID = '"+ circular_id.get(i) +"' ");
                                list.remove(i);
                                date.remove(i);
                                circular_id.remove(i);

                                adap.notifyDataSetChanged();

                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .create();
                dialog.show();

                return false;
            }
        });
    }
}
