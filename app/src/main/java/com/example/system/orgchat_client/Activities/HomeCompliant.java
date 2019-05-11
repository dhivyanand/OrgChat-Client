package com.example.system.orgchat_client.Activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.system.orgchat_client.Adapters.CircularListAdapter;
import com.example.system.orgchat_client.R;

import java.util.ArrayList;

public class HomeCompliant extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_compliant);

        getSupportActionBar().setTitle("Compliant");

        FloatingActionButton newcompliant = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        ListView list = (ListView)findViewById(R.id.list);
        TextView nocom = (TextView)findViewById(R.id.nocom);

        final ArrayList<String> compliant,date,compliantID,status;
        compliant = new ArrayList<String>();
        date = new ArrayList<String>();
        compliantID = new ArrayList<String>();
        status = new ArrayList<String>();

        final CircularListAdapter adapter = new CircularListAdapter(HomeCompliant.this, compliant, date, status);

        list.setAdapter(adapter);

        newcompliant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), NewCompliant.class));
                finish();
            }
        });

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                startActivity(new Intent(getApplicationContext(), CompliantActivity.class).putExtra("message_id",compliantID.get(i)));

            }
        });

        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int i, long l) {

                AlertDialog dialog = new AlertDialog.Builder(HomeCompliant.this)
                        .setTitle("Delete Item")
                        .setMessage("What do you want to delete this item?")
                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                SQLiteDatabase mydatabase = getApplicationContext().openOrCreateDatabase("org_chat_db", MODE_PRIVATE, null);
                                mydatabase.execSQL("delete from MESSAGE where MESSAGE_ID = '"+ compliantID.get(i) +"' ");
                                compliant.remove(i);
                                date.remove(i);
                                compliantID.remove(i);

                                adapter.notifyDataSetChanged();

                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .create();
                dialog.show();

                return false;
            }
        });

        try{

            SQLiteDatabase mydatabase = getApplicationContext().openOrCreateDatabase("org_chat_db", MODE_PRIVATE, null);

            Cursor resultSet = mydatabase.rawQuery("Select TITLE, DATE, MESSAGE_ID from MESSAGE where MESSAGE_TYPE = 'C' ",null);

            if(resultSet.moveToFirst()) {

                nocom.setVisibility(View.INVISIBLE);

                do {

                    compliant.add(resultSet.getString(0));
                    date.add(resultSet.getString(1));
                    compliantID.add(resultSet.getString(2));
                    status.add("read");

                    adapter.notifyDataSetChanged();

                } while (resultSet.moveToNext());

            }

            resultSet.close();

        }catch (Exception e){

        }
    }
}
