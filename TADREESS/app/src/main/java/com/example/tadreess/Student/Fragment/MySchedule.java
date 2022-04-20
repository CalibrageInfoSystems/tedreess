package com.example.tadreess.Student.Fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.tadreess.API;
import com.example.tadreess.AppsContants;
import com.example.tadreess.R;
import com.example.tadreess.Student.EnterLiveClass;
import com.example.tadreess.Student.EnterLiveClassAdapter.EnterLiveClassAdapter;
import com.example.tadreess.Student.EnterLiveClassAdapter.EnterLiveClassGetSet;
import com.example.tadreess.Student.MyScheduleAdapter.MyScheduleAdapter;
import com.example.tadreess.Student.MyScheduleAdapter.MyScheduleGetSet;
import com.example.tadreess.Student.OurPackageAdapter.OurPackageAdapter;
import com.example.tadreess.Student.OurPackageAdapter.OurPackageGetSet;
import com.example.tadreess.Student.OurPackages;

import org.json.JSONArray;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;


public class MySchedule extends Fragment {
    public static final String TAG = MySchedule.class.getSimpleName();
    RecyclerView recyclerview1;
    List<MyScheduleGetSet> arrayList;
    MyScheduleAdapter adapter;
    ProgressBar pBar;

    Spinner spinClasses;

    String strStudentId = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View view = inflater.inflate(R.layout.fragment_my_schedule, container, false);


        spinClasses = view.findViewById(R.id.spinClasses);
        List<String> list = new ArrayList<String>();
        list.add("My Schedules");
        list.add("Completed Classes");
        list.add("Attending Class");
        list.add("Upcoming Class");


        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinClasses.setAdapter(dataAdapter);


        spinClasses.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                switch (position) {

                    case 0: // for item 1
                      //  MySchedule();
                        new MySchedulee().execute(API.GetMyshedules+"userid="+strStudentId+"&type=0&cultureid=1");
                        break;

                    case 1:
                      //  CompletedClasses();
                        new MySchedulee().execute(API.GetMyshedules+"userid="+strStudentId+"&type=1&cultureid=1");
                        break;

                    case 2:
                        new MySchedulee().execute(API.GetMyshedules+"userid="+strStudentId+"&type=2&cultureid=1");
                      //  AttendingClasses();
                        break;
                    case 3:
                        new MySchedulee().execute(API.GetMyshedules+"userid="+strStudentId+"&type=1&cultureid=1");
                    //    UpcomingClasses();
                        break;

                    /* you can have any number of case statements */
                    default:

                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        pBar = view.findViewById(R.id.pBar);
        recyclerview1 = view.findViewById(R.id.recyclerview1);


        AppsContants.sharedpreferences = getActivity().getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
        strStudentId = AppsContants.sharedpreferences.getString(AppsContants.UserId, "");

        // MySchedule() ;
        recyclerview1.setHasFixedSize(true);
        recyclerview1.setLayoutManager(new GridLayoutManager(getActivity(), 1));


        return view;


    }
    public void MySchedule() {
        pBar.setVisibility(View.VISIBLE);
        //  AndroidNetworking.post("http://168.187.113.181:813/TadreesService.svc/LoginUser?Username="+strEmail+"&password="+strPassword+"&cultureid=1")
        AndroidNetworking.post(API.GetMyshedules+"userid="+strStudentId+"&type=0&cultureid=1")
                // AndroidNetworking.post(HTTPURL.Login)
                .addHeaders("Content-Type", "application/json")
                .addHeaders("Accept", "application/json")
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Log.e("fghjghjkfghjfg", response.toString());

                        try {

                            pBar.setVisibility(View.GONE);
                            String str = response.getString("Status");
                            if (str.equals("1")) {
                                // progress.setVisibility(View.GONE);

                                recyclerview1.setVisibility(View.VISIBLE);
                                String strResult = response.getString("Result");
                                JSONArray jsonArray = new JSONArray(strResult);
                                arrayList = new ArrayList<>();
                                for (int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    MyScheduleGetSet GetSet = new MyScheduleGetSet();
                                    GetSet.setAvialableSeats(jsonObject.getString("AvialableSeats"));
                                    GetSet.setDescription(jsonObject.getString("Description"));
                                    GetSet.setFrom(jsonObject.getString("From"));
                                    GetSet.setIsActive(jsonObject.getString("IsActive"));
                                    GetSet.setNo(jsonObject.getString("No"));
                                    GetSet.setTitle(jsonObject.getString("Title"));
                                    GetSet.setTo(jsonObject.getString("To"));
                                    GetSet.setTutor(jsonObject.getString("Tutor"));
                                    GetSet.setScheduleID(jsonObject.getString("scheduleID"));

                                    arrayList.add(GetSet);


                                }

                                adapter = new MyScheduleAdapter(arrayList, getActivity());
                                recyclerview1.setAdapter(adapter);
                                pBar.setVisibility(View.GONE);
                                Log.e("tracing", arrayList.size() + "");


                                Toast.makeText(getActivity(), "my schedules", Toast.LENGTH_SHORT).show();
                                //startActivity(new Intent(EnterLiveClass.this, EnterSecretKey.class));


                            } else {
                                pBar.setVisibility(View.GONE);
                                recyclerview1.setVisibility(View.GONE);
                                Toast.makeText(getActivity(), "you don,t have any  schedule", Toast.LENGTH_SHORT).show();

                            }

                        } catch (Exception ex) {
                            pBar.setVisibility(View.GONE);
                            Log.e("hjsdfahfasdjhfasd", ex.getMessage());

                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        pBar.setVisibility(View.GONE);
                        Log.e("hjsdfahfasdjhfasd", anError.toString());

                    }

                });
    }


    public void CompletedClasses() {
        pBar.setVisibility(View.VISIBLE);
        //  AndroidNetworking.post("http://168.187.113.181:813/TadreesService.svc/LoginUser?Username="+strEmail+"&password="+strPassword+"&cultureid=1")
        AndroidNetworking.post(API.GetMyshedules+"userid="+strStudentId+"&type=1&cultureid=1")
                // AndroidNetworking.post(HTTPURL.Login)
                .addHeaders("Content-Type", "application/json")
                .addHeaders("Accept", "application/json")
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Log.e("fghjghjkfghjfg", response.toString());

                        try {
                            recyclerview1.setVisibility(View.VISIBLE);
                            pBar.setVisibility(View.GONE);
                            String str = response.getString("Status");
                            if (str.equals("1")) {
                                // progress.setVisibility(View.GONE);


                                String strResult = response.getString("Result");
                                JSONArray jsonArray = new JSONArray(strResult);
                                arrayList = new ArrayList<>();
                                for (int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    MyScheduleGetSet GetSet = new MyScheduleGetSet();
                                    GetSet.setAvialableSeats(jsonObject.getString("AvialableSeats"));
                                    GetSet.setDescription(jsonObject.getString("Description"));
                                    GetSet.setFrom(jsonObject.getString("From"));
                                    GetSet.setIsActive(jsonObject.getString("IsActive"));
                                    GetSet.setNo(jsonObject.getString("No"));
                                    GetSet.setTitle(jsonObject.getString("Title"));
                                    GetSet.setTo(jsonObject.getString("To"));
                                    GetSet.setTutor(jsonObject.getString("Tutor"));
                                    GetSet.setScheduleID(jsonObject.getString("scheduleID"));

                                    arrayList.add(GetSet);


                                }

                                adapter = new MyScheduleAdapter(arrayList, getActivity());
                                recyclerview1.setAdapter(adapter);
                                pBar.setVisibility(View.GONE);
                                Log.e("tracing", arrayList.size() + "");


                               // Toast.makeText(getActivity(), "complete classes" + str, Toast.LENGTH_SHORT).show();
                                //startActivity(new Intent(EnterLiveClass.this, EnterSecretKey.class));


                            } else {
                                pBar.setVisibility(View.GONE);

                                Toast.makeText(getActivity(), "you have,t any complet class" , Toast.LENGTH_SHORT).show();

                            }

                        } catch (Exception ex) {
                            pBar.setVisibility(View.GONE);
                            recyclerview1.setVisibility(View.GONE);
                            Log.e("hjsdfahfasdjhfasd", ex.getMessage());

                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        pBar.setVisibility(View.GONE);
                        Log.e("hjsdfahfasdjhfasd", anError.toString());

                    }

                });
    }

    public void AttendingClasses() {
        pBar.setVisibility(View.VISIBLE);
        //  AndroidNetworking.post("http://168.187.113.181:813/TadreesService.svc/LoginUser?Username="+strEmail+"&password="+strPassword+"&cultureid=1")
        AndroidNetworking.post(API.GetMyshedules+"userid="+strStudentId+"&type=2&cultureid=1")
                // AndroidNetworking.post(HTTPURL.Login)
                .addHeaders("Content-Type", "application/json")
                .addHeaders("Accept", "application/json")
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Log.e("fghjghjkfghjfg", response.toString());

                        try {
                            recyclerview1.setVisibility(View.VISIBLE);

                            pBar.setVisibility(View.GONE);
                            String str = response.getString("Status");
                            if (str.equals("1")) {
                                // progress.setVisibility(View.GONE);


                                String strResult = response.getString("Result");
                                JSONArray jsonArray = new JSONArray(strResult);
                                arrayList = new ArrayList<>();
                                for (int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    MyScheduleGetSet GetSet = new MyScheduleGetSet();
                                    GetSet.setAvialableSeats(jsonObject.getString("AvialableSeats"));
                                    GetSet.setDescription(jsonObject.getString("Description"));
                                    GetSet.setFrom(jsonObject.getString("From"));
                                    GetSet.setIsActive(jsonObject.getString("IsActive"));
                                    GetSet.setNo(jsonObject.getString("No"));
                                    GetSet.setTitle(jsonObject.getString("Title"));
                                    GetSet.setTo(jsonObject.getString("To"));
                                    GetSet.setTutor(jsonObject.getString("Tutor"));
                                    GetSet.setScheduleID(jsonObject.getString("scheduleID"));

                                    arrayList.add(GetSet);


                                }

                                adapter = new MyScheduleAdapter(arrayList, getActivity());
                                recyclerview1.setAdapter(adapter);
                                pBar.setVisibility(View.GONE);
                                Log.e("tracing", arrayList.size() + "");


                               // Toast.makeText(getActivity(), "success" + str, Toast.LENGTH_SHORT).show();
                                //startActivity(new Intent(EnterLiveClass.this, EnterSecretKey.class));


                            } else {
                                pBar.setVisibility(View.GONE);
                                recyclerview1.setVisibility(View.GONE);
                                Toast.makeText(getActivity(), "you have,t any attending class", Toast.LENGTH_SHORT).show();

                            }

                        } catch (Exception ex) {
                            pBar.setVisibility(View.GONE);
                            Log.e("hjsdfahfasdjhfasd", ex.getMessage());

                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        pBar.setVisibility(View.GONE);
                        Log.e("hjsdfahfasdjhfasd", anError.toString());

                    }

                });
    }

    public void UpcomingClasses() {
        pBar.setVisibility(View.VISIBLE);
        //  AndroidNetworking.post("http://168.187.113.181:813/TadreesService.svc/LoginUser?Username="+strEmail+"&password="+strPassword+"&cultureid=1")
        AndroidNetworking.post(API.GetMyshedules+"userid="+strStudentId+"&type=3&cultureid=1")
                // AndroidNetworking.post(HTTPURL.Login)
                .addHeaders("Content-Type", "application/json")
                .addHeaders("Accept", "application/json")
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Log.e("fghjghjkfghjfg", response.toString());

                        try {
                            recyclerview1.setVisibility(View.VISIBLE);

                            pBar.setVisibility(View.GONE);
                            String str = response.getString("Status");
                            if (str.equals("1")) {
                                // progress.setVisibility(View.GONE);


                                String strResult = response.getString("Result");
                                JSONArray jsonArray = new JSONArray(strResult);
                                arrayList = new ArrayList<>();
                                for (int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    MyScheduleGetSet GetSet = new MyScheduleGetSet();
                                    GetSet.setAvialableSeats(jsonObject.getString("AvialableSeats"));
                                    GetSet.setDescription(jsonObject.getString("Description"));
                                    GetSet.setFrom(jsonObject.getString("From"));
                                    GetSet.setIsActive(jsonObject.getString("IsActive"));
                                    GetSet.setNo(jsonObject.getString("No"));
                                    GetSet.setTitle(jsonObject.getString("Title"));
                                    GetSet.setTo(jsonObject.getString("To"));
                                    GetSet.setTutor(jsonObject.getString("Tutor"));
                                    GetSet.setScheduleID(jsonObject.getString("scheduleID"));

                                    arrayList.add(GetSet);


                                }

                                adapter = new MyScheduleAdapter(arrayList, getActivity());
                                recyclerview1.setAdapter(adapter);
                                pBar.setVisibility(View.GONE);
                                Log.e("tracing", arrayList.size() + "");


                               // Toast.makeText(getActivity(), "success" + str, Toast.LENGTH_SHORT).show();
                                //startActivity(new Intent(EnterLiveClass.this, EnterSecretKey.class));


                            } else {

                                recyclerview1.setVisibility(View.GONE);
                                pBar.setVisibility(View.GONE);

                                Toast.makeText(getActivity(), "you have,t any upcoming class", Toast.LENGTH_SHORT).show();

                            }

                        } catch (Exception ex) {
                            pBar.setVisibility(View.GONE);
                            Log.e("hjsdfahfasdjhfasd", ex.getMessage());

                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        pBar.setVisibility(View.GONE);
                        Log.e("hjsdfahfasdjhfasd", anError.toString());

                    }

                });
    }


    private class MySchedulee  extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.i(TAG, "MySchedulee API Call");
        }

        @Override
        protected String doInBackground(String... strings) {
            String APIURL = strings[0];
            Log.i(TAG, "MySchedulee API URL:" + APIURL);
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
            Log.i(TAG, "MySchedulee API URL:" + xmlString);

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
           ArrayList<MyScheduleGetSet> scheduleGetSets = new ArrayList<>();
            int eventType = parser.getEventType();
            MyScheduleGetSet currentPlayer = null;

            while (eventType != XmlPullParser.END_DOCUMENT) {
                String eltName ;

                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        eltName = parser.getName();
Log.e("eltName==",eltName);
                        if ("Myshedule".equals(eltName)) {
                            currentPlayer = new MyScheduleGetSet();
                            scheduleGetSets.add(currentPlayer);
                        } else if (currentPlayer != null) {
                            if ("Title".equals(eltName)) {
                                currentPlayer.Title = parser.nextText();
                            }
                            else if ("Description".equals(eltName)) {
                                currentPlayer.Description = parser.nextText();
                            } else if ("Tutor".equals(eltName)) {
                                currentPlayer.Tutor = parser.nextText();

                            }else if ("From".equals(eltName)) {
                                currentPlayer.From = parser.nextText();
                            }
                            else if ("To".equals(eltName)) {
                                currentPlayer.To = parser.nextText();

                            }
                            else if ("No".equals(eltName)) {
                                currentPlayer.No = parser.nextText();

                            }
                            else if ("AvialableSeats".equals(eltName)) {
                                currentPlayer.AvialableSeats = parser.nextText();

                            }
                            else if ("scheduleID".equals(eltName)) {
                                currentPlayer.scheduleID = parser.nextText();

                            }
                            else if ("IsActive".equals(eltName)) {
                                currentPlayer.IsActive = parser.nextText();

                            }
                        }
                        break;
                }

                eventType = parser.next();
            }

            printPlayers(scheduleGetSets);
        }

        private void printPlayers(ArrayList<MyScheduleGetSet> scheduleGetSets) {

            for (int i = 0; i < scheduleGetSets.size(); i++) {
                Log.e("OUTPUT", scheduleGetSets.get(i).toString());
            }

            adapter = new MyScheduleAdapter(scheduleGetSets, getContext());
            recyclerview1.setAdapter(adapter);
           // Pbar.setVisibility(View.GONE);

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

