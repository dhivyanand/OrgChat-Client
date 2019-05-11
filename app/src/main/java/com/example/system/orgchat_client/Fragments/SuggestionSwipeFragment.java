package com.example.system.orgchat_client.Fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.system.orgchat_client.Activities.CompliantActivity;
import com.example.system.orgchat_client.Activities.NewCompliant;
import com.example.system.orgchat_client.Activities.NewSuggestion;
import com.example.system.orgchat_client.Adapters.CircularListAdapter;
import com.example.system.orgchat_client.Constant;
import com.example.system.orgchat_client.R;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.lang.Object;

import static android.content.Context.CONSUMER_IR_SERVICE;
import static android.content.Context.MODE_PRIVATE;

public class SuggestionSwipeFragment extends Fragment {

    int READ_REQUEST_CODE = 42;

    public SuggestionSwipeFragment() {
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
        View root = inflater.inflate(R.layout.fragment_suggestion_swipe, container, false);

        Constant.a = getActivity().getSupportFragmentManager().findFragmentById(getId());

        Toast.makeText(getContext(), "Suggestion Fragment", Toast.LENGTH_SHORT).show();

        Button newcompliant = (Button)root.findViewById(R.id.newsuggestion);
        ListView list = (ListView)root.findViewById(R.id.lists);

        final ArrayList<String> compliant,compliantID,date;
        compliant = new ArrayList<String>();
        compliantID = new ArrayList<String>();
        date = new ArrayList<String>();

        CircularListAdapter adapter = new CircularListAdapter(getContext(), compliant, date,null);

        list.setAdapter(adapter);

        newcompliant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), NewSuggestion.class));
            }
        });

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                startActivity(new Intent(getContext(), CompliantActivity.class).putExtra("message_id",compliantID.get(i)));

            }
        });

        try{

            SQLiteDatabase mydatabase = getContext().openOrCreateDatabase("org_chat_db", MODE_PRIVATE, null);

            Cursor resultSet = mydatabase.rawQuery("Select TITLE, MESSAGE_ID, DATE from MESSAGE where MESSAGE_TYPE = 'S' ",null);

            if(resultSet.moveToFirst()) {

                do {

                    compliant.add(resultSet.getString(0));
                    compliantID.add(resultSet.getString(1));
                    date.add(resultSet.getString(2));

                    adapter.notifyDataSetChanged();

                } while (resultSet.moveToNext());

            }

            resultSet.close();

        }catch (Exception e){

        }

        return root;
    }

}
