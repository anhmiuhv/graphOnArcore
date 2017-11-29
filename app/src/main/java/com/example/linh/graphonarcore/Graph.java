package com.example.linh.graphonarcore;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

public class Graph extends AppCompatActivity {

    private WebView graphWebView;

    private void init() {
        graphWebView = findViewById(R.id.graphWebView);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        init();

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        String url = intent.getStringExtra("URL");

        graphWebView.loadUrl(url);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (0 != (getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE))
            { WebView.setWebContentsDebuggingEnabled(true); }
        }
    }
}
