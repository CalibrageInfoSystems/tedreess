package com.example.tadreess.Student;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
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
import com.example.tadreess.AppsContants;
import com.example.tadreess.MainActivity;
import com.example.tadreess.R;
import com.example.tadreess.Student.OurPackageAdapter.OurPackageAdapter;
import com.example.tadreess.Student.OurPackageAdapter.OurPackageGetSet;
import com.example.tadreess.Student.OurPackageAdapter.StudentUpdateProfile;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.example.tadreess.R.color.colorPrimary;

public class OurPackages extends AppCompatActivity {


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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_our_packages);

        bar = findViewById(R.id.bar);
        recyclerview1 = findViewById(R.id.recyclerview1);
        imgMenu = findViewById(R.id.imgMenu);
        relative = findViewById(R.id.relative);


        txtMyProfile = findViewById(R.id.txtMyProfile);
        txtMySchedule = findViewById(R.id.txtMySchedule);
        txtMyAccount = findViewById(R.id.txtMyAccount);
        txtChangePassword = findViewById(R.id.txtChangePassword);
        txtAboutUs = findViewById(R.id.txtAboutUs);
        txtFaq = findViewById(R.id.txtFaq);
        txtServices = findViewById(R.id.txtServices);
        txtContactUs = findViewById(R.id.txtContactUs);
        txtLogout = findViewById(R.id.txtLogout);
        txtStudentName = findViewById(R.id.txtStudentName);

        AppsContants.sharedpreferences = getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
        strStudentId = AppsContants.sharedpreferences.getString(AppsContants.UserId, "");
        strStudentName = AppsContants.sharedpreferences.getString(AppsContants.StudentName, "");

        txtStudentName.setText(strStudentName);

        txtMyProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(OurPackages.this, StudentUpdateProfile.class));
            }
        });


        txtLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ForgotPass();
            }
        });

        imgMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expand();
            }
        });
        ShowLiveClass();
        recyclerview1.setHasFixedSize(true);
        recyclerview1.setLayoutManager(new GridLayoutManager(OurPackages.this, 1));

    }


   @SuppressLint("ResourceAsColor")
    public void expand() {
        relative.measure(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        final int targtetHeight = relative.getMeasuredHeight();
        if (relative.isShown()) {
            collapse(relative);

        } else {
           relative.getLayoutParams().height = 0;
           relative.setVisibility(View.VISIBLE);
           //relative.setBackgroundColor(colorPrimary);
            Animation a = new Animation() {
                @Override
                protected void applyTransformation(float interpolatedTime,
                                                   Transformation t) {
                    relative.getLayoutParams().height = interpolatedTime == 1 ? LinearLayout.LayoutParams.WRAP_CONTENT
                            : (int) (targtetHeight * interpolatedTime);
                  relative.requestLayout();
                }

                @Override
                public boolean willChangeBounds() {
                    return true;
                }
            };
            a.setDuration((int) (targtetHeight + 300));
            relative.startAnimation(a);
           // Showoffer(viewholder, id);
        }

    }


    public void collapse(final View v) {
        final int initialHeight = v.getMeasuredHeight();
        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime,
                                               Transformation t) {
                if (interpolatedTime == 1) {
                    v.setVisibility(View.GONE);
                } else {
                    v.getLayoutParams().height = initialHeight
                            - (int) (initialHeight * interpolatedTime);
                    v.requestLayout();
                }
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        a.setDuration((int) (v.getLayoutParams().height + 300));
        v.startAnimation(a);
    }

    public void ShowLiveClass() {
        bar.setVisibility(View.VISIBLE);
        //  AndroidNetworking.post("http://168.187.113.181:813/TadreesService.svc/LoginUser?Username="+strEmail+"&password="+strPassword+"&cultureid=1")
        AndroidNetworking.post("http://168.187.113.181:813/TadreesService.svc/GetStudentPackages?studentid="+strStudentId+"&cultureid=1")
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
                                    Log.e("fjgjfgjhgfjhgvncgv",jsonObject.getString("PackageId"));


                                }


                                adapter = new OurPackageAdapter(arrayList, OurPackages.this);
                                recyclerview1.setAdapter(adapter);
                                bar.setVisibility(View.GONE);
                                Log.e("tracing", arrayList.size() + "");


                                Toast.makeText(OurPackages.this, "success" + str, Toast.LENGTH_SHORT).show();
                                //startActivity(new Intent(EnterLiveClass.this, EnterSecretKey.class));


                            } else {
                                bar.setVisibility(View.GONE);

                                Toast.makeText(OurPackages.this, "" + str, Toast.LENGTH_SHORT).show();

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




    public void ForgotPass(){



        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(OurPackages.this, android.R.style.Theme_Material_Light_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(OurPackages.this);
        }
        builder.setTitle(OurPackages.this.getResources().getString(R.string.app_name))
                .setMessage("Confirm logout?")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    public void onClick(final DialogInterface dialog, int which) {

                        final ProgressDialog progressDialog = new ProgressDialog(OurPackages.this);
                        progressDialog.setMessage("Logging You Out.....");
                        progressDialog.show();
                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                            @Override
                            public void run() {

                                if (progressDialog.isShowing()) {
                                    progressDialog.dismiss();
                                }
                                AppsContants.sharedpreferences = getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = AppsContants.sharedpreferences.edit();
                                editor.putString(AppsContants.UserId, "");

                                editor.commit();

                                Intent i = new Intent(OurPackages.this, MainActivity.class);
                                startActivity(i);
                                finishAffinity();

                            }
                        }, 2000);
                        dialog.dismiss();

                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();

                    }
                })
                .setIcon(R.drawable.logout)
                .show();

    }


}
