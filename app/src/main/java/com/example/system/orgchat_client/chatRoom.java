package com.example.system.orgchat_client;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class chatRoom extends AppCompatActivity {

    ListView chatView;
    EditText message;
    Button send;

    ArrayList<String> text;
    ArrayList<Character> direction;
    ArrayList<Bitmap> image;

    ImageButton camera,attachment;

    MessageAdapter message_adapter;

    String admin_name,type;

    DisplayMetrics displayMetrics;

    int dpWidth;

    static final int REQUEST_IMAGE_CAPTURE = 1;
    private Bitmap mImageBitmap;
    private String mCurrentPhotoPath;

    void suggestion(Context c){

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!TextUtils.equals(message.getText().toString() , "")) {
                    text.add(message.getText().toString());
                    direction.add('R');
                    image.add(null);
                    message_adapter.notifyDataSetChanged();

                    text.add(message.getText().toString());
                    direction.add('L');
                    image.add(null);
                    message_adapter.notifyDataSetChanged();
                    message.setText(null);
                }

            }
        });

    }

    void compliant(Context c){

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!TextUtils.equals(message.getText().toString() , "")) {
                    text.add(message.getText().toString());
                    direction.add('R');
                    image.add(null);
                    message_adapter.notifyDataSetChanged();

                    text.add(message.getText().toString());
                    direction.add('L');
                    image.add(null);
                    message_adapter.notifyDataSetChanged();

                    message.setText(null);
                }
            }
        });

    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  // prefix
                ".jpg",         // suffix
                storageDir      // directory
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        return image;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatroom);

        admin_name = getIntent().getExtras().getString("admin_name");

        type = getIntent().getExtras().getString("type");

        getSupportActionBar().setTitle(admin_name);

        chatView = (ListView)findViewById(R.id.chatView);
        message = (EditText)findViewById(R.id.suggestion);
        send = (Button)findViewById(R.id.send);

        camera = (ImageButton)findViewById(R.id.camera);
        attachment = (ImageButton)findViewById(R.id.attachment);

        displayMetrics =  getResources().getDisplayMetrics();
        dpWidth = displayMetrics.widthPixels;

        if(TextUtils.equals(type,"suggestion"))
            suggestion(chatRoom.this);
        else if (TextUtils.equals(type,"compliant"))
            compliant(chatRoom.this);

        text = new ArrayList<String>();
        direction = new ArrayList<Character>();
        image = new ArrayList<Bitmap>();

        message_adapter = new MessageAdapter(chatRoom.this,text,direction,image);
        chatView.setAdapter(message_adapter);

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        attachment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

}
