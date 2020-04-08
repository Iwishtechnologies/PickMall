package tech.iwish.pickmall.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;

import tech.iwish.pickmall.R;

public class ReturnPolicyActivity extends AppCompatActivity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_return_policy);

        webView = (WebView)findViewById(R.id.webView);
        webView.loadUrl("https://www.javatpoint.com/android-webview-example");


    }
}
