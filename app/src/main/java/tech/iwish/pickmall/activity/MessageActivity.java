package tech.iwish.pickmall.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.Map;

import tech.iwish.pickmall.R;
import tech.iwish.pickmall.config.Constants;
import tech.iwish.pickmall.session.Share_session;

import static tech.iwish.pickmall.session.Share_session.USERMOBILE;

public class MessageActivity extends AppCompatActivity implements View.OnClickListener {

    LinearLayout suggestionLinearLayout, newsLinear, cfLooksLinear;
    Share_session shareSession;
    Map data;
    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        suggestionLinearLayout = findViewById(R.id.suggestionLinearLayout);
        newsLinear = findViewById(R.id.newsLinear);
        cfLooksLinear = findViewById(R.id.cfLooksLinear);
        back = findViewById(R.id.back);

        shareSession = new Share_session(this);
        data = shareSession.Fetchdata();

        suggestionLinearLayout.setOnClickListener(this);
        newsLinear.setOnClickListener(this);
        cfLooksLinear.setOnClickListener(this);
        back.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        int id = view.getId();

        switch (id) {
            case R.id.suggestionLinearLayout:
                if (data.get(USERMOBILE) != null) {
                    startActivity(new Intent(MessageActivity.this, SuggestionBoxActivity.class));
                }
                break;
            case R.id.newsLinear:
                if (data.get(USERMOBILE) != null) {
                    startActivity(new Intent(MessageActivity.this, NewsActivity.class).putExtra("type", Constants.ALLNEWS));
                }
                break;
            case R.id.cfLooksLinear:
                if (data.get(USERMOBILE) != null) {
                    startActivity(new Intent(MessageActivity.this, CFLooksActivity.class));
                }
                break;
            case R.id.back:
                onBackPressed();
                break;
        }

    }
}