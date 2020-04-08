package tech.iwish.pickmall.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

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
import tech.iwish.pickmall.adapter.ProductDetailsImageAdapter;
import tech.iwish.pickmall.config.Constants;
import tech.iwish.pickmall.connection.JsonHelper;
import tech.iwish.pickmall.fragment.ProductFragment;
import tech.iwish.pickmall.other.ProductDetailsImageList;

public class VendorStoreActivity extends AppCompatActivity implements View.OnClickListener {

    private String vendor_id;
    private LinearLayout follow_btn, followings_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_store);

        follow_btn = (LinearLayout) findViewById(R.id.follow_btn);
        followings_btn = (LinearLayout) findViewById(R.id.followings_btn);

        follow_btn.setOnClickListener(this);
        followings_btn.setOnClickListener(this);

        vendor_id = getIntent().getStringExtra("vendor_id");

        Log.e("venor_id" , vendor_id );
        Bundle bundle = new Bundle();
        ProductFragment productFragment = new ProductFragment();
        bundle.putString("vendor_id", vendor_id);
        bundle.putString("type", "vendorStoreAllProduct");
        productFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.vendor_product_frame_layout , productFragment).commit();


        vendor_store_Details();
    }


    private void vendor_store_Details() {

        OkHttpClient okHttpClient = new OkHttpClient();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("vendor_id", vendor_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(JSON, jsonObject.toString());
        Request request1 = new Request.Builder().url(Constants.VENDOR_STOR_DETAILS).post(body).build();
        okHttpClient.newCall(request1).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful()) {

                    String result = response.body().string();
                    Log.e("response", result);
                    JsonHelper jsonHelper = new JsonHelper(result);
                    if (jsonHelper.isValidJson()) {
                        String responses = jsonHelper.GetResult("response");
                        if (responses.equals("TRUE")) {
                            JSONArray jsonArray = jsonHelper.setChildjsonArray(jsonHelper.getCurrentJsonObj(), "data");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                jsonHelper.setChildjsonObj(jsonArray, i);
//                                productDetailsListImageList.add(new ProductDetailsImageList(jsonHelper.GetResult("sno"), jsonHelper.GetResult("product_id"), jsonHelper.GetResult("image")));
                            }
                            VendorStoreActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                }
                            });

                        }
                    }

                }
            }
        });

        followings_btn.setVisibility(View.GONE);

    }


    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.follow_btn:
                follow_btn.setVisibility(View.GONE);
                followings_btn.setVisibility(View.VISIBLE);
                break;
            case R.id.followings_btn:
                follow_btn.setVisibility(View.VISIBLE);
                followings_btn.setVisibility(View.GONE);
                break;
        }
    }
}

























