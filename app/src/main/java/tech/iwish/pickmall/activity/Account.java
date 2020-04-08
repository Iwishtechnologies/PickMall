package tech.iwish.pickmall.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.bumptech.glide.Glide;


import tech.iwish.pickmall.R;
import tech.iwish.pickmall.config.Constants;
import tech.iwish.pickmall.session.Share_session;

public class Account extends AppCompatActivity {
    ImageView viewall, profile,image,unpaid,wallet;
    LinearLayout fullview;
    Share_session share_session;
    TextView name;
    ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        InitializeActivity();
        setActivityData();
        ActivityActions();



    }



    public void setActivityData() {
        name.setText(share_session.getUserDetail().get("username"));
        if(!share_session.getUserDetail().get("image").equals("Set Now "))
        {
            Glide.with(Account.this).load("173.212.226.143:8086/Androidapi/"+share_session.getUserDetail().get("image")).into(image);
        }
    }



    public void InitializeActivity(){
        share_session= new Share_session(Account.this);
        viewall= findViewById(R.id.viewall);
        fullview= findViewById(R.id.full);
        profile= findViewById(R.id.profile);
        image= findViewById(R.id.image);
        name= findViewById(R.id.name);
        scrollView= findViewById(R.id.scrollView);
        unpaid= findViewById(R.id.unpaid);
        wallet= findViewById(R.id.wallet);
    }



    public void ActivityActions(){
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(Account.this,Profile.class);
                startActivity(intent);
                Animatoo.animateSlideDown(Account.this);
            }
        });

        viewall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fullview.setVisibility(View.VISIBLE);
            }
        });

        unpaid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(Account.this,UnpaidOrderActivity.class);
                startActivity(intent);
                Animatoo.animateSlideDown(Account.this);
            }
        });
        wallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(Account.this,WalletActivity.class);
                startActivity(intent);
                Animatoo.animateSlideDown(Account.this);
            }
        });

    }
}
