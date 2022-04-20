package com.example.tadreess.Student.Fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.tadreess.API;
import com.example.tadreess.AppsContants;
import com.example.tadreess.MainActivity;
import com.example.tadreess.R;
import com.example.tadreess.Student.OurPackageAdapter.OurPackageAdapter;
import com.example.tadreess.Student.OurPackageAdapter.OurPackageGetSet;
import com.example.tadreess.Student.OurPackageAdapter.StudentUpdateProfile;
import com.example.tadreess.Student.OurPackages;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {
    public static final String TAG = HomeFragment.class.getSimpleName();

    RecyclerView recyclerview1;
    List<OurPackageGetSet> arrayList;
    OurPackageAdapter adapter;
    ProgressBar bar;

    ImageView imgMenu;

    String strStudentName="";


    String strStudentId = "";

    RelativeLayout relative;


    TextView txtMyProfile,txtMySchedule,txtMyAccount,txtChangePassword,txtAboutUs,txtFaq,
            txtServices,txtContactUs,txtLogout,txtStudentName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);


        bar = view.findViewById(R.id.bar);
        recyclerview1 = view.findViewById(R.id.recyclerview1);
        relative = view.findViewById(R.id.relative);


        txtMyProfile = view.findViewById(R.id.txtMyProfile);
        txtMySchedule = view.findViewById(R.id.txtMySchedule);
        txtMyAccount = view.findViewById(R.id.txtMyAccount);
        txtChangePassword =view. findViewById(R.id.txtChangePassword);
        txtAboutUs = view.findViewById(R.id.txtAboutUs);
        txtFaq = view.findViewById(R.id.txtFaq);
        txtServices = view.findViewById(R.id.txtServices);
        txtContactUs = view.findViewById(R.id.txtContactUs);
        txtLogout = view.findViewById(R.id.txtLogout);
        txtStudentName =view. findViewById(R.id.txtStudentName);

        AppsContants.sharedpreferences = getActivity().getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
        strStudentId = AppsContants.sharedpreferences.getString(AppsContants.UserId, "");
        strStudentName = AppsContants.sharedpreferences.getString(AppsContants.StudentName, "");

        txtStudentName.setText(strStudentName);






        new ShowLiveClass().execute(API.GetStudentPackages+"studentid="+strStudentId+"&cultureid=1");
       // ShowLiveClass();
        recyclerview1.setHasFixedSize(true);
        recyclerview1.setLayoutManager(new GridLayoutManager(getActivity(), 1));

   return view;




    }


    public void ShowLiveClass() {
        bar.setVisibility(View.VISIBLE);
        //  AndroidNetworking.post("http://168.187.113.181:813/TadreesService.svc/LoginUser?Username="+strEmail+"&password="+strPassword+"&cultureid=1")
        AndroidNetworking.post(API.GetStudentPackages+"studentid="+strStudentId+"&cultureid=1")
                // AndroidNetworking.post(HTTPURL.Login)
                .addHeaders("Content-Type", "application/json")
                .addHeaders("Accept", "application/json")
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Log.e("jgjdgretuiydfjh", response.toString());

                        try {

                            bar.setVisibility(View.GONE);
                            String str = response.getString("Status");
                            if (str.equals("1")) {
                                // progress.setVisibility(View.GONE);


                                String strResult = response.getString("Result");
                                JSONArray jsonArray = new JSONArray(strResult);
                                arrayList = new ArrayList<>();
                                for (int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    OurPackageGetSet GetSet = new OurPackageGetSet();
                                    GetSet.setDuration(jsonObject.getString("Duration"));
                                    GetSet.setName(jsonObject.getString("Name"));
                                    GetSet.setPackageId(jsonObject.getString("PackageId"));
                                    GetSet.setScheduleId(jsonObject.getString("ScheduleId"));
                                    GetSet.setScheduleName(jsonObject.getString("ScheduleName"));
                                    GetSet.setScheduleStatus(jsonObject.getString("ScheduleStatus"));
                                    GetSet.setSubjectName(jsonObject.getString("SubjectName"));

                                    arrayList.add(GetSet);


                                }

                                adapter = new OurPackageAdapter(arrayList, getActivity());
                                recyclerview1.setAdapter(adapter);
                                bar.setVisibility(View.GONE);
                                Log.e("tracing", arrayList.size() + "");


                                Toast.makeText(getActivity(), "Available packages" , Toast.LENGTH_SHORT).show();
                                //startActivity(new Intent(EnterLiveClass.this, EnterSecretKey.class));


                            } else {
                                bar.setVisibility(View.GONE);

                                Toast.makeText(getActivity(), "No package available  for you", Toast.LENGTH_SHORT).show();

                            }

                        } catch (Exception ex) {
                            bar.setVisibility(View.GONE);
                            Log.e("hjsdfahfasdjhfasd", ex.getMessage());

                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        bar.setVisibility(View.GONE);
                        Log.e("hjsdfahfasdjhfasd", anError.toString());

                    }

                });
    }


    private class ShowLiveClass extends AsyncTask<String,String,String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.i(TAG, "home API Call");
        }

        @Override
        protected String doInBackground(String... strings) {
            String APIURL = strings[0];
            Log.i(TAG, "home API URL:" + APIURL);
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

            //  Progress.setVisibility(View.GONE);

            String xmlString = s;
            Log.i(TAG, "On Login Responce :" + xmlString);

        }
    }}