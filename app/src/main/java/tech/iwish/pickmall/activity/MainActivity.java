package tech.iwish.pickmall.activity;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

import ir.hamiss.internetcheckconnection.InternetConnectivityListener;
import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import tech.iwish.pickmall.Interface.CardProductRefreshInterface;
import tech.iwish.pickmall.Interface.FlashsaleTimeIdInterface;
import tech.iwish.pickmall.Interface.ItemCategoryInterface;
import tech.iwish.pickmall.Interface.Progressbar_product_inteface;
import tech.iwish.pickmall.R;
import tech.iwish.pickmall.RetrofitInterface.FlashSale_FriendDealInterface;
import tech.iwish.pickmall.RetrofitInterface.HotProductInterface;
import tech.iwish.pickmall.RetrofitInterface.Silder_Category_Interface;
import tech.iwish.pickmall.RetrofitModel.FlashSale_friendDeal.FlashSaleFriendDealList;
import tech.iwish.pickmall.RetrofitModel.hotProduct.HotProductList;
import tech.iwish.pickmall.RetrofitModel.silderCategory.SilderCategoryList;
import tech.iwish.pickmall.adapter.FlashSaleAdapter;
import tech.iwish.pickmall.adapter.FriendSaleAdapter;
import tech.iwish.pickmall.adapter.HotProductAdapter;
import tech.iwish.pickmall.adapter.ItemAdapter;
import tech.iwish.pickmall.adapter.SilderAdapter;
import tech.iwish.pickmall.config.Constants;
import tech.iwish.pickmall.connection.ConnectionServer;
import tech.iwish.pickmall.connection.JsonHelper;
import tech.iwish.pickmall.other.CardCount;
import tech.iwish.pickmall.other.FlashSaleTopList;
import tech.iwish.pickmall.other.FlashsalemainList;
import tech.iwish.pickmall.other.FriendSaleList;
import tech.iwish.pickmall.other.HotproductList;
import tech.iwish.pickmall.other.ItemList;
import tech.iwish.pickmall.other.ProductList;
import tech.iwish.pickmall.other.SilderLists;
import tech.iwish.pickmall.reciver.InterNetConnection;
import tech.iwish.pickmall.session.Share_session;

import static tech.iwish.pickmall.session.Share_session.USERMOBILE;


public class MainActivity extends AppCompatActivity
        implements View.OnClickListener,
        CardProductRefreshInterface,
        ItemCategoryInterface,
        FlashsaleTimeIdInterface, InternetConnectivityListener {

    private ViewPager viewPages;
    private SilderAdapter silderAdapter;
    private Timer timer;
    private int current_position = 0;
    private ConnectionServer connectionServer;

    private List<SilderLists> silderListsList = new ArrayList<>();
    private List<FlashsalemainList> flashsalemainLists = new ArrayList<>();
    private List<FriendSaleList> friendSaleLists = new ArrayList<>();
    private List<ItemList> itemLists = new ArrayList<>();
    private List<ProductList> productListList = new ArrayList<>();
    private List<HotproductList> hotproductLists = new ArrayList<>();
    private List<FlashSaleTopList> flashSaleTopLists = new ArrayList<>();

    private RecyclerView flash_sale_main_recycle, itemCateroryrecycle, friend_deal_recycleview, hotproductRecyclerView;
    public static TextView time_countDown, product_count_card;
    //    private static final long START_TIME_IN_MILLIS = 86400000;
//        private long mTimeLeftInMillis = START_TIME_IN_MILLIS;
    private long mTimeLeftInMillis;
    private LinearLayout viewAll_FreshSale, product_count_card_layout, flash_line, viewall_friend_deal, prepaid_layout, message;
    private ImageView homeBottom, feedBottom, cardBottom, accountBottom, Notification;
    private String bottomClickCheck;
    private SwipeRefreshLayout swipe_refresh_layout;
    //   adapter
    private FriendSaleAdapter friendSaleAdapter;
    private FlashSaleAdapter flashSaleAdapter;
    private ItemAdapter itemAdapter;
    boolean doubleBackToExitPressedOnce = false;
    private ItemCategoryInterface itemCategoryInterface;
    public final static String TAG = "mainActivity";
    private RelativeLayout search_bar_layout;
    Share_session share_session;
    private Map data;
    private CountDownTimer mCountDownTimer;
    NestedScrollView scrollMainActivity;
    TextView notificationCount;
    ProgressBar progress;
    Progressbar_product_inteface progressbar_product_inteface;
    private SilderCategoryList list;
    private FlashSaleFriendDealList friend_flash_List;
    HotProductList hotProductlist;
    int visibleItemCount, totalItemCount, lastVisibleItemPositions;

    private String productName, actual_prices, pimg, order_id;
    private int countProductHot = 0;

    int page_number = 1;
    boolean isLoading = true;
    int preview_total = 0, view_threshold = 10;
    //    ******************************************************
    private Integer ActivityRequestCode = 2;
    private String midString = "pMwrjE07945349166231", txnAmountString = "10", orderIdString = "2", txnTokenString = "uMnQqlhwXXBJBVx5sDC2ALyuzC6arz3ec1YhCxF56sUs6V+SpfxWRRwR2A8NEflqnAxgg0HTX69Hkuh2Ys4r8ATAYK8y8Zqv5Rl1DIU6+pg=";
    private String last_start_id;
    ProgressBar hotProgressbar;
    private boolean isLoadings = true;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        FirebaseMessaging.getInstance().subscribeToTopic("OFFER");
        String token = ("fcm" + FirebaseInstanceId.getInstance().getToken());


        InterNetConnection interNetConnection = new InterNetConnection();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        intentFilter.addAction("android.net.wifi.WIFI_STATE_CHANGED");
        registerReceiver(interNetConnection, intentFilter);


        viewPages = (ViewPager) findViewById(R.id.viewPages);
        flash_sale_main_recycle = (RecyclerView) findViewById(R.id.flash_sale_main_recycle);
        itemCateroryrecycle = (RecyclerView) findViewById(R.id.itemCateroryrecycle);
        friend_deal_recycleview = (RecyclerView) findViewById(R.id.friend_deal_recycleview);
        hotproductRecyclerView = (RecyclerView) findViewById(R.id.hotproductRecyclerView);

        notificationCount = findViewById(R.id.notificationCount);
        Notification = findViewById(R.id.Notification);
        time_countDown = (TextView) findViewById(R.id.time_countDown);
        product_count_card = (TextView) findViewById(R.id.product_count_card);
        viewAll_FreshSale = (LinearLayout) findViewById(R.id.viewAll_FreshSale);
        product_count_card_layout = (LinearLayout) findViewById(R.id.product_count_card_layout);
        flash_line = (LinearLayout) findViewById(R.id.flash_line);
        viewall_friend_deal = (LinearLayout) findViewById(R.id.viewall_friend_deal);
        prepaid_layout = (LinearLayout) findViewById(R.id.prepaid_layout);
        message = (LinearLayout) findViewById(R.id.message);

        homeBottom = (ImageView) findViewById(R.id.HomeBottom);
        feedBottom = (ImageView) findViewById(R.id.FeedBottom);
        cardBottom = (ImageView) findViewById(R.id.CardBottom);
        accountBottom = (ImageView) findViewById(R.id.accountBottom);
        progress = findViewById(R.id.progress);
        hotProgressbar = findViewById(R.id.hotProgressbar);


        scrollMainActivity = findViewById(R.id.scrollMainActivity);
        Notification.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, NotificationActivity.class));
        });

//        ***************************  Version check   *************************************************

        try {
            PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            int version = pInfo.versionCode;
            if(version < Constants.VERSION_CODE){
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setCancelable(false);
                View view = LayoutInflater.from(this).inflate(R.layout.version_update , null);
                builder.setView(view);
                RelativeLayout layoutclick = view.findViewById(R.id.layoutclick);
                layoutclick.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ProgressBar progress1 = view.findViewById(R.id.progress1);
                        progress1.setVisibility(View.VISIBLE);
                        Uri uri = Uri.parse("market://details?id=" + getPackageName());
                        Intent myAppLinkToMarket = new Intent(Intent.ACTION_VIEW, uri);
                        try {
                            startActivity(myAppLinkToMarket);
                        } catch (ActivityNotFoundException e) {
                            Toast.makeText(MainActivity.this, " unable to find market app", Toast.LENGTH_LONG).show();
                        }
                        progress1.setVisibility(View.GONE);
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

//        *********************************************************************************************
        search_bar_layout = (RelativeLayout) findViewById(R.id.search_bar_layout);
        scrollMainActivity.setNestedScrollingEnabled(false);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this);
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        flash_sale_main_recycle.setLayoutManager(linearLayoutManager);

        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(MainActivity.this);
        linearLayoutManager1.setOrientation(RecyclerView.HORIZONTAL);
        friend_deal_recycleview.setLayoutManager(linearLayoutManager1);


        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(5, StaggeredGridLayoutManager.VERTICAL);
        itemCateroryrecycle.setLayoutManager(layoutManager);


        viewAll_FreshSale.setOnClickListener(this);
//        amont return

        share_session = new Share_session(this);
        data = share_session.Fetchdata();


        if (data.get(USERMOBILE) != null) {

            sendRegistrationToServer(token);

            one_rs_amount_return();
            friend_deal_90_rs_amount_return();
            popup();
        }


//    item
        /*silder();
        itemCat();
        FlashSaleMain();
        FriendDeal();
        HotProduct();*/

        RecyclerView_INIT();

        homeBottom.setOnClickListener(this);
        feedBottom.setOnClickListener(this);
        cardBottom.setOnClickListener(this);
        accountBottom.setOnClickListener(this);
        search_bar_layout.setOnClickListener(this);
        viewall_friend_deal.setOnClickListener(this);
        prepaid_layout.setOnClickListener(this);
        message.setOnClickListener(this);
        homeBottom.setImageDrawable(getResources().getDrawable(R.drawable.home_icon_orange));
//        swipe_refresh_layout.setOnRefreshListener(this);
        cardProductCount();

        itemCategoryInterface = value -> {

        };

        progressbar_product_inteface = val -> {
            if (val.equals("PROGRESSBAR_START")) progress.setVisibility(View.VISIBLE);
            else progress.setVisibility(View.GONE);
        };

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notification();
        }

        if (0 < Integer.parseInt(share_session.GetUnread())) {
            notificationCount.setText(share_session.GetUnread());
            notificationCount.setVisibility(View.VISIBLE);
        }

    }

    private void RecyclerView_INIT() {

        itemCateroryrecycle.setLayoutManager(new GridLayoutManager(this, 5));
//        flash sale
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        flash_sale_main_recycle.setLayoutManager(linearLayoutManager);
//        Friend deal
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(this);
        linearLayoutManager1.setOrientation(RecyclerView.HORIZONTAL);
        friend_deal_recycleview.setLayoutManager(linearLayoutManager1);
//        hot Product
        hotproductRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));

        silder_and_Category();
        flashSale_FriendDeal();
        hotProduct("0");

        scrollMainActivity.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

//                if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
//                    Toast.makeText(MainActivity.this, "last", Toast.LENGTH_SHORT).show();
//                }

                if (v.getChildAt(v.getChildCount() - 1) != null) {
                    if ((scrollY >= (v.getChildAt(v.getChildCount() - 1).getMeasuredHeight() - v.getMeasuredHeight())) &&
                            scrollY > oldScrollY) {
//                        Toast.makeText(MainActivity.this, "Last", Toast.LENGTH_LONG).show();
//                        if (isLoadings) {
//                            isLoadings = false;
//                            hotProgressbar.setVisibility(View.VISIBLE);
//                            hotProduct(String.valueOf(hotProductlist.getData().size() + 30));
//                            Log.e("aaaaaaaaa", String.valueOf(hotProductlist.getData().size() + 30));
//                        }
                    }
                }

            }
        });

    }


    private void hotProduct(String val) {
        retrofit2.Call<HotProductList> gethotProduct = HotProductInterface.SilderCategory().gethot_product(val);
        gethotProduct.enqueue(new retrofit2.Callback<HotProductList>() {
            @Override
            public void onResponse(retrofit2.Call<HotProductList> call, retrofit2.Response<HotProductList> response) {
                hotProductlist = response.body();
                hotProgressbar.setVisibility(View.GONE);
                if (hotProductlist.getResponse().equals("TRUE")) {
                    isLoadings = true;
                    initHotproduct();
                } else {
                    hotProgressbar.setVisibility(View.GONE);
                }

            }

            @Override
            public void onFailure(retrofit2.Call<HotProductList> call, Throwable t) {

            }
        });
    }

    private void initHotproduct() {
        hotproductRecyclerView.setAdapter(new HotProductAdapter(hotProductlist.getData(), progressbar_product_inteface));
        new HotProductAdapter(hotProductlist.getData(), progressbar_product_inteface).notifyDataSetChanged();
    }

    private void flashSale_FriendDeal() {

        retrofit2.Call<FlashSaleFriendDealList> getFlashSale_FriendDeal = FlashSale_FriendDealInterface.ProductFrontShare().getflashSale_friend_deal("aa");
        getFlashSale_FriendDeal.enqueue(new retrofit2.Callback<FlashSaleFriendDealList>() {
            @Override
            public void onResponse(retrofit2.Call<FlashSaleFriendDealList> call, retrofit2.Response<FlashSaleFriendDealList> response) {

                friend_flash_List = response.body();
                if (Objects.requireNonNull(friend_flash_List).getFlashsale().equals("TRUE")) {
                    flash_line.setVisibility(View.VISIBLE);
                    flash_sale_main_recycle.setAdapter(new FlashSaleAdapter(MainActivity.this, friend_flash_List.getFlashsaledata()));
                } else {
                    flash_line.setVisibility(View.GONE);
                }

                if (Objects.requireNonNull(friend_flash_List).getFriendsdeal().equals("TRUE")) {
                    friend_deal_recycleview.setAdapter(new FriendSaleAdapter(MainActivity.this, friend_flash_List.getFriendsdata()));
                }
            }

            @Override
            public void onFailure(retrofit2.Call<FlashSaleFriendDealList> call, Throwable t) {
                Log.e(TAG, "onFailure: "+ t.getMessage() );
            }
        });

    }

    private void silder_and_Category() {

        retrofit2.Call<SilderCategoryList> silder_category = Silder_Category_Interface.SilderCategory().getSilder_Category("a");
        silder_category.enqueue(new retrofit2.Callback<SilderCategoryList>() {
            @Override
            public void onResponse(retrofit2.Call<SilderCategoryList> call, retrofit2.Response<SilderCategoryList> response) {
                list = response.body();
                if (list.getResponse().equals("TRUE")) {

                    SilderAdapter silderAdapter = new SilderAdapter(MainActivity.this, list.getSlider());
                    viewPages.setAdapter(silderAdapter);
                    createSilderauto();

                    ItemAdapter itemAdapter = new ItemAdapter(MainActivity.this, list.getCategory(), itemCategoryInterface);
                    itemCateroryrecycle.setAdapter(itemAdapter);

                }
            }

            @Override
            public void onFailure(retrofit2.Call<SilderCategoryList> call, Throwable t) {

            }
        });

    }

    private void createSilderauto() {
        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (current_position == list.getCategory().size())
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
        }, 250, 3000);


    }


    private void friend_deal_90_rs_amount_return() {


        OkHttpClient okHttpClient = new OkHttpClient();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("number", Objects.requireNonNull(data.get(USERMOBILE)).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(JSON, jsonObject.toString());
        Request request1 = new Request.Builder().url(Constants.FRIEND_DEAL_90_RS_AMT_RETURN).post(body).build();
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

                        }
                    }
                }
                response.close();
            }
        });

    }

    private void one_rs_amount_return() {


        OkHttpClient okHttpClient = new OkHttpClient();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("number", data.get(USERMOBILE).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(JSON, jsonObject.toString());
        Request request1 = new Request.Builder().url(Constants.ONE_RS_AMT_RETURN).post(body).build();
        okHttpClient.newCall(request1).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                response.close();
//                if (response.isSuccessful()) {
//                    String result = response.body().string();
//                    Log.e("result", result);
//                    JsonHelper jsonHelper = new JsonHelper(result);
//                    if (jsonHelper.isValidJson()) {
//                        String responses = jsonHelper.GetResult("response");
//                        if (responses.equals("TRUE")) {
//
//
//                        }
//                    }
//                }

            }
        });

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
/*

   private void HotProduct() {

        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        hotproductRecyclerView.setLayoutManager(staggeredGridLayoutManager);

        last_start_id = retriveHotProduct("0");

        scrollMainActivity.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

                if (v.getChildAt(v.getChildCount() - 1) != null) {
                    if ((scrollY >= (v.getChildAt(v.getChildCount() - 1).getMeasuredHeight() - v.getMeasuredHeight())) &&
                            scrollY > oldScrollY) {
//                        Toast.makeText(MainActivity.this, "Last", Toast.LENGTH_LONG).show();
                        last_start_id = retriveHotProduct(last_start_id);
                    }
                }

            }
        });

    }
    String retriveHotProduct(String count) {

      OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(Constants.HOT_PRODUCT)
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
                            productListList.clear();
                            String responses = jsonHelper.GetResult("response");
                            if (responses.equals("TRUE")) {
                                JSONArray jsonArray = jsonHelper.setChildjsonArray(jsonHelper.getCurrentJsonObj(), "data");

                                for (int i = 0; i < jsonArray.length(); i++) {

                                    jsonHelper.setChildjsonObj(jsonArray, i);
                                    productListList.add(new ProductList(jsonHelper.GetResult("product_id"),
                                            jsonHelper.GetResult("ProductName"),
                                            jsonHelper.GetResult("SKU"),
                                            jsonHelper.GetResult("item_id"),
                                            jsonHelper.GetResult("catagory_id"),
                                            jsonHelper.GetResult("actual_price"),
                                            jsonHelper.GetResult("discount_price"),
                                            jsonHelper.GetResult("discount_price_per"),
                                            jsonHelper.GetResult("status"),
                                            jsonHelper.GetResult("pimg"),
                                            jsonHelper.GetResult("vendor_id"),
                                            jsonHelper.GetResult("FakeRating"),
                                            jsonHelper.GetResult("gst"),
                                            jsonHelper.GetResult("hot_product"),
                                            jsonHelper.GetResult("hsn_no"),
                                            jsonHelper.GetResult("weight"),
                                            jsonHelper.GetResult("type"),
                                            jsonHelper.GetResult("flash_sale"),
                                            jsonHelper.GetResult("extraoffer"),
                                            jsonHelper.GetResult("startdate"),
                                            jsonHelper.GetResult("enddate")
                                    ));

                                }

                                Handler mainHandler = new Handler(MainActivity.this.getMainLooper());
                                mainHandler.post(() -> {
                                    ProductAdapter productAdapter = new ProductAdapter(MainActivity.this, productListList, "");
                                    hotproductRecyclerView.setAdapter(productAdapter);
                                    productAdapter.notifyDataSetChanged();
                                });


                            }
                        }
                    }

            }
        });


        OkHttpClient okHttpClient = new OkHttpClient();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("start_point", count);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(JSON, jsonObject.toString());
        Request request1 = new Request.Builder().url(Constants.HOT_PRODUCT).post(body).build();


        okHttpClient.newCall(request1).enqueue(new okhttp3.Callback() {
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
                        productListList.clear();
                        String responses = jsonHelper.GetResult("response");
                        if (responses.equals("TRUE")) {
                            JSONArray jsonArray = jsonHelper.setChildjsonArray(jsonHelper.getCurrentJsonObj(), "data");

                            for (int i = 0; i < jsonArray.length(); i++) {

                                jsonHelper.setChildjsonObj(jsonArray, i);
                                hotproductLists.add(new HotproductList(jsonHelper.GetResult("product_id"),
                                        jsonHelper.GetResult("ProductName"),
                                        jsonHelper.GetResult("SKU"),
                                        jsonHelper.GetResult("item_id"),
                                        jsonHelper.GetResult("catagory_id"),
                                        jsonHelper.GetResult("actual_price"),
                                        jsonHelper.GetResult("discount_price"),
                                        jsonHelper.GetResult("discount_price_per"),
                                        jsonHelper.GetResult("status"),
                                        jsonHelper.GetResult("pimg"),
                                        jsonHelper.GetResult("vendor_id"),
                                        jsonHelper.GetResult("FakeRating"),
                                        jsonHelper.GetResult("gst"),
                                        jsonHelper.GetResult("hot_product"),
                                        jsonHelper.GetResult("hsn_no"),
                                        jsonHelper.GetResult("weight"),
                                        jsonHelper.GetResult("type"),
                                        jsonHelper.GetResult("flash_sale"),
                                        jsonHelper.GetResult("extraoffer"),
                                        jsonHelper.GetResult("startdate"),
                                        jsonHelper.GetResult("enddate")
                                ));
                            }

                            new Handler(MainActivity.this.getMainLooper()).post(() -> {
//                                ProductAdapter productAdapter = new ProductAdapter(MainActivity.this, hotproductLists, "");
//                                hotproductRecyclerView.setAdapter(new HotProductAdapter(hotproductLists, progressbar_product_inteface));
//                                new HotProductAdapter(hotproductLists, progressbar_product_inteface).notifyDataSetChanged();
                            });


                        }
                    }
                }

            }
        });


        return String.valueOf(hotproductLists.size());


    }


    private void silder() {

//        silderAdapter = new SilderAdapter(MainActivity.this, silder_list_fack());
//        viewPages.setAdapter(silderAdapter);


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
                                silderListsList.add(new SilderLists(jsonHelper.GetResult("sno"), jsonHelper.GetResult("image"), jsonHelper.GetResult("categoryid"), jsonHelper.GetResult("item_name"), jsonHelper.GetResult("status")));
                            }
                            if (MainActivity.this != null) {
//                                MainActivity.this.runOnUiThread(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        silderAdapter = new SilderAdapter(MainActivity.this, silderListsList);
//                                        viewPages.setAdapter(silderAdapter);
//                                        createSilderauto();
//                                    }
//                                });

                                Handler mainHandler = new Handler(MainActivity.this.getMainLooper());
                                mainHandler.post(new Runnable() {
                                    @Override
                                    public void run() {
//                                        silderAdapter = new SilderAdapter(MainActivity.this, silderListsList);
//                                        viewPages.setAdapter(silderAdapter);
//                                        createSilderauto();
                                    }
                                });


                            }
                        }
                    }
                }
                response.close();
            }
        });

    }

   public void itemCat() {

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(Constants.ITEM_TYPE)
                .build();

        AsyncTask<Void, Void, String> asyncTask = new AsyncTask<Void, Void, String>() {

            @Override
            protected String doInBackground(Void... voids) {
                try {
                    Response response = client.newCall(request).execute();
                    if (!response.isSuccessful()) {
                        return null;
                    }
                    return response.body().string();
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                if (s != null) {

                    JsonHelper jsonHelper = new JsonHelper(s);
                    if (jsonHelper.isValidJson()) {
                        String responses = jsonHelper.GetResult("response");
                        if (responses.equals("TRUE")) {
                            JSONArray jsonArray = jsonHelper.setChildjsonArray(jsonHelper.getCurrentJsonObj(), "data");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                jsonHelper.setChildjsonObj(jsonArray, i);
                                itemLists.add(new ItemList(jsonHelper.GetResult("item_id"), jsonHelper.GetResult("item_name"), jsonHelper.GetResult("icon_img"), jsonHelper.GetResult("type"), jsonHelper.GetResult("item_type")));

                            }
//                            itemAdapter = new ItemAdapter(MainActivity.this, itemLists, itemCategoryInterface);
//                            itemCateroryrecycle.setAdapter(itemAdapter);

                        }
                    }

                }
            }
        };

        asyncTask.execute();


    }


    private void FriendDeal() {

//        friendSaleAdapter = new FriendSaleAdapter(MainActivity.this, friend_deal_list_fake());
//        friend_deal_recycleview.setAdapter(friendSaleAdapter);

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

                            if (MainActivity.this != null) {

//                                MainActivity.this.runOnUiThread(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        friendSaleAdapter = new FriendSaleAdapter(MainActivity.this, friendSaleLists);
//                                        friend_deal_recycleview.setAdapter(friendSaleAdapter);
//
//                                    }
//                                });
                                Handler mainHandler = new Handler(MainActivity.this.getMainLooper());
                                mainHandler.post(new Runnable() {
                                    @Override
                                    public void run() {
//                                        friendSaleAdapter = new FriendSaleAdapter(MainActivity.this, friendSaleLists);
//                                        friend_deal_recycleview.setAdapter(friendSaleAdapter);
                                    }
                                });


                            }
                        }

                    }
                }
                response.close();
            }
        });


    }

    private void FlashSaleMain() {


        OkHttpClient okHttpClient = new OkHttpClient();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("item_id", "7");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(JSON, jsonObject.toString());
        Request request1 = new Request.Builder().url(Constants.FLASE_SALE_TOP).post(body).build();


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

                            new Handler(MainActivity.this.getMainLooper()).post(() -> new CountdownTime(time_countDown));
                            JSONArray jsonArray = jsonHelper.setChildjsonArray(jsonHelper.getCurrentJsonObj(), "data");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                jsonHelper.setChildjsonObj(jsonArray, i);
                                flashSaleTopLists.add(new FlashSaleTopList(jsonHelper.GetResult("product_id"),
                                        jsonHelper.GetResult("ProductName"),
                                        jsonHelper.GetResult("SKU"),
                                        jsonHelper.GetResult("item_id"),
                                        jsonHelper.GetResult("catagory_id"),
                                        jsonHelper.GetResult("actual_price"),
                                        jsonHelper.GetResult("discount_price"),
                                        jsonHelper.GetResult("discount_price_per"),
                                        jsonHelper.GetResult("status"),
                                        jsonHelper.GetResult("pimg"),
                                        jsonHelper.GetResult("vendor_id"),
                                        jsonHelper.GetResult("FakeRating"),
                                        jsonHelper.GetResult("gst"),
                                        jsonHelper.GetResult("hot_product"),
                                        jsonHelper.GetResult("hsn_no"),
                                        jsonHelper.GetResult("weight"),
                                        jsonHelper.GetResult("type"),
                                        jsonHelper.GetResult("flash_sale"),
                                        jsonHelper.GetResult("extraoffer"),
                                        jsonHelper.GetResult("startdate"),
                                        jsonHelper.GetResult("enddate")
                                ));
                            }

                            new Handler(MainActivity.this.getMainLooper()).post(() -> {
//                                flash_sale_main_recycle.setAdapter(new FlashSaleAdapter(MainActivity.this, flashSaleTopLists));
                            });

                        } else {
                            new Handler(getMainLooper()).post(() -> flash_line.setVisibility(View.GONE));
                        }
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
        }, 250, 3000);


    }*/

    @Override
    public void onClick(View view) {
        Intent intent = new Intent();
        int id = view.getId();
        switch (id) {
            case R.id.FeedBottom:
                intent = new Intent(MainActivity.this, AllcategoryActivity.class);
                startActivity(intent);
                break;
            case R.id.CardBottom:
                this.bottomClickCheck = "card";
                intent = new Intent(MainActivity.this, CardActivity.class);
                intent.putExtra("context", "MainActivity");
//                Animatoo.animateDiagonal(MainActivity.this);
                startActivity(intent);
                break;
            case R.id.accountBottom:
                if (data.get(USERMOBILE) != null) {
                    intent = new Intent(MainActivity.this, Account.class);
                    startActivity(intent);
                } else {
                    intent = new Intent(MainActivity.this, Register1Activity.class);
                    startActivity(intent);
                }
                break;
            case R.id.search_bar_layout:
                intent = new Intent(MainActivity.this, Searchactivity.class);
                startActivity(intent);
                break;
            case R.id.viewAll_FreshSale:
                intent = new Intent(MainActivity.this, FlashSaleProductactivity.class);
                startActivity(intent);
                break;
            case R.id.viewall_friend_deal:
                intent = new Intent(MainActivity.this, FriendsDealsAllActivity.class);
                intent.putExtra("item_id", "26");
                intent.putExtra("item_type", "friend_deal");
                startActivity(intent);
                break;
            case R.id.prepaid_layout:
                intent = new Intent(MainActivity.this, ProductActivity.class);
                intent.putExtra("type", "prepaid");
                intent.putExtra("itemName", "Prepaid");
                startActivity(intent);
                break;
            case R.id.message:
                intent = new Intent(MainActivity.this, MessageActivity.class);
                startActivity(intent);
                break;

        }


    }

    @Override
    public void cardrefersh() {
        cardProductCount();
    }

    @Override
    public void itemcatinterface(String value) {

    }

    @Override
    public void flashsaleId(String saleid) {
//        FlashSaleMain();
        Log.e(TAG, "flashsaleId: " + saleid);
    }


    public int getLastVisibleItem(int[] lastVisibleItemPositions) {
        int maxSize = 0;
        for (int i = 0; i < lastVisibleItemPositions.length; i++) {
            if (i == 0) {
                maxSize = lastVisibleItemPositions[i];
            } else if (lastVisibleItemPositions[i] > maxSize) {
                maxSize = lastVisibleItemPositions[i];
            }
        }
        return maxSize;
    }


    @Override
    public void onBackPressed() {


        if (doubleBackToExitPressedOnce) {
//            super.onBackPressed();
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
            System.exit(0);
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);


    }

    @Override
    protected void onResume() {
        super.onResume();
        cardProductCount();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void notification() {

        NotificationChannel channel =
                new NotificationChannel("pickmall", "pickmall", NotificationManager.IMPORTANCE_DEFAULT);

        NotificationManager manager = getSystemService(NotificationManager.class);
        manager.createNotificationChannel(channel);

        FirebaseMessaging.getInstance().subscribeToTopic("weather")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String msg = "successfuly";
                        if (!task.isSuccessful()) {
                            msg = "Fail";
                        }
//                        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });

    }

    @Override
    public void onInternetConnectivityChanged(boolean b) {

    }

    private void popup() {

        OkHttpClient okHttpClient = new OkHttpClient();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("number", data.get(USERMOBILE).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(JSON, jsonObject.toString());
        Request request1 = new Request.Builder().url(Constants.POPUP_RATING).post(body).build();
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

                                productName = jsonHelper.GetResult("productName");
                                actual_prices = jsonHelper.GetResult("actual_price");
                                pimg = jsonHelper.GetResult("pimg");
                                order_id = jsonHelper.GetResult("orderid");

                            }

                            if (MainActivity.this != null) {
                                MainActivity.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {

                                        AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                                        View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.row_popup_layout, null);
                                        ImageView imageSet = view.findViewById(R.id.imageSet);
                                        TextView Name = view.findViewById(R.id.Name);
                                        TextView amt = view.findViewById(R.id.amt);
                                        RatingBar rating = view.findViewById(R.id.rating);
                                        ImageButton confirm_btn = view.findViewById(R.id.confirm_btn);

                                        Name.setText(productName);

                                        String a = Constants.IMAGES + pimg;
                                        Glide.with(MainActivity.this).load(a).into(imageSet);

                                        amt.setText(actual_prices);

                                        dialog.setCancelable(false);
                                        dialog.setView(view);

                                        AlertDialog alertDialog = dialog.create();
                                        alertDialog.show();

/*

                                        rating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                                            @Override
                                            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {

                                                String ratings = String.valueOf(v);

                                                switch (ratings){
//                                                    case "1.0":
//                                                    case "1.5":
//                                                    case "2.0":
//                                                    case "2.5":
//                                                    case "3.0":
//                                                        rating.setRating(3);
//                                                        break;
                                                    case "3.5":
                                                    case "4.0":
                                                    case "4.5":
                                                    case "5.0":
                                                        break;
                                                }

                                            }
                                        });
*/

                                        confirm_btn.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {

                                                updateRatingFriendDeal(String.valueOf(rating.getRating()));

                                                Uri uri = Uri.parse("market://details?id=" + getPackageName());
                                                Intent myAppLinkToMarket = new Intent(Intent.ACTION_VIEW, uri);
                                                try {
                                                    startActivity(myAppLinkToMarket);
                                                } catch (ActivityNotFoundException e) {
                                                    Toast.makeText(MainActivity.this, " unable to find market app", Toast.LENGTH_LONG).show();
                                                }

                                                alertDialog.dismiss();

                                            }
                                        });


                                    }
                                });

                            }

                        }
                    }
                }
                response.close();
            }
        });


    }

    private void updateRatingFriendDeal(String ratings) {


        OkHttpClient okHttpClient = new OkHttpClient();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("rating", ratings);
            jsonObject.put("order_id", order_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(JSON, jsonObject.toString());
        Request request1 = new Request.Builder().url(Constants.RATING_SET_FRIEND_DEAL).post(body).build();
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

                        }
                    }
                }
                response.close();
            }
        });


    }


    public void sendRegistrationToServer(String refreshedToken) {


        OkHttpClient okHttpClient = new OkHttpClient();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("token", refreshedToken);
            jsonObject.put("mobile", data.get(USERMOBILE).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(JSON, jsonObject.toString());
        Request request1 = new Request.Builder().url(Constants.TOKEN_SEND).post(body).build();
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
                }

            }
        });


    }


//    ******************************************************************************************

    public void startPaytmPayment(String token) {


    }


}

































