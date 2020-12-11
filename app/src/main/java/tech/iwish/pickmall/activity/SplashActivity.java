package tech.iwish.pickmall.activity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

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

import static tech.iwish.pickmall.session.Share_session.USER_NUMBER_CHECK;

public class SplashActivity extends AppCompatActivity {
    Share_session share_session;
    private Map data;
    String banner = "null";

    private final int SPLASH_DISPLAY_LENGTH = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        share_session = new Share_session(SplashActivity.this);
        GetOfferBanner();
        setContentView(R.layout.activity_splash);




        new Handler().postDelayed(() -> {
            data = share_session.Fetchdata();
            if (data.get(USER_NUMBER_CHECK) != null) {
                GetCount();
                if (banner.equals("null")) {
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                } else {
                    startActivity(new Intent(SplashActivity.this, OfferBannerActivity.class).putExtra("banner", banner));
                }
            } else {
                if (share_session.GetFirsttime()) {

                    GetCount();
                    if (banner.equals("null")) {
                        startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    } else {
                        startActivity(new Intent(SplashActivity.this, OfferBannerActivity.class).putExtra("banner", banner));

                    }

                } else {
                    share_session.SetCount(String.valueOf(0));
                    share_session.SetUnread(String.valueOf(0));
                    GetCount();
                    startActivity(new Intent(SplashActivity.this, Register1Activity.class));
                }
//                Intent mainIntent = new Intent(SplashActivity.this,Register1Activity.class);
//                startActivity(mainIntent);
            }

        }, SPLASH_DISPLAY_LENGTH);

    }

    public void GetCount() {
        OkHttpClient okHttpClient1 = new OkHttpClient();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("mobile", "mobile");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(JSON, jsonObject.toString());
        Request request1 = new Request.Builder().url(Constants.NotiFIcationCount).post(body).build();
        okHttpClient1.newCall(request1).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
                SplashActivity.this.runOnUiThread(() -> Toast.makeText(SplashActivity.this, "Connection Time Out", Toast.LENGTH_SHORT).show());
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

                                int a = Integer.parseInt(jsonHelper.GetResult("sno"));
                                int b = 0;
                                if (share_session.GetCount() == null) {
                                    b = 0;
                                } else {
                                    b = Integer.parseInt(share_session.GetCount());
                                }


                                int unread = a - b;
                                share_session.SetUnread(String.valueOf(unread));
                            }
                        }

                    }

                }


            }

        });
    }

    public void GetOfferBanner() {
        OkHttpClient okHttpClient1 = new OkHttpClient();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("mobile", "mobile");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(JSON, jsonObject.toString());
        Request request1 = new Request.Builder().url(Constants.OFFERBANNER).post(body).build();
        okHttpClient1.newCall(request1).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
                SplashActivity.this.runOnUiThread(() -> Toast.makeText(SplashActivity.this, "Connection Time Out", Toast.LENGTH_SHORT).show());
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
                                banner = jsonHelper.GetResult("image");
                            }
                        }

                    }

                }


            }

        });
    }

}
