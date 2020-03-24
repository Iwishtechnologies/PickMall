package tech.iwish.pickmall.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

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
import tech.iwish.pickmall.adapter.ColorSizeImageAdapter;
import tech.iwish.pickmall.adapter.ProductDetailsImageAdapter;
import tech.iwish.pickmall.connection.JsonHelper;
import tech.iwish.pickmall.fragment.ProductOverViewFragment;
import tech.iwish.pickmall.fragment.ProductSideColorBottomFragment;
import tech.iwish.pickmall.other.ProductDetailsImageList;
import tech.iwish.pickmall.other.ProductSizeColorList;

public class ProductDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView ac_priceEdit, dicount_price_Edit, title_name_edit, select_size_color;
    private List<ProductDetailsImageList> productDetailsListImageList = new ArrayList<>();
    //    private List<ProductDetailsImageList> productDetailsListImageList = new ArrayList<>();
    private List<ProductSizeColorList> productSizeColorLists = new ArrayList<>();
    private ViewPager productImageDetailsViewpager;
    private String product_color, product_name, product_Image, actual_price, discount_price, product_id;
    private RecyclerView color_size_image_recycle_view;
    private RatingBar ratingcheck;
    private Button add_card_btn, buy_now_btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        ac_priceEdit = (TextView) findViewById(R.id.priceEdit);
        dicount_price_Edit = (TextView) findViewById(R.id.dicount_price);
        title_name_edit = (TextView) findViewById(R.id.title_name_edit);
        select_size_color = (TextView) findViewById(R.id.select_size_color);
        productImageDetailsViewpager = (ViewPager) findViewById(R.id.productImageDetailsViewpager);
        color_size_image_recycle_view = (RecyclerView) findViewById(R.id.color_size_image_recycle_view);
        ratingcheck = (RatingBar) findViewById(R.id.ratingcheck);

        add_card_btn = (Button) findViewById(R.id.add_card_btn);
        buy_now_btn = (Button) findViewById(R.id.buy_now_btn);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        color_size_image_recycle_view.setLayoutManager(linearLayoutManager);

        Intent intent = getIntent();
        product_name = intent.getStringExtra("product_name");
        actual_price = intent.getStringExtra("actual_price");
        discount_price = intent.getStringExtra("discount_price");
        product_id = intent.getStringExtra("product_id");
        product_Image = intent.getStringExtra("product_Image");
        product_color = intent.getStringExtra("product_color");
        String product_type = intent.getStringExtra("product_type");

        switch (product_type) {
            case "flashSale":
                flashSaleProductimage();
                flashSaleProductsize_color();
                break;
            case "allProduct":
                ProductAllImage();
                ProductAllSize_color();

                break;
            case "friendsaleoneRs":
                friendsaleoneRsImage();
                friendsaleoneRscolor_size();
                break;
        }
        select_size_color.setOnClickListener(this);
        add_card_btn.setOnClickListener(this);
        buy_now_btn.setOnClickListener(this);


        ac_priceEdit.setText(getResources().getString(R.string.rs_symbol) + actual_price);
        title_name_edit.setText(product_name);
        dicount_price_Edit.setText(getResources().getString(R.string.rs_symbol) + discount_price);
        ratingcheck.setRating((float) 3.3);

        Bundle bundle = new Bundle();
        ProductOverViewFragment productOverViewFragment = new ProductOverViewFragment();
        bundle.putString("product_id", product_id);
        productOverViewFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.product_overview_frame, productOverViewFragment).commit();

    }

    private void ProductAllSize_color() {
        OkHttpClient okHttpClient1 = new OkHttpClient();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("product_id", product_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(JSON, jsonObject.toString());
        Request request1 = new Request.Builder().url("http://173.212.226.143:8086/api/ProductImageAll").post(body).build();
        okHttpClient1.newCall(request1).enqueue(new okhttp3.Callback() {
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
                                productSizeColorLists.add(new ProductSizeColorList(jsonHelper.GetResult("sno"), jsonHelper.GetResult("product_id"), jsonHelper.GetResult("size"), jsonHelper.GetResult("color"), jsonHelper.GetResult("qty")));
                            }

                            ProductDetailsActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    ColorSizeImageAdapter colorSizeImageAdapter = new ColorSizeImageAdapter(ProductDetailsActivity.this, productSizeColorLists, productDetailsListImageList);
                                    color_size_image_recycle_view.setAdapter(colorSizeImageAdapter);
                                }
                            });

                        }
                    }

                }
            }
        });


    }

    private void flashSaleProductimage() {

        OkHttpClient okHttpClient1 = new OkHttpClient();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("product_id", product_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(JSON, jsonObject.toString());
        Request request1 = new Request.Builder().url("http://173.212.226.143:8086/api/flashSaleIMage").post(body).build();
        okHttpClient1.newCall(request1).enqueue(new okhttp3.Callback() {
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
                                productDetailsListImageList.add(new ProductDetailsImageList(jsonHelper.GetResult("sno"), jsonHelper.GetResult("product_id"), jsonHelper.GetResult("image")));
                            }

                            ProductDetailsActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    ProductDetailsImageAdapter productDetailsImageAdapter = new ProductDetailsImageAdapter(ProductDetailsActivity.this, productDetailsListImageList);
                                    productImageDetailsViewpager.setAdapter(productDetailsImageAdapter);

                                }
                            });

                        }
                    }

                }
            }
        });


    }

    private void friendsaleoneRscolor_size() {
        OkHttpClient okHttpClient1 = new OkHttpClient();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("product_id", product_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(JSON, jsonObject.toString());
        Request request1 = new Request.Builder().url("http://173.212.226.143:8086/api/friendsaleallProductoneRs_color_size").post(body).build();
        okHttpClient1.newCall(request1).enqueue(new okhttp3.Callback() {
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
                                productSizeColorLists.add(new ProductSizeColorList(jsonHelper.GetResult("sno"), jsonHelper.GetResult("product_id"), jsonHelper.GetResult("size"), jsonHelper.GetResult("color"), jsonHelper.GetResult("qty")));
                            }

                            ProductDetailsActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    ColorSizeImageAdapter colorSizeImageAdapter = new ColorSizeImageAdapter(ProductDetailsActivity.this, productSizeColorLists, productDetailsListImageList);
                                    color_size_image_recycle_view.setAdapter(colorSizeImageAdapter);
                                }
                            });

                        }
                    }

                }
            }
        });


    }

    private void friendsaleoneRsImage() {


        OkHttpClient okHttpClient1 = new OkHttpClient();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("product_id", product_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(JSON, jsonObject.toString());
        Request request1 = new Request.Builder().url("http://173.212.226.143:8086/api/friendsaleallProductoneRsIMage").post(body).build();
        okHttpClient1.newCall(request1).enqueue(new okhttp3.Callback() {
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
                                productDetailsListImageList.add(new ProductDetailsImageList(jsonHelper.GetResult("sno"), jsonHelper.GetResult("product_id"), jsonHelper.GetResult("image")));
                            }

                            ProductDetailsActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    ProductDetailsImageAdapter productDetailsImageAdapter = new ProductDetailsImageAdapter(ProductDetailsActivity.this, productDetailsListImageList);
                                    productImageDetailsViewpager.setAdapter(productDetailsImageAdapter);
//                                    ColorSizeImageAdapter colorSizeImageAdapter = new ColorSizeImageAdapter(ProductDetailsActivity.this, productDetailsListImageList);
//                                    color_size_image_recycle_view.setAdapter(colorSizeImageAdapter);
                                }
                            });

                        }
                    }

                }
            }
        });


    }

    private void flashSaleProductsize_color() {
        OkHttpClient okHttpClient1 = new OkHttpClient();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("product_id", product_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(JSON, jsonObject.toString());
        Request request1 = new Request.Builder().url("http://173.212.226.143:8086/api/flashSaleImage").post(body).build();
        okHttpClient1.newCall(request1).enqueue(new okhttp3.Callback() {
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
                                productSizeColorLists.add(new ProductSizeColorList(jsonHelper.GetResult("sno"), jsonHelper.GetResult("product_id"), jsonHelper.GetResult("size"), jsonHelper.GetResult("color"), jsonHelper.GetResult("qty")));
                            }

                            ProductDetailsActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    ColorSizeImageAdapter colorSizeImageAdapter = new ColorSizeImageAdapter(ProductDetailsActivity.this, productSizeColorLists, productDetailsListImageList);
                                    color_size_image_recycle_view.setAdapter(colorSizeImageAdapter);
                                }
                            });

                        }
                    }

                }
            }
        });


    }


    private void ProductAllImage() {
        OkHttpClient okHttpClient = new OkHttpClient();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("product_id", product_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(JSON, jsonObject.toString());
        Request request1 = new Request.Builder().url("http://173.212.226.143:8086/api/productImage").post(body).build();
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
                                productDetailsListImageList.add(new ProductDetailsImageList(jsonHelper.GetResult("sno"), jsonHelper.GetResult("product_id"), jsonHelper.GetResult("image")));
                            }

                            ProductDetailsActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    ProductDetailsImageAdapter productDetailsImageAdapter = new ProductDetailsImageAdapter(ProductDetailsActivity.this, productDetailsListImageList);
                                    productImageDetailsViewpager.setAdapter(productDetailsImageAdapter);

                                }
                            });

                        }
                    }

                }
            }
        });


    }


    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.select_size_color:
            case R.id.add_card_btn:
                Bundle bundle = new Bundle();
                ProductSideColorBottomFragment productSideColorBottomFragment = new ProductSideColorBottomFragment(productSizeColorLists);
                bundle.putString("product_name", product_name);
                bundle.putString("actual_price", actual_price);
                bundle.putString("product_id", product_id);
                productSideColorBottomFragment.setArguments(bundle);
                productSideColorBottomFragment.show(getSupportFragmentManager(), productSideColorBottomFragment.getTag());
                break;
        }
    }
}
