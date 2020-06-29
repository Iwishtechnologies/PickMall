package tech.iwish.pickmall.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;

import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import tech.iwish.pickmall.R;
import tech.iwish.pickmall.config.Constants;
import tech.iwish.pickmall.extended.TextViewFont;
import tech.iwish.pickmall.session.Share_session;

import static tech.iwish.pickmall.session.Share_session.USER_NUMBER_CHECK;

public class OfferBannerActivity extends AppCompatActivity {
    TextViewFont time;
    ImageView layout;
    Timer myTimer;
    private Map data ;
    Share_session share_session;
    int i=3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer_banner);
        layout= findViewById(R.id.offerbanner);
        share_session= new Share_session(OfferBannerActivity.this);
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
                    if(share_session.GetFirsttime())
                    {
                        startActivity(new Intent(OfferBannerActivity.this , MainActivity.class));
                    }
                    else {
                        data = share_session.Fetchdata();
                        if(data.get(USER_NUMBER_CHECK) != null){
                            startActivity(new Intent(OfferBannerActivity.this , MainActivity.class));
                        }else {
                            startActivity(new Intent(OfferBannerActivity.this , Register1Activity.class));
                        }
//                        startActivity(new Intent(OfferBannerActivity.this , Register1Activity.class));
                    }
//                    startActivity(new Intent(OfferBannerActivity.this,Register1Activity.class));
                }
            }
        }, 1000, 1000);
    }
}