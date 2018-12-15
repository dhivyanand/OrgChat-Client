package com.example.system.orgchat_client;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

public class home extends AppCompatActivity {

    GridView grid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        grid = (GridView)findViewById(R.id.home_grid);
        grid.setAdapter(new HomeAdapter(home.this));
        grid.setNumColumns(2);
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0)
                    startActivity(new Intent(home.this,Profile.class));
                if (i == 1)
                    startActivity(new Intent(home.this,Announcements.class));
                if (i == 2)
                    startActivity(new Intent(home.this,Department.class).putExtra("type","suggestion"));
                if (i == 3)
                    startActivity(new Intent(home.this,Department.class).putExtra("type","compliant"));
            }
        });

    }
}
