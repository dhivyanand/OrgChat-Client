package com.example.system.orgchat_client.Services;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.IBinder;
import android.widget.Toast;

import com.example.system.orgchat_client.Constant;
import com.example.system.orgchat_client.Network.APIRequest;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static com.example.system.orgchat_client.Constant.password;

public class ApplicationBackgroundService extends Service {
    public ApplicationBackgroundService() {

    }

    @Override
    public IBinder onBind(Intent intent) {

        throw new UnsupportedOperationException("Not yet implemented");
    }

    public boolean check_dept(String uname, String pass){

        try{

        SQLiteDatabase mydatabase = getApplicationContext().openOrCreateDatabase("org_chat_db", MODE_PRIVATE, null);

        Cursor resultSet = mydatabase.rawQuery("Select LAST_UPDATE, COUNT from DATE where TYPE='Department'",null);

        if(resultSet.moveToFirst()) {

            String lastupdate = resultSet.getString(0);
            String count = resultSet.getString(1);

            Map<String,String> req = new HashMap<String,String>();
            req.put("id",uname);
            req.put("password",pass);
            req.put("user_agent","user");
            req.put("last_update",lastupdate);
            req.put("count",count);

            String res = APIRequest.processRequest(req, Constant.root_URL+"checkDepartmentUpdates.php",getApplicationContext());
            org.json.JSONObject obj = new org.json.JSONObject(res);

            String result = (String)obj.get("result");

                if(result.equals("TRUE"))
                    return true;
                else
                    return false;


        }else{

            Map<String,String> req = new HashMap<String,String>();
            req.put("id",uname);
            req.put("password",pass);
            req.put("user_agent","user");
            req.put("last_update","0");
            req.put("count","0");

            String res = APIRequest.processRequest(req, Constant.root_URL+"checkDepartmentUpdates.php",getApplicationContext());
            org.json.JSONObject obj = new org.json.JSONObject(res);

            String result = (String)obj.get("result");

            resultSet.close();
            mydatabase.close();

            if(result.equals("TRUE"))
                return true;
            else
                return false;



        }



    }catch(Exception e){

    }

        return true;
    }

    public void check_and_sync_circular(String uname, String pass){

        try {

            SQLiteDatabase mydatabase = getApplicationContext().openOrCreateDatabase("org_chat_db", MODE_PRIVATE, null);

            Cursor resultSet = mydatabase.rawQuery("Select LAST_UPDATE, COUNT from DATE where TYPE='Circular'",null);

            String res = null;

            if(resultSet.moveToFirst()) {

                String lastupdate = resultSet.getString(0);
                String count = resultSet.getString(1);

                Map<String,String> req = new HashMap<String,String>();
                req.put("id",uname);
                req.put("password",pass);
                req.put("time_stamp",lastupdate);

                res = APIRequest.processRequest(req, Constant.root_URL+"syncCircular.php",getApplicationContext());

            }else{

                Map<String,String> req = new HashMap<String,String>();
                req.put("id",uname);
                req.put("password",pass);
                req.put("time_stamp","all");

                res = APIRequest.processRequest(req, Constant.root_URL+"syncCircular.php",getApplicationContext());

            }


            JSONArray obj = null;

                obj = (JSONArray) new JSONParser().parse(res);

                Iterator<JSONObject> iterator = obj.iterator();
                JSONObject key = null;
                int i=0;

                mydatabase.execSQL("delete from CIRCULAR");

                String id, title, content, attachment_list, time=null;

                while (iterator.hasNext()) {
                    //iterator.next().toJSONString();
                    JSONObject dept = iterator.next();

                     id = (String)dept.get("id");

                     title = (String)dept.get("title");

                     content = (String)dept.get("content");

                     time = (String)dept.get("time");

                     attachment_list = (String)dept.get("file");

                     String attachments[] = attachment_list.split("&");

                     //timebeing
                    mydatabase.execSQL("delete from FILE where MESSAGE_ID = '"+id+"'");

                     for(int c=0 ; c < attachments.length ; c++) {
                         mydatabase.execSQL("insert into FILE values('" + id + "','" + attachments[c] + "','not_available')");
                     }
                    mydatabase.execSQL("insert into CIRCULAR values('"+id+"','"+title+"','"+content+"','"+time+"')");

                    i++;

                }

                mydatabase.execSQL("delete from DATE where TYPE = 'circular'");
                mydatabase.execSQL("insert into DATE values('circular','"+time+"','"+String.valueOf(i)+"')");

            resultSet.close();
            mydatabase.close();

        }catch(Exception e){
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }

    }

    public void sync_department(String uname, String pass) {

        try {

            SQLiteDatabase mydatabase = getApplicationContext().openOrCreateDatabase("org_chat_db", MODE_PRIVATE, null);

            Map<String,String> req = new HashMap<String,String>();
            req.put("id",uname);
            req.put("password",pass);
            req.put("user_agent","user");

            String res = APIRequest.processRequest(req, Constant.root_URL+"fetchAllDept.php",getApplicationContext());

            mydatabase.execSQL("delete from DEPARTMENT");

            JSONArray obj = null;
            try {
                obj = (JSONArray) new JSONParser().parse(res);

                Iterator<JSONObject> iterator = obj.iterator();
                JSONObject key = null;
                int i=0;
                String last_update="0";


                mydatabase.execSQL("delete from DEPARTMENT");

                while (iterator.hasNext()) {
                    //iterator.next().toJSONString();
                    JSONObject dept = iterator.next();

                    String dept_name = (String)dept.get("name");

                    String dept_id = (String)dept.get("id");

                    last_update = (String)dept.get("last_update");

                    mydatabase.execSQL("insert into DEPARTMENT values ('"+dept_id+"','"+dept+"')");

                    Map<String,String> subdpt = new HashMap<String,String>();

                    try {

                        JSONArray subdept = (JSONArray) dept.get("subdepartment");

                        System.out.println(dept_name + "\n" + dept_id);

                        Iterator<JSONObject> deptiterator = subdept.iterator();

                        while (deptiterator.hasNext()) {

                            JSONObject object = (JSONObject) deptiterator.next();

                            String subdeptname = (String) object.get("name");
                            String subdeptid = (String) object.get("id");

                            mydatabase.execSQL("insert into SUBDEPARTMENT values ('" + subdeptid + "','" + subdept + "','" + dept + "')");

                            System.out.println("\t" + subdeptname + "   " + subdeptid);

                        }
                    }catch(Exception e){

                    }

                    i++;

                }

                mydatabase.execSQL("delete from DATE where TYPE = 'Department' ");
                mydatabase.execSQL("insert into DATE values('Department','"+last_update+"','"+String.valueOf(i)+"')");


            } catch (Exception e) {
                e.printStackTrace();
            }


            mydatabase.close();

        }catch(SQLException e){

        }

    }

    @Override
    public void onCreate(){

        Toast.makeText(this, "Started the Service.", Toast.LENGTH_SHORT).show();

        SharedPreferences sharedpreferences = getSharedPreferences("AppSession", Context.MODE_PRIVATE);

        String status = sharedpreferences.getString("status","nil");
        String uname = sharedpreferences.getString("user","nil");
        String pass = sharedpreferences.getString("password","nil");

        if(status.equals("verified")){


            if (check_dept(uname, pass)) {

                sync_department(uname, pass);

            }

            check_and_sync_circular(uname, pass);

        }

    }

}
