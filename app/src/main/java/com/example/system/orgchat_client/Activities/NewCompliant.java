package com.example.system.orgchat_client.Activities;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.system.orgchat_client.Adapters.AttachmentAdapter;
import com.example.system.orgchat_client.Constant;
import com.example.system.orgchat_client.FileUtil;
import com.example.system.orgchat_client.Network.APIRequest;
import com.example.system.orgchat_client.R;

import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class NewCompliant extends AppCompatActivity {

    AttachmentAdapter adapter;
    FloatingActionButton post;
    EditText title,description;
    ImageButton add;
    ListView attachment;
    TextView empty;
    ArrayList<Bitmap> thumbnail;
    ArrayList<Boolean> is_video;
    ArrayList<String> path;
    ArrayList<File> file;
    ArrayList<Uri> file_uri;
    String first_dept;
    RelativeLayout popup;

    int READ_REQUEST_CODE = 42;

    private void performFileSearch(String action) {

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
        intent.setType(action);

        startActivityForResult(intent, READ_REQUEST_CODE);
    }

    private boolean notEmpty(ArrayList<Bitmap> thumbnail, ArrayList<Boolean> is_video, ArrayList<String> path){

        return thumbnail != null && is_video != null && path != null;
    }

    private boolean isVideo(Uri uri){

        String type = getApplication().getContentResolver().getType(uri);

        if(type.startsWith("video"))
            return true;
        else
            return false;

    }

    private void populate_adapter(Bitmap thumb , String uri , Boolean isVideo){

        if(notEmpty(thumbnail, is_video, path)){
            thumbnail.add(thumb);
            is_video.add(isVideo);
            path.add(uri);

            adapter.notifyDataSetChanged();

        }

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static String getRealPathFromURI_API19(Context context, Uri uri) {
        String filePath = "";
        if (uri.getHost().contains("com.android.providers.media")) {
            // Image pick from recent
            String wholeID = DocumentsContract.getDocumentId(uri);

            // Split at colon, use second item in the array
            String id = wholeID.split(":")[1];

            String[] column = {MediaStore.Images.Media.DATA};

            // where id is equal to
            String sel = MediaStore.Images.Media._ID + "=?";

            Cursor cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    column, sel, new String[]{id}, null);

            int columnIndex = cursor.getColumnIndex(column[0]);

            if (cursor.moveToFirst()) {
                filePath = cursor.getString(columnIndex);
            }
            cursor.close();
            return filePath;
        } else {
            // image pick from gallery
            return null;// getRealPathFromURI_BelowAPI11(context,uri)
        }

    }

    private String getRealPathFromURI(Uri contentUri) {
        String[] proj = {MediaStore.Images.Media.DATA, MediaStore.Video.Media.DATA, MediaStore.Files.FileColumns.DATA};
        CursorLoader loader = new CursorLoader(getApplicationContext(), contentUri, proj, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(column_index);
        cursor.close();
        return result;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private boolean sendToServer(String title, String description, ArrayList<String> uri, String dept){

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
            arg.put("message_type", "C");
            arg.put("subdept_id","null");

            Map<String,File> attachment = new HashMap<String,File>();

            for (int i = 0; i < file_uri.size(); i++) {

                String[] filePathColumn = { MediaStore.Images.Media.DATA, MediaStore.Video.Media.DATA, MediaStore.Files.FileColumns.DATA };
                Cursor cursor = getApplication().getContentResolver().query(file_uri.get(i), filePathColumn, null, null, null);
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String filePath = cursor.getString(columnIndex);
                cursor.close();

                File file = FileUtil.from(NewCompliant.this,file_uri.get(i));

                String fileName = file.getName().replace('/','.');
                String name = user + fileName;

                attachment.put(name,file);

            }

            String res = APIRequest.processRequest(arg, attachment, Constant.root_URL + "messageToServer.php", getApplicationContext());

            JSONObject obj = new JSONObject(res);

            String result = (String)obj.get("result");

            if(result.equals("TRUE")) {

                SQLiteDatabase mydatabase = openOrCreateDatabase("org_chat_db", MODE_PRIVATE, null);
                mydatabase.execSQL("insert into MESSAGE values('"+id+"','null','"+title+"','"+description+"','C','"+ new SimpleDateFormat("yyyy/MM/dd").format(Calendar.getInstance().getTime())+"','"+  new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()) +"') ");


                for (int i = 0; i < file_uri.size(); i++) {

                    String[] filePathColumn = { MediaStore.Images.Media.DATA, MediaStore.Video.Media.DATA, MediaStore.Files.FileColumns.DATA };
                    Cursor cursor = getApplication().getContentResolver().query(file_uri.get(i), filePathColumn, null, null, null);
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String filePath = cursor.getString(columnIndex);
                    cursor.close();

                    File file = FileUtil.from(NewCompliant.this,file_uri.get(i));

                    mydatabase.execSQL("insert into FILE values('"+id+"','"+file.getName()+"','"+getRealPathFromURI(file_uri.get(i))+"')");

                }

                mydatabase.close();

                return true;
            }else {
                return false;
            }

        }catch(Exception e){
            System.out.println(e.toString());
            return false;
        }

    }

    public String getSubDeptID(String subdept){

        try{

            SQLiteDatabase mydatabase = getApplicationContext().openOrCreateDatabase("org_chat_db", MODE_PRIVATE, null);

            Cursor resultSet = mydatabase.rawQuery("Select SUBDEPARTMENT_ID from SUBDEPARTMENT where SUBDEPARTMENT = '"+subdept+"'",null);

            Toast.makeText(this, subdept, Toast.LENGTH_SHORT).show();

            String subdept_id = "";
            if(resultSet.moveToFirst())
                subdept_id = resultSet.getString(0);

            resultSet.close();
            mydatabase.close();

            return subdept_id;

        }catch(Exception e){

        }

        return "";
    }

    ArrayAdapter<String> getAdap(String dept){

        ArrayAdapter<String> sub_dept_adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, fetch_local_subdepartment(dept));
        return sub_dept_adapter;

    }

    ArrayList<String> fetch_local_department(){

        ArrayList<String> list = new ArrayList<String>();

        try {

            SQLiteDatabase mydatabase = getApplicationContext().openOrCreateDatabase("org_chat_db", MODE_PRIVATE, null);

            Cursor resultSet = mydatabase.rawQuery("Select * from DEPARTMENT",null);

            if(resultSet.moveToFirst()) {

                do {

                    String dept = resultSet.getString(1);
                    list.add(dept);

                } while (resultSet.moveToNext());

                first_dept = list.get(0);

            }


            resultSet.close();
            mydatabase.close();

        }catch(SQLException e){

        }

        return list;

    }

    ArrayList<String> fetch_local_subdepartment(String department){

        ArrayList<String> list = new ArrayList<String>();

        try{

            SQLiteDatabase mydatabase = getApplicationContext().openOrCreateDatabase("org_chat_db", MODE_PRIVATE, null);

            Cursor resultSet = mydatabase.rawQuery("Select * from SUBDEPARTMENT where DEPARTMENT = '"+department+"' ",null);

            if(resultSet.moveToFirst()) {

                do {

                    String dept = resultSet.getString(1);
                    list.add(dept);

                } while (resultSet.moveToNext());

            }

            resultSet.close();
            mydatabase.close();

        }catch(Exception e){

        }

        return list;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_compliant);

        getSupportActionBar().setTitle("New Compliant");

        post = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        title = (EditText)findViewById(R.id.circular_title);
        description = (EditText)findViewById(R.id.circular_description);
        attachment = (ListView)findViewById(R.id.attachment_list);
        add = (ImageButton)findViewById(R.id.add);
        empty = (TextView)findViewById(R.id.empty);

        popup = (RelativeLayout)findViewById(R.id.popup_layout);

        thumbnail = new ArrayList<Bitmap>();
        is_video = new ArrayList<Boolean>();
        path = new ArrayList<String>();
        file = new ArrayList<File>();
        file_uri = new ArrayList<Uri>();

        adapter = new AttachmentAdapter(NewCompliant.this,path);

        first_dept = null;

        //ArrayAdapter<String> dept_adapter = new ArrayAdapter<String>(
          //      this, android.R.layout.simple_spinner_item, fetch_local_department());

        //ArrayAdapter<String> sub_dept_adapter = new ArrayAdapter<String>(
          //      this, android.R.layout.simple_spinner_item, fetch_local_subdepartment(first_dept));


        attachment.setAdapter(adapter);

        attachment.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int i, long l) {

                AlertDialog dialog = new AlertDialog.Builder(NewCompliant.this)
                        .setTitle("Delete Item")
                        .setMessage("What do you want to delete this item?")
                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                path.remove(i);
                                file_uri.remove(i);

                                adapter.notifyDataSetChanged();

                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .create();
                dialog.show();

                return false;
            }
        });

        adapter.notifyDataSetChanged();

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LayoutInflater layoutInflater = (LayoutInflater) NewCompliant.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View customView = layoutInflater.inflate(R.layout.attachment_popup,null);

                LinearLayout attr;
                ImageButton image, video, document, cancel;

                attr = (LinearLayout)customView.findViewById(R.id.linearLayout);

                cancel = (ImageButton)customView.findViewById(R.id.close);

                image = (ImageButton)attr.findViewById(R.id.image);
                video = (ImageButton)attr.findViewById(R.id.video);
                document = (ImageButton)attr.findViewById(R.id.document);

                //closePopupBtn = (Button) customView.findViewById(R.id.closePopupBtn);

                //instantiate popup window
                final PopupWindow popupWindow = new PopupWindow(customView, ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);

                Animation aniFade = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_in);

                popupWindow.setAnimationStyle(R.style.Animation);
                popupWindow.showAtLocation(popup, Gravity.CENTER, 0, 0);

                image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        performFileSearch("image/*");
                        popupWindow.dismiss();
                    }
                });

                video.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        performFileSearch("video/*");
                        popupWindow.dismiss();
                    }
                });

                document.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        performFileSearch("application/*");
                        popupWindow.dismiss();
                    }
                });

                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        popupWindow.dismiss();
                    }
                });

            }
        });

        post.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {

                String t = title.getText().toString();
                String desc = description.getText().toString();

                if(!t.equals("")) {

                    post.setEnabled(false);

                    if(sendToServer(t,desc,path,null)){
                        Toast.makeText(NewCompliant.this, "Message sent successfully.", Toast.LENGTH_SHORT).show();
                        finish();
                    }

                }else{
                    Toast.makeText(NewCompliant.this, "Please fill the Title", Toast.LENGTH_SHORT).show();
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

                populate_adapter(b,path,isVideo);

            }
        }
    }

}
