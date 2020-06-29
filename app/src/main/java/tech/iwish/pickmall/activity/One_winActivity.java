package tech.iwish.pickmall.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import tech.iwish.pickmall.R;
import tech.iwish.pickmall.adapter.OneWinProductAdapter;
import tech.iwish.pickmall.adapter.OneWinShareAdapter;
import tech.iwish.pickmall.config.Constants;
import tech.iwish.pickmall.connection.JsonHelper;
import tech.iwish.pickmall.other.FriendSale;
import tech.iwish.pickmall.other.OneWinShareList;
import tech.iwish.pickmall.session.Share_session;

public class One_winActivity extends AppCompatActivity {

    private String item_name, item_id;
    private RecyclerView ranking_recycleView, one_win_product_recycleview;
    private List<FriendSale> one_win_list = new ArrayList<>();
    private List<OneWinShareList> oneWinShareLists = new ArrayList<>();
    private Share_session shareSession;
    private Map data;
    private ImageView images;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_win);

        item_name = getIntent().getStringExtra("item_type");
        item_id = getIntent().getStringExtra("item_id");

        InitializeActivity();
        ActivityAction();
        SetActivityData();
        SetRecycleView();

    }

    private void InitializeActivity() {
        ranking_recycleView = (RecyclerView) findViewById(R.id.ranking_recycleView);
        one_win_product_recycleview = (RecyclerView) findViewById(R.id.one_win_product_recycleview);
        images = (ImageView) findViewById(R.id.image);



        data = new Share_session(this).Fetchdata();

    }

    private void ActivityAction() {
    }

    private void SetActivityData() {
        image(item_id);
    }

    private void SetRecycleView() {
        RankingMethod();
        ProductMethod();
    }

    private void ProductMethod() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        one_win_product_recycleview.setLayoutManager(linearLayoutManager);

        OkHttpClient okHttpClient = new OkHttpClient();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("item_id", item_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(JSON, jsonObject.toString());
        Request request1 = new Request.Builder().url(Constants.ONE_WIN_PRODUCT).post(body).build();
        okHttpClient.newCall(request1).enqueue(new okhttp3.Callback() {
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
                                one_win_list.add(new FriendSale(jsonHelper.GetResult("product_id"), jsonHelper.GetResult("productName"), jsonHelper.GetResult("item_id"), jsonHelper.GetResult("catagory_id"), jsonHelper.GetResult("actual_price"), jsonHelper.GetResult("discount_price"), jsonHelper.GetResult("discount_price_per"), jsonHelper.GetResult("status"), jsonHelper.GetResult("pimg"), jsonHelper.GetResult("vendor_id"), jsonHelper.GetResult("type"), jsonHelper.GetResult("datetime"), jsonHelper.GetResult("FakeRating"), jsonHelper.GetResult("req_users_shares"), jsonHelper.GetResult("new_users_atleast")));

                            }

                            if (One_winActivity.this != null) {
                                One_winActivity.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        OneWinProductAdapter oneWinProductAdapter = new OneWinProductAdapter(One_winActivity.this, one_win_list, item_name);
                                        one_win_product_recycleview.setAdapter(oneWinProductAdapter);
                                    }
                                });
                            }
                        }
                    }
                }

            }
        });

    }

    private void RankingMethod() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        ranking_recycleView.setLayoutManager(linearLayoutManager);


        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(Constants.RANKING)
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
                    Log.e("result",result);
                    JsonHelper jsonHelper = new JsonHelper(result);
                    if (jsonHelper.isValidJson()) {
                        String responses = jsonHelper.GetResult("response");
                        if (responses.equals("TRUE")) {

                            JSONArray jsonArray = jsonHelper.setChildjsonArray(jsonHelper.getCurrentJsonObj(), "data");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                jsonHelper.setChildjsonObj(jsonArray, i);
                                oneWinShareLists.add(new OneWinShareList(jsonHelper.GetResult("top_client"),jsonHelper.GetResult("client_name")));

                            }

                            if (One_winActivity.this != null) {
                                One_winActivity.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        OneWinShareAdapter oneWinShareAdapter = new OneWinShareAdapter(One_winActivity.this ,oneWinShareLists);
                                        ranking_recycleView.setAdapter(oneWinShareAdapter);
                                    }
                                });
                            }
                        }
                    }
                }

            }
        });


    }


    private void image(String item_id) {
        Log.e("item",item_id);
        Log.e("item",item_id);
        OkHttpClient okHttpClient = new OkHttpClient();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("item_id", item_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(JSON, jsonObject.toString());
        Request request1 = new Request.Builder().url(Constants.ITEM_IMAGE).post(body).build();
        okHttpClient.newCall(request1).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    String result = response.body().string();
                    Log.e("result",result);
                    final JsonHelper jsonHelper = new JsonHelper(result);
                    if (jsonHelper.isValidJson()) {
                        String responses = jsonHelper.GetResult("response");
                        if (responses.equals("TRUE")) {
                            JSONArray jsonArray = jsonHelper.setChildjsonArray(jsonHelper.getCurrentJsonObj(), "data");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                jsonHelper.setChildjsonObj(jsonArray, i);
                                One_winActivity.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        images.setVisibility(View.VISIBLE);
                                        String image = jsonHelper.GetResult("banner_img");
                                        String a = Constants.IMAGES + image;
                                        Glide.with(One_winActivity.this).load(a).into(images);
                                    }
                                });
                            }
                        } else {
                            One_winActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    images.setVisibility(View.GONE);
                                }
                            });
                        }
                    }

                }
            }
        });

    }



}


























