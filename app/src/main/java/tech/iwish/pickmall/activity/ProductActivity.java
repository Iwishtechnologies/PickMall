package tech.iwish.pickmall.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RatingBar;

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
import tech.iwish.pickmall.R;
import tech.iwish.pickmall.adapter.ProductAdapter;
import tech.iwish.pickmall.config.Constants;
import tech.iwish.pickmall.connection.JsonHelper;
import tech.iwish.pickmall.other.ProductList;
import tech.iwish.pickmall.session.Share_session;

public class ProductActivity extends AppCompatActivity {

    private RecyclerView product_recycleview;
    private List<ProductList> productListList = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        product_recycleview  = (RecyclerView)findViewById(R.id.product_recycleview);

        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        product_recycleview.setLayoutManager(layoutManager);

        datafetchProduct();

    }


    private void datafetchProduct(){

        Intent intent = getIntent();
        String item = intent.getStringExtra("item");

        OkHttpClient okHttpClient = new OkHttpClient();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("item_id",item);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(JSON, jsonObject.toString());
        Request request1 = new Request.Builder().url(Constants.PRODUCT).post(body).build();
        okHttpClient.newCall(request1).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if(response.isSuccessful()){
                    String result = response.body().string();
                    Log.e("output", result);
                    JsonHelper jsonHelper = new JsonHelper(result);
                    if (jsonHelper.isValidJson()) {
                        String responses = jsonHelper.GetResult("response");
                        if (responses.equals("TRUE")) {
                            JSONArray jsonArray = jsonHelper.setChildjsonArray(jsonHelper.getCurrentJsonObj(), "data");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                jsonHelper.setChildjsonObj(jsonArray, i);
//
                                productListList.add(new ProductList(jsonHelper.GetResult("product_id"),jsonHelper.GetResult("ProductName"),jsonHelper.GetResult("item_id"),jsonHelper.GetResult("catagory_id"),jsonHelper.GetResult("actual_price"),jsonHelper.GetResult("discount_price"),jsonHelper.GetResult("discount_price_per"),jsonHelper.GetResult("status"),jsonHelper.GetResult("pimg"),jsonHelper.GetResult("vendor_id")));

                            }
                            ProductActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    ProductAdapter productAdapter = new ProductAdapter(ProductActivity.this , productListList);
                                    product_recycleview.setAdapter(productAdapter);
                                }
                            });

                        }
                    }
                }
            }
        });
    }

}
