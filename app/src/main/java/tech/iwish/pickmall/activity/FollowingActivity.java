package tech.iwish.pickmall.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

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
import tech.iwish.pickmall.adapter.FollowingAdapter;
import tech.iwish.pickmall.adapter.WishListAdapter;
import tech.iwish.pickmall.config.Constants;
import tech.iwish.pickmall.connection.JsonHelper;
import tech.iwish.pickmall.other.FllowingList;
import tech.iwish.pickmall.other.WishlistList;
import tech.iwish.pickmall.session.Share_session;

public class FollowingActivity extends AppCompatActivity {
    ImageView back,no_item;
    RecyclerView recyclerView;
    private List<FllowingList> fllowingLists = new ArrayList<>();
    Share_session share_session;
    LinearLayout shimmer_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_following);
        InitializeActivity();
        ActivityAction();
        SetRecycleView();
    }

    private void InitializeActivity(){
        back= findViewById(R.id.back);
        recyclerView= findViewById(R.id.recycle);
        shimmer_view= findViewById(R.id.shimmerView);
        no_item= findViewById(R.id.noitem);
        share_session= new Share_session(FollowingActivity.this);

    }

    private void ActivityAction(){
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }

    private void SetRecycleView(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(FollowingActivity.this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        OkHttpClient client = new OkHttpClient();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("mobile",share_session.getUserDetail().get("UserMobile") );

        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(JSON, jsonObject.toString());
        Request request = new Request.Builder().post(body)
                .url(Constants.FOLLOWINGVENDOR)
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
                                fllowingLists.add(new FllowingList(jsonHelper.GetResult("shopname"),jsonHelper.GetResult("vendor_id"),""));
                            }
                            FollowingActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if(fllowingLists.size()==0){
                                        shimmer_view.setVisibility(View.GONE);
                                        recyclerView.setVisibility(View.GONE);
                                        no_item.setVisibility(View.VISIBLE);
                                    }
                                    else {
                                        FollowingAdapter  followingAdapter = new FollowingAdapter(FollowingActivity.this, fllowingLists);
                                        shimmer_view.setVisibility(View.GONE);
                                        recyclerView.setVisibility(View.VISIBLE);
                                        no_item.setVisibility(View.GONE);
                                        recyclerView.setAdapter(followingAdapter);
                                    }

                                }
                            });

                        }
                    }

                }
            }
        });


    }
}
