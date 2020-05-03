package tech.iwish.pickmall.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import cn.gavinliu.android.lib.shapedimageview.ShapedImageView;
import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import tech.iwish.pickmall.R;
import tech.iwish.pickmall.connection.JsonHelper;
import tech.iwish.pickmall.extended.TextViewFont;
import tech.iwish.pickmall.session.Share_session;

public class OrderDetailActivity extends AppCompatActivity {
    TextViewFont name,orderid,color,seller,price,cname,street,city,state,phone,approvedate;
    ShapedImageView image;
    ImageView progress;
    Intent intent;
    Share_session share_session;

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
    }
    private  void InitializeActivity(){
        share_session= new Share_session(OrderDetailActivity.this);
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

    }

    private void ActivityAction(){

    }

    private void SetActivityData(){
        name.setText(getIntent().getExtras().getString("ProductName"));
        orderid.setText(getIntent().getExtras().getString("orderid"));
        price.setText("₹ "+getIntent().getExtras().getString("orderamt"));
        cname.setText(share_session.getUserDetail().get("username"));
        street.setText(getIntent().getExtras().getString("address"));
        phone.setText(share_session.getUserDetail().get("UserMobile"));
        approvedate.setText(getIntent().getExtras().getString("orderdate"));
       if(getIntent().getExtras().getString("orderStatus").equals("PENDING"))
        {
            progress.setImageResource(R.drawable.half_fill_progressbar);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        GetColor(getIntent().getExtras().getString("product_id"));

    }


    private void GetColor(String product_id){
        OkHttpClient client = new OkHttpClient();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id",product_id);
//            jsonObject.put("parameter",parameter);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(JSON, jsonObject.toString());
        Request request = new Request.Builder().post(body)
                .url("http://173.212.226.143:8086/api/getcolor")
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




                            }


                        }
                    }

                }
            }
        });

    }
}
