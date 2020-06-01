package tech.iwish.pickmall.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;

import java.util.Set;

import ir.hamiss.internetcheckconnection.InternetAvailabilityChecker;
import ir.hamiss.internetcheckconnection.InternetConnectivityListener;
import tech.iwish.pickmall.R;
import tech.iwish.pickmall.extended.TextViewFont;
import tech.iwish.pickmall.session.Share_session;

public class Setting extends AppCompatActivity  implements InternetConnectivityListener {
    ProgressBar progressBar;
    ScrollView scrollView;
    Button submit;
    ImageView back;
    TextViewFont logout;
    LinearLayout  return_policy,termscondition,contactus,privacypolicy;
    Share_session share_session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        InitializeActivity();
        SetActivityData();
        ActivityAction();
    }


    protected void InitializeActivity(){
        back= findViewById(R.id.back);
        progressBar= findViewById(R.id.progress);
        return_policy= findViewById(R.id.return_policy);
        logout= findViewById(R.id.logout);
        scrollView= findViewById(R.id.scroll);
        termscondition= findViewById(R.id.termscondition);
        contactus= findViewById(R.id.contact);
        privacypolicy= findViewById(R.id.privacypolicy);
        share_session= new Share_session(Setting.this);
        Connectivity();

    }


    protected void  ActivityAction() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        return_policy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Setting.this,ReturnPolicyActivity.class));
            }
        });
        termscondition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Setting.this,TermsAndConditionActivity.class));
            }
        });
        contactus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Setting.this,ContactUsActivity.class));
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                share_session.Logout();
            }
        });
        privacypolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Setting.this,PrivacyPolicyActivity.class));
                Animatoo.animateFade(Setting.this);
            }
        });


    }

    protected void SetActivityData(){
    }

    public void Connectivity(){
        InternetAvailabilityChecker mInternetAvailabilityChecker;
        mInternetAvailabilityChecker = InternetAvailabilityChecker.init(this);
        mInternetAvailabilityChecker.addInternetConnectivityListener(Setting.this);
    }

    @Override
    public void onInternetConnectivityChanged(boolean isConnected) {
        if (isConnected){
        }
        else {
            startActivity(new Intent(Setting.this,NoInternetConnectionActivity.class));
        }
    }


}
