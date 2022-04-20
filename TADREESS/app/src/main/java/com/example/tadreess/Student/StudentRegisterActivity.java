package com.example.tadreess.Student;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.model.Progress;
import com.example.tadreess.API;
import com.example.tadreess.AppsContants;
import com.example.tadreess.LoginActivity;
import com.example.tadreess.R;
import com.example.tadreess.TeacherRegisterActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class StudentRegisterActivity extends AppCompatActivity {

    public static final String TAG = StudentRegisterActivity.class.getSimpleName();

    ImageView btnback;


    RadioGroup radioGroup;
    EditText edtName, edtFullName, edtEmail, edtPassword, edtConfirmPassword;
    Button btnRegister;
    LinearLayout linearFirst;
    String strUserID="";

    ProgressBar progressBar;

    String strName = "", strLastName = "", strEmail = "", strPassword = "", strConfirmPassword = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_register);

        btnback = findViewById(R.id.btnback);
        edtName = findViewById(R.id.edtName);
        edtFullName = findViewById(R.id.edtFullName);
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        edtConfirmPassword = findViewById(R.id.edtConfirmPassword);
        // radioGroup = findViewById(R.id.radioGroup);
        btnRegister = findViewById(R.id.btnRegister);
        progressBar = findViewById(R.id.progressBar);
        linearFirst = findViewById(R.id.linearFirst);

        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                strName = edtName.getText().toString().trim();
                strLastName = edtFullName.getText().toString().trim();
                strEmail = edtEmail.getText().toString().trim();
                strPassword = edtPassword.getText().toString().trim();
                strConfirmPassword = edtConfirmPassword.getText().toString().trim();



                new SIGNUP().execute(API.Signup+"username="+strName+"&password="+strPassword+"&email="+strEmail);

            }
        });


    }


    private void SIGNUP() {
        progressBar.setVisibility(View.VISIBLE);

        AndroidNetworking.post(API.Signup+"username="+strName+"&password="+strPassword+"&email="+strEmail)
                .addHeaders("Content-Type","application/json")
                .addHeaders("Accept","application/json")
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Log.e("myresponse",  response.toString());

                        try {

                            progressBar.setVisibility(View.GONE);
                            if (response.has("Status")) {
                                if (response.getString("Status").equals("1")) {

                                    String strResult=response.getString("Result");
                                    JSONArray jsonArray= new JSONArray(strResult);
                                    for (int i = 0; i <jsonArray.length() ; i++) {

                                        JSONObject jsonObject=jsonArray.getJSONObject(i);
                                         strUserID=jsonObject.getString("UserId");
                                        Log.e("dfjgdgfdfwer",strUserID);

                                        Toast.makeText(StudentRegisterActivity.this, "Register successfully" , Toast.LENGTH_SHORT).show();



                                    }

                                    AppsContants.sharedpreferences = getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = AppsContants.sharedpreferences.edit();
                                    editor.putString(AppsContants.UserId, strUserID);
                                    editor.commit();

                                    startActivity(new Intent(StudentRegisterActivity.this,StudentProfile.class));

                                } else {

                                    progressBar.setVisibility(View.GONE);
                                    Toast.makeText(StudentRegisterActivity.this, "Already Register", Toast.LENGTH_SHORT).show();
                                }





                            }


                        } catch (JSONException e) {
                            progressBar.setVisibility(View.GONE);
                            // Toast.makeText(TeacherRegisterActivity.this, "" + response, Toast.LENGTH_SHORT).show();

                            Log.e("dhgfhjdgfjhdgf",e.getMessage());


                        }


                    }

                    @Override
                    public void onError(ANError error) {
                        progressBar.setVisibility(View.GONE);
                        Log.e("dhgfhjdgfjhdgf",error.getMessage());

                    }
                });

    }

     class SIGNUP extends AsyncTask<String,String,String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.i(TAG, "reg API Call");
        }

        @Override
        protected String doInBackground(String... strings) {
            String APIURL = strings[0];
            Log.i(TAG, "reg API URL:"+APIURL);
            BufferedReader br = null;
            try {
                HttpURLConnection conn = (HttpURLConnection)(new URL(APIURL)).openConnection();
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

           // Progress.setVisibility(View.GONE);

            String xmlString=s;
            Log.i(TAG,"On reg Responce :"+xmlString);
            try {

                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(true);
                XmlPullParser xpp = factory.newPullParser();

                xpp.setInput( new StringReader(xmlString) );
                String name=xpp.getName();// pass input whatever xml you have
                int eventType = xpp.getEventType();
                while (eventType != XmlPullParser.END_DOCUMENT) {
                    String tagname = xpp.getName();
                    switch (eventType) {

                        case XmlPullParser.TEXT:
                            name = xpp.getText();
                            Log.d(TAG,"Start tag "+xpp.getName());
                            break;

                        case XmlPullParser.END_TAG:
                            if (tagname.equalsIgnoreCase("UserId")) {
                                Log.d(TAG,"user_id==322==="+name);
                                strUserID =name;

                                // builder.append("TAG1 = " + name);
                            } else if (tagname.equalsIgnoreCase("Status")) {
                                Log.d(TAG,"Status==325=== "+name);

                                Status =name;
                                //builder.append("\nTAG2 = " + name);
                            }
                            break;

                        default:
                            break;
                    }
                    eventType = xpp.next();
                }


                Log.d(TAG,"Start tag 325"+xpp.getName());
                Log.d(TAG,"End document");

            } catch (XmlPullParserException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(Status.equalsIgnoreCase("1"))
            {
            AppsContants.sharedpreferences = getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = AppsContants.sharedpreferences.edit();
            editor.putString(AppsContants.UserId, strUserID);
            editor.commit();

            startActivity(new Intent(StudentRegisterActivity.this,StudentProfile.class));

        } else {

             progressBar.setVisibility(View.GONE);
             Toast.makeText(StudentRegisterActivity.this, "Already Register", Toast.LENGTH_SHORT).show();
         }

        }
}}
