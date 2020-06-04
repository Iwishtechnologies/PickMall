package tech.iwish.pickmall.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;

import tech.iwish.pickmall.R;

public class MobileNOActivity extends AppCompatActivity {
    EditText mobile;
    ImageView next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile_n_o);
        mobile=findViewById(R.id.mobile);
        next= findViewById(R.id.next);

    }
}