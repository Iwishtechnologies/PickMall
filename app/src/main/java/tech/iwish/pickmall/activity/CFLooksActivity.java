package tech.iwish.pickmall.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import tech.iwish.pickmall.R;
import tech.iwish.pickmall.config.Constants;

public class CFLooksActivity extends AppCompatActivity implements View.OnClickListener {

    LinearLayout new_post ,show_post;
    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c_f_looks);

        new_post = findViewById(R.id.new_post);
        show_post = findViewById(R.id.show_post);
        back = findViewById(R.id.back);

        new_post.setOnClickListener(this);
        show_post.setOnClickListener(this);
        back.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        switch (id){
            case R.id.new_post:
                startActivity(new Intent(CFLooksActivity.this,NewPostActivity.class));
                break;
            case R.id.show_post:
                startActivity(new Intent(CFLooksActivity.this,NewsActivity.class).putExtra("type", Constants.NEW_POST));
                break;
            case R.id.back:
                onBackPressed();
                break;
        }

    }
}
























