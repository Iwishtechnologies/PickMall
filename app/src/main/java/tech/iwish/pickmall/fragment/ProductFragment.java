package tech.iwish.pickmall.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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
import tech.iwish.pickmall.activity.ProductActivity;
import tech.iwish.pickmall.adapter.ProductAdapter;
import tech.iwish.pickmall.config.Constants;
import tech.iwish.pickmall.connection.JsonHelper;
import tech.iwish.pickmall.other.ProductList;

public class ProductFragment extends Fragment {


    private RecyclerView product_recycleview;
    private List<ProductList> productListList = new ArrayList<>();
    private String item, type;
    public static String PRODUCT_PERAMETER;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product, null);

        product_recycleview = (RecyclerView) view.findViewById(R.id.product_recycleview);

        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        product_recycleview.setLayoutManager(layoutManager);

        datafetchProduct();

        return view;
    }


    private void datafetchProduct() {

        Bundle arguments = getArguments();
        if (arguments != null) {
            type = arguments.getString("type");
        }else {
            type = getActivity().getIntent().getExtras().getString("type");
        }
        Log.e("type", type);
        switch (type) {
            case "similar_product":
                item = arguments.getString("product_id");
                Log.e("item" , item);
                PRODUCT_PERAMETER = "similar_product";
                break;
            case "product":
                PRODUCT_PERAMETER = "product";
                item = getActivity().getIntent().getExtras().getString("item");
                break;
            case "vendorStoreAllProduct":
                PRODUCT_PERAMETER = "vendor_store_all_product";
                item = getActivity().getIntent().getExtras().getString("vendor_id");
                break;
        }


        OkHttpClient okHttpClient = new OkHttpClient();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("item_id", item);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(JSON, jsonObject.toString());
        Request request1 = new Request.Builder().url("http://173.212.226.143:8086/api/"+PRODUCT_PERAMETER).post(body).build();
        okHttpClient.newCall(request1).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    String result = response.body().string();
                    Log.e("output", result);
                    JsonHelper jsonHelper = new JsonHelper(result);
                    if (jsonHelper.isValidJson()) {
                        productListList.clear();
                        String responses = jsonHelper.GetResult("response");
                        if (responses.equals("TRUE")) {
                            JSONArray jsonArray = jsonHelper.setChildjsonArray(jsonHelper.getCurrentJsonObj(), "data");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                jsonHelper.setChildjsonObj(jsonArray, i);
//
                                productListList.add(new ProductList(jsonHelper.GetResult("product_id"), jsonHelper.GetResult("ProductName"), jsonHelper.GetResult("item_id"), jsonHelper.GetResult("catagory_id"), jsonHelper.GetResult("actual_price"), jsonHelper.GetResult("discount_price"), jsonHelper.GetResult("discount_price_per"), jsonHelper.GetResult("status"), jsonHelper.GetResult("pimg"), jsonHelper.GetResult("vendor_id"), jsonHelper.GetResult("FakeRating")));

                            }

                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    ProductAdapter productAdapter = new ProductAdapter(getActivity(), productListList);
                                    product_recycleview.setAdapter(productAdapter);
                                    productAdapter.notifyDataSetChanged();

                                }
                            });

                        }
                    }
                }
            }
        });
    }
}























