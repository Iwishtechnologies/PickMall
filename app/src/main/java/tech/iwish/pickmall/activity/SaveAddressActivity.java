
package tech.iwish.pickmall.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.StrikethroughSpan;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import tech.iwish.pickmall.Interface.CardQtyAmountRef;
import tech.iwish.pickmall.Interface.RefreshCartAmountInterface;
import tech.iwish.pickmall.R;
import tech.iwish.pickmall.adapter.CartAdapter;
import tech.iwish.pickmall.config.Constants;
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

public class SaveAddressActivity extends AppCompatActivity implements RefreshCartAmountInterface, View.OnClickListener, CardQtyAmountRef {

    private TextView name_a, full_address, city_pincode_address, number_address, change_address , amount_set;
    private RecyclerView card_product_recycleview;
    private MyhelperSql myhelperSql;
    private SQLiteDatabase sqLiteDatabase;
    private ArrayList<HashMap<String, String>> list = new ArrayList<>();
    private Share_session shareSession;
    private LinearLayout coupon, paymentoption, buy_now_product;
    private TextView cart_product_name, cart_product_act_amount, cart_product_size, product_qty, dicount_price;
    private ImageView card_product_image;

    private String B_N_product_name , B_N_product_qty , B_N_product_size , B_N_product_acture_price , B_N_product_image;
    private int finalAmount ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_save_address);

        name_a = (TextView) findViewById(R.id.name_a);
        full_address = (TextView) findViewById(R.id.full_address);
        city_pincode_address = (TextView) findViewById(R.id.city_pincode_address);
        number_address = (TextView) findViewById(R.id.number_address);
        change_address = (TextView) findViewById(R.id.change_address);
        amount_set = (TextView) findViewById(R.id.amount_set);

        card_product_recycleview = (RecyclerView) findViewById(R.id.card_product_recycleview);

        coupon = (LinearLayout) findViewById(R.id.coupon);
        paymentoption = (LinearLayout) findViewById(R.id.paymentoption);
        buy_now_product = (LinearLayout) findViewById(R.id.buy_now_product);

        shareSession = new Share_session(this);
        Map data = shareSession.Fetchdata();

        name_a.setText(data.get(NAME_ADDRESS).toString());
        full_address.setText(data.get(HOUSE_NO_ADDRESS).toString() + " " +
                        data.get(LANDMARK_ADDRESS).toString() + " " +
                        data.get(LOCATION_ADDRESS).toString()
//                + " " + data.get(STATE_ADDRESS).toString()
        );
        city_pincode_address.setText(data.get(CITY_ADDRESS).toString() + " " + data.get(PINCODE_ADDRESS).toString());
        number_address.setText(data.get(NUMBER_ADDRESS).toString());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        card_product_recycleview.setLayoutManager(linearLayoutManager);

        if (getIntent().getStringExtra("type") != null) {

            buy_now_product.setVisibility(View.VISIBLE);

            cart_product_name = (TextView) findViewById(R.id.cart_product_name);
            cart_product_size = (TextView) findViewById(R.id.cart_product_size);
            cart_product_act_amount = (TextView) findViewById(R.id.cart_product_act_amount);
            dicount_price = (TextView) findViewById(R.id.dicount_price);
            card_product_image = (ImageView) findViewById(R.id.card_product_image);
            product_qty = (TextView) findViewById(R.id.product_qty);

            B_N_product_name = getIntent().getStringExtra("product_name");
            B_N_product_acture_price = getIntent().getStringExtra("actual_price");
            B_N_product_qty = getIntent().getStringExtra("product_qty");
            B_N_product_size =  getIntent().getStringExtra("select_size");
            B_N_product_image = getIntent().getStringExtra("imagename");

            cart_product_name.setText(B_N_product_name);
            cart_product_size.setText("Size : " +B_N_product_size);
            cart_product_act_amount.setText(getResources().getString(R.string.rs_symbol) + B_N_product_acture_price);
            product_qty.setText(B_N_product_qty);


            SpannableString content = new SpannableString(getResources().getString(R.string.rs_symbol) + getIntent().getStringExtra("discount_price"));
            content.setSpan(new StrikethroughSpan(), 0, content.length(), 0);
            dicount_price.setText(content);

            String a = Constants.IMAGES + getIntent().getStringExtra("imagename");
            Glide.with(this).load(a).into(card_product_image);

            card_product_recycleview.setVisibility(View.GONE);
        } else {
            buy_now_product.setVisibility(View.GONE);
            card_product_recycleview.setVisibility(View.VISIBLE);
            productshow();

        }

        change_address.setOnClickListener(this);
        coupon.setOnClickListener(this);
        paymentoption.setOnClickListener(this);
        amount_set.setText(getResources().getString(R.string.rs_symbol) + finalAmount);
    }

    private void productshow() {
        myhelperSql = new MyhelperSql(this);
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
            CartAdapter cartAdapter = new CartAdapter(this, list, this, this);
            card_product_recycleview.setAdapter(cartAdapter);
        }

    }

    @Override
    public void Amountrefreshcart() {

    }

    @Override
    public void onClick(View view) {


        int id = view.getId();
        switch (id) {
            case R.id.change_address:
                if (getIntent().getStringExtra("type") != null) {
                    Intent intent = new Intent(SaveAddressActivity.this, EditAddressActivity.class);
                    intent.putExtra("product_name", getIntent().getStringExtra("product_name"));
                    intent.putExtra("select_size", getIntent().getStringExtra("select_size"));
                    intent.putExtra("actual_price", getIntent().getStringExtra("actual_price"));
                    intent.putExtra("discount_price", getIntent().getStringExtra("discount_price"));
                    intent.putExtra("imagename", getIntent().getStringExtra("imagename"));
                    intent.putExtra("product_qty", getIntent().getStringExtra("product_qty"));
                    intent.putExtra("type", "buy_now");
                    startActivity(intent);
                } else {
                    startActivity(new Intent(SaveAddressActivity.this, EditAddressActivity.class));
                }
                break;
            case R.id.coupon:
            case R.id.paymentoption:
                Toast.makeText(this, "not ready", Toast.LENGTH_SHORT).show();
                break;

        }
    }

    @Override
    public void cardqtyAmountref() {

    }
}






















