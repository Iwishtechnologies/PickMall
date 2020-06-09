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
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.tasks.Task;
import com.google.firebase.dynamiclinks.DynamicLink;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.ShortDynamicLink;

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

                referCodeSend();

            }
        });

    }

    private void referCodeSend() {

        String a = "{\"data\" : \"TRUE\" , \"refer_code\" : \"" + refer_code + "\"}";

        DynamicLink dynamicLink = FirebaseDynamicLinks.getInstance().createDynamicLink()
                .setLink(Uri.parse("https://pickmall.com/?refer_code=" + refer_code.toString()))
//                    .setDomainUriPrefix("https://rayru.page.link")
                .setDomainUriPrefix("https://pickmall.page.link")
                // Open links with this app on Android
                .setAndroidParameters(new DynamicLink.AndroidParameters.Builder().build())
                // Open links with com.example.ios on iOS
                .setIosParameters(new DynamicLink.IosParameters.Builder("refer code" + refer_code.toString()).build())
                .buildDynamicLink();

        Uri dynamicLinkUri = dynamicLink.getUri();
        Log.e("Tag", dynamicLinkUri + "?");
        Task<ShortDynamicLink> shortLinkTask = FirebaseDynamicLinks.getInstance().createDynamicLink()
                .setLongLink(dynamicLinkUri)
//                    .setLongLink(Uri.parse(sharelinktext))
                .buildShortDynamicLink()
                .addOnCompleteListener(OneRsShareActivity.this, task -> {

                    if (task.isSuccessful()) {
                        // Short link created
//                        Uri shortLink = task.getResult().getShortLink();
//                        Uri flowchartLink = task.getResult().getPreviewLink();
//                        Intent intent = new Intent();
//                        intent.setType("text/plain");
//                        intent.setAction(Intent.ACTION_SEND);
//                        intent.putExtra(intent.EXTRA_TEXT, shortLink.toString());
//                        startActivity(intent);

//***************************************************************************************************

                        Uri imageUri = Uri.parse("http://stacktoheap.com/images/stackoverflow.png");

                        Uri shortLink = task.getResult().getShortLink();
                        Uri flowchartLink = task.getResult().getPreviewLink();
                        Intent intent = new Intent();
                        intent.setType("text/plain");
                        intent.setType("image/*");
                        intent.setAction(Intent.ACTION_SEND);
                        intent.putExtra(intent.EXTRA_TEXT, shortLink.toString());
                        intent.putExtra(Intent.EXTRA_STREAM, imageUri);
                        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        startActivity(intent);


//                        Intent intent = new Intent();
//                        intent.setAction(Intent.ACTION_SEND);
//                        intent.setType("image/*");
//                        Uri imageUri = Uri.parse("http://stacktoheap.com/images/stackoverflow.png");
//                        intent.putExtra(Intent.EXTRA_STREAM, imageUri);
//                        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    } else {
                        Log.e("error", task.getException().getMessage());
                    }
                });

    }


}