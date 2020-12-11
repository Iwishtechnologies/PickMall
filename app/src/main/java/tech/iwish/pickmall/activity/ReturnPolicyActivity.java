package tech.iwish.pickmall.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;

import ir.hamiss.internetcheckconnection.InternetAvailabilityChecker;
import ir.hamiss.internetcheckconnection.InternetConnectivityListener;
import tech.iwish.pickmall.R;

public class ReturnPolicyActivity extends AppCompatActivity  implements InternetConnectivityListener {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_return_policy);
Connectivity();
//        webView = (WebView)findViewById(R.id.webView);
//        webView.loadUrl("https://www.javatpoint.com/android-webview-example");


    }

    public void Connectivity(){
        InternetAvailabilityChecker mInternetAvailabilityChecker;
        mInternetAvailabilityChecker = InternetAvailabilityChecker.init(this);
        mInternetAvailabilityChecker.addInternetConnectivityListener(ReturnPolicyActivity.this);
    }

    @Override
    public void onInternetConnectivityChanged(boolean isConnected) {
        if (isConnected){
        }
        else {
            startActivity(new Intent(ReturnPolicyActivity.this,NoInternetConnectionActivity.class));
        }
    }

}
