package com.example.tadreess.Student.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.tadreess.API;
import com.example.tadreess.AppsContants;
import com.example.tadreess.LoginActivity;
import com.example.tadreess.R;
import com.example.tadreess.Student.EnterLiveClass;
import com.example.tadreess.Student.PackageDetailsActivity;

import org.json.JSONArray;
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


public class ChangePassword extends Fragment {
    public static final String TAG = ChangePassword.class.getSimpleName();

    EditText edtOldPassword,edtNewPassword,edtConfirmPassword;

    Button btnSubmit;

    ProgressBar Pbar;

    String strOldPawssword="",strNewPassword="",strConfirmPassword="";

    String strStudentId="";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_change_password, container, false);


        AppsContants.sharedpreferences = getActivity().getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
        strStudentId = AppsContants.sharedpreferences.getString(AppsContants.UserId, "");

        edtOldPassword = view.findViewById(R.id.edtOldPassword);
        edtNewPassword = view.findViewById(R.id.edtNewPassword);
        edtConfirmPassword = view.findViewById(R.id.edtConfirmPassword);
        btnSubmit = view.findViewById(R.id.btnSubmit);
        Pbar = view.findViewById(R.id.Pbar);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strOldPawssword = edtOldPassword.getText().toString().trim();
                strNewPassword = edtNewPassword.getText().toString().trim();
                strConfirmPassword = edtConfirmPassword.getText().toString().trim();

/*if (strOldPawssword.equals("")){

    Toast.makeText(getActivity(), "please enter your old password", Toast.LENGTH_SHORT).show();
}else if(strNewPassword.equals("")){

    Toast.makeText(getActivity(), "please enter new password", Toast.LENGTH_SHORT).show();
}else if(strConfirmPassword.equals("")){
    Toast.makeText(getActivity(), "please re-enter new password", Toast.LENGTH_SHORT).show();
}else{

    ChangePassword();
}*/




                if (strNewPassword.equals(strConfirmPassword)) {
                   ChangePassword();
                   new ChangePasswordA().execute(API.ChangePassword+"userid=" + strStudentId + "&oldpwd=" + strOldPawssword + "&newpwd=" + strNewPassword + "");
                } else {
                    Toast.makeText(getActivity(), "Password is not match", Toast.LENGTH_SHORT).show();
                }


            }
        });


    return view;

    }

    public void ChangePassword() {
        Pbar.setVisibility(View.VISIBLE);
        AndroidNetworking.post(API.ChangePassword+"userid=" + strStudentId + "&oldpwd=" + strOldPawssword + "&newpwd=" + strNewPassword + "")
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
                                    // String strUserName=jsonObject.getString("Username");


                                }

                                Toast.makeText(getActivity(), "password change successfully", Toast.LENGTH_SHORT).show();
                                //startActivity(new Intent(PackageDetailsActivity.this, EnterLiveClass.class));

                            }  else {
                                Pbar.setVisibility(View.GONE);
                                Toast.makeText(getActivity(), "password change failed", Toast.LENGTH_SHORT).show();

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


    private class ChangePasswordA extends AsyncTask<String,String,String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.i(TAG, "ChangePassword API Call");
        }

        @Override
        protected String doInBackground(String... strings) {
            String APIURL = strings[0];
            Log.i(TAG, "ChangePassword API URL:"+APIURL);
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

         //   Progress.setVisibility(View.GONE);

            String xmlString=s;
            Log.i(TAG,"On ChangePassword Responce :"+xmlString);

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
                            if (tagname.equalsIgnoreCase("Status")) {
                                Log.d(TAG, "user_id==322===" + name);
                                Status = name;

                                // builder.append("TAG1 = " + name);

                            }

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
                Toast.makeText(getActivity(), "password change successfully", Toast.LENGTH_SHORT).show();
                //startActivity(new Intent(PackageDetailsActivity.this, EnterLiveClass.class));

            }  else {
                Pbar.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "password change failed", Toast.LENGTH_SHORT).show();

            }

        }
    }
    }

