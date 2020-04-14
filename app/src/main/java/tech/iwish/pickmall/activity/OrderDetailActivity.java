package tech.iwish.pickmall.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import tech.iwish.pickmall.R;

public class OrderDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar mActionBar = getSupportActionBar();
        mActionBar.setDisplayShowHomeEnabled(true);
        mActionBar.setHomeButtonEnabled(true);
        mActionBar.setDisplayHomeAsUpEnabled(true);
        setTitle("Order Detail");
        setContentView(R.layout.activity_order_detail);
    }
}
