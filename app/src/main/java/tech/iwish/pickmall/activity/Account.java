package tech.iwish.pickmall.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.bumptech.glide.Glide;
import tech.iwish.pickmall.R;
import tech.iwish.pickmall.config.Constants;
import tech.iwish.pickmall.session.Share_session;

public class Account extends AppCompatActivity {
    ImageView viewall, profile,image,unpaid,wallet,wishlist,shippingaddress,following,vendor,coupens,invite,myorder,entercode,setting,helpcenter;
    LinearLayout fullview;
    Share_session share_session;
    TextView name;
    ScrollView scrollView;
    private Dialog dialog;


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
            Glide.with(Account.this).load(Constants.IMAGES+share_session.getUserDetail().get("image")).into(image);
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
        myorder= findViewById(R.id.myorder);
        shippingaddress= findViewById(R.id.shippingaddress);
        entercode= findViewById(R.id.enterinvitecode);
        setting= findViewById(R.id.setting);
        helpcenter= findViewById(R.id.helpcenter);
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
        myorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(Account.this,MyOederActitvity.class);
                startActivity(intent);
                Animatoo.animateSlideDown(Account.this);
            }
        });
        following.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(Account.this,FollowingActivity.class);
                startActivity(intent);
                Animatoo.animateSlideDown(Account.this);
            }
        });

        entercode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(Account.this,EnterInviteCodeActivity.class);
                startActivity(intent);
                Animatoo.animateSlideDown(Account.this);
            }
        });

        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(Account.this,Setting.class);
                startActivity(intent);
                Animatoo.animateSlideDown(Account.this);
            }
        });
        helpcenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(Account.this,SupportActivity.class);
                startActivity(intent);
                Animatoo.animateSlideDown(Account.this);
            }
        });



    }


}
