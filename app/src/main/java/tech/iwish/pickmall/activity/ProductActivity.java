package tech.iwish.pickmall.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
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
import tech.iwish.pickmall.Interface.CardProductRefreshInterface;
import tech.iwish.pickmall.R;
import tech.iwish.pickmall.adapter.ProductAdapter;
import tech.iwish.pickmall.config.Constants;
import tech.iwish.pickmall.connection.JsonHelper;
import tech.iwish.pickmall.fragment.ProductFragment;
import tech.iwish.pickmall.other.CardCount;
import tech.iwish.pickmall.other.ProductList;
import tech.iwish.pickmall.session.Share_session;

public class ProductActivity extends AppCompatActivity implements View.OnClickListener {


    public CardProductRefreshInterface cardProductRefre;
    private ImageView back;
    private LinearLayout search_product;
    private TextView itme_name, filter, shorts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        back = (ImageView) findViewById(R.id.back);
        search_product = (LinearLayout) findViewById(R.id.search_product);
        itme_name = (TextView) findViewById(R.id.itme_name);
        filter = (TextView) findViewById(R.id.filter);
        shorts = (TextView) findViewById(R.id.shorts);

        Intent intent = getIntent();
        String type = intent.getStringExtra("type");

        switch (type) {
            case "MainActivity_product":
                productloadfradment(getIntent().getStringExtra("item_id") , getIntent().getStringExtra("item_name") ,"product");
                break;
            case "Category_by_product":
                productloadfradment(getIntent().getStringExtra("category_id") , getIntent().getStringExtra("category_name"),"Category_by_product");
                break;

        }

        search_product.setOnClickListener(this);
        back.setOnClickListener(this);
        filter.setOnClickListener(this);
        shorts.setOnClickListener(this);


    }

    private void productloadfradment(String id , String name , String type) {


        Bundle bundle = new Bundle();
        ProductFragment productFragment = new ProductFragment();
        bundle.putString("item",id );
        bundle.putString("type", type);
        productFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.product_framelayout, productFragment).commit();
        itme_name.setText(name);


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }


    @Override
    public void onClick(View view) {
        int id = view.getId();

        switch (id) {
            case R.id.search_product:
                break;
            case R.id.back:
                onBackPressed();
                break;
            case R.id.shorts:

                break;
            case R.id.filter:
                Intent intent = new Intent(ProductActivity.this, FilterActivity.class);
                intent.putExtra("item_id", getIntent().getStringExtra("item_id"));
                intent.putExtra("item_name", getIntent().getStringExtra("item_name"));
                startActivity(intent);
                overridePendingTransition(R.anim.bottom_to_top, R.anim.slide_out_up);
                break;
        }
    }
}
















