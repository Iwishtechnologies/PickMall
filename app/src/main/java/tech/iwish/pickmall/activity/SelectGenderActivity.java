package tech.iwish.pickmall.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;
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
import tech.iwish.pickmall.config.Constants;
import tech.iwish.pickmall.connection.JsonHelper;

public class SelectGenderActivity extends AppCompatActivity {
    ImageView male,female;
    Button finish;
    String number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_gender);

        male= findViewById(R.id.male);
        female= findViewById(R.id.female);
        finish= findViewById(R.id.finish);

        number = getIntent().getStringExtra("number");

        male.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                male.setBackground(getResources().getDrawable(R.drawable.select_female));
                female.setBackground(getResources().getDrawable(R.drawable.unselect_female));
            }
        });
        female.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                male.setBackground(getResources().getDrawable(R.drawable.unselect_male));
                female.setBackground(getResources().getDrawable(R.drawable.select_female));
            }
        });

        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                genderselect();
            }
        });

    }

    private void genderselect() {



    }
}

























