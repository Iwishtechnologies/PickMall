package tech.iwish.pickmall.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import tech.iwish.pickmall.R;

public class Register1Activity extends AppCompatActivity {

    private Button registerButton ,sign_in;
    private boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register1);
//        getSupportActionBar().hide();

        registerButton = (Button)findViewById(R.id.registerButton);
        sign_in = (Button)findViewById(R.id.sign_in);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Register1Activity.this , LoginActivity.class);
                startActivity(intent);
//                Animatoo.animateSpin(Register1Activity.this);
            }
        });
        sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Register1Activity.this , Sign_InActivity.class);
                startActivity(intent);
//                Animatoo.animateSpin(Register1Activity.this);
            }
        });

    }

}
