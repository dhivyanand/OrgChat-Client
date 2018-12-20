package com.example.system.orgchat_client;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.ActionMode;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class chatRoom extends AppCompatActivity {

    ListView chatView;
    EditText message;
    ImageButton camera,attachment;
    Button send;
    FrameLayout attachment_frame;

    ArrayList<String> message_data,time;
    ArrayList<Character> direction,message_type;

    MessageAdapter message_adapter;

    String admin_name,type,host_url;

    DisplayMetrics displayMetrics;
    int dpWidth , REQUEST_IMAGE_CAPTURE = 1 , READ_REQUEST_CODE=42 , actionBar = R.menu.action_bar_menu;
    Menu actionBarMenu;

    void attachment(){

    }

    void photo(Bitmap image){

        String img = image.toString();
        request(null,img,'I',hash_gen());

    }

    void suggestion_sync(){

    }

    void compliant_sync(){

    }

    void saveDB(String message , String message_type , String message_id , Time time , String from , String to){

    }

    void fetch_messages(){

    }

    void populate_message_list(String message , Character messageType , String messageTime , Character message_direction){

        message_data.add(message);
        message_type.add(messageType);
        time.add(messageTime);
        direction.add(message_direction);
        message_adapter.notifyDataSetChanged();

    }

    void request(String host , String message , Character message_type , String message_id){

        SimpleDateFormat date,time;
        date = new SimpleDateFormat("dd/MM/yyyy");
        time = new SimpleDateFormat(" HH:mm:ss");
        Date now = new Date();
        populate_message_list(message , message_type, time.format(now) , 'R');

    }

    String hash_gen(){

        return "hash";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatroom);

        admin_name = getIntent().getExtras().getString("sub_department");
        type = getIntent().getExtras().getString("type");

        getSupportActionBar().setTitle(admin_name);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_TITLE|ActionBar.DISPLAY_SHOW_CUSTOM|ActionBar.DISPLAY_USE_LOGO);

        chatView = (ListView)findViewById(R.id.chatView);
        message = (EditText)findViewById(R.id.suggestion);
        send = (Button)findViewById(R.id.send);
        camera = (ImageButton)findViewById(R.id.camera);
        attachment = (ImageButton)findViewById(R.id.attachment);
        attachment_frame = (FrameLayout)findViewById(R.id.attachment_frame);

        if(TextUtils.equals(type,"suggestion"))
        {
            suggestion_sync();
        }
        else if (TextUtils.equals(type,"compliant"))
        {
            compliant_sync();
        }

        message_data = new ArrayList<String>();
        time = new ArrayList<String>();
        direction = new ArrayList<Character>();
        message_type = new ArrayList<Character>();

        message_adapter = new MessageAdapter(chatRoom.this,message_data,direction,message_type,time);
        chatView.setAdapter(message_adapter);


        message_data.add("abc");
        time.add("abc");
        direction.add('L');
        message_type.add('F');

        message_data.add("abc");
        time.add("abc");
        direction.add('R');
        message_type.add('F');

        message_data.add("abc");
        time.add("abc");
        direction.add('L');
        message_type.add('I');

        message_data.add("abc");
        time.add("abc");
        direction.add('R');
        message_type.add('I');

        message_adapter.notifyDataSetChanged();

        registerForContextMenu(chatView);

        chatView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);

        chatView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
            @Override
            public void onItemCheckedStateChanged(android.view.ActionMode actionMode, int i, long l, boolean b) {

            }

            @Override
            public boolean onCreateActionMode(android.view.ActionMode actionMode, Menu menu) {
                return false;
            }

            @Override
            public boolean onPrepareActionMode(android.view.ActionMode actionMode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(android.view.ActionMode actionMode, MenuItem menuItem) {
                return false;
            }

            @Override
            public void onDestroyActionMode(android.view.ActionMode actionMode) {

            }
        });

        chatView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });

        chatView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                return false;
            }


        });

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                }

            }
        });

        attachment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("*/*");
                startActivityForResult(intent, READ_REQUEST_CODE);

            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String msg = message.getText().toString();

                if(!TextUtils.equals(msg,null)){

                    request(null,msg,'T',String.valueOf(hash_gen()));

                }

            }

        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode,
                                 Intent resultData) {

        if (requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK) {

            Uri uri = null;

            if (resultData != null) {

                ContentResolver cr = this.getContentResolver();
                uri = resultData.getData();
                String type = cr.getType(uri);

            }

        }

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {

            Bundle extras = resultData.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            photo(imageBitmap);

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(actionBar, menu);
        actionBarMenu = menu;
        return true;
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if (v.getId()==R.id.chatView) {
            MenuInflater inflater = getMenuInflater();
            onCreateOptionsMenu(actionBarMenu);

        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch(item.getItemId()) {
            case R.id.add:
                // add stuff here
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }


}
