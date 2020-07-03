package tech.iwish.pickmall.gateway;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

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
import tech.iwish.pickmall.activity.OneRsShareActivity;
import tech.iwish.pickmall.activity.PaymentOptionActivity;
import tech.iwish.pickmall.activity.SuccessfullyActivity;
import tech.iwish.pickmall.activity.TransactionFailed;
import tech.iwish.pickmall.config.Constants;
import tech.iwish.pickmall.connection.JsonHelper;
import tech.iwish.pickmall.session.Share_session;
import tech.iwish.pickmall.sqlconnection.MyhelperSql;

import static tech.iwish.pickmall.session.Share_session.NAME_ADDRESS;
import static tech.iwish.pickmall.session.Share_session.NUMBER_ADDRESS;
import static tech.iwish.pickmall.session.Share_session.USERMOBILE;

public class Paymentgateway extends AppCompatActivity implements PaymentResultListener {

    String type, grandTotal;
    Share_session shareSession;
    Map data;
    private List<JSONObject> jsonObjects = new ArrayList<>();
    private MyhelperSql myhelperSql;
    private SQLiteDatabase sqLiteDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        shareSession = new Share_session(this);
        data = shareSession.Fetchdata();

        wallet();

    }

    public void wallet() {

        Intent intent = getIntent();
        type = intent.getStringExtra("type");
        grandTotal = intent.getStringExtra("finalamountsInt");


        Checkout.preload(Paymentgateway.this);

        Checkout checkout = new Checkout();
        checkout.setKeyID("rzp_test_cqTSMSd3guM3Ej");

        int amount = Integer.parseInt(grandTotal);

        JSONObject object = new JSONObject();
        try {
            object.put("name", data.get(NAME_ADDRESS).toString());
            object.put("description", "Pickmall");
            object.put("amount", Double.valueOf(amount) * 100);
            object.put("current", "INR");
            object.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");

            JSONObject preFill = new JSONObject();
//            preFill.put("email" , "mailtovikas67@gmail.com");
            preFill.put("contact", data.get(NUMBER_ADDRESS).toString());
            object.put("prfill", preFill);

            checkout.open(Paymentgateway.this, object);


        } catch (JSONException e) {
//            Toast.makeText(context, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

    }

    @Override
    public void onPaymentSuccess(String s) {

        switch (type) {
            case "CardActivity":
                card_order_place("","");
                break;
            case "buy_now":
                buy_now_order_place("","");
                break;
            case "friendDeal_one_rs":
                friendDeal_one_rs_order_place("","CARD");
                break;
        }

    }

    @Override
    public void onPaymentError(int i, String s) {
        Toast.makeText(Paymentgateway.this, "fail" + s, Toast.LENGTH_SHORT).show();
        startActivity(new Intent(Paymentgateway.this, TransactionFailed.class).putExtra("status","FALSE"));
    }


    private void friendDeal_one_rs_order_place(String wallet, String paymentmethod) {

        OkHttpClient okHttpClient = new OkHttpClient();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("client_number", data.get(USERMOBILE).toString());
            jsonObject.put("client_address", shareSession.GetAddrssId());
            jsonObject.put("product_id", getIntent().getStringExtra("product_id"));
            jsonObject.put("product_type", getIntent().getStringExtra("product_type"));
            jsonObject.put("product_color", "");
            jsonObject.put("product_size", getIntent().getStringExtra("select_size"));
            jsonObject.put("product_qty", getIntent().getStringExtra("product_qty"));
            jsonObject.put("product_amount", getIntent().getStringExtra("finalamountsInt"));
            jsonObject.put("shippingCharge", "");
            jsonObject.put("gst", "");
            jsonObject.put("item_type", getIntent().getStringExtra("item_type"));
            jsonObject.put("payment_option", paymentmethod);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(JSON, jsonObject.toString());
        Request request1 = new Request.Builder().url(Constants.FRIEND_DEAL_ORDER).post(body).build();
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

                            String refer_code = jsonHelper.GetResult("data");


                            Intent intent = new Intent(Paymentgateway.this, OneRsShareActivity.class);
                            intent.putExtra("item_type",getIntent().getStringExtra("item_type"));
                            intent.putExtra("product_id",getIntent().getStringExtra("product_id"));
                            intent.putExtra("product_name",getIntent().getStringExtra("product_name"));
                            intent.putExtra("product_type",getIntent().getStringExtra("product_type"));
                            intent.putExtra("product_image",getIntent().getStringExtra("imagename"));
                            intent.putExtra("new_user_request",getIntent().getStringExtra("new_user_request"));
                            intent.putExtra("refer_code",refer_code);
                            startActivity(intent);

                        }
                    }

                }
            }
        });


    }

    private void card_order_place(final String value, String paymentmethod) {

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
            jsonObject.put("client_address", shareSession.GetAddrssId());
            jsonObject.put("product", jsonObjects.toString());
            jsonObject.put("payment_option", "CARD");
            jsonObject.put("shippingCharge", "no charge");
            jsonObject.put("gst", "");
            jsonObject.put("offer_id", getIntent().getStringExtra("coupon_amt"));
            jsonObject.put("product_amount", getIntent().getStringExtra("finalamountsInt"));

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

                            Intent intent = new Intent(Paymentgateway.this,SuccessfullyActivity.class);
                            startActivity(intent);

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
            jsonObject.put("product_id", getIntent().getStringExtra("product_id"));
            jsonObject.put("product_type", getIntent().getStringExtra("product_type"));
            jsonObject.put("product_color", "");
            jsonObject.put("product_size", getIntent().getStringExtra("select_size"));
            jsonObject.put("product_qty", getIntent().getStringExtra("product_qty"));
            jsonObject.put("product_amount", getIntent().getStringExtra("finalamountsInt"));
            jsonObject.put("shippingCharge", "");
            jsonObject.put("gst", "");
            jsonObject.put("item_type", "");
            jsonObject.put("offer_id", getIntent().getStringExtra("coupon_amt"));
            jsonObject.put("payment_option", "CARD");

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

                            Intent intent = new Intent(Paymentgateway.this,SuccessfullyActivity.class);
                            startActivity(intent);

                        }
                    }

                }
            }
        });


    }


}
