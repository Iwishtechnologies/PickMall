package tech.iwish.pickmall.activity;


import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Build;
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

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import tech.iwish.pickmall.Interface.CardProductRefreshInterface;
import tech.iwish.pickmall.Interface.ItemCategoryInterface;
import tech.iwish.pickmall.R;
import tech.iwish.pickmall.adapter.FlashSaleAdapter;
import tech.iwish.pickmall.adapter.FriendSaleAdapter;
import tech.iwish.pickmall.adapter.ItemAdapter;
import tech.iwish.pickmall.adapter.SilderAdapter;
import tech.iwish.pickmall.config.Constants;
import tech.iwish.pickmall.connection.ConnectionServer;
import tech.iwish.pickmall.connection.JsonHelper;
import tech.iwish.pickmall.other.CardCount;
import tech.iwish.pickmall.other.FlashsalemainList;
import tech.iwish.pickmall.other.FriendSaleList;
import tech.iwish.pickmall.other.ItemList;
import tech.iwish.pickmall.other.SilderLists;


import static tech.iwish.pickmall.OkhttpConnection.ProductListF.flash_sale_list_fake;
import static tech.iwish.pickmall.OkhttpConnection.ProductListF.friend_deal_list_fake;
import static tech.iwish.pickmall.OkhttpConnection.ProductListF.item_fakelist;
import static tech.iwish.pickmall.OkhttpConnection.ProductListF.silder_list_fack;


public class MainActivity extends AppCompatActivity implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener, CardProductRefreshInterface, ItemCategoryInterface {

    private ViewPager viewPages;
    private SilderAdapter silderAdapter;
    private Timer timer;
    private int current_position = 0;
    private ConnectionServer connectionServer;

    private List<SilderLists> silderListsList = new ArrayList<>();
    private List<FlashsalemainList> flashsalemainLists = new ArrayList<>();
    private List<FriendSaleList> friendSaleLists = new ArrayList<>();
    private List<ItemList> itemLists = new ArrayList<>();


    private RecyclerView flash_sale_main_recycle, itemCateroryrecycle, friend_deal_recycleview;
    private TextView time_countDown, product_count_card;
    //    private static final long START_TIME_IN_MILLIS = 86400000;
    private long mTimeLeftInMillis;
    private LinearLayout viewAll_FreshSale, product_count_card_layout;
    private ImageView homeBottom, feedBottom, cardBottom, accountBottom;
    private String bottomClickCheck;
    private SwipeRefreshLayout swipe_refresh_layout;


    //   adapter
    private FriendSaleAdapter friendSaleAdapter;
    private FlashSaleAdapter flashSaleAdapter;
    private ItemAdapter itemAdapter;

    ItemCategoryInterface itemCategoryInterface;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        viewPages = (ViewPager) findViewById(R.id.viewPages);
        flash_sale_main_recycle = (RecyclerView) findViewById(R.id.flash_sale_main_recycle);
        itemCateroryrecycle = (RecyclerView) findViewById(R.id.itemCateroryrecycle);
        friend_deal_recycleview = (RecyclerView) findViewById(R.id.friend_deal_recycleview);

        time_countDown = (TextView) findViewById(R.id.time_countDown);
        product_count_card = (TextView) findViewById(R.id.product_count_card);
        viewAll_FreshSale = (LinearLayout) findViewById(R.id.viewAll_FreshSale);
        product_count_card_layout = (LinearLayout) findViewById(R.id.product_count_card_layout);

        homeBottom = (ImageView) findViewById(R.id.HomeBottom);
        feedBottom = (ImageView) findViewById(R.id.FeedBottom);
        cardBottom = (ImageView) findViewById(R.id.CardBottom);
        accountBottom = (ImageView) findViewById(R.id.AccountBottom);

        swipe_refresh_layout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this);
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        flash_sale_main_recycle.setLayoutManager(linearLayoutManager);

        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(MainActivity.this);
        linearLayoutManager1.setOrientation(RecyclerView.HORIZONTAL);
        friend_deal_recycleview.setLayoutManager(linearLayoutManager1);


        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL);
        itemCateroryrecycle.setLayoutManager(layoutManager);

        viewAll_FreshSale.setOnClickListener(this);


//    item
        silder();
        itemCat();
        FlashSaleMain();
        Flashsaletimeset();
        FriendDeal();

        homeBottom.setOnClickListener(this);
        feedBottom.setOnClickListener(this);
        cardBottom.setOnClickListener(this);
        accountBottom.setOnClickListener(this);

        homeBottom.setImageDrawable(getResources().getDrawable(R.drawable.home_icon_orange));


        swipe_refresh_layout.setOnRefreshListener(this);
        cardProductCount();

        itemCategoryInterface = new ItemCategoryInterface() {
            @Override
            public void itemcatinterface(String value) {

            }
        };




    }

    private void cardProductCount() {
        if (!CardCount.card_count(this).equals("0")) {
            String number_of_product = CardCount.card_count(this);
            product_count_card.setText(number_of_product);
            product_count_card_layout.setVisibility(View.VISIBLE);
        } else {
            product_count_card_layout.setVisibility(View.GONE);
        }
    }

    private void silder() {

        silderAdapter = new SilderAdapter(MainActivity.this, silder_list_fack());
        viewPages.setAdapter(silderAdapter);


        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(Constants.SILDER_IMAGE)
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
                                silderListsList.add(new SilderLists(jsonHelper.GetResult("sno"), jsonHelper.GetResult("image"), jsonHelper.GetResult("categoryid"), jsonHelper.GetResult("status")));

                            }

                            MainActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    silderAdapter = new SilderAdapter(MainActivity.this, silderListsList);
                                    viewPages.setAdapter(silderAdapter);
                                    createSilderauto();
                                }
                            });


                        }
                    }
                }
            }
        });

    }

    public List itemCat() {

        itemAdapter = new ItemAdapter(MainActivity.this, item_fakelist(), this);
        itemCateroryrecycle.setAdapter(itemAdapter);

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(Constants.ITEM_TYPE)
                .build();
        client.newCall(request).enqueue(new Callback() {
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
                                itemLists.add(new ItemList(jsonHelper.GetResult("item_id"), jsonHelper.GetResult("item_name"), jsonHelper.GetResult("icon_img"), jsonHelper.GetResult("type")));

                            }
                            MainActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
//                                    itemAdapter = new ItemAdapter(MainActivity.this, itemLists , this);
                                    itemAdapter = new ItemAdapter(MainActivity.this, itemLists, itemCategoryInterface);
                                    itemCateroryrecycle.setAdapter(itemAdapter);

                                }
                            });

                        }
                    }
                }
            }
        });
        return itemLists;
    }


    private void Flashsaletimeset() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(Constants.FLASH_SALE_TIME)
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
                    Log.e("response", result);

                    JsonHelper jsonHelper = new JsonHelper(result);
                    if (jsonHelper.isValidJson()) {
                        String responses = jsonHelper.GetResult("response");
                        if (responses.equals("TRUE")) {
                            JSONArray jsonArray = jsonHelper.setChildjsonArray(jsonHelper.getCurrentJsonObj(), "data");


                            for (int i = 0; i < 1; i++) {
                                jsonHelper.setChildjsonObj(jsonArray, i);
                                final String startdatetime = jsonHelper.GetResult("startdatetime");
                                final String enddatetime = jsonHelper.GetResult("enddatetime");
//                                final String[] strt = startdatetime.split(" ", 2);
//                                final String[] end = enddatetime.split(" ", 2);


                                MainActivity.this.runOnUiThread(new Runnable() {
                                    @RequiresApi(api = Build.VERSION_CODES.O)
                                    @Override
                                    public void run() {
//                                        String strTime = strt[1];
//                                        String endTime = end[1];

//                                        Calendar c = Calendar.getInstance();
//                                        Date currentTime;
//                                        String time = "12:13";
//                                        SimpleDateFormat sdf = new SimpleDateFormat("kk:mm");
//                                        try {
//                                            Date timeCompare = sdf.parse(time);
//                                            currentTime = c.getTime();
//                                            if (timeCompare.compareTo(currentTime) == 0) {
//                                                // setStartTime.setText(time);
//                                                Toast.makeText(getApplicationContext(), "Your select future time", Toast.LENGTH_SHORT).show();
//                                            } else {
//                                                Toast.makeText(getApplicationContext(), "Your cannot select future time", Toast.LENGTH_SHORT).show();
//                                            }
//                                        } catch (Exception e) {
//                                            // TODO Auto-generated catch block
//                                            e.printStackTrace();
//                                        }



/*
                                            try {

                                                DateFormat formatter = new SimpleDateFormat("hh:mm");
                                                Date dateStart = (Date)formatter.parse(strTime);
                                                Date dateEnd= (Date)formatter.parse(endTime);
                                                long StartTimeLong =  dateStart.getTime();
                                                long EndTimeLong =  dateEnd.getTime();
                                                long time = EndTimeLong - StartTimeLong  ;
                                                mTimeLeftInMillis = time ;

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
//
                                            } catch(Exception e) {
                                                Log.e("Exception is ", e.toString());
                                            }
*/


                                    }
                                });

                            }

                        }
                    }

                }
            }
        });
    }


    private void FriendDeal() {

        friendSaleAdapter = new FriendSaleAdapter(MainActivity.this, friend_deal_list_fake());
        friend_deal_recycleview.setAdapter(friendSaleAdapter);

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(Constants.FRIEND_DEAL_TOP)
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
                    Log.e("result", result);
                    JsonHelper jsonHelper = new JsonHelper(result);
                    if (jsonHelper.isValidJson()) {
                        String responses = jsonHelper.GetResult("response");
                        if (responses.equals("TRUE")) {
                            JSONArray jsonArray = jsonHelper.setChildjsonArray(jsonHelper.getCurrentJsonObj(), "data");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                jsonHelper.setChildjsonObj(jsonArray, i);
                                friendSaleLists.add(new FriendSaleList(jsonHelper.GetResult("product_id"), jsonHelper.GetResult("ProductName"), jsonHelper.GetResult("item_id"), jsonHelper.GetResult("catagory_id"), jsonHelper.GetResult("actual_price"), jsonHelper.GetResult("discount_price"), jsonHelper.GetResult("discount_price_per"), jsonHelper.GetResult("status"), jsonHelper.GetResult("pimg"), jsonHelper.GetResult("vendor_id"), jsonHelper.GetResult("type"), jsonHelper.GetResult("datetime"), jsonHelper.GetResult("fakeRating"), jsonHelper.GetResult("req_users_shares"), jsonHelper.GetResult("new_users_atleast")));
                            }

                            MainActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    friendSaleAdapter = new FriendSaleAdapter(MainActivity.this, friendSaleLists);
                                    friend_deal_recycleview.setAdapter(friendSaleAdapter);
                                    swipe_refresh_layout.setRefreshing(false);
                                }
                            });

                        }
                    }
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


//        Calendar rightNow = Calendar.getInstance();
//
//        long offset = rightNow.get(Calendar.ZONE_OFFSET) +
//                rightNow.get(Calendar.DST_OFFSET);
//
//        long sinceMidnight = (rightNow.getTimeInMillis() + offset) %
//                (24 * 60 * 60 * 1000);
//
//        long remaining_time = 86400000 - sinceMidnight;
//        mTimeLeftInMillis = remaining_time;


        flashSaleAdapter = new FlashSaleAdapter(MainActivity.this, flash_sale_list_fake());
        flash_sale_main_recycle.setAdapter(flashSaleAdapter);

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(Constants.FLASE_SALE_TOP)
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
                                flashsalemainLists.add(new FlashsalemainList(jsonHelper.GetResult("product_id"), jsonHelper.GetResult("ProductName"), jsonHelper.GetResult("item_id"), jsonHelper.GetResult("catagory_id"), jsonHelper.GetResult("actual_price"), jsonHelper.GetResult("discount_price"), jsonHelper.GetResult("discount_price_per"), jsonHelper.GetResult("status"), jsonHelper.GetResult("pimg"), jsonHelper.GetResult("vendor_id"), jsonHelper.GetResult("type"), jsonHelper.GetResult("datetime"), jsonHelper.GetResult("FakeRating"), jsonHelper.GetResult("saleid")));
                            }

                            MainActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    flashSaleAdapter = new FlashSaleAdapter(MainActivity.this, flashsalemainLists);
                                    flash_sale_main_recycle.setAdapter(flashSaleAdapter);
                                }
                            });

                        }
                    }
                }
            }
        });

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
                            flashsalemainLists.add(new FlashsalemainList());

                        }
                        FlashSaleAdapter flashSaleAdapter = new FlashSaleAdapter(MainActivity.this, flashsalemainLists);
                        flash_sale_main_recycle.setAdapter(flashSaleAdapter);
                    }
                }
            }
        });
*/

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
                startActivity(new Intent(MainActivity.this, AllcategoryActivity.class));
                break;
            case R.id.CardBottom:
                this.bottomClickCheck = "card";
                Intent intent = new Intent(MainActivity.this, CardActivity.class);
                startActivity(intent);
//                Animatoo.animateDiagonal(MainActivity.this);
                break;
            case R.id.AccountBottom:
                startActivity(new Intent(MainActivity.this, Account.class));
                break;
            case R.id.viewAll_FreshSale:
                Intent intent1 = new Intent(MainActivity.this, FlashSaleProductactivity.class);
                startActivity(intent1);
                break;
        }
    }

    @Override
    public void onRefresh() {
        swipe_refresh_layout.setRefreshing(true);
        silderListsList.clear();
        flashsalemainLists.clear();
        friendSaleLists.clear();
        itemLists.clear();
        itemCat();
        FlashSaleMain();
        Flashsaletimeset();
        FriendDeal();

        friendSaleAdapter.notifyDataSetChanged();
        flashSaleAdapter.notifyDataSetChanged();
        itemAdapter.notifyDataSetChanged();
    }


    @Override
    public void cardrefersh() {
        cardProductCount();
    }


    @Override
    public void itemcatinterface(String value) {

    }
}