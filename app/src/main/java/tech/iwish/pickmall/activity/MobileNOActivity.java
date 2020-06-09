package tech.iwish.pickmall.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import tech.iwish.pickmall.R;

public class MobileNOActivity extends AppCompatActivity {
    EditText mobile;
    Button next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile_n_o);
        mobile=findViewById(R.id.mobile);
        next= findViewById(R.id.next);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(MobileNOActivity.this,OTPActivity.class);
                startActivity(intent);
            }
        });



    }

    public void ValidateInput(String no){
        if(no.isEmpty())
    }
}