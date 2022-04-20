package com.example.tadreess.Student;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.tadreess.API;
import com.example.tadreess.AppsContants;
import com.example.tadreess.LoginActivity;
import com.example.tadreess.MainActivity;
import com.example.tadreess.R;
import com.example.tadreess.Student.Fragment.AboutUs;
import com.example.tadreess.Student.Fragment.ChangePassword;
import com.example.tadreess.Student.Fragment.ContactUsFragment;
import com.example.tadreess.Student.Fragment.FaqFragment;
import com.example.tadreess.Student.Fragment.HomeFragment;
import com.example.tadreess.Student.Fragment.MySchedule;
import com.example.tadreess.Student.Fragment.MyTransectionFragment;
import com.example.tadreess.Student.Fragment.ServicesFragment;
import com.example.tadreess.Student.Fragment.UpdateProfileFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.os.Handler;
import android.util.Log;
import android.view.View;

import androidx.annotation.RequiresApi;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class NavigationActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static final String TAG = NavigationActivity.class.getSimpleName();

    Toolbar toolbar;
    NavigationView navigationView;
    TextView txtLoginReg;

    ImageView animation_view;




    String strUserName="";




    TextView txtUserName;




    String strUserId="";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
         toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Home");
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);


       /* AppsContants.sharedpreferences = getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
        strUserId = AppsContants.sharedpreferences.getString(AppsContants.UserId, "");*/

        Fragment fragment = new HomeFragment();
        toolbar.setTitle("Home");
        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment);
            ft.commit();
        }



        // txtLoginReg = findViewById(R.id.txtLoginReg);

        AppsContants.sharedpreferences = getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
        strUserName = AppsContants.sharedpreferences.getString(AppsContants.UserName, "");
        strUserId = AppsContants.sharedpreferences.getString(AppsContants.UserId, "");

        Log.e("fhghjdfgjfhghjf",strUserId);
        new ShowProfile().execute(API.ShowProfile + "userid=" + strUserId + "&cultureid=1");
        //ShowProfile();
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);






        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();










        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.inflateHeaderView(R.layout.nav_header_navigation);
        animation_view =  headerView.findViewById(R.id.animation_view);
        txtUserName =  headerView.findViewById(R.id.txtUserName);



        //txtUserName.setText(strUserName);


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    /*  @Override
      public boolean onCreateOptionsMenu(Menu menu) {
          // Inflate the menu; this adds items to the action bar if it is present.
          getMenuInflater().inflate(R.menu.navigation, menu);
          return true;
      }

      @Override
      public boolean onOptionsItemSelected(MenuItem item) {
          // Handle action bar item clicks here. The action bar will
          // automatically handle clicks on the Home/Up button, so long
          // as you specify a parent activity in AndroidManifest.xml.
          int id = item.getItemId();

          //noinspection SimplifiableIfStatement
          if (id == R.id.action_settings) {
              return true;
          }

          return super.onOptionsItemSelected(item);
      }
  */
    private void displaySelectedScreen(int itemId) {

        //creating fragment object
        Fragment fragment = null;

        //initializing the fragment object which is selected


        switch (itemId) {
            case R.id.nav_home:
                fragment = new HomeFragment();
                toolbar.setTitle("Home");
                break;

            case R.id.nav_Profile:
                fragment = new UpdateProfileFragment();
                toolbar.setTitle("My Profile");
                break;
            case R.id.nav_schedules:
                fragment = new MySchedule();
                toolbar.setTitle("My Schedules");
                break;
            case R.id.nav_account:
                fragment = new MyTransectionFragment();
                toolbar.setTitle("My Account");
                break;
            case R.id.nav_password:
                fragment = new ChangePassword();
                toolbar.setTitle("Change Password");
                break;
            case R.id.nav_about:
                fragment = new AboutUs();
                toolbar.setTitle("About Us");
                break;
            case R.id.nav_faq:
                fragment = new FaqFragment();
                toolbar.setTitle("FAQ");
                break;

            case R.id.nav_services:
                fragment = new ServicesFragment();
                toolbar.setTitle("Services");
                break;

            case R.id.nav_contact:
                fragment = new ContactUsFragment();
                toolbar.setTitle("Contact us");
                break;

            /*case R.id.nav_audio:
                fragment = new AudioFragment();
                toolbar.setTitle("Audio Lectures");
                break;


            case R.id.nav_text:
                fragment = new TextFragment();
                toolbar.setTitle("Posts");
                break;


            case R.id.nav_image:
                fragment = new ImageFragment();
                toolbar.setTitle("Events");
                break;


            case R.id.nav_thinkally:
                Toast.makeText(this, "Redir", Toast.LENGTH_SHORT).show();

                fragment = new ThinkalyFragment();
                toolbar.setTitle("THINKALLY");
                break;


            case R.id.nav_jobs:
                fragment = new JobsFragment();
                toolbar.setTitle("Jobs");
                break;


            case R.id.nav_findservices:
                fragment = new FindServicesFragment();
                toolbar.setTitle("Find Services");
                break;


            case R.id.nav_profile:
                fragment = new ProfileFragment();
                toolbar.setTitle("Profile");
                break;


            case R.id.nav_visit_website:
                fragment = new VisitOurWebsiteFragment();
                toolbar.setTitle("Visit Our WebSite");
                break;
*/

            case R.id.nav_logout:
                toolbar.setTitle("Logout");


                AlertDialog.Builder builder;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    builder = new AlertDialog.Builder(NavigationActivity.this, android.R.style.Theme_Material_Light_Dialog_Alert);
                } else {
                    builder = new AlertDialog.Builder(NavigationActivity.this);
                }
                builder.setTitle(NavigationActivity.this.getResources().getString(R.string.app_name))
                        .setMessage("Confirm logout?")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                            public void onClick(final DialogInterface dialog, int which) {

                                final ProgressDialog progressDialog = new ProgressDialog(NavigationActivity.this);
                                progressDialog.setMessage("Logging You Out.....");
                                progressDialog.show();
                                final Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                                    @Override
                                    public void run() {

                                        if (progressDialog.isShowing()) {
                                            progressDialog.dismiss();
                                        }
                                        AppsContants.sharedpreferences = getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
                                        SharedPreferences.Editor editor = AppsContants.sharedpreferences.edit();
                                        editor.putString(AppsContants.UserId, "");

                                        editor.commit();

                                        Intent i = new Intent(NavigationActivity.this, MainActivity.class);
                                        startActivity(i);
                                        finishAffinity();

                                    }
                                }, 2000);
                                dialog.dismiss();

                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                dialog.dismiss();

                            }
                        })
                        .setIcon(R.drawable.logout)
                        .show();


               // break;
        }


        //replacing the fragment
        //replacing the fragment
        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment);
            ft.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        displaySelectedScreen(item.getItemId());

        return true;
    }



    public void ShowProfile() {

        //  AndroidNetworking.post("http://168.187.113.181:813/TadreesService.svc/LoginUser?Username="+strEmail+"&password="+strPassword+"&cultureid=1")
        AndroidNetworking.post(API.ShowProfile + "userid=" + strUserId + "&cultureid=1")
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

                            String str = response.getString("Status");
                            if (str.equals("1")) {
                                // progress.setVisibility(View.GONE);


                                String strResult = response.getString("Result");
                                JSONArray jsonArray = new JSONArray(strResult);

                                for (int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    String strAddress = jsonObject.getString("Address");
                                    String strDob = jsonObject.getString("Dob");
                                    String strEmail = jsonObject.getString("Email");
                                    String strFatherMail = jsonObject.getString("FathersMail");
                                    String strFatherName = jsonObject.getString("FathersName");
                                    String strFatherPhone = jsonObject.getString("FathersPhone");
                                    String strFullname = jsonObject.getString("FullName");
                                    String strGender = jsonObject.getString("Gender");
                                    String strLastName = jsonObject.getString("LastName");
                                    String strNationality = jsonObject.getString("Nationality");
                                    String strPhone = jsonObject.getString("Phone");
                                    String strPhoto = jsonObject.getString("Photo");
                                    String strSchoolName = jsonObject.getString("SchoolName");
                                    String strSirName = jsonObject.getString("SirName");
                                    String strStage = jsonObject.getString("Stage");
                                    String strSubject = jsonObject.getString("Subject");
                                    String strUserId = jsonObject.getString("UserId");





                                    txtUserName.setText(strFullname);











                                }


                                // Toast.makeText(getActivity(), "success" + str, Toast.LENGTH_SHORT).show();
                                //startActivity(new Intent(EnterLiveClass.this, EnterSecretKey.class));


                            } else {


                                // Toast.makeText(getActivity(), "" + str, Toast.LENGTH_SHORT).show();

                            }

                        } catch (Exception ex) {

                            Log.e("hjsdfahfasdjhfasd", ex.getMessage());

                        }
                    }

                    @Override
                    public void onError(ANError anError) {

                        Log.e("hjsdfahfasdjhfasd", anError.toString());

                    }

                });
    }

    private class ShowProfile extends AsyncTask<String,String,String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.i(TAG, "Login API Call");
        }

        @Override
        protected String doInBackground(String... strings) {
            String APIURL = strings[0];
            Log.i(TAG, "Login API URL:" + APIURL);
            BufferedReader br = null;
            try {
                HttpURLConnection conn = (HttpURLConnection) (new URL(APIURL)).openConnection();
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


            String xmlString = s;
            Log.i(TAG, "On Login Responce :" + xmlString);

        }
    }}
