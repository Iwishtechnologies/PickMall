package tech.iwish.pickmall.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
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
import tech.iwish.pickmall.R;
import tech.iwish.pickmall.adapter.ProductDetailsImageAdapter;
import tech.iwish.pickmall.config.Constants;
import tech.iwish.pickmall.connection.JsonHelper;
import tech.iwish.pickmall.other.ProductColorList;
import tech.iwish.pickmall.other.ProductDetailsImageList;
import tech.iwish.pickmall.other.ProductSizeColorList;
import tech.iwish.pickmall.session.Share_session;

public class Product extends AppCompatActivity {
    private TextView ac_priceEdit, dicount_price_Edit, title_name_edit, select_size_color, one_product_name,
            one_rs_amount, one_rs_dicount_price, dicount_price_text, product_count_value, new_user_text, total_user_req;
    private List<ProductDetailsImageList> productDetailsListImageList = new ArrayList<>();
    //    private List<ProductDetailsImageList> productDetailsListImageList = new ArrayList<>();
    private List<ProductSizeColorList> productSizeColorLists = new ArrayList<>();
    private ViewPager productImageDetailsViewpager;
    private String product_color, product_name, product_Image, sku, actual_price, discount_price, product_id, vendor_id;
    private RecyclerView color_size_image_recycle_view;
    private RatingBar ratingcheck;
    private Button add_card_btn, buy_now_btn, one_rs_button_place_order;
    private LinearLayout product_layout, one_rs_main_layout, button_layout, one_rs_bottom_layout, one_rs_rule, store, wishlist, product_count_layout;
    private String PRODUCT_TYPE, total_request_user, new_user_request, gst;
    private ScrollView scrollview;
    private RelativeLayout card;
    private Map data;
    Share_session shareSession;
    private ImageView save_hearth;
    public boolean wishlistchechi;
    private List<ProductColorList> productColorLists = new ArrayList<>();
    String aaa;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product2);
        productImageDetailsViewpager = (ViewPager) findViewById(R.id.productImageDetailsViewpager);
        All_Image(Constants.PRODDUCT_IMAGE);
    }

    private void All_Image(String IMAGE_API) {

        OkHttpClient okHttpClient = new OkHttpClient();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("product_id", 12);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(JSON, jsonObject.toString());
        Request request1 = new Request.Builder().url(IMAGE_API).post(body).build();
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

                            if (Product.this != null) {

                                Product.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {

//                                        ProductDetailsImageAdapter productDetailsImageAdapter = new ProductDetailsImageAdapter(Product.this, productDetailsListImageList);
//                                        productImageDetailsViewpager.setAdapter(productDetailsImageAdapter);

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