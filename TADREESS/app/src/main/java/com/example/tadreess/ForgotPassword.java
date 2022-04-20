package com.example.tadreess;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONObject;

public class ForgotPassword extends AppCompatActivity {

     EditText edtEmail;

     Button btnForgotPassword;

     String strEmail="";

     ProgressBar Pbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        edtEmail = findViewById(R.id.edtEmail);
        btnForgotPassword = findViewById(R.id.btnForgotPassword);
        Pbar = findViewById(R.id.Pbar);


        btnForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                strEmail = edtEmail.getText().toString().trim();


                if (strEmail.equals("")) {


                    Toast.makeText(ForgotPassword.this, "Enter Email", Toast.LENGTH_SHORT).show();
                } else {

                    //ForGotPass();

                }


            }
        });

    }

   /* public void ForGotPass() {

        Pbar.setVisibility(View.VISIBLE);

        AndroidNetworking.post(HTTPURL.ForgotPassword)
                .addBodyParameter("email", strEmail)
                .setTag("text")
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {


                            if (response.has("result")) {

                                String strResult = response.getString("result");
                                if (strResult.equals("An email has been sent to you with instructions")) {

                                    finish();

                                    Toast.makeText(ForgotPassword.this, "Check your email", Toast.LENGTH_SHORT).show();

                                } else {

                                    Pbar.setVisibility(View.GONE);

                                    Toast.makeText(ForgotPassword.this, "" + strResult, Toast.LENGTH_SHORT).show();
                                }


                            }

                        } catch (Exception ex) {
                            Pbar.setVisibility(View.GONE);


                        }

                    }

                    @Override
                    public void onError(ANError anError) {
                        Pbar.setVisibility(View.GONE);
                    }
                });*/



}
