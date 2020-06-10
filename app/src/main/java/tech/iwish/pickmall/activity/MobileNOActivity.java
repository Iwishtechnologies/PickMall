package tech.iwish.pickmall.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

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
import tech.iwish.pickmall.session.Share_session;

public class MobileNOActivity extends AppCompatActivity  implements InternetConnectivityListener {
    EditText mobile;
    Button next;
    ProgressBar progressBar;
    LinearLayout mainview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile_n_o);
        mobile = findViewById(R.id.mobile);
        next = findViewById(R.id.next);
        progressBar = findViewById(R.id.progress);
        mainview = findViewById(R.id.mainview);
        Connectivity();


        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                next.setEnabled(false);
                if (mobile.getText().toString().isEmpty()) {
                    Toast.makeText(MobileNOActivity.this, "Field not empty", Toast.LENGTH_SHORT).show();
                } else if (mobile.length() < 10) {
                    Toast.makeText(MobileNOActivity.this, "", Toast.LENGTH_SHORT).show();
                } else {
                    mobileinsert();
                }
                mainview.setAlpha((float) 0.5);
                progressBar.setVisibility(View.VISIBLE);
                next.setEnabled(true);


            }
        });


    }

    private void mobileinsert() {

        OkHttpClient okHttpClient = new OkHttpClient();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("number", mobile.getText().toString().trim());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(JSON, jsonObject.toString());
        Request request1 = new Request.Builder().url(Constants.MOBILE_NUMBER).post(body).build();
        okHttpClient.newCall(request1).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
                mainview.setAlpha(1);
                progressBar.setVisibility(View.GONE);
                Toast.makeText(MobileNOActivity.this, "Connection Timeout", Toast.LENGTH_SHORT).show();
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
                            MobileNOActivity.this.runOnUiThread(new TimerTask() {
                                @Override
                                public void run() {
                                    Intent intent = new Intent(MobileNOActivity.this, OTPActivity.class);
                                    intent.putExtra("number",mobile.getText().toString());
                                    mainview.setAlpha(1);
                                    progressBar.setVisibility(View.GONE);
                                    startActivity(intent);
                                }
                            });

                        }
                    }
                }
            }
        });

    }

    public void Connectivity(){
        InternetAvailabilityChecker mInternetAvailabilityChecker;
        mInternetAvailabilityChecker = InternetAvailabilityChecker.init(this);
        mInternetAvailabilityChecker.addInternetConnectivityListener(MobileNOActivity.this);
    }

    @Override
    public void onInternetConnectivityChanged(boolean isConnected) {
        if (isConnected){
        }
        else {
            startActivity(new Intent(MobileNOActivity.this,NoInternetConnectionActivity.class));
        }
    }

}