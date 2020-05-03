package tech.iwish.pickmall.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import tech.iwish.pickmall.Interface.FiltersInterface;
import tech.iwish.pickmall.R;
import tech.iwish.pickmall.adapter.FilterSideAdapter;
import tech.iwish.pickmall.fragment.FiltersLoadFragment;

import static tech.iwish.pickmall.OkhttpConnection.ProductListF.FilterSideList;

public class FilterActivity extends AppCompatActivity implements FiltersInterface {

    private RecyclerView filter_side_recycleview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        filter_side_recycleview = (RecyclerView)findViewById(R.id.filter_side_recycleview);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        filter_side_recycleview.setLayoutManager(linearLayoutManager);

        FilterSideAdapter filterSideAdapter = new FilterSideAdapter(this , FilterSideList(),this);
        filter_side_recycleview.setAdapter(filterSideAdapter);



    }

    @Override
    public void filterid( String value) {

        FiltersLoadFragment filtersLoadFragment = new FiltersLoadFragment();
        Bundle bundle = new Bundle();
        filtersLoadFragment.setArguments(bundle);
        bundle.putString("value",value);
        bundle.putString("item_id",getIntent().getStringExtra("item_id"));
        bundle.putString("item_name",getIntent().getStringExtra("item_name"));
        getSupportFragmentManager().beginTransaction().replace(R.id.filter_framelayout,filtersLoadFragment).commit();

    }
}

















