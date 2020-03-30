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
import com.ldoublem.loadingviewlib.LVCircularCD;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import tech.iwish.pickmall.R;
import tech.iwish.pickmall.connection.JsonHelper;
import tech.iwish.pickmall.session.Share_session;

public class Account extends AppCompatActivity {
    ImageView viewall, profile,image;
    LinearLayout fullview;
    Share_session share_session;
    TextView name;
    ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        share_session= new Share_session(Account.this);
        viewall= findViewById(R.id.viewall);
        fullview= findViewById(R.id.full);
        profile= findViewById(R.id.profile);
        image= findViewById(R.id.image);
        name= findViewById(R.id.name);
        scrollView= findViewById(R.id.scrollView);
        setActivityData();

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


    }







    public void setActivityData()
    {
        name.setText(share_session.getUserDetail().get("username"));
        if(!share_session.getUserDetail().get("image").equals("Set Now "))
        {
            Glide.with(Account.this).load("173.212.226.143:8086/Androidapi/"+share_session.getUserDetail().get("image")).into(image);
        }
    }
}
