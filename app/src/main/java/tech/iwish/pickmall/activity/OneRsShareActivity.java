package tech.iwish.pickmall.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.StrikethroughSpan;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import tech.iwish.pickmall.R;
import tech.iwish.pickmall.config.Constants;

public class OneRsShareActivity extends AppCompatActivity {

    private ImageView image;
    private TextView productName, productAmount, productmrp;
    private Button invite_friend_deal;
    private String refer_code, refer_count;
    private LinearLayout LinearLayoutShare;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_rs_share);

        image = (ImageView) findViewById(R.id.image);
        productName = (TextView) findViewById(R.id.productName);
        productAmount = (TextView) findViewById(R.id.productAmount);
        productmrp = (TextView) findViewById(R.id.productmrp);
        invite_friend_deal = (Button) findViewById(R.id.invite_friend_deal);
        LinearLayoutShare = (LinearLayout) findViewById(R.id.LinearLayoutShare);

        refer_code = getIntent().getStringExtra("refer_code");
        refer_count = getIntent().getStringExtra("refer_count");

        productName.setText(getIntent().getStringExtra("product_name"));


        SpannableString content = new SpannableString(getResources().getString(R.string.rs_symbol) + getIntent().getStringExtra("discount_price"));
        content.setSpan(new StrikethroughSpan(), 0, content.length(), 0);
        productmrp.setText(content);

        String a = Constants.IMAGES + getIntent().getStringExtra("product_image");
        Glide.with(this).load(a).into(image);


        LinearLayoutShare.setWeightSum(5);
        for (int i = 0; i < 5; i++) {

            ImageView imageView = new ImageView(this);
            imageView.setImageResource(R.drawable.circle_plus_new_icon);
            LinearLayoutShare.addView(imageView);

//            LinearLayout.LayoutParams layoutParams= new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
//            layoutParams.setMargins(15,0,15,0);
//            imageView.setLayoutParams(layoutParams);

        }

        invite_friend_deal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                https://pickmall.com
//                google-site-verification=mzUJN-Fxf1sqqZo8haCkkID4Ba-tyyjebfatcP44m1Y
//                Toast.makeText(OneRsShareActivity.this, "" + refer_code, Toast.LENGTH_SHORT).show();



            }
        });

    }




}