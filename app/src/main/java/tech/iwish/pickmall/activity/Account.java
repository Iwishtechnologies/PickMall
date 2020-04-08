package tech.iwish.pickmall.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.bumptech.glide.Glide;
import com.ldoublem.loadingviewlib.LVCircularCD;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import tech.iwish.pickmall.R;
import tech.iwish.pickmall.connection.JsonHelper;
import tech.iwish.pickmall.session.Share_session;

public class Account extends AppCompatActivity {
    ImageView viewall, profile,image,unpaid,wallet,wishlist,shippingaddress,following,vendor,coupens,invite;
    LinearLayout fullview;
    Share_session share_session;
    TextView name;
    ScrollView scrollView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        InitializeActivity();
        setActivityData();
        ActivityActions();



    }



    public void setActivityData() {
        name.setText(share_session.getUserDetail().get("username"));
        if(!share_session.getUserDetail().get("image").equals("Set Now "))
        {
            Glide.with(Account.this).load("173.212.226.143:8086/Androidapi/"+share_session.getUserDetail().get("image")).into(image);
        }
    }



    public void InitializeActivity(){
        share_session= new Share_session(Account.this);
        viewall= findViewById(R.id.viewall);
        fullview= findViewById(R.id.full);
        profile= findViewById(R.id.profile);
        image= findViewById(R.id.image);
        name= findViewById(R.id.name);
        scrollView= findViewById(R.id.scrollView);
        unpaid= findViewById(R.id.unpaid);
        wallet= findViewById(R.id.wallet);
        wishlist= findViewById(R.id.wishlist);
        following= findViewById(R.id.following);
        vendor= findViewById(R.id.vendoe);
        coupens= findViewById(R.id.coupens);
        invite= findViewById(R.id.invite);
        shippingaddress= findViewById(R.id.shippingaddress);
    }



    public void ActivityActions(){
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(Account.this,Profile.class);
                startActivity(intent);
                Animatoo.animateSlideDown(Account.this);
            }
        });

        viewall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fullview.setVisibility(View.VISIBLE);
            }
        });

        unpaid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(Account.this,UnpaidOrderActivity.class);
                startActivity(intent);
                Animatoo.animateSlideDown(Account.this);
            }
        });
        wallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(Account.this,WalletActivity.class);
                startActivity(intent);
                Animatoo.animateSlideDown(Account.this);
            }
        });
        wishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(Account.this,WishListActivity.class);
                startActivity(intent);
                Animatoo.animateSlideDown(Account.this);
            }
        });
        shippingaddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(Account.this,ShipingAddressActivity.class);
                startActivity(intent);
                Animatoo.animateSlideDown(Account.this);
            }
        });
        vendor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(Account.this,VendorRequestActivity.class);
                startActivity(intent);
                Animatoo.animateSlideDown(Account.this);
            }
        });
        coupens.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(Account.this,CoupenActivity.class);
                startActivity(intent);
                Animatoo.animateSlideDown(Account.this);
            }
        });
        invite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(Account.this,InviteActivity.class);
                startActivity(intent);
                Animatoo.animateSlideDown(Account.this);
            }
        });


    }
}
