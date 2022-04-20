package com.example.tadreess.Student;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.tadreess.AppsContants;
import com.example.tadreess.HTTPURL;
import com.example.tadreess.LoginActivity;
import com.example.tadreess.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class StudentProfile extends AppCompatActivity {


    EditText edtFullName, edtLastName, edtEmail, edtDob, edtNationality, edtDescription;

    String strFullName = "", strLastName = "", strEmail = "", strDob = "", strNationality = "", strDescription = "";


    EditText edtSchoolName, edtFatherPhone, edtPhone, edtFatherEmail, edtFatherName;

    String strSchoolName = "", strFatherPhone, strPhone = "", strFatherEmail = "", strFatherName = "";

    RadioGroup radioGroup;
    RadioButton radioMale, radioFemale;


    List<String> arrayListStageId;
    List<String> arrayListStageName;


    List<String> arrayListSubjectId;
    List<String> arraListSubjectName;

    ProgressBar progressBar;

    Spinner spinStage, spinSubject;

    LinearLayout linearFirst, linearSecond;

    String strUserType = "";
    Button btnRegister, btnContnue2;

    String strStageId = "", strStageName = "", strSubjectId = "", strSubjectName = "";

    String strTeacherId="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_profile);

        radioGroup = findViewById(R.id.radioGroup);
        radioMale = findViewById(R.id.radioMale);
        radioFemale = findViewById(R.id.radioFemale);

        //cristinaalberd1803

        AppsContants.sharedpreferences = getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
        strTeacherId = AppsContants.sharedpreferences.getString(AppsContants.UserId, "");

        Log.e("sdjfshdjkfsfs",strTeacherId);

        edtFullName = findViewById(R.id.edtFullName);
        edtLastName = findViewById(R.id.edtLastName);
        edtEmail = findViewById(R.id.edtEmail);
        edtDob = findViewById(R.id.edtDob);
        edtNationality = findViewById(R.id.edtNationality);
        edtDescription = findViewById(R.id.edtDescription);
        spinStage = findViewById(R.id.spinStage);
        spinSubject = findViewById(R.id.spinSubject);
        btnRegister = findViewById(R.id.btnRegister);
        linearFirst = findViewById(R.id.linearFirst);
        linearSecond = findViewById(R.id.linearSecond);


        edtSchoolName = findViewById(R.id.edtSchoolName);
        edtFatherPhone = findViewById(R.id.edtFatherPhone);
        edtPhone = findViewById(R.id.edtPhone);
        edtFatherEmail = findViewById(R.id.edtFatherEmail);
        btnContnue2 = findViewById(R.id.btnContnue2);
        edtFatherName = findViewById(R.id.edtFatherName);
        progressBar = findViewById(R.id.progressBar);
        ShowStage();
        ShowSubject();

        btnContnue2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                strFullName = edtFullName.getText().toString().trim();
                strLastName = edtLastName.getText().toString().trim();
                strEmail = edtEmail.getText().toString().trim();
                strDob = edtDob.getText().toString().trim();
                strNationality = edtNationality.getText().toString().trim();
                strDescription = edtDescription.getText().toString().trim();


                strSchoolName = edtSchoolName.getText().toString().trim();
                strFatherName = edtFatherName.getText().toString().trim();
                strFatherPhone = edtFatherPhone.getText().toString().trim();
                strPhone = edtPhone.getText().toString().trim();
                strFatherEmail = edtFatherEmail.getText().toString().trim();

                Profile();


            }
        });


        spinStage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                strStageId = arrayListStageId.get(i);
                strStageName = arrayListStageName.get(i);


                Log.e("gfjhfgjhdg", strStageId);
                Log.e("gfjhfgjhdg", strStageName);


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

                Toast.makeText(StudentProfile.this, "Admin[position]", Toast.LENGTH_SHORT).show();

            }
        });
        spinSubject.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                strSubjectId = arrayListSubjectId.get(position);
                strSubjectName = arrayListStageName.get(position);


                Log.e("jdgfjdgjhdgfjh",strSubjectId);
                Log.e("jdgfjdgjhdgfjh",strSubjectName);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(StudentProfile.this, "Admin[position]", Toast.LENGTH_SHORT).show();
            }
        });


        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               linearFirst.setVisibility(View.GONE);
                linearSecond.setVisibility(View.VISIBLE);

               // Profile();

            }
        });


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected


                if (radioMale.isChecked()) {

                    strUserType = "Male";


                } else if (radioFemale.isChecked()) {
                    strUserType = "Female";


                } else {

                }


            }
        });
    }


    private void Profile() {

       /* Log.e("fhkjfhkfg",strStageId);
     Log.e("fhkjfhkfg",strFatherPhone);
        Log.e("fhkjfhkfg",strSubjectId);
        Log.e("fhkjfhkfg",strFullName);
        Log.e("fhkjfhkfg",strEmail);
        Log.e("fhkjfhkfg",strNationality);
        Log.e("fhkjfhkfg",strFatherEmail);
        Log.e("fhkjfhkfg",strTeacherId);
        Log.e("fhkjfhkfg",strFatherName);
        Log.e("fhkjfhkfg",strUserType);
        Log.e("fhkjfhkfg",strSchoolName);
        Log.e("fhkjfhkfg",strLastName);
        Log.e("fhkjfhkfg",strDob);*/





        progressBar.setVisibility(View.VISIBLE);

        JSONObject jObj = new JSONObject();

        try {

            jObj.put("FathersPhone", strFatherPhone);
            jObj.put("Subject", strSubjectId);
            jObj.put("SirName", strFullName);
            jObj.put("Email", strEmail);
            jObj.put("Photo", "photo");
            jObj.put("Nationality", strNationality);
            jObj.put("FathersMail", strFatherEmail);
            jObj.put("UserId", strTeacherId);
            jObj.put("FathersName", strFatherName);
            jObj.put("Phone", strPhone);
            jObj.put("FullName", strFullName);
            jObj.put("Address", "mp nagar");
            jObj.put("Stage", strStageId);
            jObj.put("Gender", "1");
            jObj.put("SchoolName", strSchoolName);
            jObj.put("LastName", strLastName);
            jObj.put("Dob", strDob);


        } catch (JSONException e) {
            e.printStackTrace();
        }


         Log.e("sdfshdfhsdf",jObj.toString());


        AndroidNetworking.post("http://168.187.113.181:813/TadreesService.svc/AddNewStudent")
                .addHeaders("Content-Type", "application/json")
                .addHeaders("Accept", "application/json")
                .addJSONObjectBody(jObj)
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {


                   Log.e("wetwetwefsdvv",response.toString());
                        try {

                            progressBar.setVisibility(View.GONE);
                            String str = response.getString("Status");
                            if (str.equals("1")) {
                                // progress.setVisibility(View.GONE);


                                String strResult=response.getString("Result");
                                JSONArray jsonArray= new JSONArray(strResult);

                                for (int i = 0; i <jsonArray.length() ; i++) {

                                    JSONObject jsonObject=jsonArray.getJSONObject(i);
                                    String strStageId=jsonObject.getString("StageId");
                                    String strUserId=jsonObject.getString("UserId");
                                    String strStatus=jsonObject.getString("Userstatus");


                                }


                                Toast.makeText(StudentProfile.this, "Success", Toast.LENGTH_SHORT).show();

                               // startActivity(new Intent(StudentProfile.this, StudentProfile.class));


                            } else {

                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(StudentProfile.this, "" + str, Toast.LENGTH_SHORT).show();

                            }

                        } catch (Exception ex) {
                            progressBar.setVisibility(View.GONE);
                            Log.e("hjsdfahfasdjhfasd", ex.getMessage());

                        }
                    }



                    @Override
                    public void onError(ANError error) {
                        progressBar.setVisibility(View.GONE);
                        Log.e("dhgfhjdgfjhdgf", error.getMessage());

                    }
                });
    }

    private void ShowStage() {

        AndroidNetworking.post("http://168.187.113.181:813/TadreesService.svc/GetStages?cultureid=1")
                .addHeaders("Content-Type", "application/json")
                .addHeaders("Accept", "application/json")
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Log.e("myresponse", response.toString());
                        arrayListStageId = new ArrayList<>();
                        arrayListStageName = new ArrayList<>();
                        try {


                            if (response.has("Status")) {
                                if (response.getString("Status").equals("1")) {

                                    String strResult = response.getString("Result");
                                    JSONArray jsonArray = new JSONArray(strResult);
                                    for (int i = 0; i < jsonArray.length(); i++) {

                                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                                        String strStageId = jsonObject.getString("SatgeId");
                                        String strStageName = jsonObject.getString("Name");



                                        arrayListStageName.add(strStageName);
                                        arrayListStageId.add(strStageId);

                                    }

                                    ArrayAdapter aa = new ArrayAdapter(StudentProfile.this, android.R.layout.simple_spinner_item, arrayListStageName);
                                    aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    //Setting the ArrayAdapter data on the Spinner
                                    spinStage.setAdapter(aa);

                                } else {


                                    Toast.makeText(StudentProfile.this, "Unsuccess", Toast.LENGTH_SHORT).show();
                                }


                            }


                        } catch (JSONException e) {

                            // Toast.makeText(TeacherRegisterActivity.this, "" + response, Toast.LENGTH_SHORT).show();

                            Log.e("dhgfhjdgfjhdgf", e.getMessage());


                        }


                    }

                    @Override
                    public void onError(ANError error) {

                        Log.e("dhgfhjdgfjhdgf", error.getMessage());

                    }
                });

    }

    private void ShowSubject() {

        AndroidNetworking.post("http://168.187.113.181:813/TadreesService.svc/GetAllSubjects?cultureid=1")
                .addHeaders("Content-Type", "application/json")
                .addHeaders("Accept", "application/json")
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Log.e("myresponse", response.toString());
                        arrayListSubjectId = new ArrayList<>();
                        arraListSubjectName = new ArrayList<>();
                        try {


                            if (response.has("Status")) {
                                if (response.getString("Status").equals("1")) {

                                    String strResult = response.getString("Result");
                                    JSONArray jsonArray = new JSONArray(strResult);
                                    for (int i = 0; i < jsonArray.length(); i++) {

                                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                                        String strSubjectName = jsonObject.getString("Subject");
                                        String strSubjectId = jsonObject.getString("SubjectId");


                                        arraListSubjectName.add(strSubjectName);
                                        arrayListSubjectId.add(strSubjectId);

                                    }

                                    ArrayAdapter aa = new ArrayAdapter(StudentProfile.this, android.R.layout.simple_spinner_item, arraListSubjectName);
                                    aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    //Setting the ArrayAdapter data on the Spinner
                                    spinSubject.setAdapter(aa);

                                } else {


                                    Toast.makeText(StudentProfile.this, "Unsuccess", Toast.LENGTH_SHORT).show();
                                }


                            }


                        } catch (JSONException e) {

                            // Toast.makeText(TeacherRegisterActivity.this, "" + response, Toast.LENGTH_SHORT).show();

                            Log.e("dhgfhjdgfjhdgf", e.getMessage());


                        }


                    }

                    @Override
                    public void onError(ANError error) {

                        Log.e("dhgfhjdgfjhdgf", error.getMessage());

                    }
                });

    }
}


