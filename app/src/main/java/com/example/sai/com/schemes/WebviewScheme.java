package com.example.sai.com.schemes;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.sai.myfarmerapp.R;

public class WebviewScheme extends AppCompatActivity {
    private WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview_scheme);
        Intent intent = getIntent();
        String durl = intent.getExtras().getString("url");

        webView =findViewById(R.id.webView);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(durl);

        WebSettings webSettings = webView.getSettings();
        webSettings.getJavaScriptEnabled();
    }

    @Override
    public void onBackPressed() {
        if(webView.canGoBack()){
            webView.goBack();
        }else {
            super.onBackPressed();
        }
    }

}
