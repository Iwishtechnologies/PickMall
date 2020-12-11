package tech.iwish.pickmall.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Map;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import tech.iwish.pickmall.R;
import tech.iwish.pickmall.config.Constants;
import tech.iwish.pickmall.connection.JsonHelper;
import tech.iwish.pickmall.session.Share_session;

import static tech.iwish.pickmall.session.Share_session.NUMBER_ADDRESS;

public class Cod_mobile_verify_activity extends AppCompatActivity {

    private TextView mobile;
    Share_session shareSession;
    Map data;
    Button next;
    String shippingchargebuy_now ,finalamountsInt ,product_id ,product_type ,select_size,product_qty,item_type,type,coupen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cod_mobile_verify_activity);

        mobile = findViewById(R.id.mobile);
        shareSession = new Share_session(this);
        data = shareSession.Fetchdata();
        next = findViewById(R.id.next);

//        mobile.setText(data.get(NUMBER_ADDRESS).toString());
        if(data.get(NUMBER_ADDRESS).toString().contains("@")){
            mobile.setMaxLines(1);
        }else {
            mobile.setText(data.get(NUMBER_ADDRESS).toString());
        }

        shippingchargebuy_now = getIntent().getStringExtra("shippingchargebuy_now");
        finalamountsInt = getIntent().getStringExtra("finalamountsInt");
        product_id = getIntent().getStringExtra("product_id");
        product_type = getIntent().getStringExtra("product_type");
        select_size = getIntent().getStringExtra("select_size");
        product_qty = getIntent().getStringExtra("product_qty");
        item_type = getIntent().getStringExtra("item_type");
        type = getIntent().getStringExtra("type");
        coupen = getIntent().getStringExtra("coupen");


        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mobil();
            }
        });

    }

    private void mobil() {


        OkHttpClient okHttpClient = new OkHttpClient();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("number", data.get(NUMBER_ADDRESS).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(JSON, jsonObject.toString());
        Request request1 = new Request.Builder().url(Constants.COD_MOBILE_VERIFY).post(body).build();
        okHttpClient.newCall(request1).enqueue(new okhttp3.Callback() {
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
                            Intent intent = new Intent(Cod_mobile_verify_activity.this, OTP_verifyActivity.class);
                            intent.putExtra("shippingchargebuy_now",shippingchargebuy_now);
                            intent.putExtra("finalamountsInt",finalamountsInt);
                            intent.putExtra("product_id",product_id);
                            intent.putExtra("product_type",product_type);
                            intent.putExtra("select_size",select_size);
                            intent.putExtra("product_qty",product_qty);
                            intent.putExtra("item_type",item_type);
                            intent.putExtra("type",type);
                            intent.putExtra("coupen",coupen);
                            startActivity(intent);
                        }
                    }
                }
            }
        });


    }
}




















