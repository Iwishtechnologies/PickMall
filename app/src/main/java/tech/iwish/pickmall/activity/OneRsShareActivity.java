package tech.iwish.pickmall.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.StrikethroughSpan;
import android.util.Log;
import android.view.LayoutInflater;
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
    private TextView productName, productAmount, productmrp, time_set, shares;
    private Button invite_friend_deal;
    private String refer_code, refer_count, new_user_request, item_type, product_id;
    private LinearLayout LinearLayoutShare, time_layout;
    private int intReferCount;
    private ProgressBar progressBar;
    private Share_session shareSession;
    private ImageView refund_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_rs_share);

        image = (ImageView) findViewById(R.id.image);
        productName = (TextView) findViewById(R.id.productName);
        productAmount = (TextView) findViewById(R.id.productAmount);
        time_set = (TextView) findViewById(R.id.time_set);
        productmrp = (TextView) findViewById(R.id.productmrp);
        shares = (TextView) findViewById(R.id.shares);
        invite_friend_deal = (Button) findViewById(R.id.invite_friend_deal);
        LinearLayoutShare = (LinearLayout) findViewById(R.id.LinearLayoutShare);
        time_layout = (LinearLayout) findViewById(R.id.time_layout);

        refund_image = (ImageView) findViewById(R.id.refund_image);

        progressBar = (ProgressBar) findViewById(R.id.progress);

        progressBar.setVisibility(View.GONE);

        shareSession = new Share_session(this);

        refer_code = getIntent().getStringExtra("refer_code");
        refer_count = getIntent().getStringExtra("refer_count");
        new_user_request = getIntent().getStringExtra("new_user_request");
        item_type = getIntent().getStringExtra("item_type");
        product_id = getIntent().getStringExtra("product_id");

        productName.setText(getIntent().getStringExtra("product_name"));


        refund_image.setVisibility(View.VISIBLE);
        time_layout.setVisibility(View.VISIBLE);

        if (item_type.equals("friend_deal")) {

        } else if (item_type.equals("one_win")) {
            refund_image.setVisibility(View.GONE);
            time_layout.setVisibility(View.GONE);
        } else {

        }


        if (refer_count == null) {
            refer_count = "0";
        }
        shares.setText("Your Shares " + refer_count + " Out of " + new_user_request);

//        Toast.makeText(this, "" + new_user_request, Toast.LENGTH_SHORT).show();

//        SpannableString content = new SpannableString(getResources().getString(R.string.rs_symbol) + getIntent().getStringExtra("discount_price"));
//        content.setSpan(new StrikethroughSpan(), 0, content.length(), 0);
//        productmrp.setText(content);


//        popup


        String a = Constants.IMAGES + getIntent().getStringExtra("product_image");
        Glide.with(this).load(a).into(image);

        if (refer_count != null) {
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

                String data = null;

                if (item_type.equals("friend_deal")) {
                    data = "PICKMALL Online Shopping App India.\n\nRs.1 Friend’s Deal where you have a fair chance to win a product, you will get an amazing product for just Rs.1.\n\nPay Rs.1 to start a deal in Friends Deal.\n\nIsn’t it a great deal? So, what are you waiting for? Log on to the PICKMALL App now and choose from the different products that PICKMALL offers under this deal!\n\nYou can also get unbelievable cashback and discounts on orders! Hurry!\n";
                } else if (item_type.equals("one_win")) {
                    data = "PICKMALL Online Shopping App India.\n\n(1 WIN)\nGet Free Deal's In 1Win where you have a fair chance to win a product, you will get an amazing product Free Of Cost..\n\nClick On Share Button To Start A 1Win Deal's..\nShare With Maximum People's For Win This Deal.\n\nIsn’t it a great deal? So, what are you waiting for? Log on to the PICKMALL App now and choose from the different products that PICKMALL offers under this deal!\n\nYou can also get unbelievable cashback and discounts on orders! Hurry!\n";
                } else {
                    data = "PICKMALL Online Shopping App India.\n\nRs.90 Big Deal's\nwhere you have a fair chance to win a product, you will get an amazing Big product for just Rs.90.\n\nPay Rs.90 to start a deal in Big Deal..\n\nIsn’t it a great deal? So, what are you waiting for? Log on to the PICKMALL App now and choose from the different products that PICKMALL offers under this deal!\n\nYou can also get unbelievable cashback and discounts on orders! Hurry!\n";
                }
                if (data != null) {
                    refer_codeMethod(data);
                }

            }
        });

    }

    private void refer_codeMethod(String msg) {

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
                        intent.putExtra(intent.EXTRA_TEXT, msg + shortLink);
                        startActivity(intent);

//                        Drawable drawable = getResources().getDrawable(R.drawable.pick_mall_image);
//                        Bitmap bmp = null;
//                        bmp = ((BitmapDrawable) drawable).getBitmap();
//                        Uri uri = Uri.parse("android.resource://tech.iwish.pickmall/"+R.drawable.applogo);
                      /*  Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.drawable.applogo);
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_SEND);
                        intent.setType("text/plain");
                        intent.setPackage("com.whatsapp");
                        intent.setType("image/*");
                        intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Trip from Voyajo");
                        intent.putExtra(intent.EXTRA_TEXT, "Pay Rs.1 to start a deal in Friends Deal\n\nRs.1 Friend’s Deal wherein you have a fair chance to win a product, you will get an amazing product for just Rs.1.\n\nIsn’t it a great deal? So, what are you waiting for? Log on to the PICKMALL App now and choose from the different products that PICKMALL offers under this deal!\n\nYou can also get unbelievable cashback and discounts on orders! Hurry!\n\n " + shortLink);
                        intent.putExtra(Intent.EXTRA_STREAM, uri);
                        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        startActivity(intent);
                      */


                    } else {
                        Log.e("error", task.getException().toString());
                    }
                });

        progressBar.setVisibility(View.GONE);
        invite_friend_deal.setEnabled(true);

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(OneRsShareActivity.this, MainActivity.class);
        startActivity(intent);
    }
}