package com.example.system.orgchat_client.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.system.orgchat_client.Adapters.AttachmentAdapter;
import com.example.system.orgchat_client.Constant;
import com.example.system.orgchat_client.Network.APIRequest;
import com.example.system.orgchat_client.R;

import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class NewSuggestion extends AppCompatActivity {

    AttachmentAdapter adapter;
    Button post;
    EditText title, description;
    ImageButton add;
    ListView attachment;
    TextView empty;
    ArrayList<Bitmap> thumbnail;
    ArrayList<Boolean> is_video;
    ArrayList<String> path;
    ArrayList<File> file;
    ArrayList<Uri> file_uri;

    int READ_REQUEST_CODE = 42;

    private void performFileSearch() {

        // ACTION_OPEN_DOCUMENT is the intent to choose a file via the system's file
        // browser.
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);

        // Filter to only show results that can be "opened", such as a
        // file (as opposed to a list of contacts or timezones)
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        // Filter to show only images, using the image MIME data type.
        // If one wanted to search for ogg vorbis files, the type would be "audio/ogg".
        // To search for all documents available via installed storage providers,
        // it would be "*/*".
        intent.setType("*/*");

        startActivityForResult(intent, READ_REQUEST_CODE);
    }

    private boolean notEmpty(ArrayList<Bitmap> thumbnail, ArrayList<Boolean> is_video, ArrayList<String> path) {

        return thumbnail != null && is_video != null && path != null;
    }

    private boolean isVideo(Uri uri) {

        String type = getApplication().getContentResolver().getType(uri);

        if (type.startsWith("video"))
            return true;
        else
            return false;

    }

    private void populate_adapter(Bitmap thumb, String uri, Boolean isVideo) {

        if (notEmpty(thumbnail, is_video, path)) {
            thumbnail.add(thumb);
            is_video.add(isVideo);
            path.add(uri);

            adapter.notifyDataSetChanged();

        }

    }

    private boolean sendToServer(String title, String description, ArrayList<String> uri) {

        try {

            SharedPreferences sharedpreferences = getSharedPreferences("AppSession", Context.MODE_PRIVATE);

            String user = sharedpreferences.getString("user", "nil");
            String password = sharedpreferences.getString("password", "nil");

            Map<String, String> arg = new HashMap<String, String>();

            String id = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime());

            arg.put("id", user);
            arg.put("password", password);
            arg.put("title", title);
            arg.put("data", description);
            arg.put("message_id", id);
            arg.put("message_type", "S");

            Map<String, File> attachment = new HashMap<String, File>();

            for (int i = 0; i < file_uri.size(); i++) {

                Toast.makeText(this, file_uri.get(i).toString(), Toast.LENGTH_SHORT).show();

                String[] filePathColumn = {MediaStore.Images.Media.DATA, MediaStore.Video.Media.DATA, MediaStore.Files.FileColumns.DATA};
                Cursor cursor = getApplication().getContentResolver().query(file_uri.get(i), filePathColumn, null, null, null);
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String filePath = cursor.getString(columnIndex);
                cursor.close();

                File file = new File(filePath);

                Toast.makeText(this, file.getName(), Toast.LENGTH_SHORT).show();

                String name = user + id + i + file.getName();

                attachment.put(file.getName(), file);

            }

            String res = APIRequest.processRequest(arg, attachment, Constant.root_URL + "messageToServer.php", getApplicationContext());

            JSONObject obj = new JSONObject(res);

            String result = (String) obj.get("result");

            Toast.makeText(this, result, Toast.LENGTH_SHORT).show();

            if (result.equals("TRUE")) {

                SQLiteDatabase mydatabase = openOrCreateDatabase("org_chat_db", MODE_PRIVATE, null);
                mydatabase.execSQL("insert into MESSAGE values('" + id + "','null','" + title + "','" + description + "','S','" + new SimpleDateFormat("yyyy/MM/dd").format(Calendar.getInstance().getTime()) + "','" + new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()) + "') ");
                mydatabase.close();

                Toast.makeText(this, "new circ", Toast.LENGTH_SHORT).show();

                return true;
            } else {
                return false;
            }

        } catch (Exception e) {
            System.out.println(e.toString());
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
            return false;
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_suggestion);

        post = (Button) findViewById(R.id.post);
        title = (EditText) findViewById(R.id.circular_title);
        description = (EditText) findViewById(R.id.circular_description);
        attachment = (ListView) findViewById(R.id.attachment_list);
        add = (ImageButton) findViewById(R.id.add);
        empty = (TextView) findViewById(R.id.empty);

        thumbnail = new ArrayList<Bitmap>();
        is_video = new ArrayList<Boolean>();
        path = new ArrayList<String>();
        file = new ArrayList<File>();
        file_uri = new ArrayList<Uri>();

        adapter = new AttachmentAdapter(NewSuggestion.this, path);

        attachment.setAdapter(adapter);

        adapter.notifyDataSetChanged();

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                performFileSearch();

            }
        });

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String t = title.getText().toString();
                String desc = description.getText().toString();

                if (!t.equals("")) {

                    if (sendToServer(t, desc, path)) {
                        Toast.makeText(NewSuggestion.this, "Circular sent successfully.", Toast.LENGTH_SHORT).show();
                        finish();
                    }

                } else {
                    Toast.makeText(NewSuggestion.this, "Please fill the Title", Toast.LENGTH_SHORT).show();
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
                uri = resultData.getData();

                Bitmap b = null;//thumbnailFromPath(uri);
                String path;
                path = uri.getPath();
                file_uri.add(uri);
                Boolean isVideo = isVideo(uri);

                populate_adapter(b, path, isVideo);

            }
        }
    }

}