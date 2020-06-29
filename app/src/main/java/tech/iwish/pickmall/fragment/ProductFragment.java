package tech.iwish.pickmall.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.bumptech.glide.load.model.Model;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

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

import static tech.iwish.pickmall.session.Share_session.FILTER_LIST;
import static tech.iwish.pickmall.session.Share_session.FILTER_LIST_COLOR;

public class ProductFragment extends Fragment {


    private RecyclerView product_recycleview;
    private List<ProductList> productListList = new ArrayList<>();
    private String item, type;
    public static String PRODUCT_PERAMETER;
    private Share_session shareSession;
    private Map data;


    public ProductFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product, null);

        product_recycleview = (RecyclerView) view.findViewById(R.id.product_recycleview);

        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        product_recycleview.setLayoutManager(layoutManager);


        Bundle arguments = getArguments();
        if (arguments != null) {
            type = arguments.getString("type");
        } else {
            type = getActivity().getIntent().getExtras().getString("type");
        }
        Log.e("type", type);
        switch (type) {
            case "similar_product":
//                item = arguments.getString("product_id");
//                PRODUCT_PERAMETER = "similar_product";
                datafetchProduct(Constants.SIMILAER_PRODUCT, arguments.getString("product_id"));
                break;
            case "product":
//                PRODUCT_PERAMETER = "product";
//                item = getActivity().getIntent().getExtras().getString("item");
//                item = arguments.getString("item");
                datafetchProduct(Constants.ALL_PRODUCT, arguments.getString("item"));
                break;
            case "vendorStoreAllProduct":
//                PRODUCT_PERAMETER = "vendor_store_all_product";
//                item = getActivity().getIntent().getExtras().getString("vendor_id");
                datafetchProduct(Constants.VENDOR_STORE_ALL_PRODUCT, getActivity().getIntent().getExtras().getString("vendor_id"));
                break;
            case "Category_by_product":
//                PRODUCT_PERAMETER = "Category_by_product";
//                item =  getActivity().getIntent().getExtras().getString("category_id");
                datafetchProduct(Constants.CATEGORY_BY_PRODUUCT, getActivity().getIntent().getExtras().getString("category_id"));
                break;
            case "hotproduct":
                hotproducts(Constants.HOT_PRODUCT);
                break;
            case "searchActivity":
                datafetchProduct(Constants.SEARCH_PRODUCT_BY_NAME, getActivity().getIntent().getStringExtra("name"));
                break;
            case "FilterActivity":
                FilterProduct(Constants.FILTER_PRODUCT, getActivity().getIntent().getStringExtra("itemId"));
                break;
            case "prepaid":
                datafetchProduct(Constants.PREPAID_PRODUCT, "prepaid");
                break;
            case "Price":
                datafetchProduct(Constants.SORTBYPRICE, arguments.getString("item"));
                break;
            case "short":
                datafetchProduct(Constants.SORTBYREVIEW, arguments.getString("item"));
                break;
        }

        return view;
    }


    private void FilterProduct(String Api, String item_id) {


        shareSession = new Share_session(getActivity());
        data = shareSession.Fetchdata();


        OkHttpClient okHttpClient = new OkHttpClient();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("item_id", item_id);
            if (data.get(FILTER_LIST) != null) {
                jsonObject.put("size_filter", data.get(FILTER_LIST).toString());
            } else {
                jsonObject.put("size_filter", false);
            }
            if (data.get(FILTER_LIST_COLOR) != null) {
                jsonObject.put("color_filter", data.get(FILTER_LIST_COLOR).toString());
            } else {
                jsonObject.put("color_filter", false);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(JSON, jsonObject.toString());
        Request request1 = new Request.Builder().url(Api).post(body).build();
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
                                productListList.add(new ProductList(jsonHelper.GetResult("product_id"),
                                        jsonHelper.GetResult("ProductName"),
                                        jsonHelper.GetResult("SKU"),
                                        jsonHelper.GetResult("item_id"),
                                        jsonHelper.GetResult("catagory_id"),
                                        jsonHelper.GetResult("actual_price"),
                                        jsonHelper.GetResult("discount_price"),
                                        jsonHelper.GetResult("discount_price_per"),
                                        jsonHelper.GetResult("status"),
                                        jsonHelper.GetResult("pimg"),
                                        jsonHelper.GetResult("vendor_id"),
                                        jsonHelper.GetResult("FakeRating"),
                                        jsonHelper.GetResult("gst"),
                                        jsonHelper.GetResult("hot_product"),
                                        jsonHelper.GetResult("hsn_no"),
                                        jsonHelper.GetResult("weight"),
                                        jsonHelper.GetResult("type"),
                                        jsonHelper.GetResult("flash_sale")
                                ));

                            }

                            if (getActivity() != null) {
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        ProductAdapter productAdapter = new ProductAdapter(getActivity(), productListList, item_id);
                                        product_recycleview.setAdapter(productAdapter);
//                                    product_recycleview.addItemDecoration(new GridSpacingItemDecoration(50));
                                        productAdapter.notifyDataSetChanged();

                                    }
                                });
                            }

                        }
                    }
                }
            }
        });
    }


    private void datafetchProduct(String Api, String item_id) {


        OkHttpClient okHttpClient = new OkHttpClient();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("item_id", item_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(JSON, jsonObject.toString());
//        Request request1 = new Request.Builder().url("http://173.212.226.143:8086/api/"+PRODUCT_PERAMETER).post(body).build();
        Request request1 = new Request.Builder().url(Api).post(body).build();
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
                                productListList.add(new ProductList(jsonHelper.GetResult("product_id"),
                                        jsonHelper.GetResult("ProductName"),
                                        jsonHelper.GetResult("SKU"),
                                        jsonHelper.GetResult("item_id"),
                                        jsonHelper.GetResult("catagory_id"),
                                        jsonHelper.GetResult("actual_price"),
                                        jsonHelper.GetResult("discount_price"),
                                        jsonHelper.GetResult("discount_price_per"),
                                        jsonHelper.GetResult("status"),
                                        jsonHelper.GetResult("pimg"),
                                        jsonHelper.GetResult("vendor_id"),
                                        jsonHelper.GetResult("FakeRating"),
                                        jsonHelper.GetResult("gst"),
                                        jsonHelper.GetResult("hot_product"),
                                        jsonHelper.GetResult("hsn_no"),
                                        jsonHelper.GetResult("weight"),
                                        jsonHelper.GetResult("type"),
                                        jsonHelper.GetResult("flash_sale")
                                ));

                            }

                            if (getActivity() != null) {
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        ProductAdapter productAdapter = new ProductAdapter(getActivity(), productListList, item_id);
                                        product_recycleview.setAdapter(productAdapter);
//                                    product_recycleview.addItemDecoration(new GridSpacingItemDecoration(50));
                                        productAdapter.notifyDataSetChanged();

                                    }
                                });
                            }

                        }
                    }
                }
            }
        });
    }

    private void hotproducts(String Api) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(Api)
                .build();
        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful()) {
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
                                    productListList.add(new ProductList(jsonHelper.GetResult("product_id"),
                                            jsonHelper.GetResult("ProductName"),
                                            jsonHelper.GetResult("SKU"),
                                            jsonHelper.GetResult("item_id"),
                                            jsonHelper.GetResult("catagory_id"),
                                            jsonHelper.GetResult("actual_price"),
                                            jsonHelper.GetResult("discount_price"),
                                            jsonHelper.GetResult("discount_price_per"),
                                            jsonHelper.GetResult("status"),
                                            jsonHelper.GetResult("pimg"),
                                            jsonHelper.GetResult("vendor_id"),
                                            jsonHelper.GetResult("FakeRating"),
                                            jsonHelper.GetResult("gst"),
                                            jsonHelper.GetResult("hot_product"),
                                            jsonHelper.GetResult("hsn_no"),
                                            jsonHelper.GetResult("weight"),
                                            jsonHelper.GetResult("type"),
                                            jsonHelper.GetResult("flash_sale")
                                    ));

                                }

                                if (getActivity() != null) {
                                    getActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            ProductAdapter productAdapter = new ProductAdapter(getActivity(), productListList, "");
                                            product_recycleview.setAdapter(productAdapter);
//                                    product_recycleview.addItemDecoration(new GridSpacingItemDecoration(50));
                                            productAdapter.notifyDataSetChanged();

                                        }
                                    });
                                }

                            }
                        }
                    }
                }
            }
        });
    }


}























