package tech.iwish.pickmall.activity;

import androidx.activity.ComponentActivity;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;

import java.sql.Time;
import java.util.Timer;
import java.util.TimerTask;

import ir.hamiss.internetcheckconnection.InternetAvailabilityChecker;
import ir.hamiss.internetcheckconnection.InternetConnectivityListener;
import tech.iwish.pickmall.R;

public class NoInternetConnectionActivity extends AppCompatActivity   implements InternetConnectivityListener {
    private InternetAvailabilityChecker mInternetAvailabilityChecker;
    LinearLayout layout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_internet_connection);
        layout = findViewById(R.id.layout);
        mInternetAvailabilityChecker = InternetAvailabilityChecker.init(this);
        mInternetAvailabilityChecker.addInternetConnectivityListener(NoInternetConnectionActivity.this);



    }

    @Override
    public void onInternetConnectivityChanged(boolean isConnected) {
        if (isConnected){
            layout.setBackground(getDrawable(R.drawable.back_online));
        Intent intent= new Intent(NoInternetConnectionActivity.this,MainActivity.class);
        startActivity(intent);

        }
    }


}