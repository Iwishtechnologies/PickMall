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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import tech.iwish.pickmall.Interface.RefreshCartAmountInterface;
import tech.iwish.pickmall.R;
import tech.iwish.pickmall.adapter.CartAdapter;
import tech.iwish.pickmall.sqlconnection.MyhelperSql;

public class CardActivity extends AppCompatActivity implements View.OnClickListener, RefreshCartAmountInterface {

    private ImageView homeBottom, feedBottom, cardBottom, accountBottom, cardImage;
    private MyhelperSql myhelperSql;
    private SQLiteDatabase sqLiteDatabase;
    private RecyclerView card_recycle_view;
    private ArrayList<HashMap<String, String>> data;
    private TextView edit_amount;
    private LinearLayout price_layout;
    private int TotalAMT;
    private int final_total_amount ,check;
    private String valuecheck;


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
        price_layout = (LinearLayout) findViewById(R.id.price_layout);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        card_recycle_view.setLayoutManager(linearLayoutManager);


        String query = "Select *  from PRODUCT_CARD";
        sqLiteDatabase = myhelperSql.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(query, new String[]{});
        ArrayList<HashMap<String, String>> list = new ArrayList<>();
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
                list.add(map);
                cursor.moveToNext();
            }
            CartAdapter cartAdapter = new CartAdapter(this, list, this);
            card_recycle_view.setAdapter(cartAdapter);
            amountset();

        } else {
            price_layout.setVisibility(View.GONE);
        }
        homeBottom.setOnClickListener(this);
        feedBottom.setOnClickListener(this);
        cardBottom.setOnClickListener(this);
        accountBottom.setOnClickListener(this);

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
                int tot = amount * qty;
                this.TotalAMT = tot;
                this.final_total_amount = TotalAMT + tot;

                cursor.moveToNext();
            }
//            edit_amount.setText(getResources().getString(R.string.rs_symbol) + check);
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
                Toast.makeText(this, "feed", Toast.LENGTH_SHORT).show();
                break;
            case R.id.CardBottom:
                break;
            case R.id.AccountBottom:
                Toast.makeText(this, "account", Toast.LENGTH_SHORT).show();
                break;

        }
    }

    @Override
    public void Amountrefreshcart() {
        amountset();
//        edit_amount.setText(getResources().getString(R.string.rs_symbol) + check);
//        Toast.makeText(this, "remove item refresh", Toast.LENGTH_SHORT).show();
    }
}
