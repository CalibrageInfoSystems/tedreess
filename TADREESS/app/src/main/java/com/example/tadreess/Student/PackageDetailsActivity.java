package com.example.tadreess.Student;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.tadreess.AppsContants;
import com.example.tadreess.LoginActivity;
import com.example.tadreess.R;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

public class PackageDetailsActivity extends AppCompatActivity {


    ProgressBar Pbar;

    String strStudentId = "", strStudentName = "", strPackageId = "", strScheduleId = "";
    TextView txtStage;

    Button btnBookPackage;

    ImageView imgBack;


    TextView txtStagee, txtSubject, txtTotalMinutes, txtName, txtMonth, txtMinutes, txtVaccencies;

    TextView txtWeekDay, txtTime, txtWeekDay2, txtTime2, txtTutionFees;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_package_details);

        txtStage = findViewById(R.id.txtStage);
        txtStagee = findViewById(R.id.txtStagee);
        txtSubject = findViewById(R.id.txtSubject);
        txtTotalMinutes = findViewById(R.id.txtTotalMinutes);
        txtName = findViewById(R.id.txtName);
        txtMonth = findViewById(R.id.txtMonth);
        txtMinutes = findViewById(R.id.txtMinutes);
        txtVaccencies = findViewById(R.id.txtVaccencies);
        txtWeekDay = findViewById(R.id.txtWeekDay);
        txtTime = findViewById(R.id.txtTime);
        txtWeekDay2 = findViewById(R.id.txtWeekDay2);
        txtTime2 = findViewById(R.id.txtTime2);
        txtTutionFees = findViewById(R.id.txtTutionFees);
        Pbar = findViewById(R.id.Pbar);
        btnBookPackage = findViewById(R.id.btnBookPackage);
        imgBack = findViewById(R.id.imgBack);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        AppsContants.sharedpreferences = getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
        strStudentId = AppsContants.sharedpreferences.getString(AppsContants.UserId, "");
        strStudentName = AppsContants.sharedpreferences.getString(AppsContants.StudentName, "");
        strPackageId = AppsContants.sharedpreferences.getString(AppsContants.PackageId, "");
        strScheduleId = AppsContants.sharedpreferences.getString(AppsContants.ScheduleId, "");

        btnBookPackage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BookPackage();
            }
        });

        ShowLiveClass();

    }


    public void ShowLiveClass() {
        Pbar.setVisibility(View.VISIBLE);
        //  AndroidNetworking.post("http://168.187.113.181:813/TadreesService.svc/LoginUser?Username="+strEmail+"&password="+strPassword+"&cultureid=1")
        AndroidNetworking.post("http://168.187.113.181:813/TadreesService.svc/GetPackageDetails?packageid=" + strPackageId + "&scheduleid=" + strScheduleId + "&cultureid=1")
                .addHeaders("Content-Type", "application/json")
                .addHeaders("Accept", "application/json")
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Log.e("gfhjkghjkhjkhgjkh", response.toString());

                        try {

                            Pbar.setVisibility(View.GONE);
                            String str = response.getString("Status");
                            if (str.equals("1")) {
                                // progress.setVisibility(View.GONE);


                                String strResult = response.getString("Result");
                                JSONArray jsonArray = new JSONArray(strResult);

                                for (int i = 0; i < jsonArray.length(); i++) {


                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    String strDuration = jsonObject.getString("Duration");
                                    String strMonth = jsonObject.getString("Month");
                                    String strName = jsonObject.getString("Name");
                                    String strPackageName = jsonObject.getString("PackageName");
                                    String strSchedule = jsonObject.getString("SheduleId");
                                    String strStage = jsonObject.getString("Stage");
                                    String strSubject = jsonObject.getString("Subject");
                                    String strTutionFees = jsonObject.getString("Tutionfees");
                                    String strVacancies = jsonObject.getString("Vancancies");


                                    txtStage.setText(strPackageName);
                                    txtStagee.setText(strStage);
                                    txtSubject.setText(strSubject);
                                    txtTotalMinutes.setText(strDuration + "min");
                                    txtName.setText(strName);
                                    txtMonth.setText(strMonth);
                                    txtMinutes.setText(strDuration);
                                    txtVaccencies.setText(strVacancies + "Vaccencies");
                                    txtTutionFees.setText(strTutionFees + "KWD");

                                }


                              /*  AppsContants.sharedpreferences = getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = AppsContants.sharedpreferences.edit();
                                editor.putString(AppsContants.StudentName, strName);
                                editor.commit();*/


                                Toast.makeText(PackageDetailsActivity.this, "package details" , Toast.LENGTH_SHORT).show();
                                //startActivity(new Intent(EnterLiveClass.this, EnterSecretKey.class));


                            }  else {
                                Pbar.setVisibility(View.GONE);

                                Toast.makeText(PackageDetailsActivity.this, "No details available for this package" , Toast.LENGTH_SHORT).show();

                            }

                        } catch (Exception ex) {
                            Pbar.setVisibility(View.GONE);
                            Log.e("Details", ex.getMessage());

                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Pbar.setVisibility(View.GONE);
                        Log.e("Details", anError.toString());

                    }

                });
    }

    public void BookPackage() {
        Pbar.setVisibility(View.VISIBLE);
        AndroidNetworking.post("http://168.187.113.181:813/TadreesService.svc/NewBooking?sheduleid=" + strScheduleId + "&userid=" + strStudentId + "")
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

                            Pbar.setVisibility(View.GONE);
                            String str = response.getString("Status");
                            if (str.equals("1")) {
                                // progress.setVisibility(View.GONE);


                                String strResult = response.getString("Result");
                                JSONArray jsonArray = new JSONArray(strResult);

                                for (int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    // strUserID=jsonObject.getString("UserId");


                                }

                                Toast.makeText(PackageDetailsActivity.this, "Successfully booked" + str, Toast.LENGTH_SHORT).show();
                                //startActivity(new Intent(PackageDetailsActivity.this, EnterLiveClass.class));

                            }else if (str.equals("3")){


                                Toast.makeText(PackageDetailsActivity.this, "You have already scheduled for this package", Toast.LENGTH_SHORT).show();
                            }




                            else {
                                Pbar.setVisibility(View.GONE);
                                Toast.makeText(PackageDetailsActivity.this, "Booking failed" + str, Toast.LENGTH_SHORT).show();

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
    }

}
