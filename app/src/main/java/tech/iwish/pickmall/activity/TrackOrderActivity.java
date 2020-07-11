package tech.iwish.pickmall.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

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

public class TrackOrderActivity extends AppCompatActivity implements InternetConnectivityListener {
      ImageView outfordelivery,shipped,packed,contactus;
      TextViewFont orderid,date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_order);
        InitializeActivity();
        SetActivityData();
        ActivityAction();
    }
    private void InitializeActivity(){
        outfordelivery= findViewById(R.id.outfordelivery);
        shipped= findViewById(R.id.shipped);
        packed= findViewById(R.id.packed);
        contactus= findViewById(R.id.contact);
        orderid= findViewById(R.id.orderid);
        date= findViewById(R.id.date);
        Connectivity();

    }

    private void SetActivityData(){
       orderid.setText("OrderID : "+getIntent().getExtras().getString("orderid"));
       date.setText("Purchase Date - "+getIntent().getExtras().getString("date"));
    }

    private void ActivityAction(){
        contactus.setOnClickListener(v -> {
        startActivity(new Intent(TrackOrderActivity.this,ContactUsActivity.class));
        });
    }
//
//    private void SetRecycleView(){
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(WishListActivity.this);
//        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
//        recyclerView.setLayoutManager(linearLayoutManager);
//
//        OkHttpClient client = new OkHttpClient();
//        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
//        JSONObject jsonObject = new JSONObject();
//        try {
//            jsonObject.put("mobile",share_session.getUserDetail().get("UserMobile") );
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        RequestBody body = RequestBody.create(JSON, jsonObject.toString());
//        Request request = new Request.Builder().post(body)
//                .url(Constants.GET_USER_WISHLIST)
//                .build();
//        client.newCall(request).enqueue(new okhttp3.Callback() {
//            @Override
//            public void onFailure(@NotNull Call call, @NotNull IOException e) {
//                e.printStackTrace();
//            }
//
//            @Override
//            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
//                if (response.isSuccessful()) {
//                    String result = response.body().string();
//
//                    JsonHelper jsonHelper = new JsonHelper(result);
//                    if (jsonHelper.isValidJson()) {
//                        String responses = jsonHelper.GetResult("response");
//                        if (responses.equals("TRUE")) {
//                            JSONArray jsonArray = jsonHelper.setChildjsonArray(jsonHelper.getCurrentJsonObj(), "data");
//                            for (int i = 0; i < jsonArray.length(); i++) {
//                                jsonHelper.setChildjsonObj(jsonArray, i);
//                                wishlistLists.add(new WishlistList(jsonHelper.GetResult("product_id"), jsonHelper.GetResult("ProductName"), jsonHelper.GetResult("item_id"), jsonHelper.GetResult("catagory_id"), jsonHelper.GetResult("actual_price"), jsonHelper.GetResult("discount_price"), jsonHelper.GetResult("discount_price_per"), jsonHelper.GetResult("status"), jsonHelper.GetResult("pimg"), jsonHelper.GetResult("vendor_id"), jsonHelper.GetResult("type"), jsonHelper.GetResult("datetime")));
//                            }
//                            WishListActivity.this.runOnUiThread(new Runnable() {
//                                @Override
//                                public void run() {
//                                    if(wishlistLists.size()==0){
//                                        no_product.setVisibility(View.VISIBLE);
//                                        shimmer_view.setVisibility(View.GONE);
//                                        recyclerView.setVisibility(View.GONE);
//                                    }
//                                    else {
//                                        WishListAdapter wishListAdapter = new WishListAdapter(WishListActivity.this, wishlistLists);
//                                        shimmer_view.setVisibility(View.GONE);
//                                        recyclerView.setVisibility(View.VISIBLE);
//                                        recyclerView.setAdapter(wishListAdapter);
//                                    }
//
//                                }
//                            });
//
//                        }
//                    }
//
//                }
//            }
//        });
//
//
//    }


    public void Connectivity(){
        InternetAvailabilityChecker mInternetAvailabilityChecker;
        mInternetAvailabilityChecker = InternetAvailabilityChecker.init(this);
        mInternetAvailabilityChecker.addInternetConnectivityListener(TrackOrderActivity.this);
    }

    @Override
    public void onInternetConnectivityChanged(boolean isConnected) {
        if (isConnected){
        }
        else {
            startActivity(new Intent(TrackOrderActivity.this,NoInternetConnectionActivity.class));
        }
    }



}