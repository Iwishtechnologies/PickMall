package tech.iwish.pickmall.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Map;

import tech.iwish.pickmall.Interface.FiltersInterface;
import tech.iwish.pickmall.R;
import tech.iwish.pickmall.adapter.FilterSideAdapter;
import tech.iwish.pickmall.fragment.FiltersLoadFragment;
import tech.iwish.pickmall.session.Share_session;

import static tech.iwish.pickmall.OkhttpConnection.ProductListF.FilterSideList;

public class FilterActivity extends AppCompatActivity implements FiltersInterface, View.OnClickListener {

    private RecyclerView filter_side_recycleview;
    Share_session shareSession;
    private TextView applybtn ,close;
    private Map data;
    private ProgressBar progressbar;
    private LinearLayout clearAll;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);




        filter_side_recycleview = (RecyclerView) findViewById(R.id.filter_side_recycleview);
        applybtn = (TextView) findViewById(R.id.applybtn);
        close = (TextView) findViewById(R.id.close);
        progressbar = (ProgressBar)findViewById(R.id.progressbar);
        clearAll = (LinearLayout)findViewById(R.id.clearAll);

        progressbar.setVisibility(View.GONE);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        filter_side_recycleview.setLayoutManager(linearLayoutManager);

        FilterSideAdapter filterSideAdapter = new FilterSideAdapter(this, FilterSideList(), this);
        filter_side_recycleview.setAdapter(filterSideAdapter);
        shareSession = new Share_session(this);
        applybtn.setOnClickListener(this);
        clearAll.setOnClickListener(this);
        close.setOnClickListener(this);
        shareSession.filterSizeRemoveMethod();
        shareSession.filterColorRemoveMethod();

    }

    @Override
    public void filterid(String value) {

        FiltersLoadFragment filtersLoadFragment = new FiltersLoadFragment(progressbar);
        Bundle bundle = new Bundle();
        filtersLoadFragment.setArguments(bundle);
        bundle.putString("value", value);
        bundle.putString("item_id", getIntent().getStringExtra("item_id"));
        bundle.putString("item_name", getIntent().getStringExtra("item_name"));
        getSupportFragmentManager().beginTransaction().replace(R.id.filter_framelayout, filtersLoadFragment).commit();

    }


    @Override
    public void onClick(View view) {

        data = shareSession.Fetchdata();
        int id = view.getId();
        switch (id) {
            case R.id.applybtn:

//                Log.e("onClick: ", data.get(FILTER_LIST).toString());
//                Log.e("onClick: ", data.get(FILTER_LIST_COLOR).toString());
                Intent intent = new Intent(FilterActivity.this, ProductActivity.class);
                intent.putExtra("type", "FilterActivity");
                intent.putExtra("item_id", getIntent().getStringExtra("item_id"));
                intent.putExtra("itemName", getIntent().getStringExtra("item_name"));
                startActivity(intent);
                break;
            case R.id.clearAll:
                shareSession.filterSizeRemoveMethod();
                shareSession.filterColorRemoveMethod();
                Intent intent1 = new Intent(FilterActivity.this , FilterActivity.class);
                intent1.putExtra("item_id",getIntent().getStringExtra("item_id"));
                intent1.putExtra("item_name",getIntent().getStringExtra("item_name"));
                startActivity(intent1);
                break;
            case R.id.close:
                Intent intent2 = new Intent(FilterActivity.this, ProductActivity.class);
                intent2.putExtra("type", "MainActivity_product");
                intent2.putExtra("item_id", getIntent().getStringExtra("item_id"));
                intent2.putExtra("item_name", getIntent().getStringExtra("item_name"));
                startActivity(intent2);
                break;
        }


    }
}

















