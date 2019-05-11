package com.example.system.orgchat_client.Activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.system.orgchat_client.Constant;
import com.example.system.orgchat_client.Network.APIRequest;
import com.example.system.orgchat_client.R;

import java.util.HashMap;
import java.util.Map;

public class ChangePassword extends AppCompatActivity {

    EditText current_password, new_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        getSupportActionBar().setTitle("Change Password");

        current_password = (EditText)findViewById(R.id.current_password);
        new_password = (EditText)findViewById(R.id.new_password);

        Button done = (Button)findViewById(R.id.done);

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences sharedpreferences = getApplicationContext().getSharedPreferences("AppSession", Context.MODE_PRIVATE);

                String user = sharedpreferences.getString("user","nil");
                String password = sharedpreferences.getString("password","nil");

                if(password.equals(current_password.getText().toString())){


                    Map<String, String> arg = new HashMap<String, String>();
                    arg.put("id",user);
                    arg.put("password",password);
                    arg.put("new_password",new_password.getText().toString());

                    String res = APIRequest.processRequest(arg, Constant.root_URL + "changePassword.php", getApplicationContext());

                    if(res.equals("TRUE")){
                        Toast.makeText(ChangePassword.this, "Password Changed Successfully.", Toast.LENGTH_SHORT).show();
                        sharedpreferences.edit().putString("password",new_password.getText().toString());
                        finish();
                    }

                }else{
                    Toast.makeText(ChangePassword.this, "Please enter the correct Password.", Toast.LENGTH_SHORT).show();
                    current_password.setText("");
                }

            }
        });
    }
}
