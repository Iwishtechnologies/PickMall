package tech.iwish.pickmall.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.StrikethroughSpan;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import tech.iwish.pickmall.R;
import tech.iwish.pickmall.config.Constants;

public class OneRsShareActivity extends AppCompatActivity {

    private ImageView image;
    private TextView productName, productAmount, productmrp;
    private Button invite_friend_deal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_rs_share);

        image = (ImageView) findViewById(R.id.image);
        productName = (TextView) findViewById(R.id.productName);
        productAmount = (TextView) findViewById(R.id.productAmount);
        productmrp = (TextView) findViewById(R.id.productmrp);
        invite_friend_deal = (Button) findViewById(R.id.invite_friend_deal);

        productName.setText(getIntent().getStringExtra("product_name"));

        SpannableString content = new SpannableString(getResources().getString(R.string.rs_symbol) + getIntent().getStringExtra("discount_price"));
        content.setSpan(new StrikethroughSpan(), 0, content.length(), 0);
        productmrp.setText(content);

        String a = Constants.IMAGES + getIntent().getStringExtra("product_image");
        Glide.with(this).load(a).into(image);

        invite_friend_deal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });

    }


}