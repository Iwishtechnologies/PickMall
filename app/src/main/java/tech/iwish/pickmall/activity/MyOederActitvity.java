package tech.iwish.pickmall.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageView;

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
import tech.iwish.pickmall.adapter.CouponAdapter;
import tech.iwish.pickmall.connection.JsonHelper;
import tech.iwish.pickmall.other.CouponList;
import tech.iwish.pickmall.session.Share_session;

public class MyOederActitvity extends AppCompatActivity {
    ImageView back;
    RecyclerView recyclerView;
    private List<CouponList> couponLists = new ArrayList<>();
    Share_session share_session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_oeder_actitvity);
//        InitializeActitvity();
//        ActivityAction();
//        SetRecycleView();
    }

//
//    private void InitializeActitvity(){
//        recyclerView= findViewById(R.id.recycle);
//
//    }
//    private  void ActivityAction(){
//
//    }
//    private void SetRecycleView(){
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(CoupenActivity.this);
//        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
//        recyclerView.setLayoutManager(linearLayoutManager);
//
//        OkHttpClient client = new OkHttpClient();
//        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
//        JSONObject jsonObject = new JSONObject();
//        try {
//            jsonObject.put("mobile",share_session.getUserDetail().get("UserMobile") );
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        RequestBody body = RequestBody.create(JSON, jsonObject.toString());
//        Request request = new Request.Builder().post(body)
//                .url("http://173.212.226.143:8086/api/Coupons")
//                .build();
//        client.newCall(request).enqueue(new okhttp3.Callback() {
//            @Override
//            public void onFailure(@NotNull Call call, @NotNull IOException e) {
//                e.printStackTrace();
//            }
//
//            @Override
//            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
//                if (response.isSuccessful()) {
//                    String result = response.body().string();
//
//                    JsonHelper jsonHelper = new JsonHelper(result);
//                    if (jsonHelper.isValidJson()) {
//                        String responses = jsonHelper.GetResult("response");
//                        if (responses.equals("TRUE")) {
//                            JSONArray jsonArray = jsonHelper.setChildjsonArray(jsonHelper.getCurrentJsonObj(), "data");
//                            for (int i = 0; i < jsonArray.length(); i++) {
//                                jsonHelper.setChildjsonObj(jsonArray, i);
//                                couponLists.add(new CouponList(jsonHelper.GetResult("name_title"), jsonHelper.GetResult("subtitle"), jsonHelper.GetResult("termscon"), jsonHelper.GetResult("icon"), jsonHelper.GetResult("validity"), "Prize ₹ "+jsonHelper.GetResult("prize")+" off", jsonHelper.GetResult("code"), jsonHelper.GetResult("vendorid")));
//                            }
//                            CoupenActivity.this.runOnUiThread(new Runnable() {
//                                @Override
//                                public void run() {
//                                    CouponAdapter couponAdapter = new CouponAdapter(CoupenActivity.this, couponLists);
//                                    recyclerView.setAdapter(couponAdapter);
//                                }
//                            });
//
//                        }
//                    }
//
//                }
//            }
//        });
//
//
//    }

}
