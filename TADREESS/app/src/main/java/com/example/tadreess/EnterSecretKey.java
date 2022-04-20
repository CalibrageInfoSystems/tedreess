package com.example.tadreess;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONArray;
import org.json.JSONObject;

public class EnterSecretKey extends AppCompatActivity {


    ImageView imgBack;

    EditText edtKey;

    Button btnSubmit;

    ProgressBar Pbar;

    String strStudentId="",strKey="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_secret_key);

        imgBack = findViewById(R.id.imgBack);
        edtKey = findViewById(R.id.edtKey);
        btnSubmit = findViewById(R.id.btnSubmit);
        Pbar = findViewById(R.id.Pbar);

        AppsContants.sharedpreferences = getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
        strStudentId = AppsContants.sharedpreferences.getString(AppsContants.UserId, "");

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                strKey = edtKey.getText().toString().trim();
                RechargeCoupans();
            }
        });

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    public void RechargeCoupans() {
        Pbar.setVisibility(View.VISIBLE);
        AndroidNetworking.post("http://168.187.113.181:813/TadreesService.svc/FillMyAccount?userid=" + strStudentId + "&key=" + strKey + "")
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
                                   // String strUserName = jsonObject.getString("Username");


                                }

                                Toast.makeText(EnterSecretKey.this, "success" , Toast.LENGTH_SHORT).show();
                                //startActivity(new Intent(PackageDetailsActivity.this, EnterLiveClass.class));

                            } else {
                                Pbar.setVisibility(View.GONE);
                                Toast.makeText(EnterSecretKey.this, "Invalid key" , Toast.LENGTH_SHORT).show();

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
