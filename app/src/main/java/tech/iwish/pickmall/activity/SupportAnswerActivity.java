package tech.iwish.pickmall.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;

import tech.iwish.pickmall.R;
import tech.iwish.pickmall.extended.TextViewFont;

public class SupportAnswerActivity extends AppCompatActivity {
    TextViewFont answer,query;
    ImageView back;
    RelativeLayout contactus;
    Button yes,no;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support_answer);
        InitializeActivity();
        ActivityAction();
        SetActivityData();
    }

    private void InitializeActivity(){
        back= findViewById(R.id.back);
        answer= findViewById(R.id.answer);
        query= findViewById(R.id.query);
        contactus= findViewById(R.id.contact);
        yes= findViewById(R.id.yes);
        no= findViewById(R.id.no);
    }

    private void ActivityAction(){
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        contactus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(SupportAnswerActivity.this,ContactUsActivity.class);
                startActivity(intent);
                Animatoo.animateInAndOut(SupportAnswerActivity.this);
            }
        });
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(SupportAnswerActivity.this, "Thanks for your valuable feedback", Toast.LENGTH_SHORT).show();
            }
        });
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(SupportAnswerActivity.this, "Thanks for your valuable feedback", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void SetActivityData(){
        answer.setText(getIntent().getStringExtra("answer"));
        query.setText(getIntent().getStringExtra("query"));
    }
}
