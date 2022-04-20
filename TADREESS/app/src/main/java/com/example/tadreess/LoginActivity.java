package com.example.tadreess;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.tadreess.Student.EnterLiveClass;
import com.example.tadreess.model.LoginModel;
import com.example.tadreess.model.MyPojo;
import com.example.tadreess.service.ApiDataInterface;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import fr.arnaudguyon.xmltojsonlib.XmlToJson;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

public class LoginActivity extends AppCompatActivity {

    public static final String TAG = LoginActivity.class.getSimpleName();
    TextView forgotpass;
    TextView register;
    EditText edtEmail, edtPassword;
    TextView txtLogin;

    String strEmail = "", strPassword = "";

    LottieAnimationView Progress;

    String strUserID = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        forgotpass = findViewById(R.id.forgotpass);
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        txtLogin = findViewById(R.id.txtLogin);
        Progress = findViewById(R.id.Progress);
        forgotpass.setPaintFlags(forgotpass.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        forgotpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, ForgotPassword.class));
            }
        });
        register = findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SelectUser.class);
                startActivity(intent);
            }
        });


        //Other stuff in OnCreate();


        txtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                strEmail = edtEmail.getText().toString().trim();
                strPassword = edtPassword.getText().toString().trim();
                if (strEmail.equals("")) {

                    Toast.makeText(LoginActivity.this, "Enter Email", Toast.LENGTH_SHORT).show();
                } else if (strPassword.equals("")) {


                    Toast.makeText(LoginActivity.this, "Enter Password", Toast.LENGTH_SHORT).show();
                } else {

                    Login();
                }
            }
        });


    }

    public void Login() {
        Progress.setVisibility(View.VISIBLE);




        new LoginAsyncTask().execute(API.Login+"Username="+strEmail+"&password="+strPassword+"&cultureid=1");


    }

    class RetrieveFeedTask extends AsyncTask<String, Void, String> {

        private Exception exception;

        protected String doInBackground(String... urls) {

            BufferedReader br = null;
            try {
                HttpURLConnection conn = (HttpURLConnection)(new URL("http://rest.tadreess.com/TadreesService.web.asmx/LoginUser?Username=nona&password=123456&cultureid=1")).openConnection();
                br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                String line;
                final StringBuilder sb = new StringBuilder();
                while ((line = br.readLine()) != null) {
                    sb.append(line).append("\n");
                }


                String xmlString=sb.toString(); // some XML String previously created
                XmlToJson xmlToJson = new XmlToJson.Builder(xmlString).build();

                Log.d("XML", sb.toString());
                Log.d("JSON", xmlToJson.toString());
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

        protected void onPostExecute(String feed) {
            // TODO: check this.exception
            // TODO: do something with the feed
        }

    }

    class LoginAsyncTask extends AsyncTask<String,String,String>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.i(TAG, "Login API Call");
        }

        @Override
        protected String doInBackground(String... strings) {
            String APIURL = strings[0];
            Log.i(TAG, "Login API URL:"+APIURL);
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

            Progress.setVisibility(View.GONE);

            String xmlString=s;
            Log.i(TAG,"On Login Responce :"+xmlString);

           // XmlPullParserFactory factory;
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
                Toast.makeText(LoginActivity.this, "Login Success full", Toast.LENGTH_SHORT).show();
                AppsContants.sharedpreferences = getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = AppsContants.sharedpreferences.edit();
                editor.putString(AppsContants.UserId, strUserID);
                editor.commit();

                Log.e("ghdfeyrfhdsgfvv", strUserID);

                Toast.makeText(LoginActivity.this, "successfully login", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(LoginActivity.this, EnterLiveClass.class));
                finish();
            }else {
                Toast.makeText(LoginActivity.this, "Invalid username Or password", Toast.LENGTH_SHORT).show();
            }

        }
    }
}
