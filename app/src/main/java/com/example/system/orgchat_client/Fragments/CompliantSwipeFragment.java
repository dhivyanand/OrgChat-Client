package com.example.system.orgchat_client.Fragments;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.system.orgchat_client.Activities.NewCompliant;
import com.example.system.orgchat_client.Adapters.CircularListAdapter;
import com.example.system.orgchat_client.Constant;
import com.example.system.orgchat_client.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

public class CompliantSwipeFragment extends Fragment {

    public CompliantSwipeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_compliant_swipe, container, false);

        Button newcompliant = (Button)root.findViewById(R.id.newcompliant);
        ListView list = (ListView)root.findViewById(R.id.list);

        ArrayList<String> compliant,date;
        compliant = new ArrayList<String>();
        date = new ArrayList<String>();

        CircularListAdapter adapter = new CircularListAdapter(getContext(), compliant, date);

        list.setAdapter(adapter);

        newcompliant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), NewCompliant.class));
            }
        });

        try{

            SQLiteDatabase mydatabase = getContext().openOrCreateDatabase("org_chat_db", MODE_PRIVATE, null);

            Cursor resultSet = mydatabase.rawQuery("Select TITLE, DATE from MESSAGE where MESSAGE_TYPE = 'C' ",null);

            if(resultSet.moveToFirst()) {

                do {

                    compliant.add(resultSet.getString(0));
                    date.add(resultSet.getString(1));

                    adapter.notifyDataSetChanged();

                } while (resultSet.moveToNext());

            }

            resultSet.close();

        }catch (Exception e){

        }

        return root;
    }

}
