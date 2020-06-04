package tech.iwish.pickmall.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import tech.iwish.pickmall.R;

public class SelectGenderActivity extends AppCompatActivity {
    ImageView male,female,next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_gender);
        male= findViewById(R.id.male);
        male= findViewById(R.id.female);
        next= findViewById(R.id.next);
    }
}