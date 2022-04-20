package com.example.tadreess.Student;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.tadreess.API;
import com.example.tadreess.AppsContants;
import com.example.tadreess.CommonHelper;
import com.example.tadreess.EnterSecretKey;
import com.example.tadreess.IEnterLiveClassListner;
import com.example.tadreess.LoginActivity;
import com.example.tadreess.R;
import com.example.tadreess.Student.EnterLiveClassAdapter.EnterLiveClassAdapter;
import com.example.tadreess.Student.EnterLiveClassAdapter.EnterLiveClassGetSet;
import com.example.tadreess.Student.OurPackageAdapter.OurPackageAdapter;
import com.example.tadreess.Student.OurPackageAdapter.OurPackageGetSet;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EnterLiveClass extends AppCompatActivity implements IEnterLiveClassListner {

    public static final String TAG = EnterLiveClass.class.getSimpleName();

    TextView txtSubject, txtTutorName, txtTime, txtSkip, txtStudentName;

    String strName = "";


    String Room_id = "";

    Button btnEnterClass;

    ProgressBar Pbar;

    String strUserId = "";


    String Sub, Tutor;


    RecyclerView recyclerview1;
    List<EnterLiveClassGetSet> arrayList;
    EnterLiveClassAdapter adapter;
    ProgressBar bar;

    ImageView imgMenu;

    String strStudentName = "";


    String strStudentId = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_live_class);

        txtSubject = findViewById(R.id.txtSubject);
        // txtTutorName = findViewById(R.id.txtTutorName);
        txtTime = findViewById(R.id.txtTime);
        btnEnterClass = findViewById(R.id.btnEnterClass);
        Pbar = findViewById(R.id.Pbar);
        txtSkip = findViewById(R.id.txtSkip);
        txtStudentName = findViewById(R.id.txtStudentName);
        recyclerview1 = findViewById(R.id.recyclerview1);


        txtSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EnterLiveClass.this, NavigationActivity.class));
            }
        });

        AppsContants.sharedpreferences = getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
        strUserId = AppsContants.sharedpreferences.getString(AppsContants.UserId, "");

        Log.e("dfjgjdfgjhdgfhdsgf", strUserId);

        new ShowLiveClass().execute(API.GetMyClasses+"userid="+strUserId+"&cultureid=1");
        // ShowLiveClass();
        //  API.GetMyClasses+"userid="+strUserId+"&cultureid=1"
        recyclerview1.setHasFixedSize(true);
        recyclerview1.setLayoutManager(new GridLayoutManager(EnterLiveClass.this, 1));


    }



  /*  public void ShowLiveClass() {
        Pbar.setVisibility(View.VISIBLE);
      //  AndroidNetworking.post("http://168.187.113.181:813/TadreesService.svc/LoginUser?Username="+strEmail+"&password="+strPassword+"&cultureid=1")
        AndroidNetworking.post("http://168.187.113.181:813/TadreesService.svc/GetMyClasses?userid="+strUserId+"&cultureid=1")
                // AndroidNetworking.post(HTTPURL.Login)
                .addHeaders("Content-Type","application/json")
                .addHeaders("Accept","application/json")
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Log.e("jgjdgretuiydfjh",response.toString());

                        try {

                            Pbar.setVisibility(View.GONE);
                            String str = response.getString("Status");
                            if (str.equals("1")) {
                                // progress.setVisibility(View.GONE);


                                String strResult=response.getString("Result");
                                JSONArray jsonArray= new JSONArray(strResult);

                                for (int i = 0; i <jsonArray.length() ; i++) {




                                    JSONObject jsonObject=jsonArray.getJSONObject(i);
                                    String strEndTime=jsonObject.getString("EndTime");
                                    String strIsActive=jsonObject.getString("IsActive");
                                    strName=jsonObject.getString("Name");
                                    String strPgoto=jsonObject.getString("Photo");
                                    String strScheduleId=jsonObject.getString("ScheduleId");
                                    String strStageId=jsonObject.getString("StageId");
                                    String strStartTime=jsonObject.getString("StartTime");
                                    String strSubject=jsonObject.getString("Subject");
                                    String strTutor=jsonObject.getString("Tutor");



                                    txtSubject.setText(strSubject);
                                    txtTutorName.setText(strTutor);
                                    txtStudentName.setText(strName);
                                    txtTime.setText(strStartTime+"-"+strEndTime);

                                }




                                AppsContants.sharedpreferences = getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = AppsContants.sharedpreferences.edit();
                                editor.putString(AppsContants.StudentName,strName);
                                editor.commit();






                                Toast.makeText(EnterLiveClass.this, "success" + str, Toast.LENGTH_SHORT).show();
                                //startActivity(new Intent(EnterLiveClass.this, EnterSecretKey.class));




                            } else {
                                Pbar.setVisibility(View.GONE);

                                Toast.makeText(EnterLiveClass.this, "" + str, Toast.LENGTH_SHORT).show();

                            }

                        } catch (Exception ex) {
                            Pbar.setVisibility(View.GONE);
                            Log.e("hjsdfahfasdjhfasd", ex.getMessage());

                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Pbar.setVisibility(View.GONE);
                        Log.e("hjsdfahfasdjhfasd", anError.toString());

                    }

                });
    }*/

//
//    public void ShowLiveClass() {
//        Pbar.setVisibility(View.VISIBLE);
//        //  AndroidNetworking.post("http://168.187.113.181:813/TadreesService.svc/LoginUser?Username="+strEmail+"&password="+strPassword+"&cultureid=1")
//        AndroidNetworking.post(API.GetMyClasses + "userid=" + strUserId + "&cultureid=1")
//                .addHeaders("Content-Type", "application/json")
//                .addHeaders("Accept", "application/json")
//                .setTag("test")
//                .setPriority(Priority.MEDIUM)
//                .build()
//                .getAsJSONObject(new JSONObjectRequestListener() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//
//                        Log.e("jgjdgretuiydfjh", response.toString());
//
//                        try {
//
//                            Pbar.setVisibility(View.GONE);
//                            String str = response.getString("Status");
//                            if (str.equals("1")) {
//                                // progress.setVisibility(View.GONE);
//
//
//                                String strResult = response.getString("Result");
//                                JSONArray jsonArray = new JSONArray(strResult);
//
//
//                                arrayList = new ArrayList<>();
//                                for (int i = 0; i < jsonArray.length(); i++) {
//
//                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
//
//
//                                    strName = jsonObject.getString("Name");
//
//
//                                    txtStudentName.setText(strName);
//
//
//                                    EnterLiveClassGetSet GetSet = new EnterLiveClassGetSet();
//                                    GetSet.setEndTime(jsonObject.getString("EndTime"));
//                                    GetSet.setIsActive(jsonObject.getString("IsActive"));
//                                    GetSet.setName(jsonObject.getString("Name"));
//                                    GetSet.setPhoto(jsonObject.getString("Photo"));
//                                    GetSet.setScheduleId(jsonObject.getString("ScheduleId"));
//                                    GetSet.setStageId(jsonObject.getString("StageId"));
//                                    GetSet.setStartTime(jsonObject.getString("StartTime"));
//                                    GetSet.setSubject(jsonObject.getString("Subject"));
//                                    GetSet.setTutor(jsonObject.getString("Tutor"));
//                                    GetSet.setTutor(jsonObject.getString("Tutor"));
//                                    GetSet.setRoomId(jsonObject.getString("RoomID"));
//
//
//                                    arrayList.add(GetSet);
//
//
//                                }
//
//                                AppsContants.sharedpreferences = getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
//                                SharedPreferences.Editor editor = AppsContants.sharedpreferences.edit();
//                                editor.putString(AppsContants.UserName, strName);
//                                editor.commit();
//
//                                Log.e("gjfjgfjfgjgjdfgh", strName);
//
//
//                                adapter = new EnterLiveClassAdapter(arrayList, EnterLiveClass.this, EnterLiveClass.this);
//                                recyclerview1.setAdapter(adapter);
//                                Pbar.setVisibility(View.GONE);
//                                Log.e("tracing", arrayList.size() + "");
//
//
//                                Toast.makeText(EnterLiveClass.this, "Available live classes", Toast.LENGTH_SHORT).show();
//                                //startActivity(new Intent(EnterLiveClass.this, EnterSecretKey.class));
//
//
//                            } else {
//                                Pbar.setVisibility(View.GONE);
//
//                                Toast.makeText(EnterLiveClass.this, "you don,t have any live class available", Toast.LENGTH_SHORT).show();
//
//                            }
//
//                        } catch (Exception ex) {
//                            Pbar.setVisibility(View.GONE);
//                            Log.e("hjsdfahfasdjhfasd", ex.getMessage());
//
//                        }
//                    }
//
//                    @Override
//                    public void onError(ANError anError) {
//                        Pbar.setVisibility(View.GONE);
//                        Log.e("hjsdfahfasdjhfasd", anError.toString());
//
//                    }
//
//                });
//    }


    @Override
    public void onEnterClick(EnterLiveClassGetSet enterLiveClassGetSet) {

        if (CommonHelper.appInstalledOrNot(this, ""))
            Log.d(TAG, "------------------------------------------------------");
        Log.d(TAG, "getEndTime : " + enterLiveClassGetSet.getEndTime());
        Log.d(TAG, "getIsActive: " + enterLiveClassGetSet.getIsActive());
        Log.d(TAG, "getName    : " + enterLiveClassGetSet.getName());
        Log.d(TAG, "getPhoto   : " + enterLiveClassGetSet.getPhoto());
        Log.d(TAG, "getScheduleId : " + enterLiveClassGetSet.getScheduleId());
        Log.d(TAG, "getStageId : " + enterLiveClassGetSet.getStageId());
        Log.d(TAG, "getStartTime : " + enterLiveClassGetSet.getStartTime());
        Log.d(TAG, "getSubject : " + enterLiveClassGetSet.getSubject());
        Log.d(TAG, "getTutor : " + enterLiveClassGetSet.getTutor());

        if (CommonHelper.appInstalledOrNot(this, "com.electa.app")) {
            Intent i = new Intent(EnterLiveClass.this, WebActivity.class);
            i.putExtra("name", strName);
            i.putExtra("roomid", Room_id);
            Log.e("RoomId======",Room_id +" "+ strName);
            startActivity(i);
          //  startActivity(new Intent(this, WebActivity.class));
        } else {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.electa.app&hl=en_IN"));
            startActivity(browserIntent);
        }
    }

    private class ShowLiveClass extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.i(TAG, "ShowLiveClass API Call");
        }

        @Override
        protected String doInBackground(String... strings) {
            String APIURL = strings[0];
            Log.i(TAG, "ShowLiveClass API URL:" + APIURL);
            BufferedReader br = null;
            try {
                HttpURLConnection conn = (HttpURLConnection) (new URL(APIURL)).openConnection();
                br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                String line;
                final StringBuilder sb = new StringBuilder();
                while ((line = br.readLine()) != null) {
                    sb.append(line).append("\n");
                }


                // some XML String previously created

                return sb.toString();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            } finally {
                try {
                    if (br != null) br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            String Status = "";

            //    Progress.setVisibility(View.GONE);

            String xmlString = s;
            XmlPullParserFactory parserFactory;
            try {
                parserFactory = XmlPullParserFactory.newInstance();
                XmlPullParser parser = parserFactory.newPullParser();
                InputStream is= new ByteArrayInputStream(xmlString.getBytes(StandardCharsets.UTF_8));
                parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                parser.setInput(is, null);

                processParsing(parser);

            } catch (XmlPullParserException e) {

            } catch (IOException e) {
            }
        }

        private void processParsing(XmlPullParser parser) throws IOException, XmlPullParserException{
            ArrayList<EnterLiveClassGetSet> players = new ArrayList<>();
            int eventType = parser.getEventType();
            EnterLiveClassGetSet currentPlayer = null;

            while (eventType != XmlPullParser.END_DOCUMENT) {
                String eltName = null;

                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        eltName = parser.getName();

                        if ("MyClass".equals(eltName)) {
                            currentPlayer = new EnterLiveClassGetSet();
                            players.add(currentPlayer);
                        } else if (currentPlayer != null) {
                            if ("Subject".equals(eltName)) {
                                currentPlayer.Subject = parser.nextText();
                            }
                            else if ("StartTime".equals(eltName)) {
                                currentPlayer.StartTime = parser.nextText();
                            } else if ("EndTime".equals(eltName)) {
                                currentPlayer.EndTime = parser.nextText();

                            }else if ("Tutor".equals(eltName)) {
                                currentPlayer.Tutor = parser.nextText();
                            }
                            else if ("RoomId".equals(eltName)) {
                                currentPlayer.RoomId = parser.nextText();
                                Room_id= currentPlayer.RoomId;
                                Log.e("RoomId===",Room_id);
                            }
                            else if ("Name".equals(eltName)) {
                                currentPlayer.Name = parser.nextText();
                                strName= currentPlayer.Name;
                                Log.e("strName===",strName);
                            }
                        }
                        break;
                }

                eventType = parser.next();
            }

            printPlayers(players);
        }

        private void printPlayers(ArrayList<EnterLiveClassGetSet> players) {

            for (int i = 0; i < players.size(); i++) {
                Log.e("OUTPUT", players.get(i).toString());
            }

            adapter = new EnterLiveClassAdapter(players, EnterLiveClass.this, EnterLiveClass.this);
            recyclerview1.setAdapter(adapter);
            Pbar.setVisibility(View.GONE);

//        }
//            for (EnterLiveClassGetSet player : players) {
//                builder.append(player.name).append("\n").
//                        append(player.age).append("\n").
//                        append(player.position).append("\n\n");
//            }
//
//            txt.setText(builder.toString());
//        }

    }}}

