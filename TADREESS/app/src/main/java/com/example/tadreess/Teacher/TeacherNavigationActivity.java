package com.example.tadreess.Teacher;

import android.os.Bundle;

import com.example.tadreess.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.view.View;

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

public class TeacherNavigationActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_navigation);
         toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
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

   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.teacher_navigation, menu);
        return true;
    }
*/
   /* @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);*/


    private void displaySelectedScreen(int itemId) {

        //creating fragment object
        Fragment fragment = null;

        //initializing the fragment object which is selected


       /* switch (itemId) {
            case R.id.nav_video:
                fragment = new VideoFragment();
                toolbar.setTitle(" ");
                break;*/








           /* case R.id.nav_logout:
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
                                        editor.putString(AppsContants.UserEmail, "");
                                        editor.putString(AppsContants.UserName, "");
                                        editor.putString(AppsContants.Message, "");
                                        editor.putString(AppsContants.User_catname, "");
                                        editor.commit();

                                        Intent i = new Intent(NavigationActivity.this, SplashScreen.class);
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


                break;*/



        //replacing the fragment
        //replacing the fragment
      /*  if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment);
            ft.commit();
        }*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        displaySelectedScreen(item.getItemId());

        return true;
    }
}
