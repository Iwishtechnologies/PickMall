package tech.iwish.pickmall.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.bumptech.glide.Glide;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ir.hamiss.internetcheckconnection.InternetAvailabilityChecker;
import ir.hamiss.internetcheckconnection.InternetConnectivityListener;
import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import tech.iwish.pickmall.R;
import tech.iwish.pickmall.adapter.WishListAdapter;
import tech.iwish.pickmall.config.Constants;
import tech.iwish.pickmall.connection.JsonHelper;
import tech.iwish.pickmall.extended.TextViewFont;
import tech.iwish.pickmall.other.WishlistList;
import tech.iwish.pickmall.session.Share_session;

import static tech.iwish.pickmall.session.Share_session.USERMOBILE;

public class Account extends AppCompatActivity implements InternetConnectivityListener {
    ImageView viewall, profile,image,unpaid,wallet,wishlist,shippingaddress
            ,following,vendor,coupens,invite,myorder,entercode,setting,helpcenter,delivered,accountBottom,guestorder;
    LinearLayout fullview;
    Share_session share_session;
    TextView name;
    private List<WishlistList> wishlistLists = new ArrayList<>();
    TextViewFont wishcount;
    ScrollView scrollView;
    private Dialog dialog;
    Map data;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        InitializeActivity();
        share_session = new Share_session(this);
        WishCount();
        data = share_session.Fetchdata();
        if(data.get(USERMOBILE) != null){
            setActivityData();
            ActivityActions();
        }else {

            Toast.makeText(this, "activity open", Toast.LENGTH_SHORT).show();
        }



    }



    public void setActivityData() {
        name.setText(share_session.getUserDetail().get("username"));
        if(!share_session.getUserDetail().get("image").equals("Set Now"))
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
        delivered= findViewById(R.id.delivered);
        wishcount= findViewById(R.id.wishcount);
        guestorder= findViewById(R.id.guestorder);
        accountBottom= (ImageView) findViewById(R.id.accountBottom);
//        accountBottom.setImageDrawable(getResources().getDrawable(R.drawable.account_icon));
//        accountBottom.setVisibility(View.GONE);
        Connectivity();
    }




    public void ActivityActions(){
        profile.setOnClickListener(view -> {
            Intent intent= new Intent(Account.this,Profile.class);
            startActivity(intent);
            Animatoo.animateSlideDown(Account.this);
        });

        viewall.setOnClickListener(view -> fullview.setVisibility(View.VISIBLE));

        unpaid.setOnClickListener(view -> {
            Intent intent= new Intent(Account.this,UnpaidOrderActivity.class);
            startActivity(intent);
            Animatoo.animateSlideDown(Account.this);
        });
        wallet.setOnClickListener(view -> {
            Intent intent= new Intent(Account.this,WalletActivity.class);
            startActivity(intent);
            Animatoo.animateSlideDown(Account.this);
        });
        wishlist.setOnClickListener(view -> {
            Intent intent= new Intent(Account.this,WishListActivity.class);
            startActivity(intent);
            Animatoo.animateSlideDown(Account.this);
        });
        shippingaddress.setOnClickListener(view -> {
            Intent intent= new Intent(Account.this,ShipingAddressActivity.class);
            startActivity(intent);
            Animatoo.animateSlideDown(Account.this);
        });
        vendor.setOnClickListener(view -> {
            Intent intent= new Intent(Account.this,VendorRequestActivity.class);
            startActivity(intent);
            Animatoo.animateSlideDown(Account.this);
        });
        coupens.setOnClickListener(view -> {
            Intent intent= new Intent(Account.this,CoupenActivity.class);
            startActivity(intent);
            Animatoo.animateSlideDown(Account.this);
        });
        invite.setOnClickListener(view -> {
            Intent intent= new Intent(Account.this,InviteActivity.class);
            startActivity(intent);
            Animatoo.animateSlideDown(Account.this);
        });
        myorder.setOnClickListener(view -> {
            Intent intent= new Intent(Account.this,MyOederActitvity.class);
            startActivity(intent);
            Animatoo.animateSlideDown(Account.this);
        });
        following.setOnClickListener(view -> {
            Intent intent= new Intent(Account.this,FollowingActivity.class);
            startActivity(intent);
            Animatoo.animateSlideDown(Account.this);
        });

        entercode.setOnClickListener(view -> {
            Intent intent= new Intent(Account.this,EnterInviteCodeActivity.class);
            startActivity(intent);
            Animatoo.animateSlideDown(Account.this);
        });

        setting.setOnClickListener(view -> {
            Intent intent= new Intent(Account.this,Setting.class);
            startActivity(intent);
            Animatoo.animateSlideDown(Account.this);
        });
        helpcenter.setOnClickListener(view -> {
            Intent intent= new Intent(Account.this,SupportActivity.class);
            startActivity(intent);
            Animatoo.animateSlideDown(Account.this);
        });


        delivered.setOnClickListener(view -> {
            Intent intent= new Intent(Account.this,DeliveredItemActivity.class);
            startActivity(intent);
            Animatoo.animateSlideDown(Account.this);
        });

        guestorder.setOnClickListener(view -> {
            Intent intent= new Intent(Account.this,GuestOderActivity.class);
            startActivity(intent);
            Animatoo.animateSlideDown(Account.this);
        });


    }


   public void Connectivity(){
       InternetAvailabilityChecker mInternetAvailabilityChecker;
       mInternetAvailabilityChecker = InternetAvailabilityChecker.init(this);
       mInternetAvailabilityChecker.addInternetConnectivityListener(Account.this);
   }

    @Override
    public void onInternetConnectivityChanged(boolean isConnected) {
        if (isConnected){
        }
        else {
            startActivity(new Intent(Account.this,NoInternetConnectionActivity.class));
        }
    }



    private void WishCount(){
        OkHttpClient client = new OkHttpClient();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("mobile",share_session.getUserDetail().get("UserMobile") );

        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(JSON, jsonObject.toString());
        Request request = new Request.Builder().post(body)
                .url(Constants.GET_USER_WISHLIST)
                .build();
        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    String result = response.body().string();

                    JsonHelper jsonHelper = new JsonHelper(result);
                    if (jsonHelper.isValidJson()) {
                        String responses = jsonHelper.GetResult("response");
                        if (responses.equals("TRUE")) {
                            JSONArray jsonArray = jsonHelper.setChildjsonArray(jsonHelper.getCurrentJsonObj(), "data");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                jsonHelper.setChildjsonObj(jsonArray, i);
                                wishlistLists.add(new WishlistList(jsonHelper.GetResult("product_id"), jsonHelper.GetResult("ProductName"), jsonHelper.GetResult("item_id"), jsonHelper.GetResult("catagory_id"), jsonHelper.GetResult("actual_price"), jsonHelper.GetResult("discount_price"), jsonHelper.GetResult("discount_price_per"), jsonHelper.GetResult("status"), jsonHelper.GetResult("pimg"), jsonHelper.GetResult("vendor_id"), jsonHelper.GetResult("type"), jsonHelper.GetResult("datetime")));
                            }
                            Account.this.runOnUiThread(() -> {
//                                Log.e("fsg", String.valueOf(wishlistLists.size()));
                             wishcount.setText( String.valueOf(wishlistLists.size()));

                            });

                        }
                    }

                }
            }
        });
    }

}
