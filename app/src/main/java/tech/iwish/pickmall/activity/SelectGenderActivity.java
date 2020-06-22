package tech.iwish.pickmall.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.razorpay.PaymentResultListener;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
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
import tech.iwish.pickmall.session.Share_session;

public class SelectGenderActivity extends AppCompatActivity implements InternetConnectivityListener {
    ImageView male, female;
    Button finish;
    String number;
    String gender, refercode;
    Share_session share_session;
    ProgressBar progressBar;
    LinearLayout mainview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_gender);
        share_session = new Share_session(SelectGenderActivity.this);
        InternetAvailabilityChecker mInternetAvailabilityChecker;
        mInternetAvailabilityChecker = InternetAvailabilityChecker.init(this);
        mInternetAvailabilityChecker.addInternetConnectivityListener(SelectGenderActivity.this);
        male = findViewById(R.id.male);
        female = findViewById(R.id.female);
        finish = findViewById(R.id.finish);
        progressBar = findViewById(R.id.progress);
        mainview = findViewById(R.id.mainview);

        number = getIntent().getStringExtra("number");

        refercodecheck();

        male.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                male.setBackground(getResources().getDrawable(R.drawable.border));
                female.setBackground(null);
                gender = "Male";
//
            }
        });
        female.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                male.setBackground(null);
                female.setBackground(getResources().getDrawable(R.drawable.border));
                gender = "FEMALE";
//                female.setAlpha((float) 0.5);
//                male.setAlpha(1);
            }
        });

        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainview.setAlpha((float) 0.5);
                progressBar.setVisibility(View.VISIBLE);
                genderselect(gender);
            }
        });

    }

    private void refercodecheck() {

        FirebaseDynamicLinks.getInstance()
                .getDynamicLink(getIntent())
                .addOnSuccessListener(SelectGenderActivity.this, pendingDynamicLinkData -> {
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
                .addOnFailureListener(SelectGenderActivity.this, e -> Log.e("onFailure", e.getMessage()));

    }

    private void refer_friend_insert() {

        OkHttpClient okHttpClient = new OkHttpClient();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("mobile", getIntent().getStringExtra("number"));
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
                SelectGenderActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.setVisibility(View.GONE);

                        Toast.makeText(SelectGenderActivity.this, "Connection Time Out", Toast.LENGTH_SHORT).show();
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
                            SelectGenderActivity.this.runOnUiThread(new TimerTask() {
                                @Override
                                public void run() {
                                    progressBar.setVisibility(View.GONE);
                                }
                            });
                        } else {
                            SelectGenderActivity.this.runOnUiThread(new TimerTask() {
                                @Override
                                public void run() {
                                    progressBar.setVisibility(View.GONE);
                                    Toast.makeText(SelectGenderActivity.this, "Number all ready register", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                }
            }
        });

    }

    private void genderselect(String gender) {

        OkHttpClient okHttpClient = new OkHttpClient();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("mobile", getIntent().getStringExtra("number"));
            jsonObject.put("gender", gender);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(JSON, jsonObject.toString());
        Request request1 = new Request.Builder().url(Constants.GENDER_UPDATE).post(body).build();
        okHttpClient.newCall(request1).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
                SelectGenderActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.setVisibility(View.GONE);
                        mainview.setAlpha(1);
                        Toast.makeText(SelectGenderActivity.this, "Connection Time Out", Toast.LENGTH_SHORT).show();
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
                            SelectGenderActivity.this.runOnUiThread(new TimerTask() {
                                @Override
                                public void run() {
                                    share_session.CreateSession(getIntent().getStringExtra("number"));
                                    share_session.user_number_check();
                                    GetAddress(share_session.getUserDetail().get("UserMobile"));
                                    Intent intent = new Intent(SelectGenderActivity.this, MainActivity.class);
                                    startActivity(intent);
                                }
                            });

                        }
                    }
                }
            }
        });
    }

    public void GetAddress(String mobile) {
        OkHttpClient okHttpClient1 = new OkHttpClient();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("mobile", mobile);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(JSON, jsonObject.toString());
        Request request1 = new Request.Builder().url(Constants.GETADDRESS).post(body).build();
        okHttpClient1.newCall(request1).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
                SelectGenderActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.setVisibility(View.GONE);
                        mainview.setAlpha(1);
                        Toast.makeText(SelectGenderActivity.this, "Connection Time Out", Toast.LENGTH_SHORT).show();
                    }
                });
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

                            for (int i = 0; i < 1; i++) {
                                jsonHelper.setChildjsonObj(jsonArray, i);
                                share_session.address(jsonHelper.GetResult("name"), jsonHelper.GetResult("mobile"), jsonHelper.GetResult("pincode"), jsonHelper.GetResult("house_no"), jsonHelper.GetResult("colony"), jsonHelper.GetResult("landmark"), jsonHelper.GetResult("state"), jsonHelper.GetResult("city"),jsonHelper.GetResult("sno"));

                            }
                            GetUserProfile(share_session.getUserDetail().get("UserMobile"));


                        } else {
                            GetUserProfile(share_session.getUserDetail().get("UserMobile"));
                        }
                    }

                }


            }

        });
    }

    public void GetUserProfile(String mobile) {


        OkHttpClient okHttpClient1 = new OkHttpClient();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("mobile", mobile);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(JSON, jsonObject.toString());
        Request request1 = new Request.Builder().url(Constants.PROFILE).post(body).build();
        okHttpClient1.newCall(request1).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
                SelectGenderActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.setVisibility(View.GONE);
                        mainview.setAlpha(1);
                        Toast.makeText(SelectGenderActivity.this, "Connection Time Out", Toast.LENGTH_SHORT).show();
                    }
                });
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
                                share_session.setUserDetail(jsonHelper.GetResult("name"), jsonHelper.GetResult("gender"), jsonHelper.GetResult("image"), jsonHelper.GetResult("client_id"));

                            }

                            SelectGenderActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    progressBar.setVisibility(View.GONE);
                                    mainview.setAlpha(1);
                                    Intent intent = new Intent(SelectGenderActivity.this, MainActivity.class);
                                    startActivity(intent);
//                                    Animatoo.animateFade(UserDetail.this);
                                }
                            });

                        }
                    }

                }


            }

        });
    }

    @Override
    public void onInternetConnectivityChanged(boolean isConnected) {
        if (isConnected) {
        } else {
            startActivity(new Intent(SelectGenderActivity.this, NoInternetConnectionActivity.class));
        }
    }
}

























