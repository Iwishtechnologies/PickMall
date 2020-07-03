package tech.iwish.pickmall.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import tech.iwish.pickmall.Interface.CardQtyAmountRef;
import tech.iwish.pickmall.Interface.RefreshCartAmountInterface;
import tech.iwish.pickmall.R;
import tech.iwish.pickmall.adapter.CartAdapter;
import tech.iwish.pickmall.adapter.FlashSaleAdapter;
import tech.iwish.pickmall.adapter.ShippingAddressAdapter;
import tech.iwish.pickmall.config.Constants;
import tech.iwish.pickmall.connection.JsonHelper;
import tech.iwish.pickmall.other.AddressDataList;
import tech.iwish.pickmall.other.CardCount;
import tech.iwish.pickmall.other.FlashsalemainList;
import tech.iwish.pickmall.session.Share_session;
import tech.iwish.pickmall.sqlconnection.MyhelperSql;

import static tech.iwish.pickmall.session.Share_session.CITY_ADDRESS;
import static tech.iwish.pickmall.session.Share_session.HOUSE_NO_ADDRESS;
import static tech.iwish.pickmall.session.Share_session.LANDMARK_ADDRESS;
import static tech.iwish.pickmall.session.Share_session.LOCATION_ADDRESS;
import static tech.iwish.pickmall.session.Share_session.NAME_ADDRESS;
import static tech.iwish.pickmall.session.Share_session.NUMBER_ADDRESS;
import static tech.iwish.pickmall.session.Share_session.PINCODE_ADDRESS;
import static tech.iwish.pickmall.session.Share_session.STATE_ADDRESS;
import static tech.iwish.pickmall.session.Share_session.USERMOBILE;

public class CardActivity extends AppCompatActivity implements View.OnClickListener, RefreshCartAmountInterface, CardQtyAmountRef {

    private ImageView homeBottom, feedBottom, cardBottom, accountBottom, cardImage,CardBottom;
    private MyhelperSql myhelperSql;
    private SQLiteDatabase sqLiteDatabase;
    private RecyclerView card_recycle_view;
    private ArrayList<HashMap<String, String>> data;
    private TextView edit_amount, pincode, name_address, full_address, product_count_card, pricr, total_amount_tax;
    private LinearLayout price_layout, product_count_card_layout, price_details_layout, address_layout, change_address,prepaid_layout,empty_layout;
    private int TotalAMT;
    private int final_total_amount, check;
    private String valuecheck;
    private Button place_order_btn;
    private Share_session shareSession;
    private Map sharedata;
    private ArrayList<HashMap<String, String>> list = new ArrayList<>();
    private int finalAmount, amtvalue;
    boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);

        myhelperSql = new MyhelperSql(this);

        getSupportActionBar().setTitle("Card");

        homeBottom = (ImageView) findViewById(R.id.HomeBottom);
        feedBottom = (ImageView) findViewById(R.id.FeedBottom);
        cardBottom = (ImageView) findViewById(R.id.CardBottom);
        accountBottom = (ImageView) findViewById(R.id.accountBottom);
        cardImage = (ImageView) findViewById(R.id.cardImage);
        CardBottom = (ImageView) findViewById(R.id.CardBottom);
        card_recycle_view = (RecyclerView) findViewById(R.id.card_recycle_view);

        edit_amount = (TextView) findViewById(R.id.edit_amount);
//        address set
        pincode = (TextView) findViewById(R.id.pincode);
        name_address = (TextView) findViewById(R.id.name_address);
        full_address = (TextView) findViewById(R.id.full_address);
        product_count_card = (TextView) findViewById(R.id.product_count_card);
        pricr = (TextView) findViewById(R.id.pricr);
        total_amount_tax = (TextView) findViewById(R.id.total_amount_tax);


        price_layout = (LinearLayout) findViewById(R.id.price_layout);
        product_count_card_layout = (LinearLayout) findViewById(R.id.product_count_card_layout);
        price_details_layout = (LinearLayout) findViewById(R.id.price_details_layout);
        address_layout = (LinearLayout) findViewById(R.id.address_layout);
        change_address = (LinearLayout) findViewById(R.id.change_address);
        prepaid_layout = (LinearLayout) findViewById(R.id.prepaid_layout);
        empty_layout = (LinearLayout) findViewById(R.id.empty_layout);

        place_order_btn = (Button) findViewById(R.id.place_order_btn);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        card_recycle_view.setLayoutManager(linearLayoutManager);


        shareSession = new Share_session(this);
        Map data = shareSession.Fetchdata();
        if ((data.get(NUMBER_ADDRESS) != null) && (data.get(PINCODE_ADDRESS) != null) && (data.get(HOUSE_NO_ADDRESS) != null)) {
            pincode.setText(data.get(PINCODE_ADDRESS).toString());
            name_address.setText(data.get(NAME_ADDRESS).toString());
            full_address.setText(data.get(HOUSE_NO_ADDRESS).toString() + " " +
                    data.get(LANDMARK_ADDRESS).toString() + " " +
                    data.get(LOCATION_ADDRESS).toString() + " " +
                    data.get(CITY_ADDRESS).toString() + " " +
                    data.get(STATE_ADDRESS).toString());
        }

        CardBottom.setImageDrawable(getResources().getDrawable(R.drawable.red_card_icon));

        card_product();


        homeBottom.setOnClickListener(this);
        feedBottom.setOnClickListener(this);
        cardBottom.setOnClickListener(this);
        accountBottom.setOnClickListener(this);
        place_order_btn.setOnClickListener(this);
        change_address.setOnClickListener(this);
        prepaid_layout.setOnClickListener(this);
        empty_layout.setOnClickListener(this);


        if (!CardCount.card_count(this).equals("0")) {
            String number_of_product = CardCount.card_count(this);
            product_count_card.setText(number_of_product);
            product_count_card_layout.setVisibility(View.VISIBLE);
//            price_details_layout.setVisibility(View.VISIBLE);
            address_layout.setVisibility(View.VISIBLE);
            cardImage.setVisibility(View.GONE);
            empty_layout.setVisibility(View.GONE);
        } else {
            product_count_card_layout.setVisibility(View.GONE);
            price_details_layout.setVisibility(View.GONE);
            address_layout.setVisibility(View.GONE);
            cardImage.setVisibility(View.VISIBLE);
            empty_layout.setVisibility(View.VISIBLE);
        }

    }

    private void card_product() {

        String query = "Select * from PRODUCT_CARD";
        sqLiteDatabase = myhelperSql.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(query, new String[]{});
        cursor.moveToFirst();
        if (cursor != null && cursor.moveToFirst()) {
            for (int i = 0; i < cursor.getCount(); i++) {
                HashMap<String, String> map = new HashMap<>();
                map.put("ID", cursor.getString(cursor.getColumnIndex("_id")));
                map.put("PRODUCT_ID", cursor.getString(cursor.getColumnIndex("PRODUCT_ID")));
                map.put("PRODUCT_NAME", cursor.getString(cursor.getColumnIndex("PRODUCT_NAME")));
                map.put("PRODUCT_QTY", cursor.getString(cursor.getColumnIndex("PRODUCT_QTY")));
                map.put("PRODUCT_COLOR", cursor.getString(cursor.getColumnIndex("PRODUCT_COLOR")));
                map.put("PRODUCT_SIZE", cursor.getString(cursor.getColumnIndex("PRODUCT_SIZE")));
                map.put("PRODUCT_IMAGE", cursor.getString(cursor.getColumnIndex("PRODUCT_IMAGE")));
                map.put("PRODUCT_AMOUNT", cursor.getString(cursor.getColumnIndex("PRODUCT_AMOUNT")));
                map.put("PRODUCT_AMOUNT_DICOUNT", cursor.getString(cursor.getColumnIndex("PRODUCT_AMOUNT_DICOUNT")));
                map.put("PRODUCT_TYPE", cursor.getString(cursor.getColumnIndex("PRODUCT_TYPE")));
                map.put("GST", cursor.getString(cursor.getColumnIndex("GST")));
                map.put("VENDOR_ID", cursor.getString(cursor.getColumnIndex("VENDOR_ID")));
                map.put("PRODUCT_DICOUNT_PERCEN", cursor.getString(cursor.getColumnIndex("PRODUCT_DICOUNT_PERCEN")));


                int qty = Integer.parseInt(cursor.getString(cursor.getColumnIndex("PRODUCT_QTY")));
                int amount = Integer.parseInt(cursor.getString(cursor.getColumnIndex("PRODUCT_AMOUNT")));
                int amt = qty * amount;
                finalAmount += amt;
                list.add(map);
                cursor.moveToNext();
            }
            CartAdapter cartAdapter = new CartAdapter(this, list, this, this);
            card_recycle_view.setAdapter(cartAdapter);
            edit_amount.setText(getResources().getString(R.string.rs_symbol) + String.valueOf(finalAmount));
            total_amount_tax.setText(getResources().getString(R.string.rs_symbol) + String.valueOf(finalAmount));
            pricr.setText(getResources().getString(R.string.rs_symbol) + String.valueOf(finalAmount));
        } else {
            price_layout.setVisibility(View.GONE);
        }

    }

    private void amountset() {
        String query = "Select * from PRODUCT_CARD";
        sqLiteDatabase = myhelperSql.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(query, new String[]{});
        cursor.moveToFirst();
        if (cursor != null && cursor.moveToFirst()) {
            for (int i = 0; i < cursor.getCount(); i++) {
                int amount = Integer.parseInt(cursor.getString(cursor.getColumnIndex("PRODUCT_AMOUNT")));
                int qty = Integer.parseInt(cursor.getString(cursor.getColumnIndex("PRODUCT_QTY")));
                int amt = amount * qty;
                amtvalue += amt;
                cursor.moveToNext();
            }
            edit_amount.setText(getResources().getString(R.string.rs_symbol) + String.valueOf(amtvalue));
            total_amount_tax.setText(getResources().getString(R.string.rs_symbol) + String.valueOf(amtvalue));
            pricr.setText(getResources().getString(R.string.rs_symbol) + String.valueOf(amtvalue));
        } else {
            price_layout.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View view) {

        int id = view.getId();
        switch (id) {

            case R.id.HomeBottom:
                Intent intent = new Intent(CardActivity.this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.FeedBottom:
                startActivity(new Intent(CardActivity.this, AllcategoryActivity.class));
                break;
            case R.id.CardBottom:
                break;
            case R.id.accountBottom:
                shareSession = new Share_session(this);
                sharedata = shareSession.Fetchdata();
                if(sharedata.get(USERMOBILE) != null){
                    intent = new Intent(CardActivity.this, Account.class);
                }else {
                    intent = new Intent(CardActivity.this, Register1Activity.class);
                }
                startActivity(intent);
                break;
            case R.id.place_order_btn:
                PlaceOrder();
                break;
            case R.id.change_address:
                Intent intent1 = new Intent(CardActivity.this, EditAddressActivity.class);
                intent1.putExtra("context", "CardActivity");
                intent1.putExtra("type", "CardActivity");
                startActivity(intent1);
                break;
            case R.id.prepaid_layout:
                Intent intent2 = new Intent(CardActivity.this,ProductActivity.class);
                intent2.putExtra("type","prepaid");
                intent2.putExtra("itemName","Prepaid");
                startActivity(intent2);
                break;
            case R.id.empty_layout:
                startActivity(new Intent(CardActivity.this,MainActivity.class));
                break;
        }
    }

    private void PlaceOrder() {

        shareSession = new Share_session(this);
        startActivity(new Intent(CardActivity.this, AddressActivity.class));
        sharedata = shareSession.Fetchdata();
        if (sharedata.get(NUMBER_ADDRESS) != null) {
            Intent intent = new Intent(CardActivity.this, SaveAddressActivity.class);
            intent.putExtra("name_address", name_address.getText().toString());
            intent.putExtra("full_address", full_address.getText().toString());
            intent.putExtra("poncode_address", pincode.getText().toString());
            intent.putExtra("mobile_address", sharedata.get(NUMBER_ADDRESS).toString());
            intent.putExtra("city_address", sharedata.get(CITY_ADDRESS).toString());
            intent.putExtra("total_amount_product", edit_amount.getText().toString().trim());
            intent.putExtra("type", "CardActivity");

            startActivity(intent);
        } else {
            Intent intent = new Intent(CardActivity.this, AddressActivity.class);
            intent.putExtra("type", "CardActivity");
            startActivity(intent);
        }
    }

    @Override
    public void Amountrefreshcart() {
        amtvalue = 0;
        amountset();
        if (!CardCount.card_count(this).equals("0")) {
            String number_of_product = CardCount.card_count(this);
            product_count_card.setText(number_of_product);
            product_count_card_layout.setVisibility(View.VISIBLE);
//            price_details_layout.setVisibility(View.VISIBLE);
            address_layout.setVisibility(View.VISIBLE);
            cardImage.setVisibility(View.GONE);
            empty_layout.setVisibility(View.GONE);
        } else {
            product_count_card_layout.setVisibility(View.GONE);
            price_details_layout.setVisibility(View.GONE);
            address_layout.setVisibility(View.GONE);
            cardImage.setVisibility(View.VISIBLE);
            empty_layout.setVisibility(View.VISIBLE);
        }
//        edit_amount.setText(getResources().getString(R.string.rs_symbol) + check);
//        Toast.makeText(this, "remove item refresh", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void cardqtyAmountref() {
        amtvalue = 0;
        amountset();
    }

    @Override
    public void onBackPressed() {


        if (getIntent().getStringExtra("context") == null) {
            super.onBackPressed();
        } else {

            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();


                finish();
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addCategory(Intent.CATEGORY_HOME);
                startActivity(intent);

                return;
            }

            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Click again to exit", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);

        }
    }

    protected void ProductQtyChehck(String productId, String color_size_sno) {

        OkHttpClient client = new OkHttpClient();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("productId", productId);
            jsonObject.put("color_size_sno", color_size_sno);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(JSON, jsonObject.toString());
        Request request = new Request.Builder().post(body)
                .url(Constants.ADDRESS_FETCH)
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

                            }
                            if (CardActivity.this != null) {
                                CardActivity.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {

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