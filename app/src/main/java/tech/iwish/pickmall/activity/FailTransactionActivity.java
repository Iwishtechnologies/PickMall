package tech.iwish.pickmall.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import tech.iwish.pickmall.R;

public class FailTransactionActivity extends AppCompatActivity {

    ImageView image;
    Button failed;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fail_transaction);

        image = findViewById(R.id.image);


        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FailTransactionActivity.this,MainActivity.class));
            }
        });



    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(FailTransactionActivity.this,MainActivity.class));
    }
}