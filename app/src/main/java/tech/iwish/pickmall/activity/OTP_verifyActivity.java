package tech.iwish.pickmall.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.razorpay.OTP;

import org.jetbrains.annotations.NotNull;
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
import tech.iwish.pickmall.config.Constants;
import tech.iwish.pickmall.connection.JsonHelper;
import tech.iwish.pickmall.session.Share_session;
import tech.iwish.pickmall.sqlconnection.MyhelperSql;

import static tech.iwish.pickmall.session.Share_session.ADDRESSID;
import static tech.iwish.pickmall.session.Share_session.NUMBER_ADDRESS;
import static tech.iwish.pickmall.session.Share_session.USERMOBILE;

public class OTP_verifyActivity extends AppCompatActivity {

    private EditText otp;
    private Button next;
    private MyhelperSql myhelperSql;
    private List<JSONObject> jsonObjects = new ArrayList<>();
    private SQLiteDatabase sqLiteDatabase;
    private Share_session shareSession;
    private Map data;
    ProgressBar progress;
    String shippingchargebuy_now, finalamountsInt, product_id, product_type, select_size, product_qty, item_type, type , coupon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_o_t_p_verify);

        otp = findViewById(R.id.otp);
        next = findViewById(R.id.next);
        progress = findViewById(R.id.progress);

        shareSession = new Share_session(this);
        data = shareSession.Fetchdata();

        shippingchargebuy_now = getIntent().getStringExtra("shippingchargebuy_now");
        finalamountsInt = getIntent().getStringExtra("finalamountsInt");
        product_id = getIntent().getStringExtra("product_id");
        product_type = getIntent().getStringExtra("product_type");
        select_size = getIntent().getStringExtra("select_size");
        product_qty = getIntent().getStringExtra("product_qty");
        item_type = getIntent().getStringExtra("item_type");
        type = getIntent().getStringExtra("type");
        coupon = getIntent().getStringExtra("coupen");

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (otp.getText().toString().trim().isEmpty()) {
                    Toast.makeText(OTP_verifyActivity.this, "", Toast.LENGTH_SHORT).show();
                } else {
                    progress.setVisibility(View.VISIBLE);
                    otpCheck();
                }
            }
        });
    }

    private void otpCheck() {


        OkHttpClient okHttpClient = new OkHttpClient();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("otp", otp.getText().toString().trim());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(JSON, jsonObject.toString());
        Request request1 = new Request.Builder().url(Constants.COD_OTP_VERIFY).post(body).build();
        okHttpClient.newCall(request1).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
                OTP_verifyActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progress.setVisibility(View.GONE);
                    }
                });
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

                            OTP_verifyActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    progress.setVisibility(View.GONE);
                                }
                            });

                            switch (type) {
                                case "CardActivity":
                                    card_order_place("COD", "COD");
                                    break;
                                case "buy_now":
                                    buy_now_order_place("COD", "COD");
                                    break;
                            }
                        } else {
                            OTP_verifyActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(OTP_verifyActivity.this, "Otp not match", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                }
            }
        });


    }


    private void buy_now_order_place(final String value, String paymentmethod) {

        OkHttpClient okHttpClient = new OkHttpClient();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("client_number", data.get(USERMOBILE).toString());
            jsonObject.put("client_address", shareSession.GetAddrssId());
            jsonObject.put("product_id", product_id);
            jsonObject.put("product_type", product_type);
            jsonObject.put("product_color", "");
            jsonObject.put("product_size", select_size);
            jsonObject.put("product_qty", product_qty);
            jsonObject.put("product_amount", finalamountsInt);
            jsonObject.put("shippingCharge", shippingchargebuy_now);
            jsonObject.put("gst", "");
            jsonObject.put("item_type", item_type);
            jsonObject.put("offer_id", coupon);
            jsonObject.put("payment_option", paymentmethod);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(JSON, jsonObject.toString());
        Request request1 = new Request.Builder().url(Constants.ORDER_PLACE_BUY_NOW).post(body).build();
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

                            if (OTP_verifyActivity.this != null) {

                                OTP_verifyActivity.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {

                                        startActivity(new Intent(OTP_verifyActivity.this, SuccessfullyActivity.class).putExtra("status","TRUE"));

                                    }
                                });

                            }

                        }
                    }

                }
            }
        });


    }

    private void card_order_place(final String value, String paymentmethod) {

//        progressbar.setVisibility(View.VISIBLE);

        myhelperSql = new MyhelperSql(this);
        String query = "Select * from PRODUCT_CARD";
        sqLiteDatabase = myhelperSql.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(query, new String[]{});
        cursor.moveToFirst();
        if (cursor != null && cursor.moveToFirst()) {
            for (int i = 0; i < cursor.getCount(); i++) {
                JSONObject jsonObject1 = new JSONObject();
                try {
                    jsonObject1.put("product_id", cursor.getString(cursor.getColumnIndex("PRODUCT_ID")));
                    jsonObject1.put("product_qty", cursor.getString(cursor.getColumnIndex("PRODUCT_QTY")));
                    jsonObject1.put("PRODUCT_SIZE", cursor.getString(cursor.getColumnIndex("PRODUCT_SIZE")));
                    jsonObject1.put("PRODUCT_COLOR", cursor.getString(cursor.getColumnIndex("PRODUCT_COLOR")));
                    jsonObject1.put("PRODUCT_AMOUNT", cursor.getString(cursor.getColumnIndex("PRODUCT_AMOUNT")));
                    jsonObject1.put("PRODUCT_TYPE", cursor.getString(cursor.getColumnIndex("PRODUCT_TYPE")));
                    jsonObjects.add(jsonObject1);

                    cursor.moveToNext();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        OkHttpClient okHttpClient = new OkHttpClient();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("client_number", data.get(USERMOBILE).toString());
            jsonObject.put("client_address", data.get(ADDRESSID).toString());
            jsonObject.put("product", jsonObjects.toString());
            jsonObject.put("payment_option", paymentmethod);
            jsonObject.put("shippingCharge", shippingchargebuy_now);
            jsonObject.put("gst", "");
            jsonObject.put("product_amount", finalamountsInt);
            jsonObject.put("offer_id", coupon);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody body = RequestBody.create(JSON, jsonObject.toString());
        Request request1 = new Request.Builder().url(Constants.ORDER_PLACE_CARD).post(body).build();
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

                            if (OTP_verifyActivity.this != null) {

                                OTP_verifyActivity.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {

                                        startActivity(new Intent(OTP_verifyActivity.this, SuccessfullyActivity.class).putExtra("status","TRUE"));

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






















