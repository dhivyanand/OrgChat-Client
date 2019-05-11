package com.example.system.orgchat_client.Activities;

import android.app.ActionBar;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.system.orgchat_client.Constant;
import com.example.system.orgchat_client.Network.APIRequest;
import com.example.system.orgchat_client.R;

import java.util.HashMap;
import java.util.Map;

public class HomeProfile extends AppCompatActivity {

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_profile);

        getSupportActionBar().setTitle("Profile");

        TextView name, reg, phone, address;

        name = (TextView)findViewById(R.id.name);
        reg = (TextView)findViewById(R.id.reg);
        phone = (TextView)findViewById(R.id.phone);
        address = (TextView)findViewById(R.id.address);

        ImageView dp = (ImageView)findViewById(R.id.dp);

        Button change_password = (Button)findViewById(R.id.change_password);

        SharedPreferences sharedpreferences = getApplicationContext().getSharedPreferences("AppSession", Context.MODE_PRIVATE);

        String uname = sharedpreferences.getString("user","nil");

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        reg.setText(uname);


        try{

            SQLiteDatabase mydatabase = getApplicationContext().openOrCreateDatabase("org_chat_db", MODE_PRIVATE, null);

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

        change_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(HomeProfile.this,ChangePassword.class));

            }
        });

    }
}
