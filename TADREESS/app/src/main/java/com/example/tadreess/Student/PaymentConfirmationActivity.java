package com.example.tadreess.Student;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
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

public class PaymentConfirmationActivity extends AppCompatActivity {
 ImageView imgBack;
 EditText edtAmount;
 Button btnPurchase,btnCancel;

 String strStudentId="";

 ProgressBar Progress;

 String strAmount="";

    String strUrl="";

    WebView webView;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_confirmation);

        imgBack = findViewById(R.id.imgBack);
        edtAmount = findViewById(R.id.edtAmount);
        btnPurchase = findViewById(R.id.btnPurchase);
        btnCancel = findViewById(R.id.btnCancel);
        Progress = findViewById(R.id.Progress);

        webView = findViewById(R.id.webView);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);


        AppsContants.sharedpreferences = getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
        strStudentId = AppsContants.sharedpreferences.getString(AppsContants.UserId, "");


        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnPurchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strAmount = edtAmount.getText().toString().trim();


                if (strAmount.equals(""))
                {
                    Toast.makeText(PaymentConfirmationActivity.this, "Please enter amount", Toast.LENGTH_SHORT).show();
                }
                else{
                    MakePayment();
                }


            }
        });

    }



    public void MakePayment() {

        AppsContants.sharedpreferences = getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
        strStudentId = AppsContants.sharedpreferences.getString(AppsContants.UserId, "");


        Progress.setVisibility(View.VISIBLE);
        AndroidNetworking.post("http://168.187.113.181:813/TadreesService.svc/MakePayment?userid="+strStudentId+"&price="+strAmount+"")
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

                            Progress.setVisibility(View.GONE);
                            String str = response.getString("Status");
                            if (str.equals("1")) {
                                // progress.setVisibility(View.GONE);


                                String strResult = response.getString("Result");
                                JSONArray jsonArray = new JSONArray(strResult);

                                for (int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    strUrl = jsonObject.getString("url");
                                   String strUserId = jsonObject.getString("userid");

                                    webView.loadUrl(strUrl);


                                }


                                AppsContants.sharedpreferences = getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = AppsContants.sharedpreferences.edit();
                                editor.putString(AppsContants.URL, strUrl);
                                editor.commit();

                                //Toast.makeText(PaymentConfirmationActivity.this, "success" + str, Toast.LENGTH_SHORT).show();
                                //startActivity(new Intent(PaymentConfirmationActivity.this, WebActivity.class));
                                webView.loadUrl(strUrl);

                            } else {
                                Progress.setVisibility(View.GONE);

                                Toast.makeText(PaymentConfirmationActivity.this, "Transection failed" , Toast.LENGTH_SHORT).show();

                            }

                        } catch (Exception ex) {
                            Progress.setVisibility(View.GONE);
                            Log.e("hjsdfahfasdjhfasd", ex.getMessage());

                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Progress.setVisibility(View.GONE);
                        Log.e("hjsdfahfasdjhfasd", anError.toString());

                    }

                });
    }





}
