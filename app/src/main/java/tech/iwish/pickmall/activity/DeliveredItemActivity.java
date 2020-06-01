package tech.iwish.pickmall.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ir.hamiss.internetcheckconnection.InternetAvailabilityChecker;
import ir.hamiss.internetcheckconnection.InternetConnectivityListener;
import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import tech.iwish.pickmall.R;
import tech.iwish.pickmall.adapter.DeliveredProductAdapter;
import tech.iwish.pickmall.adapter.MyOrderAdapter;
import tech.iwish.pickmall.config.Constants;
import tech.iwish.pickmall.connection.JsonHelper;
import tech.iwish.pickmall.other.OrderList;
import tech.iwish.pickmall.session.Share_session;

public class DeliveredItemActivity extends AppCompatActivity implements InternetConnectivityListener {
    ImageView back,no_item;
    RecyclerView recyclerView;
    private List<OrderList> orderLists = new ArrayList<>();
    Share_session share_session;
    LinearLayout shimmer_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivered_item);
        InitializeActitvity();
        ActivityAction();
        SetRecycleView();
    }


    private void InitializeActitvity(){
        recyclerView= findViewById(R.id.recycle);
        shimmer_view= findViewById(R.id.shimmerView);
        no_item= findViewById(R.id.noitem);
        share_session= new Share_session(DeliveredItemActivity.this);

    }

    private  void ActivityAction(){

    }

    private void SetRecycleView(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(DeliveredItemActivity.this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

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
                .url(Constants.DELIVERED_PRODUCT)
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
                                orderLists.add(new OrderList(jsonHelper.GetResult("orderid"),jsonHelper.GetResult("customer_id"),jsonHelper.GetResult("delhivery_address"),jsonHelper.GetResult("order_amount"),jsonHelper.GetResult("payment_method"),jsonHelper.GetResult("gst"),jsonHelper.GetResult("shipping_charge"),jsonHelper.GetResult("status"),jsonHelper.GetResult("product_id"),jsonHelper.GetResult("qty"),jsonHelper.GetResult("type"),jsonHelper.GetResult("color"),jsonHelper.GetResult("size"),jsonHelper.GetResult("datetime"),
                                        jsonHelper.GetResult("item_id"),jsonHelper.GetResult("actual_price"),jsonHelper.GetResult("discount_price"),jsonHelper.GetResult("discount_price_per"),jsonHelper.GetResult("pimg"),jsonHelper.GetResult("FakeRating"),jsonHelper.GetResult("hot_product"),jsonHelper.GetResult("hsn_no"),jsonHelper.GetResult("weight"),jsonHelper.GetResult("flash_sale"),jsonHelper.GetResult("product_name"),jsonHelper.GetResult("order_status"),jsonHelper.GetResult("vendor_id")));
                            }
                            DeliveredItemActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if(orderLists.size()==0){
                                        shimmer_view.setVisibility(View.GONE);
                                        recyclerView.setVisibility(View.GONE);
                                        no_item.setVisibility(View.VISIBLE);
                                    }
                                    else
                                    {
                                        shimmer_view.setVisibility(View.GONE);
                                        no_item.setVisibility(View.GONE);
                                        recyclerView.setVisibility(View.VISIBLE);
                                        DeliveredProductAdapter myOrderAdapter = new DeliveredProductAdapter(DeliveredItemActivity.this, orderLists);
                                        recyclerView.setAdapter(myOrderAdapter);

                                    }

                                }
                            });

                        }
                    }

                }
            }
        });


    }

    public void Connectivity(){
        InternetAvailabilityChecker mInternetAvailabilityChecker;
        mInternetAvailabilityChecker = InternetAvailabilityChecker.init(this);
        mInternetAvailabilityChecker.addInternetConnectivityListener(DeliveredItemActivity.this);
    }

    @Override
    public void onInternetConnectivityChanged(boolean isConnected) {
        if (isConnected){
        }
        else {
            startActivity(new Intent(DeliveredItemActivity.this,NoInternetConnectionActivity.class));
        }
    }



}