package com.example.tadreess;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tadreess.Student.EnterLiveClass;

public class MainActivity extends AppCompatActivity {
    ImageView imgLogo;
    String strMobile = "";
    private static int TIME_OUT = 2000;

    String strUserID = "", strUserType = "", strEmail = "", strStatus = "";


    private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 100;
    private static final int PERMISSIONS_CALL_PHONE = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

      /*  new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                Intent i = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(i);
                finish();
            }


        }, 3000);
*/



        AppsContants.sharedpreferences = getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
        strUserID = AppsContants.sharedpreferences.getString(AppsContants.UserId, "");







        new Handler().postDelayed(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            public void run() {
                //  Intent i1 = new Intent(SplashActivity.this, HomeActivity .class);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                        checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                                != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]
                                    {android.Manifest.permission.READ_EXTERNAL_STORAGE
                                            , android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                                            , Manifest.permission.ACCESS_FINE_LOCATION
                                            , Manifest.permission.CAMERA
                                            , Manifest.permission.READ_CONTACTS},
                            PERMISSIONS_REQUEST_READ_CONTACTS);
                    //After this point you wait for callback in onRequestPermissionsResult(int, String[], int[]) overriden method
                } else {
                    if (strUserID.equals("")) {

                        Intent i = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(i);
                        finish();


                    } else {
                        Intent i = new Intent(MainActivity.this, EnterLiveClass.class);
                        startActivity(i);
                        finish();

                    }
                }
            }
        }, 3000);



    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        if (requestCode == PERMISSIONS_REQUEST_READ_CONTACTS) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission is granted
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(
                        android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE
                                    , Manifest.permission.WRITE_EXTERNAL_STORAGE
                                    , Manifest.permission.ACCESS_FINE_LOCATION
                                    , Manifest.permission.CAMERA
                                    , Manifest.permission.READ_CONTACTS},
                            PERMISSIONS_CALL_PHONE);
                    //After this point you wait for callback in onRequestPermissionsResult(int, String[], int[]) overriden method
                } else {
                    if (strUserID.equals("")) {

                        Intent i = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(i);
                        finish();


                    } else {
                        Intent i = new Intent(MainActivity.this, EnterLiveClass.class);
                        startActivity(i);
                        finish();

                    }
                }
            } else {
                Toast.makeText(this, "Until you grant the permission, we canot display the names", Toast.LENGTH_SHORT).show();
            }
        }
        if (requestCode == PERMISSIONS_CALL_PHONE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission is granted

                if (strUserID.equals("")) {

                    Intent i = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(i);
                    finish();


                } else {
                    Intent i = new Intent(MainActivity.this, EnterLiveClass.class);
                    startActivity(i);
                    finish();

                }
            } else {
                Toast.makeText(this, "Until you grant the permission, we canot display the names", Toast.LENGTH_SHORT).show();
            }
        }
    }


   /* public void Virend(View view){


        Toast.makeText(this, "Check this", Toast.LENGTH_SHORT).show();
    }
*/

}

