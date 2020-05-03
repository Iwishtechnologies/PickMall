package tech.iwish.pickmall.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.facebook.shimmer.ShimmerFrameLayout;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import tech.iwish.pickmall.R;
import tech.iwish.pickmall.adapter.CouponAdapter;
import tech.iwish.pickmall.adapter.MyOrderAdapter;
import tech.iwish.pickmall.adapter.WishListAdapter;
import tech.iwish.pickmall.connection.JsonHelper;
import tech.iwish.pickmall.other.CouponList;
import tech.iwish.pickmall.other.OrderList;
import tech.iwish.pickmall.other.WishlistList;
import tech.iwish.pickmall.session.Share_session;

public class MyOederActitvity extends AppCompatActivity {
    ImageView back;
    RecyclerView recyclerView;
    private List<OrderList> orderLists = new ArrayList<>();
    Share_session share_session;
    ShimmerFrameLayout progresss;

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_oeder_actitvity);
        InitializeActitvity();
        ActivityAction();
        SetRecycleView();
    }


    private void InitializeActitvity(){
        recyclerView= findViewById(R.id.recycle);
        progresss= findViewById(R.id.shimmerLayout);
        share_session= new Share_session(MyOederActitvity.this);

    }

    private  void ActivityAction(){

    }

    private void SetRecycleView(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MyOederActitvity.this);
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
                .url("http://173.212.226.143:8086/api/ordresummary")
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
                                orderLists.add(new OrderList(jsonHelper.GetResult("customer_id"),jsonHelper.GetResult("delhivery_address"),jsonHelper.GetResult("order_amount"),jsonHelper.GetResult("payment_method"),jsonHelper.GetResult("status"),jsonHelper.GetResult("datetime"),jsonHelper.GetResult("orderid"),jsonHelper.GetResult("product_id"),jsonHelper.GetResult("qty"),jsonHelper.GetResult("type")));
                            }
                            MyOederActitvity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    progresss.stopShimmer();
                                    progresss.setShimmer(null);
                                    MyOrderAdapter myOrderAdapter = new MyOrderAdapter(MyOederActitvity.this, orderLists);
                                    recyclerView.setAdapter(myOrderAdapter);

                                }
                            });

                        }
                    }

                }
            }
        });


    }

}
