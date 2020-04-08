package tech.iwish.pickmall.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;

import tech.iwish.pickmall.R;
import tech.iwish.pickmall.adapter.CartAdapter;
import tech.iwish.pickmall.adapter.UnpaidOrderAdapter;
import tech.iwish.pickmall.extended.TextViewFont;
import tech.iwish.pickmall.session.Share_session;

public class UnpaidOrderActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    Share_session share_session;
    TextViewFont noitem;
    ArrayList<HashMap<String, String>> list = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unpaid_order);
        InitializeActivity();
        ActivityAction();
        SetRecycleView();
    }


    private void InitializeActivity(){
       recyclerView=findViewById(R.id.recycle);
       noitem=findViewById(R.id.noitem);
       share_session = new Share_session(this);
    }


    private void ActivityAction(){

    }


    private void SetRecycleView(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        for (int i = 0; i < 10; i++) {
            HashMap<String, String> map = new HashMap<>();
            map.put("ID",String.valueOf(i));
            map.put("PRODUCT_ID", String.valueOf(i));
            map.put("PRODUCT_NAME","Shirt");
            map.put("PRODUCT_QTY",String.valueOf(i) );
            map.put("PRODUCT_COLOR","red");
            map.put("PRODUCT_SIZE", String.valueOf(i));
            map.put("PRODUCT_IMAGE","https://cdn.dribbble.com/users/2502853/screenshots/5122499/0.jpg");
            map.put("PRODUCT_AMOUNT","200");
            map.put("PRODUCT_SELLER_NAME","Balaji Garments Pvt Ltd");
            list.add(map);
        }
        CheckListCount();
        UnpaidOrderAdapter unpaidOrderAdapter = new UnpaidOrderAdapter(this, list);
        recyclerView.setAdapter(unpaidOrderAdapter);

    }

    private void CheckListCount(){
        Log.e("list",String.valueOf(list.size()));
        if(list.size()==0){
            noitem.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }
    }
}

