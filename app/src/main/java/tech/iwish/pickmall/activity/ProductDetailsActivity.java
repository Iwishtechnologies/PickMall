package tech.iwish.pickmall.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.media.Rating;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.StrikethroughSpan;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Console;
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
import tech.iwish.pickmall.adapter.ItemAdapter;
import tech.iwish.pickmall.adapter.ProductDetailsImageAdapter;
import tech.iwish.pickmall.connection.JsonHelper;
import tech.iwish.pickmall.fragment.ProductSideColorBottomFragment;
import tech.iwish.pickmall.other.ItemList;
import tech.iwish.pickmall.other.ProductDetailsList;

public class ProductDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView ac_priceEdit, dicount_price_Edit, title_name_edit, select_size_color;
    private List<ProductDetailsList> productDetailsListList = new ArrayList<>();
    private ViewPager productImageDetailsViewpager;
    private String product_color ,product_name  ,product_Image;
    private RecyclerView color_size_image_recycle_view;
    private RatingBar ratingcheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        ac_priceEdit = (TextView) findViewById(R.id.priceEdit);
        dicount_price_Edit = (TextView) findViewById(R.id.dicount_price);
        title_name_edit = (TextView) findViewById(R.id.title_name_edit);
        select_size_color = (TextView) findViewById(R.id.select_size_color);
        productImageDetailsViewpager = (ViewPager) findViewById(R.id.productImageDetailsViewpager);
        color_size_image_recycle_view = (RecyclerView)findViewById(R.id.color_size_image_recycle_view);
        ratingcheck = (RatingBar)findViewById(R.id.ratingcheck);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        color_size_image_recycle_view.setLayoutManager(linearLayoutManager);

        Intent intent = getIntent();
        product_name = intent.getStringExtra("product_name");
        String actual_price = intent.getStringExtra("actual_price");
        String discount_price = intent.getStringExtra("discount_price");
        String product_id = intent.getStringExtra("product_id");
        product_Image =  intent.getStringExtra("product_Image");
        product_color = intent.getStringExtra("product_color");

        select_size_color.setOnClickListener(this);


        OkHttpClient okHttpClient = new OkHttpClient();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("product_id", product_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(JSON, jsonObject.toString());
        Request request1 = new Request.Builder().url("http://173.212.226.143:8086/api/ProductImageAll").post(body).build();
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
                                productDetailsListList.add(new ProductDetailsList(jsonHelper.GetResult("sno"), jsonHelper.GetResult("product_id"), jsonHelper.GetResult("imgname"), jsonHelper.GetResult("size"), jsonHelper.GetResult("color"), jsonHelper.GetResult("qty")));
                            }


                            ProductDetailsActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    ProductDetailsImageAdapter productDetailsImageAdapter = new ProductDetailsImageAdapter(ProductDetailsActivity.this, productDetailsListList);
                                    productImageDetailsViewpager.setAdapter(productDetailsImageAdapter);
                                    ColorSizeImageAdapter colorSizeImageAdapter = new ColorSizeImageAdapter(ProductDetailsActivity.this , productDetailsListList);
                                    color_size_image_recycle_view.setAdapter(colorSizeImageAdapter);
                                }
                            });

                        }
                    }

                }
            }
        });


        ac_priceEdit.setText(getResources().getString(R.string.rs_symbol) + actual_price);
        title_name_edit.setText(product_name);
        dicount_price_Edit.setText(getResources().getString(R.string.rs_symbol) + discount_price);
        ratingcheck.setRating((float) 3.3);


    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.select_size_color:
                Bundle bundle = new Bundle();
                ProductSideColorBottomFragment productSideColorBottomFragment = new ProductSideColorBottomFragment(productDetailsListList);
                bundle.putString("product_name " , product_name );
                productSideColorBottomFragment.setArguments(bundle);
                productSideColorBottomFragment.show(getSupportFragmentManager(), productSideColorBottomFragment.getTag());
                break;

        }
    }
}
