package tech.iwish.pickmall.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;

import java.util.Timer;
import java.util.TimerTask;

import tech.iwish.pickmall.R;
import tech.iwish.pickmall.config.Constants;
import tech.iwish.pickmall.extended.TextViewFont;

public class OfferBannerActivity extends AppCompatActivity {
    TextViewFont time;
    ImageView layout;
    Timer myTimer;
    int i=3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer_banner);
        layout= findViewById(R.id.offerbanner);
        Glide.with(this).load(Constants.IMAGES+getIntent().getExtras().getString("banner")).into(layout);
        time= findViewById(R.id.time);
        myTimer = new Timer();
        myTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                OfferBannerActivity.this.runOnUiThread(() ->  time.setText(String.valueOf(i)));
                i--;
                if(i==0){
                    myTimer.cancel();
                    startActivity(new Intent(OfferBannerActivity.this,MainActivity.class));
                }
            }
        }, 1000, 1000);
    }
}