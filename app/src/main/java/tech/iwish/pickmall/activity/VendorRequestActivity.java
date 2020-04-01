package tech.iwish.pickmall.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import tech.iwish.pickmall.R;

public class VendorRequestActivity extends AppCompatActivity {
    LinearLayout business_detail,product_detail,Product_detail1;
    Button business_next,product_prev,product_next,product1_prev,product1_next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_request);
        InitializeActivity();
        ActivityAction();
    }


    public void InitializeActivity(){

        business_detail= findViewById(R.id.business_detail);
        product_detail= findViewById(R.id.product_detail);
        Product_detail1= findViewById(R.id.product_detail1);
        business_next= findViewById(R.id.business_next);
        product_prev= findViewById(R.id.product_prev);
        product_next= findViewById(R.id.product_next);
        product1_prev= findViewById(R.id.product1_prev);
        product1_next= findViewById(R.id.product1_next);


    }

    public void ActivityAction(){
        business_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                business_detail.setVisibility(View.GONE);
                product_detail.setVisibility(View.VISIBLE);
            }
        });
        product_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                product_detail.setVisibility(View.GONE);
                Product_detail1.setVisibility(View.VISIBLE);
            }
        });

        product_prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                business_detail.setVisibility(View.VISIBLE);
                product_detail.setVisibility(View.GONE);
            }
        });
        product1_prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                product_detail.setVisibility(View.VISIBLE);
                Product_detail1.setVisibility(View.GONE);
            }
        });
    }
}

