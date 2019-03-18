package com.example.system.orgchat_client.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.system.orgchat_client.Constant;
import com.example.system.orgchat_client.Database.CreateDatabaseUsingHelper;
import com.example.system.orgchat_client.Network.APIRequest;
import com.example.system.orgchat_client.R;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {

    EditText username, password;
    Button login;

    boolean verify_user(String uname , String pass){

        Map<String,String> req = new HashMap<String,String>();
        req.put("id",uname);
        req.put("password",pass);

        String res = APIRequest.processRequest(req, Constant.root_URL+"userLogin.php",getApplicationContext());

        JSONObject obj = null;

        try {

            obj = new JSONObject(res);

            String result = (String)obj.get("result");

            if(result.equals("TRUE"))
                return true;
            else
                return false;

        } catch (Exception e) {
            return false;
        }

    }

    private boolean create_local_database(){

        try{

            CreateDatabaseUsingHelper db = new CreateDatabaseUsingHelper(getApplicationContext());
            db.getWritableDatabase();
            return true;

        }catch(Exception e){
            return false;
        }

    }

    private boolean create_local_pref(String uname , String pass){

        try {

            SharedPreferences sharedpreferences = getSharedPreferences("AppSession", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString("status", "verified");
            editor.putString("user", uname);
            editor.putString("password", pass);
            editor.commit();

            return true;

        }catch(Exception e){
            return false;
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = (EditText)findViewById(R.id.uname);
        password = (EditText)findViewById(R.id.password);
        login = (Button)findViewById(R.id.button);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String uname = username.getText().toString() , pass = password.getText().toString();

                if (uname != "" && pass != "") {

                    if(verify_user(uname,pass)){

                        if(create_local_pref(uname,pass) && create_local_database()){
                            startActivity(new Intent(Login.this,HomeNav.class));
                            finish();
                        }

                    }

                } else
                    Toast.makeText(Login.this, "Please fill in the fields.", Toast.LENGTH_SHORT).show();

            }
        });

    }

}
