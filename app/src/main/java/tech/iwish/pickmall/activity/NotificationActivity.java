package tech.iwish.pickmall.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.shimmer.Shimmer;

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
import tech.iwish.pickmall.adapter.NotificationAdapter;
import tech.iwish.pickmall.config.Constants;
import tech.iwish.pickmall.connection.JsonHelper;
import tech.iwish.pickmall.other.NotificationList;
import tech.iwish.pickmall.session.Share_session;

public class NotificationActivity extends AppCompatActivity {

    private RecyclerView notificationRecycleview;
    List<NotificationList> notificationLists = new ArrayList<>();
    LinearLayout shimmerView;
    TextView no;
    Share_session share_session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        share_session= new Share_session(NotificationActivity.this);
        notificationRecycleview= findViewById(R.id.notificationRecycleview);
        shimmerView= findViewById(R.id.shimmerView);
        no= findViewById(R.id.no);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        notificationRecycleview.setLayoutManager(linearLayoutManager);


        OkHttpClient okHttpClient = new OkHttpClient();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("count", share_session.GetCount());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(JSON, jsonObject.toString());
        Request request1 = new Request.Builder().url(Constants.NOTIFICATION).post(body).build();
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

                            JSONArray jsonArray = jsonHelper.setChildjsonArray(jsonHelper.getCurrentJsonObj(), "data");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                jsonHelper.setChildjsonObj(jsonArray, i);
                                notificationLists.add(new NotificationList(jsonHelper.GetResult("sno"),jsonHelper.GetResult("title"),jsonHelper.GetResult("body"),jsonHelper.GetResult("start_date"),jsonHelper.GetResult("end_date"),jsonHelper.GetResult("status"),jsonHelper.GetResult("image") ));
                            }
                            NotificationActivity.this.runOnUiThread(() -> {
                                if (notificationLists.size()==0){
                                    shimmerView.setVisibility(View.GONE);
                                    no.setVisibility(View.VISIBLE);
                                }else {
                                    shimmerView.setVisibility(View.GONE);
                                    notificationRecycleview.setVisibility(View.VISIBLE);
                                    share_session.SetUnread(String.valueOf(0));
                                    share_session.SetCount(String.valueOf(notificationLists.get(0).getSno()));
                                    NotificationAdapter notificationAdapter = new NotificationAdapter(NotificationActivity.this , notificationLists );
                                    notificationRecycleview.setAdapter(notificationAdapter);
                                }

                            });
                        }
                    }
                }
            }
        });


    }

    public void UpdateCount(String ClientID, String count){
        OkHttpClient okHttpClient = new OkHttpClient();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", ClientID);
            jsonObject.put("count", count);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(JSON, jsonObject.toString());
        Request request1 = new Request.Builder().url(Constants.NOTIFICATION).post(body).build();
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

                            JSONArray jsonArray = jsonHelper.setChildjsonArray(jsonHelper.getCurrentJsonObj(), "data");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                jsonHelper.setChildjsonObj(jsonArray, i);
                                notificationLists.add(new NotificationList(jsonHelper.GetResult("sno"),jsonHelper.GetResult("title"),jsonHelper.GetResult("body"),jsonHelper.GetResult("start_date"),jsonHelper.GetResult("end_date"),jsonHelper.GetResult("status"),jsonHelper.GetResult("image") ));
                            }
                        }
                    }
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
      startActivity(new Intent(NotificationActivity.this,MainActivity.class));
    }
}