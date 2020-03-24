package tech.iwish.pickmall.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;

import tech.iwish.pickmall.R;

public class Register1Activity extends AppCompatActivity {

    private Button registerButton;
    private boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register1);
//        getSupportActionBar().hide();

        registerButton = (Button)findViewById(R.id.registerButton);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Register1Activity.this , Signup.class);
                startActivity(intent);
//                Animatoo.animateSpin(Register1Activity.this);
            }
        });

    }

    @Override
    public void onBackPressed() {


        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            return;
        }
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();
        this.doubleBackToExitPressedOnce = true;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);

    }
}
