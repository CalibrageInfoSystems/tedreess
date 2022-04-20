package com.example.tadreess.Student;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.tadreess.AppsContants;
import com.example.tadreess.CommonHelper;
import com.example.tadreess.R;
import com.example.tadreess.Student.EnterLiveClassAdapter.EnterLiveClassAdapter;
import com.example.tadreess.model.TokenResponce;
import com.example.tadreess.service.ApiDataInterface;

import org.json.JSONArray;
import org.json.JSONObject;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

public class WebActivity extends AppCompatActivity {
    WebView webView;
//7030 noor mezahem
    String roomID;//7030
    String firstname;//7030
    String lastname="";//7030
    String email="";//7030
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {

//
            firstname = extras.getString("name");
            roomID = extras.getString("roomid");
            Log.e("RoomId===55",roomID +" "+ firstname);
        }



        email="nona@gmail.com";

        webView = findViewById(R.id.webview);
        webView.getSettings().setJavaScriptEnabled(true);
        //Add these lines according to your requirements
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setSaveFormData(true);
        webView.getSettings().setAllowContentAccess(true);
        webView.getSettings().setAllowFileAccess(true);
        webView.getSettings().setAllowFileAccessFromFileURLs(true);
        webView.getSettings().setAllowUniversalAccessFromFileURLs(true);
        //webView.getSettings().setSupportMultipleWindows(true);
        webView.getSettings().setSupportZoom(true);
        webView.setClickable(true);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                if (url.startsWith("http") || url.startsWith("https")) {
                    return true;
                } else {
                    webView.stopLoading();
                    webView.goBack();
                    Toast.makeText(WebActivity.this, "Unknown Link, unable to handle", Toast.LENGTH_SHORT).show();
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(browserIntent);
                    Log.d("Login", url);
                }
                return false;
            }
            @Override
            public void onPageFinished(WebView view, String url) {
                view.loadUrl("javascript: $('.item,h3,.need-help,.footer,.navbar-toggle').css('display','none')");
                //super.onPageFinished(view, url);
            }
        });

       // webView.loadUrl("file:///android_asset/sample.html?cid=4204&roomid=6704&usertypeid=std&gender=&firstname=nona&lastname=nona&email=m@m.com");
        Log.d("Electa","file:///android_asset/sample.html?cid=4204&roomid="+roomID+"&usertypeid=std&gender=&firstname="+firstname+"&lastname="+lastname+"&email=m@m.com&externalname="+firstname);
        webView.loadUrl("file:///android_asset/sample.html?cid=4204&roomid="+roomID+"&usertypeid=std&gender=&firstname="+firstname+"&lastname="+lastname+"&email=m@m.com&externalname="+firstname);
       // webView.loadUrl("file:///android_asset/sample.html?cid=4204&roomid=7622&usertypeid=std&gender=&firstname=nona&lastname=nona&email=m@m.com");
     //   readXmlFeed();
    }

}
