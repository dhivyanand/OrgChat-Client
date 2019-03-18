package com.example.system.orgchat_client.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.system.orgchat_client.Constant;
import com.example.system.orgchat_client.R;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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

        final EditText et = (EditText)root.findViewById(R.id.editText);

        Button bt = (Button)root.findViewById(R.id.button2);

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    RequestQueue queue = Volley.newRequestQueue(getContext());
                    StringRequest sr = new StringRequest(com.android.volley.Request.Method.POST,"https://ide50-dhivianand998.legacy.cs50.io:8080/Org_chat_Server/scripts/addSuggestionData.php", new com.android.volley.Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                        }
                    }, new com.android.volley.Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                        }
                    }){
                        @Override
                        protected Map<String,String> getParams(){
                            Map<String,String> params = new HashMap<String, String>();
                            params.put("data",et.getText().toString());
                            params.put("type","c");


                            return params;
                        }

                        @Override
                        public Map<String, String> getHeaders() throws AuthFailureError {
                            Map<String,String> params = new HashMap<String, String>();
                            params.put("Content-Type","application/x-www-form-urlencoded");
                            return params;
                        }
                    };
                    queue.add(sr);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });



        return root;
    }

}
