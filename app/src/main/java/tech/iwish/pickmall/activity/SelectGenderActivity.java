package tech.iwish.pickmall.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import tech.iwish.pickmall.R;

public class SelectGenderActivity extends AppCompatActivity {
    ImageView male,female;
    Button finish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_gender);
        male= findViewById(R.id.male);
        female= findViewById(R.id.female);
        finish= findViewById(R.id.finish);

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
    }
}