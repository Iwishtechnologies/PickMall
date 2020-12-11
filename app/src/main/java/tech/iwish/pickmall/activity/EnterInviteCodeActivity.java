package tech.iwish.pickmall.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

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

public class EnterInviteCodeActivity extends AppCompatActivity implements InternetConnectivityListener {
    ProgressBar progressBar;
    ScrollView scrollView;
    EditText code;
    Button submit;
    ImageView back;
    Share_session share_session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_invite_code);
        InitializeActivity();
        SetActivityData();
        ActivityAction();
    }

    protected void InitializeActivity() {
        back = findViewById(R.id.back);
        progressBar = findViewById(R.id.progress);
        code = findViewById(R.id.code);
        submit = findViewById(R.id.submit);
        scrollView = findViewById(R.id.scroll);
        share_session = new Share_session(EnterInviteCodeActivity.this);
        Connectivity();

    }


    protected void ActivityAction() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (code.getText().toString().length() >= 8) {
                    scrollView.setAlpha((float) 0.2);
                    progressBar.setVisibility(View.VISIBLE);
                    if (ValidateInput(code.getText().toString())) {
                        ProcessingCode(code.getText().toString(), share_session.getUserDetail().get("UserMobile"));
                    }

                }
            }
        });
        code.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @SuppressLint("ResourceAsColor")
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (code.getText().toString().length() >= 8) {
                    submit.setBackgroundColor(getResources().getColor(R.color.redColor));
                    submit.setClickable(true);
                } else {
                    submit.setBackgroundColor(getResources().getColor(R.color.lightgray));
                }

            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });


    }

    protected void SetActivityData() {
    }

    protected Boolean ValidateInput(String code) {
        if (code.equals("")) {
            return false;
        } else {
            if (code.length() >= 8) {
                return true;
            } else {
                return false;
            }
        }
    }


    private void ProcessingCode(String code, String mobile1) {
        OkHttpClient client = new OkHttpClient();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("code", code);
            jsonObject.put("mobile", mobile1);


        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(JSON, jsonObject.toString());
        Request request = new Request.Builder().post(body)
                .url(Constants.PROCESSREFERREL)
                .build();
        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
                EnterInviteCodeActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.setVisibility(View.GONE);
                        scrollView.setAlpha(1);
                        Toast.makeText(EnterInviteCodeActivity.this, "Connection Time Uut", Toast.LENGTH_SHORT).show();
                    }
                });
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
                            EnterInviteCodeActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    progressBar.setVisibility(View.GONE);
                                    scrollView.setAlpha(1);
                                    SaveTransaction();
                                    Toast.makeText(EnterInviteCodeActivity.this, "" + jsonHelper.GetResult("data"), Toast.LENGTH_SHORT).show();
                                }
                            });

                        } else {
                            EnterInviteCodeActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    progressBar.setVisibility(View.GONE);
                                    scrollView.setAlpha(1);
                                    Toast.makeText(EnterInviteCodeActivity.this, "" + jsonHelper.GetResult("data"), Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }

                }
            }
        });

    }

    private void SaveTransaction() {
        OkHttpClient okHttpClient1 = new OkHttpClient();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("mobile", share_session.getUserDetail().get("UserMobile"));
            jsonObject.put("description", "Earned Referral Amount ");
            jsonObject.put("type", "Credit");
            jsonObject.put("status", "Completed");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(JSON, jsonObject.toString());
        Request request1 = new Request.Builder().url(Constants.REFERREL_TRANSACTION).post(body).build();
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

    public void Connectivity() {
        InternetAvailabilityChecker mInternetAvailabilityChecker;
        mInternetAvailabilityChecker = InternetAvailabilityChecker.init(this);
        mInternetAvailabilityChecker.addInternetConnectivityListener(EnterInviteCodeActivity.this);
    }

    @Override
    public void onInternetConnectivityChanged(boolean isConnected) {
        if (isConnected) {
        } else {
            startActivity(new Intent(EnterInviteCodeActivity.this, NoInternetConnectionActivity.class));
        }
    }


}
