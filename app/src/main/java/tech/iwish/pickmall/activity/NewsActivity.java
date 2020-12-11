package tech.iwish.pickmall.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.media.Image;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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
import tech.iwish.pickmall.Interface.Comment_Interface;
import tech.iwish.pickmall.Interface.New_PostIInterface;
import tech.iwish.pickmall.R;
import tech.iwish.pickmall.adapter.New_PostAdapter;
import tech.iwish.pickmall.adapter.NewsAdapter;
import tech.iwish.pickmall.config.Constants;
import tech.iwish.pickmall.connection.JsonHelper;
import tech.iwish.pickmall.fragment.CommentBottom;
import tech.iwish.pickmall.other.New_PostList;
import tech.iwish.pickmall.other.NewsList;

public class NewsActivity extends AppCompatActivity {

    private RecyclerView news_recyclerView;
    ImageView back;
    TextView no_message,titleSet;
    List<NewsList> newsLists = new ArrayList<>();
    List<New_PostList> new_postLists = new ArrayList<>();
    Comment_Interface comment_interface;
    New_PostIInterface new_postIInterface;
    String type;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        news_recyclerView = findViewById(R.id.news_recyclerView);
        back = findViewById(R.id.back);
        no_message = findViewById(R.id.no_message);
        titleSet = findViewById(R.id.titleSet);


        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        type = getIntent().getStringExtra("type");

        switch (type){
            case "new_post":
                titleSet.setText("New Post");
                New_Post();
                break;
            case "news":
                titleSet.setText("News");
                news();
                break;

        }





        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        news_recyclerView.setLayoutManager(linearLayoutManager);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });






    }

    private void news() {

        comment_interface = new Comment_Interface() {
            @Override
            public void commentInterface(String sno) {
                Bundle bundle = new Bundle();
                bundle.putString("sno",sno);
                bundle.putString("type",Constants.ALLNEWS);
                CommentBottom commentBottom = new CommentBottom();
                commentBottom.setArguments(bundle);
                commentBottom.show(NewsActivity.this.getSupportFragmentManager(), commentBottom.getTag());
            }
        };

        newspostShow();

    }

    private void New_Post() {

        new_postIInterface = new New_PostIInterface() {
            @Override
            public void newPostInterface(String sno) {


                Bundle bundle = new Bundle();
                bundle.putString("sno",sno);
                bundle.putString("type",Constants.NEW_POST);
                CommentBottom commentBottom = new CommentBottom();
                commentBottom.setArguments(bundle);
                commentBottom.show(NewsActivity.this.getSupportFragmentManager(), commentBottom.getTag());

            }
        };

        new_postShow();

    }

    private void newspostShow() {

        OkHttpClient okHttpClient = new OkHttpClient();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("item_id", "7");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(JSON, jsonObject.toString());
        Request request1 = new Request.Builder().url(Constants.NEWS).post(body).build();
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

                            if(NewsActivity.this != null){

                                NewsActivity.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        no_message.setVisibility(View.GONE);
                                    }
                                });

                            }

                            JSONArray jsonArray = jsonHelper.setChildjsonArray(jsonHelper.getCurrentJsonObj(), "data");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                jsonHelper.setChildjsonObj(jsonArray, i);
                                newsLists.add(new NewsList(jsonHelper.GetResult("sno") ,jsonHelper.GetResult("description"),jsonHelper.GetResult("image"),jsonHelper.GetResult("likes"),jsonHelper.GetResult("date_time"),jsonHelper.GetResult("status")));
                            }


                            if(NewsActivity.this != null){

                                NewsActivity.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {

                                        NewsAdapter newsAdapter = new NewsAdapter(newsLists,comment_interface);
                                        news_recyclerView.setAdapter(newsAdapter);

                                    }
                                });

                            }
                        }else {

                            if(NewsActivity.this != null){

                                NewsActivity.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        no_message.setVisibility(View.VISIBLE);
                                    }
                                });

                            }

                        }
                    }
                }
            }
        });

    }

    private void new_postShow() {

        OkHttpClient okHttpClient = new OkHttpClient();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("item_id", "7");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(JSON, jsonObject.toString());
        Request request1 = new Request.Builder().url(Constants.NEW_POST_SHOW).post(body).build();
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

                            if(NewsActivity.this != null){

                                NewsActivity.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        no_message.setVisibility(View.GONE);
                                    }
                                });

                            }

                            JSONArray jsonArray = jsonHelper.setChildjsonArray(jsonHelper.getCurrentJsonObj(), "data");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                jsonHelper.setChildjsonObj(jsonArray, i);
                                new_postLists.add(new New_PostList(jsonHelper.GetResult("sno") ,jsonHelper.GetResult("image"),jsonHelper.GetResult("user_number"),jsonHelper.GetResult("description"),jsonHelper.GetResult("date_time"),jsonHelper.GetResult("likes")));
                            }


                            if(NewsActivity.this != null){

                                NewsActivity.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {

                                        New_PostAdapter new_postAdapter = new New_PostAdapter(new_postLists,new_postIInterface, NewsActivity.this);
                                        news_recyclerView.setAdapter(new_postAdapter);

                                    }
                                });

                            }
                        }else {

                            if(NewsActivity.this != null){

                                NewsActivity.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        no_message.setVisibility(View.VISIBLE);
                                    }
                                });

                            }

                        }
                    }
                }
            }
        });

    }


}



















