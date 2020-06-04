package tech.iwish.pickmall.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import java.util.Map;

import tech.iwish.pickmall.R;
import tech.iwish.pickmall.session.Share_session;

import static tech.iwish.pickmall.session.Share_session.USER_NUMBER_CHECK;

public class SplashActivity extends AppCompatActivity {
    Share_session share_session;
    private Map data ;

    private final int SPLASH_DISPLAY_LENGTH = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        share_session= new Share_session(SplashActivity.this);

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                data = share_session.Fetchdata();
                if(data.get(USER_NUMBER_CHECK) != null){
                    startActivity(new Intent(SplashActivity.this , MainActivity.class));
                }
                else
                {
                    Intent mainIntent = new Intent(SplashActivity.this,UserDetail.class);
                    startActivity(mainIntent);
                }

            }
        }, SPLASH_DISPLAY_LENGTH);

    }
}
