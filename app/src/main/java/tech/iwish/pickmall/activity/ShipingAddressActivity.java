package tech.iwish.pickmall.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

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
import tech.iwish.pickmall.adapter.ShippingAddressAdapter;
import tech.iwish.pickmall.adapter.WishListAdapter;
import tech.iwish.pickmall.connection.JsonHelper;
import tech.iwish.pickmall.other.ShippingAddressList;
import tech.iwish.pickmall.other.WishlistList;
import tech.iwish.pickmall.session.Share_session;

public class ShipingAddressActivity extends AppCompatActivity {
    ImageView back;
    RecyclerView recyclerView;
    Share_session share_session;
    private List<ShippingAddressList> shippingAddressLists = new ArrayList<>();
    int type = 0 ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shiping_address);
        InitializeActivity();
        ActivityAction();
        SetRecycleView();
    }

    private void InitializeActivity(){
        back= findViewById(R.id.back);
        recyclerView= findViewById(R.id.recycle);
        share_session= new Share_session(ShipingAddressActivity.this);
    }

    private void ActivityAction(){
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }

    private void SetRecycleView(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ShipingAddressActivity.this);
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
                .url("http://173.212.226.143:8086/api/Shipping_Address")
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
                                shippingAddressLists.add(new ShippingAddressList(jsonHelper.GetResult("sno"),"Name : "+ jsonHelper.GetResult("name"),"Address : "+jsonHelper.GetResult("house_no")+jsonHelper.GetResult("colony")+jsonHelper.GetResult("landmark"),"Pincode : "+jsonHelper.GetResult("pincode"), "City/State : "+jsonHelper.GetResult("city")+" "+jsonHelper.GetResult("state"), jsonHelper.GetResult("address_type")));
                            }
                            ShipingAddressActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    ShippingAddressAdapter shippingAddressAdapter = new ShippingAddressAdapter(ShipingAddressActivity.this, shippingAddressLists , type ,null);
                                    recyclerView.setAdapter(shippingAddressAdapter);
                                }
                            });

                        }
                    }

                }
            }
        });


    }

}


