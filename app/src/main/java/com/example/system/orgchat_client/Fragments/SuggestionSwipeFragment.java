package com.example.system.orgchat_client.Fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
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
import android.widget.Button;
import android.widget.EditText;
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
import java.util.HashMap;
import java.util.Map;
import java.lang.Object;

public class SuggestionSwipeFragment extends Fragment {

    int READ_REQUEST_CODE = 42;

    public SuggestionSwipeFragment() {
        // Required empty public constructor
    }

    private String getRealPathFromURI(Uri contentURI) {

        String result;
        Cursor cursor = getContext().getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) {
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
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

        final EditText et = (EditText)root.findViewById(R.id.editText);

        Button bt = (Button)root.findViewById(R.id.button2);

        Button link = (Button)root.findViewById(R.id.button3);

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                        try {
                            RequestQueue queue = Volley.newRequestQueue(getContext());
                            StringRequest sr = new StringRequest(Request.Method.POST,"https://ide50-dhivianand998.legacy.cs50.io:8080/Org_chat_Server/scripts/addSuggestionData.php", new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                }
                            }){
                                @Override
                                protected Map<String,String> getParams(){
                                    Map<String,String> params = new HashMap<String, String>();
                                    params.put("data",et.getText().toString());
                                    params.put("type","s");


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

        link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);

                intent.addCategory(Intent.CATEGORY_OPENABLE);

                intent.setType("*/*");

                startActivityForResult(intent,READ_REQUEST_CODE);

            }
        });

        return root;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode,
                                 Intent resultData) {

        if (requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK) {

            if (resultData != null) {
                final Uri uri  = resultData.getData();

                String path = uri.toString();

                Thread t = new Thread(){

                    @Override
                    public void run(){

                        try {
                            HttpURLConnection connection;
                            DataOutputStream dataOutputStream;
                            String lineEnd = "\r\n";
                            String twoHyphens = "--";
                            String boundary = "*****";

                            int bytesRead,bytesAvailable,bufferSize;
                            byte[] buffer;
                            int maxBufferSize = 1 * 1024 * 1024;
                            //File selectedFile = new File(new URI(uri.getPath().toString()));

                            String[] parts = uri.getPath().split("/");

                            FileInputStream fileInputStream = new FileInputStream(getRealPathFromURI(uri));
                            URL url = new URL("http://ide50-dhivianand998.legacy.cs50.io:8080/Org_chat_Server/scripts/link.php");
                            connection = (HttpURLConnection) url.openConnection();
                            connection.setDoInput(true);//Allow Inputs
                            connection.setDoOutput(true);//Allow Outputs
                            connection.setUseCaches(false);//Don't use a cached Copy
                            connection.setRequestMethod("POST");
                            connection.setRequestProperty("Connection", "Keep-Alive");
                            connection.setRequestProperty("ENCTYPE", "multipart/form-data");
                            connection.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
                            connection.setRequestProperty("uploaded_file", uri.getPath());

                            //creating new dataoutputstream
                            dataOutputStream = new DataOutputStream(connection.getOutputStream());

                            //writing bytes to data outputstream
                            dataOutputStream.writeBytes(twoHyphens + boundary + lineEnd);
                            dataOutputStream.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";filename=\""
                                    + uri.getPath() + "\"" + lineEnd);

                            dataOutputStream.writeBytes(lineEnd);

                            //returns no. of bytes present in fileInputStream
                            bytesAvailable = fileInputStream.available();
                            //selecting the buffer size as minimum of available bytes or 1 MB
                            bufferSize = Math.min(bytesAvailable, maxBufferSize);
                            //setting the buffer as byte array of size of bufferSize
                            buffer = new byte[bufferSize];

                            //reads bytes from FileInputStream(from 0th index of buffer to buffersize)
                            bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                            //loop repeats till bytesRead = -1, i.e., no bytes are left to read
                            while (bytesRead > 0) {
                                //write the bytes read from inputstream
                                dataOutputStream.write(buffer, 0, bufferSize);
                                bytesAvailable = fileInputStream.available();
                                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                                bytesRead = fileInputStream.read(buffer, 0, bufferSize);
                            }

                            dataOutputStream.writeBytes(lineEnd);
                            dataOutputStream.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);


                            final String serverResponseMessage = connection.getResponseMessage();

                            final DataInputStream in = new DataInputStream(connection.getInputStream());



                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        Toast.makeText(getContext(), in.readLine(), Toast.LENGTH_SHORT).show();
                                    }catch(Exception e){}
                                    }
                            });

                        }catch(final Exception e){

                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getContext(),e.toString(),Toast.LENGTH_SHORT).show();
                                }
                            });

                        }

                    }

                };

                t.start();

            }
        }
    }

}
