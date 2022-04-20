package com.example.tadreess.Student;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.tadreess.AppsContants;
import com.example.tadreess.R;
import com.example.tadreess.Student.ClassDetailsAdapter.ClassDetailsAdapter;
import com.example.tadreess.Student.ClassDetailsAdapter.ClassDetailsGetSet;
import com.example.tadreess.Student.MyScheduleAdapter.MyScheduleAdapter;
import com.example.tadreess.Student.MyScheduleAdapter.MyScheduleGetSet;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ClassDetailsActivity extends AppCompatActivity {
    RecyclerView recyclerview1;
    List<ClassDetailsGetSet> arrayList;
    ClassDetailsAdapter adapter;
    ProgressBar pBar;

    ImageView imgBack;

    Spinner spinClasses;

    String strStudentId = "",strScheduleId="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_details);




        pBar = findViewById(R.id.pBar);
        recyclerview1 = findViewById(R.id.recyclerview1);
        imgBack = findViewById(R.id.imgBack);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        AppsContants.sharedpreferences = getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
        strStudentId = AppsContants.sharedpreferences.getString(AppsContants.UserId, "");
        strScheduleId = AppsContants.sharedpreferences.getString(AppsContants.ScheduleId, "");

        // MySchedule() ;
        recyclerview1.setHasFixedSize(true);
        recyclerview1.setLayoutManager(new GridLayoutManager(ClassDetailsActivity.this, 1));



        ClassDetails();


    }




    public void ClassDetails() {
        pBar.setVisibility(View.VISIBLE);
        //  AndroidNetworking.post("http://168.187.113.181:813/TadreesService.svc/LoginUser?Username="+strEmail+"&password="+strPassword+"&cultureid=1")
        AndroidNetworking.post("http://168.187.113.181:813/TadreesService.svc/GetClassDetails?type=2&userid=" + strStudentId + "&scheduleid=" + strScheduleId + "&cultureid=1")
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
                                    ClassDetailsGetSet GetSet = new ClassDetailsGetSet();
                                    GetSet.setLoginDate(jsonObject.getString("LoginDate"));
                                    GetSet.setLoginTime(jsonObject.getString("LoginTime"));
                                    GetSet.setRecodings(jsonObject.getString("Recodings"));
                                    GetSet.setTutor(jsonObject.getString("Tutor"));

                                    arrayList.add(GetSet);


                                }

                                adapter = new ClassDetailsAdapter(arrayList,ClassDetailsActivity.this);
                                recyclerview1.setAdapter(adapter);
                                pBar.setVisibility(View.GONE);
                                Log.e("tracing", arrayList.size() + "");


                               // Toast.makeText(ClassDetailsActivity.this, "success" + str, Toast.LENGTH_SHORT).show();
                                //startActivity(new Intent(EnterLiveClass.this, EnterSecretKey.class));


                            } else {
                                pBar.setVisibility(View.GONE);
                                recyclerview1.setVisibility(View.GONE);
                                Toast.makeText(ClassDetailsActivity.this, "No details available" , Toast.LENGTH_SHORT).show();

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


}
