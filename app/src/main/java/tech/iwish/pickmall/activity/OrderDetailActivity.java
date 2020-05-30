package tech.iwish.pickmall.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;

import com.bumptech.glide.Glide;
import com.facebook.shimmer.ShimmerFrameLayout;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import javax.sql.RowSetReader;

import cn.gavinliu.android.lib.shapedimageview.ShapedImageView;
import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import tech.iwish.pickmall.R;
import tech.iwish.pickmall.config.Constants;
import tech.iwish.pickmall.connection.JsonHelper;
import tech.iwish.pickmall.extended.TextViewFont;
import tech.iwish.pickmall.session.Share_session;

public class OrderDetailActivity extends AppCompatActivity {
    TextViewFont name,orderid,color,seller,price,cname,street,city,state,phone,approvedate,delivery_status,cencelled_statement,order_approved,colony,actual_price,selling_price,discount_amount,shipping_fee,total_amount;
    ShapedImageView image;
    ImageView progress;
    Intent intent;
    Share_session share_session;
    String dis_amt;
    Button help;
    RatingBar RatingBar;
    ShimmerFrameLayout shimmer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar mActionBar = getSupportActionBar();
        mActionBar.setDisplayShowHomeEnabled(true);
        mActionBar.setHomeButtonEnabled(true);
        mActionBar.setDisplayHomeAsUpEnabled(true);
        setTitle("Order Detail");
        setContentView(R.layout.activity_order_detail);
        InitializeActivity();
        SetActivityData();
        ActivityAction();
    }
    private  void InitializeActivity(){
        share_session= new Share_session(OrderDetailActivity.this);
        actual_price= findViewById(R.id.actual_price);
        selling_price= findViewById(R.id.selling_price);
        discount_amount= findViewById(R.id.discount_amount);
        shipping_fee= findViewById(R.id.shipping_fee);
        total_amount= findViewById(R.id.total_amount);
        name= findViewById(R.id.name);
        orderid= findViewById(R.id.orderid);
        color= findViewById(R.id.color);
        seller= findViewById(R.id.seller);
        price= findViewById(R.id.price);
        cname= findViewById(R.id.cname);
        street= findViewById(R.id.street);
        city= findViewById(R.id.city);
        state= findViewById(R.id.state);
        phone= findViewById(R.id.contact);
        progress= findViewById(R.id.progress);
        approvedate= findViewById(R.id.approvedate);
        delivery_status= findViewById(R.id.delivery_status);
        cencelled_statement= findViewById(R.id.cencelled_statement);
        order_approved= findViewById(R.id.order_approved);
        colony= findViewById(R.id.colony);
        image= findViewById(R.id.image);
        help= findViewById(R.id.help);
        RatingBar= findViewById(R.id.rating);
        shimmer= findViewById(R.id.shimmer);
        GetAddress(getIntent().getExtras().getString("address"));
        GetVendorDetail(getIntent().getExtras().getString("vendor_id"));

    }

    private void ActivityAction(){
        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(OrderDetailActivity.this,SupportActivity.class));
            }
        });
        RatingBar.setOnRatingBarChangeListener(new android.widget.RatingBar.OnRatingBarChangeListener() {
            public void onRatingChanged(RatingBar ratingBar, float rating,
                                        boolean fromUser) {
                uploadRating(getIntent().getExtras().getString("product_id"),share_session.getUserDetail().get("id"), String.valueOf(rating));

            }
        });

        seller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(OrderDetailActivity.this,VendorStoreActivity.class);
                intent.putExtra("vendor_id",getIntent().getExtras().getString("vendor_id"));
                startActivity(intent);

            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void SetActivityData(){
        name.setText(getIntent().getExtras().getString("ProductName"));
        orderid.setText("Order ID - "+getIntent().getExtras().getString("orderid"));
        price.setText("₹ "+getIntent().getExtras().getString("orderamt"));
        cname.setText(share_session.getUserDetail().get("username"));
        street.setText(getIntent().getExtras().getString("address"));
        phone.setText(share_session.getUserDetail().get("UserMobile"));
        approvedate.setText(getIntent().getExtras().getString("orderdate"));
        price.setText(getIntent().getExtras().getString("oederAmount"));
        color.setText(getIntent().getExtras().getString("color"));
        Glide.with(OrderDetailActivity.this).load(Constants.IMAGES+getIntent().getExtras().getString("image")).placeholder(R.drawable.male_icon).into(image);
        actual_price.setText(getIntent().getExtras().getString("actual_price"));
        shipping_fee.setText(getIntent().getExtras().getString("shipping_charge"));
        selling_price.setText(getIntent().getExtras().getString("selling_price"));
        dis_amt= String.valueOf(Integer.parseInt(getIntent().getExtras().getString("actual_price"))-Integer.parseInt(getIntent().getExtras().getString("selling_price")));
        discount_amount.setText(dis_amt);
        total_amount.setText(getIntent().getExtras().getString("oederAmount"));
        GetRating(getIntent().getExtras().getString("product_id"),share_session.getUserDetail().get("id"));


       if(getIntent().getExtras().getString("orderStatus").equals("PENDING"))
        {
            progress.setImageResource(R.drawable.half_fill_progressbar);
            delivery_status.setText("Pending");
            order_approved.setText("Pending");
            cencelled_statement.setVisibility(View.GONE);
        }else if(getIntent().getExtras().getString("orderStatus").equals("DELIVERED")){
           progress.setImageResource(R.drawable.full_fill_progressbar);
           delivery_status.setText("Delivered");
           order_approved.setText("Ordered And Approvep");
           cencelled_statement.setVisibility(View.GONE);
       }else if(getIntent().getExtras().getString("orderStatus").equals("CENCELLED")){
           progress.setImageResource(R.drawable.half_fill_progressbar);
           delivery_status.setText("Cancelled");
           order_approved.setText("Ordered And Approvep");
           cencelled_statement.setVisibility(View.VISIBLE);
       }else if(getIntent().getExtras().getString("orderStatus").equals("CONFIRM")){
           progress.setImageResource(R.drawable.half_fill_progressbar);
           delivery_status.setText("Pending");
           order_approved.setText("Ordered And Approvep");
           cencelled_statement.setVisibility(View.GONE);
       }

    }

    private void GetAddress(String id){
        OkHttpClient client = new OkHttpClient();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id",id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(JSON, jsonObject.toString());
        Request request = new Request.Builder().post(body)
                .url(Constants.ORDER_ADDRESS)
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

                    final JsonHelper jsonHelper = new JsonHelper(result);
                    if (jsonHelper.isValidJson()) {
                        String responses = jsonHelper.GetResult("response");
                        if (responses.equals("TRUE")) {
                            JSONArray jsonArray = jsonHelper.setChildjsonArray(jsonHelper.getCurrentJsonObj(), "data");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                jsonHelper.setChildjsonObj(jsonArray, i);
                                OrderDetailActivity.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {

                                        street.setText(jsonHelper.GetResult("house_no") +""+jsonHelper.GetResult("landmark") );
                                        colony.setText(jsonHelper.GetResult("colony"));
                                        city.setText(jsonHelper.GetResult("city"));
                                        state.setText(jsonHelper.GetResult("state") + " - "+ jsonHelper.GetResult("pincode") );
                                        phone.setText("Phone Number : "+jsonHelper.GetResult("delivery_number") );

                                    }
                                });

                            }
                        }
                    }

                }
            }
        });

    }



    private void uploadRating(String pid , String cid, final String rating){
        OkHttpClient client = new OkHttpClient();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("pid",pid);
            jsonObject.put("cid",cid);
            jsonObject.put("rating",rating);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(JSON, jsonObject.toString());
        Request request = new Request.Builder().post(body)
                .url(Constants.UPLOADRATING)
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

                    final JsonHelper jsonHelper = new JsonHelper(result);
                    if (jsonHelper.isValidJson()) {
                        String responses = jsonHelper.GetResult("response");
                        if (responses.equals("TRUE")) {
//                            JSONArray jsonArray = jsonHelper.setChildjsonArray(jsonHelper.getCurrentJsonObj(), "data");
//                            for (int i = 0; i < jsonArray.length(); i++) {
//                                jsonHelper.setChildjsonObj(jsonArray, i);
//                                OrderDetailActivity.this.runOnUiThread(new Runnable() {
//                                    @Override
//                                    public void run() {
////                                        RatingBar.setRating(Float.parseFloat(rating));
//                                    }
//                                });
//
//                            }
                        }
                    }

                }
            }
        });

    }

    private void GetRating(String pid , String cid){
        OkHttpClient client = new OkHttpClient();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("pid",pid);
            jsonObject.put("cid",cid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(JSON, jsonObject.toString());
        Request request = new Request.Builder().post(body)
                .url(Constants.GETRATING)                .build();
        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    String result = response.body().string();

                    final JsonHelper jsonHelper = new JsonHelper(result);
                    if (jsonHelper.isValidJson()) {
                        String responses = jsonHelper.GetResult("response");
                        if (responses.equals("TRUE")) {
                            JSONArray jsonArray = jsonHelper.setChildjsonArray(jsonHelper.getCurrentJsonObj(), "data");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                jsonHelper.setChildjsonObj(jsonArray, i);
                                Log.e("vggh",jsonHelper.GetResult("rating"));
                                OrderDetailActivity.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
//                                      Log.e("vggh",jsonHelper.GetResult("data"));
                                        shimmer.setShimmer(null);
                                        shimmer.stopShimmer();
                                        RatingBar.setRating(Float.parseFloat(jsonHelper.GetResult("rating")));
                                    }
                                });

                            }
                        }
                    }

                }
            }
        });

    }

    private void GetVendorDetail(String id){
        OkHttpClient client = new OkHttpClient();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        final JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id",id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(JSON, jsonObject.toString());
        Request request = new Request.Builder().post(body)
                .url(Constants.GETVENDORDETAIL)
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

                    final JsonHelper jsonHelper = new JsonHelper(result);
                    if (jsonHelper.isValidJson()) {
                        String responses = jsonHelper.GetResult("response");
                        if (responses.equals("TRUE")) {
                            JSONArray jsonArray = jsonHelper.setChildjsonArray(jsonHelper.getCurrentJsonObj(), "data");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                jsonHelper.setChildjsonObj(jsonArray, i);
                                OrderDetailActivity.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
//                                      Log.e("vggh",jsonHelper.GetResult("data"));
                                        seller.setText("Seller : "+jsonHelper.GetResult("shopname"));

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
