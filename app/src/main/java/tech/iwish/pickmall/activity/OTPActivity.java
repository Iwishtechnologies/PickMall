package tech.iwish.pickmall.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.razorpay.PaymentResultListener;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.TimerTask;

import ir.hamiss.internetcheckconnection.InternetAvailabilityChecker;
import ir.hamiss.internetcheckconnection.InternetConnectivityListener;
import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import tech.iwish.pickmall.R;
import tech.iwish.pickmall.config.Constants;
import tech.iwish.pickmall.connection.JsonHelper;

public class OTPActivity extends AppCompatActivity implements  InternetConnectivityListener {
    EditText otp;
    Button next;
    private String number;
    ProgressBar progressBar;
    LinearLayout mainview;
    String   refercode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_o_t_p);

        progressBar = findViewById(R.id.progress);
        mainview = findViewById(R.id.mainview);
        Connectivity();
        otp = (EditText) findViewById(R.id.otp);
        number = getIntent().getStringExtra("number");
        next = findViewById(R.id.next);
        refercodecheck();
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (otp.getText().toString().trim().isEmpty()) {
                    Toast.makeText(OTPActivity.this, "Fill not Empty", Toast.LENGTH_SHORT).show();
                } else {
                    mainview.setAlpha((float) 0.5);
                    progressBar.setVisibility(View.VISIBLE);
                    otpMethod();
                }


            }
        });

    }

    private void otpMethod() {

        OkHttpClient okHttpClient = new OkHttpClient();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("number", number);
            jsonObject.put("otp", otp.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(JSON, jsonObject.toString());
        Request request1 = new Request.Builder().url(Constants.OTP_CHECK).post(body).build();
        okHttpClient.newCall(request1).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                OTPActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        e.printStackTrace();
                        mainview.setAlpha(1);
                        progressBar.setVisibility(View.INVISIBLE);
                        Toast.makeText(OTPActivity.this, "Connection Timeout", Toast.LENGTH_SHORT).show();
                    }
                });
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
                            OTPActivity.this.runOnUiThread(new TimerTask() {
                                @Override
                                public void run() {
                                    Intent intent = new Intent(OTPActivity.this, SelectGenderActivity.class);
                                    mainview.setAlpha(1);
                                    progressBar.setVisibility(View.INVISIBLE);
                                    intent.putExtra("number", number);
                                    startActivity(intent);
                                }
                            });

                        }else {
                            if(OTPActivity.this != null){
                                OTPActivity.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        mainview.setAlpha(1);
                                        progressBar.setVisibility(View.INVISIBLE);
                                        Toast.makeText(OTPActivity.this, "otp not match ", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }
                    }
                }
            }
        });

    }

    public void Connectivity(){
        InternetAvailabilityChecker mInternetAvailabilityChecker;
        mInternetAvailabilityChecker = InternetAvailabilityChecker.init(this);
        mInternetAvailabilityChecker.addInternetConnectivityListener(OTPActivity.this);
    }

    @Override
    public void onInternetConnectivityChanged(boolean isConnected) {
        if (isConnected){
        }
        else {
            startActivity(new Intent(OTPActivity.this,NoInternetConnectionActivity.class));
        }
    }


    private void refercodecheck() {

        FirebaseDynamicLinks.getInstance()
                .getDynamicLink(getIntent())
                .addOnSuccessListener(OTPActivity.this, pendingDynamicLinkData -> {
                    // Get deep link from result (may be null if no link is found)
                    Uri deepLink = null;
                    if (pendingDynamicLinkData != null) {
                        deepLink = pendingDynamicLinkData.getLink();
                        String[] arrOfStr = deepLink.toString().split("=", 2);
                        Log.e("onClick: ", arrOfStr[1]);
                        Toast.makeText(this, ""+arrOfStr[1], Toast.LENGTH_SHORT).show();
                        refercode = arrOfStr[1];

                        refer_friend_insert();
                    }
                })
                .addOnFailureListener(OTPActivity.this, e -> Log.e("onFailure", e.getMessage()));

    }

    private void refer_friend_insert() {

        OkHttpClient okHttpClient = new OkHttpClient();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("mobile", number);
            jsonObject.put("refer_code", refercode);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(JSON, jsonObject.toString());
        Request request1 = new Request.Builder().url(Constants.REFERCODE_FRIEND_INVITE).post(body).build();
        okHttpClient.newCall(request1).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
                OTPActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                        progressBar.setVisibility(View.GONE);

                        Toast.makeText(OTPActivity.this, "Connection Time Out", Toast.LENGTH_SHORT).show();
                    }
                });
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
                            OTPActivity.this.runOnUiThread(new TimerTask() {
                                @Override
                                public void run() {
//                                    progressBar.setVisibility(View.GONE);
                                }
                            });
                        } else {
                            OTPActivity.this.runOnUiThread(new TimerTask() {
                                @Override
                                public void run() {
//                                    progressBar.setVisibility(View.GONE);
                                    Toast.makeText(OTPActivity.this, "Number all ready register", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                }
            }
        });

    }



}






















