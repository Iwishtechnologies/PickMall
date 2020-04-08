package tech.iwish.pickmall.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;

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

public class WalletActivity extends AppCompatActivity {
    ShapedImageView tap_to_view;
    TextViewFont wallet,balance;
     String Balance = "0";
     Share_session share_session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);
        InitializeActivity();
        ActivityAction();

    }


    private void InitializeActivity(){
        share_session= new Share_session(WalletActivity.this);
        Wallet_Balance(share_session.getUserDetail().get("UserMobile"));
        tap_to_view= findViewById(R.id.tap_to_refresh);
        wallet= findViewById(R.id.wallet);
        balance= findViewById(R.id.balance);

    }

    private void ActivityAction(){
        tap_to_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SetActivityData();
                tap_to_view.setVisibility(View.GONE);
                wallet.setVisibility(View.VISIBLE);
                balance.setVisibility(View.VISIBLE);

            }
        });

    }


    private void SetActivityData(){
        balance.setText(Balance);
    }


    public String Wallet_Balance(String mobile) {

        OkHttpClient okHttpClient1 = new OkHttpClient();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("mobile", mobile);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(JSON, jsonObject.toString());
        Request request1 = new Request.Builder().url("http://173.212.226.143:8086/api/WalletBalance").post(body).build();
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
                                   Balance =jsonHelper.GetResult("wallet");
                            }

                        }
                    }

                }
            }
        });
        return Balance;
    }
}
