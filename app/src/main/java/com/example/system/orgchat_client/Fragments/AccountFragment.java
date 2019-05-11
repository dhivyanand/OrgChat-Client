package com.example.system.orgchat_client.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.system.orgchat_client.R;

import org.w3c.dom.Text;

import static android.content.Context.MODE_PRIVATE;

public class AccountFragment extends Fragment {

    public AccountFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public Bitmap StringToBitMap(String encodedString){
        try{
            byte [] encodeByte= Base64.decode(encodedString,Base64.DEFAULT);
            Bitmap bitmap= BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        }catch(Exception e){
            e.getMessage();
            return null;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_account, container, false);

        TextView name, reg, phone, address;

        name = (TextView) root.findViewById(R.id.name);
        reg = (TextView)root.findViewById(R.id.reg);
        phone = (TextView)root.findViewById(R.id.phone);
        address = (TextView)root.findViewById(R.id.address);

        ImageView dp = (ImageView)root.findViewById(R.id.dp);

        SharedPreferences sharedpreferences = getContext().getSharedPreferences("AppSession", Context.MODE_PRIVATE);

        String uname = sharedpreferences.getString("user","nil");

        reg.setText(uname);

        try{

            SQLiteDatabase mydatabase = getContext().openOrCreateDatabase("org_chat_db", MODE_PRIVATE, null);

            Cursor resultSet = mydatabase.rawQuery("Select NAME, DOB, PROFILE, PHONE, ADDRESS from USER ",null);

            if(resultSet.moveToFirst()) {

                name.setText(resultSet.getString(0));

                String profile = resultSet.getString(2);

                if(!profile.equals("null"))
                    dp.setImageBitmap(StringToBitMap(profile));

                phone.setText(resultSet.getString(3));

                address.setText(resultSet.getString(4));

            }

            resultSet.close();

        }catch (Exception e){

        }

        return root;

    }

}
