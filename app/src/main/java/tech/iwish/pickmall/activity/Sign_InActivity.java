package tech.iwish.pickmall.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

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
import tech.iwish.pickmall.extended.TextViewFont;
import tech.iwish.pickmall.session.Share_session;

public class Sign_InActivity extends AppCompatActivity  implements InternetConnectivityListener {

    EditText number, password;
    ImageButton signInButton;
    LinearLayout linearLayout;
    Share_session share_session;
    TextViewFont skip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing_in);
         share_session= new Share_session(Sign_InActivity.this);
        number = findViewById(R.id.number);
        password = findViewById(R.id.password);
        signInButton = findViewById(R.id.signInButton);
        linearLayout = findViewById(R.id.linearLayout);
        skip = findViewById(R.id.skip);
        InternetAvailabilityChecker mInternetAvailabilityChecker;
        mInternetAvailabilityChecker = InternetAvailabilityChecker.init(this);
        mInternetAvailabilityChecker.addInternetConnectivityListener(Sign_InActivity.this);

        skip.setOnClickListener(view -> {
           startActivity(new Intent(Sign_InActivity.this,MainActivity.class));
        });
        signInButton.setOnClickListener(view -> {

            linearLayout.setAlpha((float) 0.5);

            OkHttpClient okHttpClient = new OkHttpClient();
            MediaType JSON = MediaType.parse("application/json; charset=utf-8");
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("number", number.getText().toString().trim());
                jsonObject.put("password", password.getText().toString().trim());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            RequestBody body = RequestBody.create(JSON, jsonObject.toString());
            Request request1 = new Request.Builder().url(Constants.SIGN_IN).post(body).build();
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
//                                    Intent intent = new Intent(Sign_InActivity.this, SelectGenderActivity.class);
//                                    intent.putExtra("number", );
//                                    startActivity(intent);
                                share_session.CreateSession(number.getText().toString().trim());
                                share_session.user_number_check();
                                GetAddress(number.getText().toString().trim());
                            } else {
                                Sign_InActivity.this.runOnUiThread(() -> {
                                    linearLayout.setAlpha(1);
                                    Toast.makeText(Sign_InActivity.this, "Login Fail", Toast.LENGTH_SHORT).show();
                                });
                            }
                        }
                    }
                }
            });

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
                Sign_InActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                        progressBar.setVisibility(View.GONE);
//                        mainview.setAlpha(1);
                        Toast.makeText(Sign_InActivity.this, "Connection Time Out", Toast.LENGTH_SHORT).show();
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
                Sign_InActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                        progressBar.setVisibility(View.GONE);
//                        mainview.setAlpha(1);
                        Toast.makeText(Sign_InActivity.this, "Connection Time Out", Toast.LENGTH_SHORT).show();
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

                            Sign_InActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
//                                    progressBar.setVisibility(View.GONE);
//                                    mainview.setAlpha(1);
                                    Intent intent = new Intent(Sign_InActivity.this, MainActivity.class);
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
            startActivity(new Intent(Sign_InActivity.this, NoInternetConnectionActivity.class));
        }
    }
}