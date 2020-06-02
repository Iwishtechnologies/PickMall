package tech.iwish.pickmall.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
import tech.iwish.pickmall.adapter.WalletAdapter;
import tech.iwish.pickmall.config.Constants;
import tech.iwish.pickmall.connection.JsonHelper;
import tech.iwish.pickmall.extended.TextViewFont;
import tech.iwish.pickmall.other.WalletList;
import tech.iwish.pickmall.session.Share_session;

public class WalletActivity extends AppCompatActivity implements PaymentResultListener {
    TextViewFont balance;
     String Balance = "0";
     Share_session share_session;
     Button add_amount;
     EditText amount;
     ShimmerFrameLayout shimmer;
    LinearLayout layout,shimmerview,noitem;
     ImageView back;
     ProgressBar progressBar;
     ScrollView scrollView;
     RecyclerView recyclerView;
    List<WalletList> walletLists= new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);
        InitializeActivity();
        ActivityAction();
        SetActivityData();
        SetRecycleView();

    }


    private void InitializeActivity(){
        share_session= new Share_session(WalletActivity.this);
        Wallet_Balance(share_session.getUserDetail().get("UserMobile"));
        balance= findViewById(R.id.balance);
        add_amount = findViewById(R.id.add_amount);
        amount= findViewById(R.id.amount);
        shimmer= findViewById(R.id.shimmer);
        layout= findViewById(R.id.layout);
        back= findViewById(R.id.back);

        shimmerview= findViewById(R.id.shimmerview);
        noitem= findViewById(R.id.noitem);
        progressBar= findViewById(R.id.progress);
        scrollView= findViewById(R.id.scroll);
        recyclerView= findViewById(R.id.recycle);
    }

    private void ActivityAction(){
             add_amount.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (ValidateInput(amount.getText().toString())){
                            progressBar.setVisibility(View.VISIBLE);
                            scrollView.setAlpha((float) 0.2);
                          AddAmount(Integer.parseInt(amount.getText().toString()));
                        }

                    }
                });
             back.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View view) {
                     onBackPressed();
                 }
             });

            amount.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                      if(!amount.getText().toString().isEmpty()){
                          add_amount.setBackground(getResources().getDrawable(R.drawable.add_amount_enable));
                      }
                      else {
                          add_amount.setBackground(getResources().getDrawable(R.drawable.add_amount_desable));
                      }
                }

                @Override
                public void afterTextChanged(Editable editable) {

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
        Request request1 = new Request.Builder().url(Constants.WALLET_BALANCE).post(body).build();
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
                    final JsonHelper jsonHelper = new JsonHelper(result);
                    if (jsonHelper.isValidJson()) {
                        String responses = jsonHelper.GetResult("response");
                        if (responses.equals("TRUE")) {
                         JSONArray jsonArray = jsonHelper.setChildjsonArray(jsonHelper.getCurrentJsonObj(), "data");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                jsonHelper.setChildjsonObj(jsonArray, i);
                                   Balance = jsonHelper.GetResult("wallet");
                            }

                            WalletActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    layout.setAlpha(1);
                                    shimmer.stopShimmer();
                                    shimmer.setShimmer(null);
                                    balance.setText(jsonHelper.GetResult("wallet"));
                                    share_session.walletAmount(jsonHelper.GetResult("wallet"));
                                }
                            });

                        }
                    }

                }
            }
        });
        return Balance;
    }

    public Boolean ValidateInput(String amount1){
     if(amount1.isEmpty()){
         amount.setError("EnterAmount");
         return false;
     }else {
         return true;
     }

    }


    public void AddAmount(int amount){
            Checkout.preload(WalletActivity.this);
            Checkout checkout = new Checkout();
            checkout.setKeyID("rzp_test_cqTSMSd3guM3Ej");
            JSONObject object= new JSONObject();
            try {
                object.put("name" ,share_session.getUserDetail().get("username"));
                object.put("amount" ,Double.valueOf(amount)*100);
                object.put("current" ,"INR");
                object.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
                JSONObject preFill = new JSONObject();
                preFill.put("contact" ,share_session.getUserDetail().get("UserMobile"));
                object.put("prfill" ,preFill);
                checkout.open(WalletActivity.this, object);


            } catch (JSONException e) {
//            Toast.makeText(context, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }



    }


    @Override
    public void onPaymentSuccess(String s) {
       UpdateWallet(share_session.getUserDetail().get("UserMobile"),amount.getText().toString());
        SaveTransaction();
       if(!(getIntent().getExtras().isEmpty())){
           onBackPressed();
       }
    }

    @Override
    public void onPaymentError(int i, String s) {
        Toast.makeText(WalletActivity.this, "fail"+s, Toast.LENGTH_SHORT).show();
        progressBar.setVisibility(View.GONE);
        scrollView.setAlpha(1);
//        UpdateWallet(share_session.getUserDetail().get("UserMobile"),amount.getText().toString());
    }

    public String UpdateWallet(String mobile , final String amount) {

        OkHttpClient okHttpClient1 = new OkHttpClient();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("mobile", mobile);
            jsonObject.put("amount", amount);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(JSON, jsonObject.toString());
        Request request1 = new Request.Builder().url("http://173.212.226.143:8086/api/updatewallet").post(body).build();
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
                    final JsonHelper jsonHelper = new JsonHelper(result);
                    if (jsonHelper.isValidJson()) {
                        String responses = jsonHelper.GetResult("response");
                        if (responses.equals("TRUE")) {
                            JSONArray jsonArray = jsonHelper.setChildjsonArray(jsonHelper.getCurrentJsonObj(), "data");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                jsonHelper.setChildjsonObj(jsonArray, i);
                                Balance =jsonHelper.GetResult("wallet");
                            }

                            WalletActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    progressBar.setVisibility(View.GONE);
                                    scrollView.setAlpha(1);
                                    balance.setText(jsonHelper.GetResult("wallet"));
                                    share_session.walletAmount(jsonHelper.GetResult("wallet"));
                                }
                            });

                        }
                    }

                }
            }
        });
        return Balance;
    }


    private void SetRecycleView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(WalletActivity.this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        OkHttpClient client = new OkHttpClient();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("mobile", share_session.getUserDetail().get("UserMobile"));

        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(JSON, jsonObject.toString());
        Request request = new Request.Builder().post(body)
                .url(Constants.Wallet_TRANSACTION)
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

                    JsonHelper jsonHelper = new JsonHelper(result);
                    if (jsonHelper.isValidJson()) {
                        String responses = jsonHelper.GetResult("response");
                        if (responses.equals("TRUE")) {
                            JSONArray jsonArray = jsonHelper.setChildjsonArray(jsonHelper.getCurrentJsonObj(), "data");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                jsonHelper.setChildjsonObj(jsonArray, i);
                                walletLists.add(new WalletList(jsonHelper.GetResult("id"), jsonHelper.GetResult("client_id"), "₹ " + jsonHelper.GetResult("amount"), jsonHelper.GetResult("date"), jsonHelper.GetResult("description"), jsonHelper.GetResult("type"), jsonHelper.GetResult("status")));
                            }
                            WalletActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (walletLists.size() == 0) {
                                        recyclerView.setVisibility(View.GONE);
                                        shimmerview.setVisibility(View.GONE);
                                        noitem.setVisibility(View.VISIBLE);
                                    } else {
                                        recyclerView.setVisibility(View.VISIBLE);
                                        shimmerview.setVisibility(View.GONE);
                                        WalletAdapter walletAdapter = new WalletAdapter(WalletActivity.this, walletLists);
                                        recyclerView.setAdapter(walletAdapter);
                                    }
                                }
                            });
                        }

                    }
                }
            }
        });

    }




        private void SaveTransaction(){
            OkHttpClient okHttpClient1 = new OkHttpClient();
            MediaType JSON = MediaType.parse("application/json; charset=utf-8");
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("mobile",share_session.getUserDetail().get("UserMobile") );
                jsonObject.put("amount", amount.getText().toString());
                jsonObject.put("description","Add to wallet");
                jsonObject.put("type", "Credit");
                jsonObject.put("status","Completed");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            RequestBody body = RequestBody.create(JSON, jsonObject.toString());
            Request request1 = new Request.Builder().url(Constants.SAVE_TRANSACTION).post(body).build();
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
                        final JsonHelper jsonHelper = new JsonHelper(result);
                        if (jsonHelper.isValidJson()) {
                            String responses = jsonHelper.GetResult("response");
                            if (responses.equals("TRUE")) {
                                JSONArray jsonArray = jsonHelper.setChildjsonArray(jsonHelper.getCurrentJsonObj(), "data");
//
//                            for (int i = 0; i < jsonArray.length(); i++) {
//                                jsonHelper.setChildjsonObj(jsonArray, i);
//                                Balance =jsonHelper.GetResult("wallet");
//                            }
//
//                            WalletActivity.this.runOnUiThread(new Runnable() {
//                                @Override
//                                public void run() {
//                                    progressBar.setVisibility(View.GONE);
//                                    scrollView.setAlpha(1);
//                                    balance.setText(jsonHelper.GetResult("wallet"));
//                                    share_session.walletAmount(jsonHelper.GetResult("wallet"));
//                                }
//                            });

                            }
                        }

                    }
                }
            });
        }




    }
