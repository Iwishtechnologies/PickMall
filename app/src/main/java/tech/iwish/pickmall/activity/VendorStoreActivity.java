package tech.iwish.pickmall.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import tech.iwish.pickmall.Interface.ProgressbarStartProduct;
import tech.iwish.pickmall.OkhttpConnection.ProductListF;
import tech.iwish.pickmall.R;
import tech.iwish.pickmall.adapter.AllOfferProductAdapter;
import tech.iwish.pickmall.adapter.ProductAdapter;
import tech.iwish.pickmall.adapter.ProductDetailsImageAdapter;
import tech.iwish.pickmall.config.Constants;
import tech.iwish.pickmall.connection.JsonHelper;
import tech.iwish.pickmall.fragment.ProductFragment;
import tech.iwish.pickmall.other.ProductDetailsImageList;
import tech.iwish.pickmall.other.ProductList;
import tech.iwish.pickmall.session.Share_session;

import static tech.iwish.pickmall.session.Share_session.USERMOBILE;

public class VendorStoreActivity extends AppCompatActivity implements View.OnClickListener {

    private String vendor_id;
    private LinearLayout follow_btn, followings_btn;
    private Share_session shareSession;
    private Map data;
    private TextView following_text, total_product, store_name, rating;
    RecyclerView vendorProductRecycleView;
    StaggeredGridLayoutManager layoutManager;
    AllOfferProductAdapter allOfferProductAdapter;
    RatingBar ratingbar;
    private List<ProductList> productListList = new ArrayList<>();
    ProgressbarStartProduct progressbarStartProduct;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_store);

        rating = findViewById(R.id.rating);
        ratingbar = findViewById(R.id.ratingbar1);
        follow_btn = (LinearLayout) findViewById(R.id.follow_btn);
        followings_btn = (LinearLayout) findViewById(R.id.followings_btn);

        following_text = (TextView) findViewById(R.id.following_text);
        total_product = (TextView) findViewById(R.id.total_product);
        store_name = (TextView) findViewById(R.id.store_name);
        vendorProductRecycleView = findViewById(R.id.vendorProductRecycleView);


        layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        vendorProductRecycleView.setLayoutManager(layoutManager);

        shareSession = new Share_session(this);
        data = shareSession.Fetchdata();

        follow_btn.setOnClickListener(this);
        followings_btn.setOnClickListener(this);

        vendor_id = getIntent().getStringExtra("vendor_id");

        progressbarStartProduct = val -> {

        };

//        Log.e("venor_id", vendor_id);
//        Bundle bundle = new Bundle();
//        ProductFragment productFragment = new ProductFragment();
//        bundle.putString("vendor_id", vendor_id);
//        bundle.putString("type", "vendorStoreAllProduct");
//        productFragment.setArguments(bundle);
//        getSupportFragmentManager().beginTransaction().replace(R.id.vendor_product_frame_layout, productFragment).commit();


        vendor_store_Details();
        vendorProduct();
        follow_show(Constants.VENDOR_STORE_FOLLOW_CHECK);
    }

    private void vendorProduct() {

        allOfferProductAdapter = new AllOfferProductAdapter(VendorStoreActivity.this, ProductListF.productFake(), "", progressbarStartProduct);
        vendorProductRecycleView.setAdapter(allOfferProductAdapter);

        OkHttpClient okHttpClient = new OkHttpClient();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("item_id", vendor_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(JSON, jsonObject.toString());
//        Request request1 = new Request.Builder().url("http://173.212.226.143:8086/api/"+PRODUCT_PERAMETER).post(body).build();
        Request request1 = new Request.Builder().url(Constants.VENDOR_STORE_ALL_PRODUCT).post(body).build();
        okHttpClient.newCall(request1).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    String result = response.body().string();
                    Log.e("output", result);
                    JsonHelper jsonHelper = new JsonHelper(result);
                    if (jsonHelper.isValidJson()) {
                        productListList.clear();
                        String responses = jsonHelper.GetResult("response");
                        if (responses.equals("TRUE")) {


                            JSONArray jsonArray = jsonHelper.setChildjsonArray(jsonHelper.getCurrentJsonObj(), "data");

                            for (int i = 0; i < jsonArray.length(); i++) {

                                jsonHelper.setChildjsonObj(jsonArray, i);
                                productListList.add(new ProductList(jsonHelper.GetResult("product_id"),
                                        jsonHelper.GetResult("ProductName"),
                                        jsonHelper.GetResult("SKU"),
                                        jsonHelper.GetResult("item_id"),
                                        jsonHelper.GetResult("catagory_id"),
                                        jsonHelper.GetResult("actual_price"),
                                        jsonHelper.GetResult("discount_price"),
                                        jsonHelper.GetResult("discount_price_per"),
                                        jsonHelper.GetResult("status"),
                                        jsonHelper.GetResult("pimg"),
                                        jsonHelper.GetResult("vendor_id"),
                                        jsonHelper.GetResult("FakeRating"),
                                        jsonHelper.GetResult("gst"),
                                        jsonHelper.GetResult("hot_product"),
                                        jsonHelper.GetResult("hsn_no"),
                                        jsonHelper.GetResult("weight"),
                                        jsonHelper.GetResult("type"),
                                        jsonHelper.GetResult("flash_sale"),
                                        jsonHelper.GetResult("extraoffer"),
                                        jsonHelper.GetResult("startdate"),
                                        jsonHelper.GetResult("enddate")
                                ));

                            }
                
                            new Handler(getMainLooper()).post(() -> {
                                vendorProductRecycleView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
                                vendorProductRecycleView.setAdapter(new ProductAdapter(VendorStoreActivity.this, productListList, "", progressbarStartProduct));
                            });

                        }

                    }
                }
                response.close();
            }
        });


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
        Request request1 = new Request.Builder().url(Constants.VENDOR_STORE_DETAILS).post(body).build();
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
                    final JsonHelper jsonHelper = new JsonHelper(result);
                    if (jsonHelper.isValidJson()) {
                        String responses = jsonHelper.GetResult("response");
                        if (responses.equals("TRUE")) {
                            JSONArray jsonArray = jsonHelper.setChildjsonArray(jsonHelper.getCurrentJsonObj(), "data");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                jsonHelper.setChildjsonObj(jsonArray, i);
                                if (VendorStoreActivity.this != null) {
                                    VendorStoreActivity.this.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            following_text.setText(jsonHelper.GetResult("following_count"));
                                            total_product.setText(jsonHelper.GetResult("product_count"));
                                            store_name.setText(jsonHelper.GetResult("shope_name"));
                                            rating.setText(jsonHelper.GetResult("rating") + "/5");
                                            ratingbar.setRating(Float.parseFloat(jsonHelper.GetResult("rating")));
                                        }
                                    });
                                }
                            }
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
            case R.id.followings_btn:
                follow_show(Constants.VENDOR_STORE_FOLLOWING);
                break;
        }
    }


    private void follow_show(String Api) {

        OkHttpClient okHttpClient = new OkHttpClient();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("client_number", data.get(USERMOBILE).toString());
            jsonObject.put("vendor_id", vendor_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(JSON, jsonObject.toString());
        Request request1 = new Request.Builder().url(Api).post(body).build();
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

                            if (VendorStoreActivity.this != null) {
                                VendorStoreActivity.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {

                                        follow_btn.setVisibility(View.GONE);
                                        followings_btn.setVisibility(View.VISIBLE);
                                    }
                                });
                            }
                        } else {
                            if (VendorStoreActivity.this != null) {
                                VendorStoreActivity.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        follow_btn.setVisibility(View.VISIBLE);
                                        followings_btn.setVisibility(View.GONE);
                                    }
                                });
                            }
                        }


                    }

                }
            }
        });

    }

}

























