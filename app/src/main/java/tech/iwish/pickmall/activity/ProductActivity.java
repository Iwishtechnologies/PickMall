package tech.iwish.pickmall.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

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
import tech.iwish.pickmall.Interface.CardProductRefreshInterface;
import tech.iwish.pickmall.R;
import tech.iwish.pickmall.config.Constants;
import tech.iwish.pickmall.connection.JsonHelper;
import tech.iwish.pickmall.fragment.ProductFragment;
import tech.iwish.pickmall.fragment.ProductSideColorBottomFragment;

public class ProductActivity extends AppCompatActivity implements View.OnClickListener {


    public CardProductRefreshInterface cardProductRefre;
    private ImageView back, image_item;
    private LinearLayout search_product, item_image_layout;
    private TextView itme_name, filter, shorts, best_sellers, pricefilter;
    String type;
    String imageSet , a;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        back = (ImageView) findViewById(R.id.back);
        image_item = (ImageView) findViewById(R.id.image_item);
        search_product = (LinearLayout) findViewById(R.id.search_product);
        item_image_layout = (LinearLayout) findViewById(R.id.item_image_layout);
        itme_name = (TextView) findViewById(R.id.itme_name);
        filter = (TextView) findViewById(R.id.filter);
        shorts = (TextView) findViewById(R.id.shorts);
        best_sellers = (TextView) findViewById(R.id.best_sellers);
        pricefilter = (TextView) findViewById(R.id.pricefilter);

        Intent intent = getIntent();
         type = intent.getStringExtra("type");

        switch (type) {
            case "MainActivity_product":
                image(getIntent().getStringExtra("item_id"));
                productloadfradment(getIntent().getStringExtra("item_id"), getIntent().getStringExtra("item_name"), "product");
                break;
            case "Category_by_product":
                categoryImage(getIntent().getStringExtra("category_id"));
                productloadfradment(getIntent().getStringExtra("category_id"), getIntent().getStringExtra("category_name"), "Category_by_product");
                break;
            case "searchActivity":
                item_image_layout.setVisibility(View.GONE);
                productloadfradment(getIntent().getStringExtra("name"), getIntent().getStringExtra("name"), "searchActivity");
                break;
            case "FilterActivity":
                item_image_layout.setVisibility(View.GONE);
                productloadfradment(getIntent().getStringExtra("item_id"), getIntent().getStringExtra("itemName"), "FilterActivity");
                break;
            case "prepaid":
                image("30");
                productloadfradment("30", getIntent().getStringExtra("itemName"), "prepaid");
                break;
            case "silder_load":
                item_image_layout.setVisibility(View.GONE);
                productloadfradment(getIntent().getStringExtra("item_id"), getIntent().getStringExtra("itemName"), "silder_load");
                break;
            case "both_category_open":
                item_image_layout.setVisibility(View.GONE);
                productloadsfradment(getIntent().getStringExtra("item_id"),getIntent().getStringExtra("category_id"), getIntent().getStringExtra("itemName"), "both_category_open");
                break;

        }



        search_product.setOnClickListener(this);
        back.setOnClickListener(this);
        filter.setOnClickListener(this);
        shorts.setOnClickListener(this);
        best_sellers.setOnClickListener(this);
        pricefilter.setOnClickListener(this);


    }


    private void image(String item_id) {

        OkHttpClient okHttpClient = new OkHttpClient();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("item_id", item_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(JSON, jsonObject.toString());
        Request request1 = new Request.Builder().url(Constants.ITEM_IMAGE).post(body).build();
        okHttpClient.newCall(request1).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    String result = response.body().string();
                    Log.e("result",result);
                    final JsonHelper jsonHelper = new JsonHelper(result);
                    if (jsonHelper.isValidJson()) {
                        String responses = jsonHelper.GetResult("response");
                        if (responses.equals("TRUE")) {
                            JSONArray jsonArray = jsonHelper.setChildjsonArray(jsonHelper.getCurrentJsonObj(), "data");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                jsonHelper.setChildjsonObj(jsonArray, i);

                                ProductActivity.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        item_image_layout.setVisibility(View.VISIBLE);
                                        String image = jsonHelper.GetResult("banner_img");
                                        a = Constants.IMAGES + image;
                                        Glide.with(ProductActivity.this).load(a).into(image_item);

                                    }
                                });



                            }
                        } else {
                            ProductActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    item_image_layout.setVisibility(View.GONE);
                                }
                            });
                        }
                    }

                }
            }
        });

    }


    private void categoryImage(String item_id) {

        OkHttpClient okHttpClient = new OkHttpClient();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("item_id", item_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(JSON, jsonObject.toString());
        Request request1 = new Request.Builder().url(Constants.CATEGORY_IMAGE_SET).post(body).build();
        okHttpClient.newCall(request1).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    String result = response.body().string();
                    Log.e("result",result);
                    final JsonHelper jsonHelper = new JsonHelper(result);
                    if (jsonHelper.isValidJson()) {
                        String responses = jsonHelper.GetResult("response");
                        if (responses.equals("TRUE")) {
                            JSONArray jsonArray = jsonHelper.setChildjsonArray(jsonHelper.getCurrentJsonObj(), "data");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                jsonHelper.setChildjsonObj(jsonArray, i);
                                ProductActivity.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        item_image_layout.setVisibility(View.VISIBLE);
                                        String image = jsonHelper.GetResult("banner_img");
                                        String a = Constants.IMAGES + image;
                                        Glide.with(ProductActivity.this).load(a).into(image_item);
                                    }
                                });
                            }
                        } else {
                            ProductActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    item_image_layout.setVisibility(View.GONE);
                                }
                            });
                        }
                    }

                }
            }
        });

    }

    private void productloadfradment(String id, String name, String type) {

        Bundle bundle = new Bundle();
        ProductFragment productFragment = new ProductFragment();
        bundle.putString("item", id);
        bundle.putString("type", type);
        productFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.product_framelayout, productFragment).commit();
        itme_name.setText(name);


    }
    private void productloadsfradment(String item_id,String category_id, String name, String type ) {

        Bundle bundle = new Bundle();
        ProductFragment productFragment = new ProductFragment();
        bundle.putString("item", item_id);
        bundle.putString("category_id", category_id);
        bundle.putString("type", type);
        productFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.product_framelayout, productFragment).commit();
        itme_name.setText(name);


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }


    @Override
    public void onClick(View view) {
        int id = view.getId();

        switch (id) {
            case R.id.search_product:
                break;
            case R.id.back:
                onBackPressed();
                break;
            case R.id.shorts:
              if(type.equals("prepaid")){
                  Log.e("prepaid", getIntent().getStringExtra("itemName"));
                  productloadfradment("30", getIntent().getStringExtra("itemName"), "short");
              }
              else {
                  productloadfradment(getIntent().getStringExtra("item_id"), getIntent().getStringExtra("item_name"), "short");
              }

                break;
            case R.id.best_sellers:
            case R.id.pricefilter:
                if(type.equals("prepaid")){
//                    Log.e("prepaid", getIntent().getStringExtra("itemName"));
                    productloadfradment("30", getIntent().getStringExtra("itemName"), "Price");
                }
                else {
                    productloadfradment(getIntent().getStringExtra("item_id"), getIntent().getStringExtra("item_name"), "Price");
                }

                break;
            case R.id.filter:
                Intent intent = new Intent(ProductActivity.this, FilterActivity.class);
                intent.putExtra("item_id", getIntent().getStringExtra("item_id"));
                intent.putExtra("item_name", getIntent().getStringExtra("item_name"));
                startActivity(intent);
                overridePendingTransition(R.anim.bottom_to_top, R.anim.slide_out_up);
                break;
        }
    }
}
















