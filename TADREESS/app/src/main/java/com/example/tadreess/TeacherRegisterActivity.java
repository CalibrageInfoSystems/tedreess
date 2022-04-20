package com.example.tadreess;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class TeacherRegisterActivity extends AppCompatActivity {

    ImageView btnback;


    RadioGroup radioGroup;
    EditText edtName, edtFullName, edtEmail, edtPassword, edtMobile;
    Button btnRegister;

    ProgressBar progressBar;

    String strName = "", strLastName = "", strEmail = "", strPassword = "", strMobile = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        btnback = findViewById(R.id.btnback);
        edtName = findViewById(R.id.edtName);
        edtFullName = findViewById(R.id.edtFullName);
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        edtMobile = findViewById(R.id.edtMobile);
        // radioGroup = findViewById(R.id.radioGroup);
        btnRegister = findViewById(R.id.btnRegister);
        progressBar = findViewById(R.id.progressBar);

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
             //   strLastName = edtFullName.getText().toString().trim();
                strEmail = edtEmail.getText().toString().trim();
                strPassword = edtPassword.getText().toString().trim();
              //  strMobile = edtMobile.getText().toString().trim();


              //  SIGNUP();

            }
        });




    }


   /* private void SIGNUP() {
        progressBar.setVisibility(View.VISIBLE);

        AndroidNetworking.post("http://168.187.113.181:813/TadreesService.svc/AddNewUser?username="+strName+"&password="+strPassword+"&email="+strEmail)
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
                                        String strUserID=jsonObject.getString("UserId");
                                        Log.e("dfjgdgfdfwer",strUserID);

                                        Toast.makeText(TeacherRegisterActivity.this, "success" , Toast.LENGTH_SHORT).show();

                                    }

                                } else {

                                    progressBar.setVisibility(View.GONE);
                                    Toast.makeText(TeacherRegisterActivity.this, "Already Register", Toast.LENGTH_SHORT).show();
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
                });*/

}
