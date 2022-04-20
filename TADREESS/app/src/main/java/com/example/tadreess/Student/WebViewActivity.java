package com.example.tadreess.Student;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.example.tadreess.AppsContants;
import com.example.tadreess.R;

public class WebViewActivity extends AppCompatActivity {
String strUrl;

WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

//        webView = findViewById(R.id.webView);
//
//
//
//        AppsContants.sharedpreferences = getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
//        strUrl = AppsContants.sharedpreferences.getString(AppsContants.URL, "");
//
//        webView .loadUrl("http://www.google.com");
//        setContentView(webView );
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
                    Toast.makeText(WebViewActivity.this, "Unknown Link, unable to handle", Toast.LENGTH_SHORT).show();

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
        webView.loadUrl("http://183.82.111.111/Tadreess/trader.html?Cid=41393&Token=8d91becd-4ae7-489a-9b1c-8ea635ca2188&Roomid=55735&Usertype=STD&Firstname=mallem&Lastname=mahesh&Email=mallemmahesh@gamil.com&UniqueUsername=iammahesh");



    }
}
