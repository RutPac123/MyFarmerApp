package com.example.sai.com;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

import com.example.sai.myfarmerapp.R;

public class NewsDetails extends AppCompatActivity {
    private WebView mwebview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);

        mwebview = findViewById(R.id.rel);
        Bundle bundle = getIntent().getExtras();
        mwebview.loadUrl(bundle.getString("link"));
    }
}
