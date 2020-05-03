package tech.iwish.pickmall.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tech.iwish.pickmall.Interface.CardQtyAmountRef;
import tech.iwish.pickmall.Interface.RefreshCartAmountInterface;
import tech.iwish.pickmall.R;
import tech.iwish.pickmall.adapter.CartAdapter;
import tech.iwish.pickmall.other.CardCount;
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

public class CardActivity extends AppCompatActivity implements View.OnClickListener, RefreshCartAmountInterface , CardQtyAmountRef {

    private ImageView homeBottom, feedBottom, cardBottom, accountBottom, cardImage;
    private MyhelperSql myhelperSql;
    private SQLiteDatabase sqLiteDatabase;
    private RecyclerView card_recycle_view;
    private ArrayList<HashMap<String, String>> data;
    private TextView edit_amount, pincode, name_address, full_address, product_count_card ,pricr;
    private LinearLayout price_layout, product_count_card_layout, price_details_layout, address_layout , change_address;
    private int TotalAMT;
    private int final_total_amount, check;
    private String valuecheck;
    private Button place_order_btn;
    private Share_session shareSession;
    private Map sharedata;
    private ArrayList<HashMap<String, String>> list = new ArrayList<>();
    private int finalAmount , amtvalue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);

        myhelperSql = new MyhelperSql(this);

        homeBottom = (ImageView) findViewById(R.id.HomeBottom);
        feedBottom = (ImageView) findViewById(R.id.FeedBottom);
        cardBottom = (ImageView) findViewById(R.id.CardBottom);
        accountBottom = (ImageView) findViewById(R.id.AccountBottom);
        cardImage = (ImageView) findViewById(R.id.cardImage);
        card_recycle_view = (RecyclerView) findViewById(R.id.card_recycle_view);

        edit_amount = (TextView) findViewById(R.id.edit_amount);
//        address set
        pincode = (TextView) findViewById(R.id.pincode);
        name_address = (TextView) findViewById(R.id.name_address);
        full_address = (TextView) findViewById(R.id.full_address);
        product_count_card = (TextView) findViewById(R.id.product_count_card);
        pricr = (TextView) findViewById(R.id.pricr);


        price_layout = (LinearLayout) findViewById(R.id.price_layout);
        product_count_card_layout = (LinearLayout) findViewById(R.id.product_count_card_layout);
        price_details_layout = (LinearLayout) findViewById(R.id.price_details_layout);
        address_layout = (LinearLayout) findViewById(R.id.address_layout);
        change_address = (LinearLayout) findViewById(R.id.change_address);

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

        card_product();



        homeBottom.setOnClickListener(this);
        feedBottom.setOnClickListener(this);
        cardBottom.setOnClickListener(this);
        accountBottom.setOnClickListener(this);
        place_order_btn.setOnClickListener(this);
        change_address.setOnClickListener(this);


        if (!CardCount.card_count(this).equals("0")) {
            String number_of_product = CardCount.card_count(this);
            product_count_card.setText(number_of_product);
            product_count_card_layout.setVisibility(View.VISIBLE);
            price_details_layout.setVisibility(View.VISIBLE);
            address_layout.setVisibility(View.VISIBLE);
            cardImage.setVisibility(View.GONE);
        } else {
            product_count_card_layout.setVisibility(View.GONE);
            price_details_layout.setVisibility(View.GONE);
            address_layout.setVisibility(View.GONE);
            cardImage.setVisibility(View.VISIBLE);
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

                int qty = Integer.parseInt(cursor.getString(cursor.getColumnIndex("PRODUCT_QTY")));
                int amount = Integer.parseInt(cursor.getString(cursor.getColumnIndex("PRODUCT_AMOUNT")));
                int amt = qty * amount;
                finalAmount += amt;
                list.add(map);
                cursor.moveToNext();
            }
            CartAdapter cartAdapter = new CartAdapter(this, list, this , this);
            card_recycle_view.setAdapter(cartAdapter);
            edit_amount.setText(getResources().getString(R.string.rs_symbol)+String.valueOf(finalAmount));
            pricr.setText(getResources().getString(R.string.rs_symbol)+String.valueOf(finalAmount));
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
            edit_amount.setText(getResources().getString(R.string.rs_symbol)+String.valueOf(amtvalue));
            pricr.setText(getResources().getString(R.string.rs_symbol)+String.valueOf(amtvalue));
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
            case R.id.AccountBottom:
                startActivity(new Intent(CardActivity.this, Account.class));
                break;
            case R.id.place_order_btn:
                PlaceOrder();
                break;
            case R.id.change_address:
                startActivity(new Intent(CardActivity.this , EditAddressActivity.class));
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
            startActivity(intent);
        } else {
            startActivity(new Intent(CardActivity.this, AddressActivity.class));
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
            price_details_layout.setVisibility(View.VISIBLE);
            address_layout.setVisibility(View.VISIBLE);
            cardImage.setVisibility(View.GONE);
        } else {
            product_count_card_layout.setVisibility(View.GONE);
            price_details_layout.setVisibility(View.GONE);
            address_layout.setVisibility(View.GONE);
            cardImage.setVisibility(View.VISIBLE);
        }
//        edit_amount.setText(getResources().getString(R.string.rs_symbol) + check);
//        Toast.makeText(this, "remove item refresh", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void cardqtyAmountref() {
        amtvalue = 0;
        amountset();
    }
}
