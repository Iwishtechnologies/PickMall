package tech.iwish.pickmall.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import tech.iwish.pickmall.R;

public class SuccessfullyActivity extends AppCompatActivity {

    private ImageView successButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_successfully);

        successButton = (ImageView)findViewById(R.id.successButton);
        successButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SuccessfullyActivity.this , MainActivity.class));
            }
        });

    }
}
