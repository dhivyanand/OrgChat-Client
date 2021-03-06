package com.example.system.orgchat_client.Activities;

import android.app.ActivityManager;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.system.orgchat_client.Database.CreateDatabaseUsingHelper;
import com.example.system.orgchat_client.Fragments.AccountFragment;
import com.example.system.orgchat_client.Fragments.CircularFragment;
import com.example.system.orgchat_client.Fragments.DocFragment;
import com.example.system.orgchat_client.Fragments.PostFragment;
import com.example.system.orgchat_client.Fragments.SubDeptFragment;
import com.example.system.orgchat_client.R;
import com.example.system.orgchat_client.Services.ApplicationBackgroundService;

public class HomeNav extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    public void setActionBarTitle(String title){

        getSupportActionBar().setTitle(title);

    }

    public static boolean isMyServiceRunning(Class<?> serviceClass,Context c) {
        ActivityManager manager = (ActivityManager) c.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    private boolean isOnline(Context context) {
        try {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            //should check null because in airplane mode it will be null
            return (netInfo != null && netInfo.isConnected());
        } catch (NullPointerException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void start_bg_service(){

        if (isOnline(getApplicationContext()) && !isMyServiceRunning(ApplicationBackgroundService.class, getApplicationContext())) {

            Intent appBgSer = new Intent(getApplicationContext(),ApplicationBackgroundService.class);
            startService(appBgSer);

        }

    }

    public void logout(){
        finish();

        SharedPreferences sharedpreferences = getSharedPreferences("AppSession", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("status", "nil");
        editor.putString("user", "nil");
        editor.putString("password", "nil");
        editor.commit();

        SQLiteDatabase sqLiteDatabase = openOrCreateDatabase("org_chat_db", MODE_PRIVATE, null);

        sqLiteDatabase.execSQL("DELETE FROM USER");
        sqLiteDatabase.execSQL("DELETE FROM DEPARTMENT");
        sqLiteDatabase.execSQL("DELETE FROM SUBDEPARTMENT");
        sqLiteDatabase.execSQL("DELETE FROM MESSAGE");
        sqLiteDatabase.execSQL("DELETE FROM CIRCULAR");
        sqLiteDatabase.execSQL("DELETE FROM ATTACHMENT");
        sqLiteDatabase.execSQL("DELETE FROM FILE");
        sqLiteDatabase.execSQL("DELETE FROM DATE");

        startActivity(new Intent(HomeNav.this,MainActivity.class));
    }

    public String getUserID(){

        SharedPreferences sharedpreferences = getSharedPreferences("AppSession", Context.MODE_PRIVATE);

        return sharedpreferences.getString("user","nil");

    }

    public String getUserPassword(){

        SharedPreferences sharedpreferences = getSharedPreferences("AppSession", Context.MODE_PRIVATE);

        return sharedpreferences.getString("password","nil");

    }

    public String getUserName(){

        try{

            SQLiteDatabase mydatabase = getApplicationContext().openOrCreateDatabase("org_chat_db", MODE_PRIVATE, null);

            Cursor resultSet = mydatabase.rawQuery("Select NAME from USER ",null);

            if(resultSet.moveToFirst()){

                do{

                    return resultSet.getString(0);

                }while(resultSet.moveToNext());

            }

        }catch(Exception e){

        }
        return " ";

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_nav);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);

        TextView navid, navname;

        navid = (TextView)headerView.findViewById(R.id.id);
        navid.setText(getUserID());

        navname = (TextView)headerView.findViewById(R.id.name);
        navname.setText(getUserName());


        Thread t = new Thread(){
            @Override
            public void run(){

                start_bg_service();

            }

        };

        t.start();

        //start_bg_service();

    }

    @Override
    public void onBackPressed() {

        FragmentManager fm = getFragmentManager();
        fm.popBackStackImmediate();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_nav, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Fragment fragment = null;
        FragmentTransaction transaction;
        transaction = getSupportFragmentManager().beginTransaction();

        if (id == R.id.nav_profile) {

            fragment = new AccountFragment();

        } else if (id == R.id.nav_post) {

            fragment = new PostFragment();

        } else if (id == R.id.nav_circular) {


            Toast.makeText(this, "Loading...", Toast.LENGTH_LONG).show();

            fragment = new CircularFragment();

        } else if (id == R.id.nav_link) {

            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://unifiedportal-mem.epfindia.gov.in/"));
            startActivity(browserIntent);

        } else if (id == R.id.nav_esi){

            fragment = new DocFragment();

        } else if (id == R.id.nav_logout) {

            logout();

        }

        if (fragment != null) {

            transaction.replace(R.id.home_frame, fragment);
            transaction.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
