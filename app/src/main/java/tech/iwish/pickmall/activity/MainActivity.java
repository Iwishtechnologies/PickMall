package tech.iwish.pickmall.activity;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import tech.iwish.pickmall.R;
import tech.iwish.pickmall.adapter.FlashSaleAdapter;
import tech.iwish.pickmall.adapter.ItemAdapter;
import tech.iwish.pickmall.adapter.SilderAdapter;
import tech.iwish.pickmall.config.Constants;
import tech.iwish.pickmall.connection.ConnectionServer;
import tech.iwish.pickmall.connection.JsonHelper;
import tech.iwish.pickmall.other.FlashsalemainList;
import tech.iwish.pickmall.other.ItemList;
import tech.iwish.pickmall.other.SilderLists;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ViewPager viewPages;
    private SilderAdapter silderAdapter;
    private Timer timer;
    private int current_position = 0;
    private ConnectionServer connectionServer;
    private List<SilderLists> silderListsList = new ArrayList<>();
    private List<FlashsalemainList> flashsalemainLists = new ArrayList<>();
    private List<ItemList> itemLists = new ArrayList<>();
    private RecyclerView flash_sale_main, itemCateroryrecycle;
    private TextView time_countDown;
    private static final long START_TIME_IN_MILLIS = 86400000;
    private long mTimeLeftInMillis = START_TIME_IN_MILLIS;
    private LinearLayout viewAll_FreshSale;
    private ImageView homeBottom , feedBottom , cardBottom , accountBottom;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPages = (ViewPager) findViewById(R.id.viewPages);
        flash_sale_main = (RecyclerView) findViewById(R.id.flash_sale_main);
        itemCateroryrecycle = (RecyclerView) findViewById(R.id.itemCateroryrecycle);
        time_countDown = (TextView) findViewById(R.id.time_countDown);
        viewAll_FreshSale = (LinearLayout) findViewById(R.id.viewAll_FreshSale);


        homeBottom = (ImageView) findViewById(R.id.HomeBottom);
        feedBottom = (ImageView) findViewById(R.id.FeedBottom);
        cardBottom = (ImageView) findViewById(R.id.CardBottom);
        accountBottom = (ImageView) findViewById(R.id.AccountBottom);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this);
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        flash_sale_main.setLayoutManager(linearLayoutManager);

        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL);
        itemCateroryrecycle.setLayoutManager(layoutManager);


        viewAll_FreshSale.setOnClickListener(this);


//      silder
/*
        connectionServer = new ConnectionServer();
        connectionServer.set_url(Constants.SILDER_IMAGE);
        connectionServer.requestedMethod("POST");
        connectionServer.execute(new ConnectionServer.AsyncResponse() {
            @Override
            public void processFinish(String output) {
                Log.e("output", output);
                JsonHelper jsonHelper = new JsonHelper(output);
                if (jsonHelper.isValidJson()) {
                    String response = jsonHelper.GetResult("response");
                    if (response.equals("TRUE")) {
                        JSONArray jsonArray = jsonHelper.setChildjsonArray(jsonHelper.getCurrentJsonObj(), "data");

                        for (int i = 0; i < jsonArray.length(); i++) {
                            jsonHelper.setChildjsonObj(jsonArray, i);
                            silderListsList.add(new SilderLists(jsonHelper.GetResult("sno"), jsonHelper.GetResult("image"), jsonHelper.GetResult("categoryid"), jsonHelper.GetResult("status")));

                        }
                        silderAdapter = new SilderAdapter(MainActivity.this, silderListsList);
                        viewPages.setAdapter(silderAdapter);
                        createSilderauto();
                    }
                }
            }
        });
*/

//    item
        itemCat();
        FlashSaleMain();


        homeBottom.setOnClickListener(this);
        feedBottom.setOnClickListener(this);
        cardBottom.setOnClickListener(this);
        accountBottom.setOnClickListener(this);

        homeBottom.setImageDrawable(getResources().getDrawable(R.drawable.home_icon_orange));


        new CountDownTimer(mTimeLeftInMillis, 1000) {

            @Override
            public void onTick(long l) {
                mTimeLeftInMillis = l;
                updateCountDownText();
            }

            @Override
            public void onFinish() {

            }
        }.start();


    }

    private void itemCat() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("http://173.212.226.143:8086/api/item_type")
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
                    Log.e("output", result);
                    JsonHelper jsonHelper = new JsonHelper(result);
                    if (jsonHelper.isValidJson()) {
                        String responses = jsonHelper.GetResult("response");
                        if (responses.equals("TRUE")) {
                            JSONArray jsonArray = jsonHelper.setChildjsonArray(jsonHelper.getCurrentJsonObj(), "data");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                jsonHelper.setChildjsonObj(jsonArray, i);
                                itemLists.add(new ItemList(jsonHelper.GetResult("item_id"), jsonHelper.GetResult("item_name"), jsonHelper.GetResult("icon_img")));

                            }
                            MainActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    ItemAdapter itemAdapter = new ItemAdapter(MainActivity.this , itemLists);
                                    itemCateroryrecycle.setAdapter(itemAdapter);
                                }
                            });

                        }
                    }




//
//
                }
            }
        });
    }

    private void updateCountDownText() {
        int hours = (int) (mTimeLeftInMillis / 1000) / 3600;
        int minutes = (int) ((mTimeLeftInMillis / 1000) % 3600) / 60;
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;


        String timeLeftFormatted;
        if (hours > 0) {
            timeLeftFormatted = String.format(Locale.getDefault(),
                    "%d:%02d:%02d", hours, minutes, seconds);
        } else {
            timeLeftFormatted = String.format(Locale.getDefault(),
                    "%02d:%02d", minutes, seconds);
        }

        time_countDown.setText(timeLeftFormatted);

    }

    private void FlashSaleMain() {
        connectionServer = new ConnectionServer();
        connectionServer.set_url(Constants.SILDER_IMAGE);
        connectionServer.requestedMethod("POST");
        connectionServer.execute(new ConnectionServer.AsyncResponse() {
            @Override
            public void processFinish(String output) {
                Log.e("output", output);
                JsonHelper jsonHelper = new JsonHelper(output);
                if (jsonHelper.isValidJson()) {
                    String response = jsonHelper.GetResult("response");
                    if (response.equals("TRUE")) {
                        JSONArray jsonArray = jsonHelper.setChildjsonArray(jsonHelper.getCurrentJsonObj(), "data");

                        for (int i = 0; i < jsonArray.length(); i++) {
                            jsonHelper.setChildjsonObj(jsonArray, i);
                            flashsalemainLists.add(new FlashsalemainList());

                        }
                        FlashSaleAdapter flashSaleAdapter = new FlashSaleAdapter(MainActivity.this, flashsalemainLists);
                        flash_sale_main.setAdapter(flashSaleAdapter);


                    }
                }
            }
        });

    }

    private void createSilderauto() {
        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (current_position == silderListsList.size())
                    current_position = 0;
                viewPages.setCurrentItem(current_position++, true);
            }
        };

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(runnable);
            }
        }, 250, 25000);

    }


    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.HomeBottom:
                Toast.makeText(this, "home", Toast.LENGTH_SHORT).show();
                break;
            case R.id.FeedBottom:
                Toast.makeText(this, "feed", Toast.LENGTH_SHORT).show();
                break;
            case R.id.CardBottom:
                Toast.makeText(this, "card", Toast.LENGTH_SHORT).show();
                break;
            case R.id.AccountBottom:
                Toast.makeText(this, "account", Toast.LENGTH_SHORT).show();
                break;

            case R.id.viewAll_FreshSale:
                Intent intent1 = new Intent(MainActivity.this, FreshSaleViewAllActivity.class);
                startActivity(intent1);
                break;


        }
    }

}
