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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.dynamiclinks.DynamicLink;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.ShortDynamicLink;

import tech.iwish.pickmall.R;
import tech.iwish.pickmall.config.Constants;
import tech.iwish.pickmall.countdowntime.FriendDeaTimeSet;
import tech.iwish.pickmall.session.Share_session;

public class OneRsShareActivity extends AppCompatActivity {

    private ImageView image;
    private TextView productName, productAmount, productmrp,time_set;
    private Button invite_friend_deal;
    private String refer_code, refer_count, new_user_request,item_type ,product_id ;
    private LinearLayout LinearLayoutShare;
    private int intReferCount;
    private ProgressBar progressBar;
    private Share_session shareSession ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_rs_share);

        image = (ImageView) findViewById(R.id.image);
        productName = (TextView) findViewById(R.id.productName);
        productAmount = (TextView) findViewById(R.id.productAmount);
        time_set = (TextView) findViewById(R.id.time_set);
        productmrp = (TextView) findViewById(R.id.productmrp);
        invite_friend_deal = (Button) findViewById(R.id.invite_friend_deal);
        LinearLayoutShare = (LinearLayout) findViewById(R.id.LinearLayoutShare);

        progressBar = (ProgressBar) findViewById(R.id.progress);

        progressBar.setVisibility(View.GONE);

        shareSession = new Share_session(this);

        refer_code = getIntent().getStringExtra("refer_code");
        refer_count = getIntent().getStringExtra("refer_count");
        new_user_request = getIntent().getStringExtra("new_user_request");
        item_type = getIntent().getStringExtra("item_type");
        product_id = getIntent().getStringExtra("product_id");

        productName.setText(getIntent().getStringExtra("product_name"));

//        Toast.makeText(this, "" + new_user_request, Toast.LENGTH_SHORT).show();

//        SpannableString content = new SpannableString(getResources().getString(R.string.rs_symbol) + getIntent().getStringExtra("discount_price"));
//        content.setSpan(new StrikethroughSpan(), 0, content.length(), 0);
//        productmrp.setText(content);

        String a = Constants.IMAGES + getIntent().getStringExtra("product_image");
        Glide.with(this).load(a).into(image);

        if(refer_count != null){
            intReferCount = Integer.parseInt(refer_count);
        }



        if (item_type.equals("friend_deal")) {
            new FriendDeaTimeSet(product_id, shareSession.getUserDetail().get("UserMobile"), OneRsShareActivity.this, time_set, item_type).Time_12_H();
        } else if (item_type.equals("one_win")) {
        } else {
            new FriendDeaTimeSet(product_id, shareSession.getUserDetail().get("UserMobile"), OneRsShareActivity.this, time_set, item_type).Time_24_H();
        }


//        int new_user_requestInt = Integer.parseInt(new_user_request);
//        LinearLayoutShare.setWeightSum(5);
//        for (int i = 0; i < new_user_requestInt; i++) {
//            if(refer_count != null){
//
//                if (i <= intReferCount - 1) {
//
//                    LinearLayout linearLayout = new LinearLayout(this);
//                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//                    linearLayout.setLayoutParams(params);
//                    params.setMargins(10, 0, 10, 0);
//                    ImageView imageView = new ImageView(this);
//                    imageView.setImageResource(R.drawable.circle_plus_icon);
//                    linearLayout.addView(imageView);
//                    LinearLayoutShare.addView(linearLayout);
//
//                } else {
//                    LinearLayout linearLayout = new LinearLayout(this);
//                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//                    linearLayout.setLayoutParams(params);
//                    params.setMargins(10, 0, 10, 0);
//                    ImageView imageView = new ImageView(this);
//                    imageView.setImageResource(R.drawable.circle_plus_new_icon);
//                    linearLayout.addView(imageView);
//                    LinearLayoutShare.addView(linearLayout);
//
//                }
//            }else {
//                LinearLayout linearLayout = new LinearLayout(this);
//                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//                linearLayout.setLayoutParams(params);
//                params.setMargins(10, 0, 10, 0);
//                ImageView imageView = new ImageView(this);
//                imageView.setImageResource(R.drawable.circle_plus_new_icon);
//                linearLayout.addView(imageView);
//                LinearLayoutShare.addView(linearLayout);
//
//
//            }
//
//
//        }

        invite_friend_deal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                refer_codeMethod();

            }
        });

    }

    private void refer_codeMethod() {

        invite_friend_deal.setEnabled(false);
        progressBar.setVisibility(View.VISIBLE);

        String a = "{\"data\" : \"TRUE\" , \"refer_code\" : \"" + refer_code + "\"}";
        DynamicLink dynamicLink = FirebaseDynamicLinks.getInstance().createDynamicLink()
                .setLink(Uri.parse("https://pickmall.com/?refer_code=" + refer_code.toString()))
                .setDomainUriPrefix("https://pickmall.page.link")
                .setAndroidParameters(new DynamicLink.AndroidParameters.Builder().build())
                .setIosParameters(new DynamicLink.IosParameters.Builder("refer code" + refer_code.toString()).build())
                .buildDynamicLink();

        Uri dynamicLinkUri = dynamicLink.getUri();
        Task<ShortDynamicLink> shortLinkTask = FirebaseDynamicLinks.getInstance().createDynamicLink()
                .setLongLink(dynamicLinkUri)
                .buildShortDynamicLink()
                .addOnCompleteListener(this, task -> {

                    if (task.isSuccessful()) {
                        Uri shortLink = task.getResult().getShortLink();
                        Uri flowchartLink = task.getResult().getPreviewLink();
                        Intent intent = new Intent();
                        intent.setType("text/plain");
                        intent.setAction(Intent.ACTION_SEND);
                        intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Trip from Voyajo");
                        intent.putExtra(intent.EXTRA_TEXT, "Pay Rs.1 to start a deal in Friends Deal\n\nRs.1 Friend’s Deal wherein you have a fair chance to win a product, you will get an amazing product for just Rs.1.\n\nIsn’t it a great deal? So, what are you waiting for? Log on to the PICKMALL App now and choose from the different products that PICKMALL offers under this deal!\n\nYou can also get unbelievable cashback and discounts on orders! Hurry!\n\n " + shortLink);
                        startActivity(intent);
                    } else {
                        Log.e("error", task.getException().toString());
                    }
                });

        progressBar.setVisibility(View.GONE);
        invite_friend_deal.setEnabled(true);

    }


}