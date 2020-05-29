package tech.iwish.pickmall.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.StrikethroughSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

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
import tech.iwish.pickmall.Interface.ProductCountInterface;
import tech.iwish.pickmall.R;
import tech.iwish.pickmall.adapter.ColorSizeImageAdapter;
import tech.iwish.pickmall.adapter.ProductDetailsImageAdapter;
import tech.iwish.pickmall.config.Constants;
import tech.iwish.pickmall.connection.JsonHelper;
import tech.iwish.pickmall.fragment.ProductOverViewFragment;
import tech.iwish.pickmall.fragment.ProductSideColorBottomFragment;
import tech.iwish.pickmall.other.CardCount;
import tech.iwish.pickmall.other.ProductColorList;
import tech.iwish.pickmall.other.ProductDetailsImageList;
import tech.iwish.pickmall.other.ProductSizeColorList;
import tech.iwish.pickmall.session.Share_session;

import static tech.iwish.pickmall.session.Share_session.USERMOBILE;

public class ProductDetailsActivity extends AppCompatActivity implements View.OnClickListener, ProductCountInterface {

    private TextView ac_priceEdit, dicount_price_Edit, title_name_edit, select_size_color, one_product_name,
            one_rs_amount, one_rs_dicount_price, dicount_price_text, product_count_value, new_user_text, total_user_req;
    private List<ProductDetailsImageList> productDetailsListImageList = new ArrayList<>();
    //    private List<ProductDetailsImageList> productDetailsListImageList = new ArrayList<>();
    private List<ProductSizeColorList> productSizeColorLists = new ArrayList<>();
    private ViewPager productImageDetailsViewpager;
    private String product_color, product_name, product_Image, sku,actual_price, discount_price, product_id, vendor_id;
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
        setContentView(R.layout.activity_product_details);

        getSupportActionBar().hide();

        ac_priceEdit = (TextView) findViewById(R.id.priceEdit);
        dicount_price_Edit = (TextView) findViewById(R.id.dicount_price);
        title_name_edit = (TextView) findViewById(R.id.title_name_edit);
        select_size_color = (TextView) findViewById(R.id.select_size_color);
        one_product_name = (TextView) findViewById(R.id.one_product_name);
        one_rs_amount = (TextView) findViewById(R.id.one_rs_amount);
        one_rs_dicount_price = (TextView) findViewById(R.id.one_rs_dicount_price);
        dicount_price_text = (TextView) findViewById(R.id.dicount_price_text);
        product_count_value = (TextView) findViewById(R.id.product_count_value);
        new_user_text = (TextView) findViewById(R.id.new_user_text);
        total_user_req = (TextView) findViewById(R.id.total_user_req);

        productImageDetailsViewpager = (ViewPager) findViewById(R.id.productImageDetailsViewpager);
        color_size_image_recycle_view = (RecyclerView) findViewById(R.id.color_size_image_recycle_view);
        ratingcheck = (RatingBar) findViewById(R.id.ratingcheck);

        add_card_btn = (Button) findViewById(R.id.add_card_btn);
        buy_now_btn = (Button) findViewById(R.id.buy_now_btn);
        one_rs_button_place_order = (Button) findViewById(R.id.one_rs_button_place_order);

        product_layout = (LinearLayout) findViewById(R.id.product_layout);
        one_rs_main_layout = (LinearLayout) findViewById(R.id.one_rs_main_layout);
        button_layout = (LinearLayout) findViewById(R.id.button_layout);
        one_rs_bottom_layout = (LinearLayout) findViewById(R.id.one_rs_bottom_layout);
        one_rs_rule = (LinearLayout) findViewById(R.id.one_rs_rule);
        store = (LinearLayout) findViewById(R.id.store);
        wishlist = (LinearLayout) findViewById(R.id.wishlist);
        product_count_layout = (LinearLayout) findViewById(R.id.product_count_layout);

        scrollview = (ScrollView) findViewById(R.id.scrollview);

        card = (RelativeLayout) findViewById(R.id.card);

        save_hearth = (ImageView) findViewById(R.id.save_hearth);

        scrollview.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {

//                int maxDistance   = 250 ;
//                int movement  = scrollview.getScrollY();
//                if (movement >= 0 && movement <= maxDistance) {
//                    Toast.makeText(ProductDetailsActivity.this, "scroll bar work", Toast.LENGTH_SHORT).show();
//                }
            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        color_size_image_recycle_view.setLayoutManager(linearLayoutManager);


        shareSession = new Share_session(this);
        data = shareSession.Fetchdata();

        Intent intent = getIntent();
        product_name = intent.getStringExtra("product_name");
        actual_price = intent.getStringExtra("actual_price");
        discount_price = intent.getStringExtra("discount_price");
        product_id = intent.getStringExtra("product_id");
        product_Image = intent.getStringExtra("product_Image");
        product_color = intent.getStringExtra("product_color");
        String product_type = intent.getStringExtra("product_type");
        vendor_id = intent.getStringExtra("vendor_id");
        gst = intent.getStringExtra("gst");
        sku = intent.getStringExtra("sku");

        switch (product_type) {

//            case "flashSale":
//                All_Image(Constants.FLASH_SALE_IMAGE);
//                coloAndImageData(Constants.FLASH_SALE_COLOR_SIDE);
//                sizedatafetch(Constants.FLASH_SALE_SIZE);
//                PRODUCT_TYPE = "flashsale";
//                product_layout.setVisibility(View.VISIBLE);
//                one_rs_main_layout.setVisibility(View.GONE);
//                one_rs_bottom_layout.setVisibility(View.GONE);
//                one_rs_rule.setVisibility(View.GONE);
//                button_layout.setVisibility(View.VISIBLE);
//                break;
            case "flashSale":
            case "allProduct":
                All_Image(Constants.PRODDUCT_IMAGE);
                coloAndImageData(Constants.PRODDUCT_SIZE_COLOR);
                sizedatafetch(Constants.PRODDUCT_SIZE);

                PRODUCT_TYPE = "product";
                product_layout.setVisibility(View.VISIBLE);
                one_rs_main_layout.setVisibility(View.GONE);
                one_rs_bottom_layout.setVisibility(View.GONE);
                one_rs_rule.setVisibility(View.GONE);
                button_layout.setVisibility(View.VISIBLE);
                break;
            case "friendsaleoneRs":

                one_product_name.setText(product_name);
                one_rs_amount.setText(getResources().getString(R.string.rs_symbol) + actual_price);
                product_layout.setVisibility(View.GONE);
                button_layout.setVisibility(View.GONE);
                one_rs_main_layout.setVisibility(View.VISIBLE);
                one_rs_bottom_layout.setVisibility(View.VISIBLE);
                one_rs_rule.setVisibility(View.VISIBLE);

                SpannableString content = new SpannableString(getResources().getString(R.string.rs_symbol) + discount_price);
                content.setSpan(new StrikethroughSpan(), 0, content.length(), 0);
                one_rs_dicount_price.setText(content);


                one_rs_button_place_order.setText(getResources().getString(R.string.rs_symbol) + actual_price + " Start a Deal");

                total_request_user = getIntent().getStringExtra("total_request_user");
                new_user_request = getIntent().getStringExtra("new_user_request");
                total_user_req.setText("Request " + total_request_user + " Users Total");
                new_user_text.setText("(" + new_user_request + " new user at least)");

                PRODUCT_TYPE = "frienddeal";

                All_Image(Constants.FRIEND_SALE_IMAGE);
                coloAndImageData(Constants.FRIEND_SALE_SIZE_COLOR);
                sizedatafetch(Constants.FRIEND_SALE_SIZE);

                break;
        }

        select_size_color.setOnClickListener(this);
        add_card_btn.setOnClickListener(this);
        buy_now_btn.setOnClickListener(this);
        one_rs_button_place_order.setOnClickListener(this);
        store.setOnClickListener(this);
        wishlist.setOnClickListener(this);
        card.setOnClickListener(this);


        ac_priceEdit.setText(getResources().getString(R.string.rs_symbol) + actual_price);
        title_name_edit.setText(product_name);

        float mrp = Float.parseFloat(discount_price);
        float actual_prices = Float.parseFloat(actual_price);
        float sub = mrp - actual_prices ;
        float div = sub/actual_prices;
        aaa = String.valueOf((int) (div *100));

        dicount_price_text.setText(" "+aaa+"% OFF" );

        SpannableString content = new SpannableString(getResources().getString(R.string.rs_symbol) + discount_price);
        content.setSpan(new StrikethroughSpan(), 0, content.length(), 0);
        dicount_price_Edit.setText(content);
        ratingcheck.setRating((float) 3.3);

        if (!vendor_id.equals("1")) {
            store.setVisibility(View.VISIBLE);
        } else {
            store.setVisibility(View.GONE);
        }

        Bundle bundle = new Bundle();
        ProductOverViewFragment productOverViewFragment = new ProductOverViewFragment();
        bundle.putString("product_id", product_id);
        bundle.putString("vendor_id", vendor_id);
        productOverViewFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.product_overview_frame, productOverViewFragment).commit();
        cardcount();

        wishlistchechk(product_id, data.get(USERMOBILE).toString(), PRODUCT_TYPE, null);

    }


    private void All_Image(String IMAGE_API) {

        OkHttpClient okHttpClient = new OkHttpClient();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("product_id", product_id);
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

                            if (ProductDetailsActivity.this != null) {

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
            }
        });


    }

    private void coloAndImageData(String SIZE_COLOR_API) {


        OkHttpClient okHttpClient1 = new OkHttpClient();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("product_id", product_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(JSON, jsonObject.toString());
        Request request1 = new Request.Builder().url(SIZE_COLOR_API).post(body).build();
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
                                productColorLists.add(new ProductColorList(jsonHelper.GetResult("sno"), jsonHelper.GetResult("product_id"), jsonHelper.GetResult("imgname"), jsonHelper.GetResult("size"), jsonHelper.GetResult("color"), jsonHelper.GetResult("qty")));
                            }

                            if (ProductDetailsActivity.this != null) {

                                ProductDetailsActivity.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {

//                                        ColorSizeImageAdapter colorSizeImageAdapter = new ColorSizeImageAdapter(ProductDetailsActivity.this, productSizeColorLists, productDetailsListImageList);
//                                        color_size_image_recycle_view.setAdapter(colorSizeImageAdapter);

                                        ColorSizeImageAdapter colorSizeImageAdapter = new ColorSizeImageAdapter(ProductDetailsActivity.this, productColorLists, productDetailsListImageList);
                                        color_size_image_recycle_view.setAdapter(colorSizeImageAdapter);


                                    }
                                });
                            }


                        }
                    }

                }
            }
        });

    }


    @Override
    public void onClick(View view) {

        Share_session share_session = new Share_session(this);
        Map data = share_session.Fetchdata();
        ProductSideColorBottomFragment productSideColorBottomFragment;
        Bundle bundle;

        int id = view.getId();
        switch (id) {
            case R.id.select_size_color:
            case R.id.add_card_btn:

                bundle = new Bundle();
//                productSideColorBottomFragment = new ProductSideColorBottomFragment(productSizeColorLists, this);
                productSideColorBottomFragment = new ProductSideColorBottomFragment(productColorLists, productSizeColorLists);
                bundle.putString("product_name", product_name);
                bundle.putString("actual_price", actual_price);
                bundle.putString("product_id", product_id);
                bundle.putString("discount_price", discount_price);
                bundle.putString("product_type", PRODUCT_TYPE);
                bundle.putString("gst", gst);
                bundle.putString("vendor_id", vendor_id);
                bundle.putString("product_dicount_percent", aaa );
                bundle.putString("sku", sku);
                bundle.putString("type", "add_to_card");
                productSideColorBottomFragment.setArguments(bundle);
                productSideColorBottomFragment.show(getSupportFragmentManager(), productSideColorBottomFragment.getTag());
                break;
            case R.id.one_rs_button_place_order:
//                one rs place order check
                Intent intent1 = new Intent(ProductDetailsActivity.this, SaveAddressActivity.class);
                intent1.putExtra("type", "friendDeal_one_rs");
                startActivity(intent1);

                break;
            case R.id.card:
                startActivity(new Intent(ProductDetailsActivity.this, CardActivity.class));
                break;
            case R.id.store:
                Intent intent = new Intent(ProductDetailsActivity.this, VendorStoreActivity.class);
                intent.putExtra("vendor_id", vendor_id);
                startActivity(intent);
                break;
            case R.id.wishlist:
                wishlist.setClickable(false);
                wishlistchechk(product_id, data.get(USERMOBILE).toString(), PRODUCT_TYPE, "dsdas");
                wishlist.setClickable(true);
                break;
            case R.id.buy_now_btn:
                bundle = new Bundle();
//                productSideColorBottomFragment = new ProductSideColorBottomFragment(productSizeColorLists, this);
                productSideColorBottomFragment = new ProductSideColorBottomFragment(productColorLists, productSizeColorLists);
                bundle.putString("product_name", product_name);
                bundle.putString("actual_price", actual_price);
                bundle.putString("product_id", product_id);
                bundle.putString("discount_price", discount_price);
                bundle.putString("product_type", PRODUCT_TYPE);
                bundle.putString("gst", gst);
                bundle.putString("sku", sku);
                bundle.putString("type", "buy_now");
                productSideColorBottomFragment.setArguments(bundle);
                productSideColorBottomFragment.show(getSupportFragmentManager(), productSideColorBottomFragment.getTag());
                break;
        }
    }


    private void cardcount() {
        if (!CardCount.card_count(this).equals("0")) {
            String number_of_product = CardCount.card_count(this);
            product_count_value.setText(number_of_product);
            product_count_layout.setVisibility(View.VISIBLE);
        } else {
            product_count_layout.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        cardcount();
    }

    @Override
    public void counntproduct() {
        cardcount();
    }

    private void image(String imageurl) {
        View view = LayoutInflater.from(this).inflate(R.layout.image_load, null);
        ImageView imageView = view.findViewById(R.id.img_load);
    }


    protected void sizedatafetch(String SIZE_API) {

        OkHttpClient client = new OkHttpClient();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("product_id", product_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(JSON, jsonObject.toString());
        Request request = new Request.Builder().post(body)
                .url(SIZE_API)
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
                    Log.e("result", result);
                    JsonHelper jsonHelper = new JsonHelper(result);
                    if (jsonHelper.isValidJson()) {
                        String responses = jsonHelper.GetResult("response");
                        if (responses.equals("TRUE")) {
                            JSONArray jsonArray = jsonHelper.setChildjsonArray(jsonHelper.getCurrentJsonObj(), "data");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                jsonHelper.setChildjsonObj(jsonArray, i);
                                productSizeColorLists.add(new ProductSizeColorList(jsonHelper.GetResult("sno"), jsonHelper.GetResult("product_id"), jsonHelper.GetResult("imgname"), jsonHelper.GetResult("size"), jsonHelper.GetResult("color"), jsonHelper.GetResult("qty"), jsonHelper.GetResult("count_size")));

                            }
                        }
                    }
                }

            }
        });
    }


    public boolean wishlistchechk(final String product_id, String number, String producttype, final String type) {

        OkHttpClient okHttpClient1 = new OkHttpClient();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("product_id", product_id);
            jsonObject.put("product_type", producttype);
            jsonObject.put("user_number", number);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(JSON, jsonObject.toString());
        Request request1 = new Request.Builder().url(Constants.PRODUCT_WISHLIST_CHEHCL).post(body).build();
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
                        final String responses = jsonHelper.GetResult("response");

                        if (ProductDetailsActivity.this != null) {
                            ProductDetailsActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    if (type != null) {

                                        if (responses.equals("TRUE")) {
                                            new CardCount().delect_wishlist(PRODUCT_TYPE, product_id, data.get(USERMOBILE).toString());
                                            save_hearth.setImageDrawable(getDrawable(R.drawable.heart_icon));
                                        } else {

                                            new CardCount().save_wishlist(PRODUCT_TYPE, product_id, data.get(USERMOBILE).toString());
                                            save_hearth.setImageDrawable(getDrawable(R.drawable.red_heart));
                                        }


                                    } else {
                                        if (responses.equals("TRUE")) {
                                            save_hearth.setImageDrawable(getDrawable(R.drawable.red_heart));
                                        } else {
                                            save_hearth.setImageDrawable(getDrawable(R.drawable.heart_icon));
                                        }
                                    }

                                }
                            });
                        }


                    }

                }
            }
        });
        return wishlistchechi;
    }


}


















