package tech.iwish.pickmall.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.bumptech.glide.load.model.Model;
import com.facebook.shimmer.ShimmerFrameLayout;

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
import java.util.Objects;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import tech.iwish.pickmall.Interface.ProgressbarStartProduct;
import tech.iwish.pickmall.Interface.Progressbar_product_inteface;
import tech.iwish.pickmall.OkhttpConnection.ProductListF;
import tech.iwish.pickmall.R;
import tech.iwish.pickmall.adapter.AllOfferProductAdapter;
import tech.iwish.pickmall.adapter.ProductAdapter;
import tech.iwish.pickmall.config.Constants;
import tech.iwish.pickmall.connection.JsonHelper;
import tech.iwish.pickmall.other.ProductList;
import tech.iwish.pickmall.other.ProductOfferlist;
import tech.iwish.pickmall.session.Share_session;

import static tech.iwish.pickmall.session.Share_session.FILTER_LIST;
import static tech.iwish.pickmall.session.Share_session.FILTER_LIST_COLOR;

public class ProductFragment extends Fragment {


    private RecyclerView product_recycleview;
    private List<ProductList> productListList = new ArrayList<>();
    private List<ProductOfferlist> productOfferlists = new ArrayList<>();
    private String item, type;
    public static String PRODUCT_PERAMETER;
    private Share_session shareSession;
    private Map data;
    Boolean isScrolling = false;
    int currentItems, totalItems, scrollOutItems;
    AllOfferProductAdapter allOfferProductAdapter;
    StaggeredGridLayoutManager layoutManager;
    private int pastVisibleItems;
    private boolean isLoading;
    TextView no_products;
    ProgressbarStartProduct progressbarStartProduct;
    Progressbar_product_inteface progressbar_product_inteface;

    public ProductFragment(Progressbar_product_inteface progressbar_product_inteface) {
        this.progressbar_product_inteface = progressbar_product_inteface;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product, null);

        product_recycleview = (RecyclerView) view.findViewById(R.id.product_recycleview);
        no_products = (TextView) view.findViewById(R.id.no_products);

        layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        product_recycleview.setLayoutManager(layoutManager);

        progressbarStartProduct = val -> {
            progressbar_product_inteface.progressbar_product_intefaces(val);
        };

        Bundle arguments = getArguments();
        if (arguments != null) {
            type = arguments.getString("type");
        } else {
            type = getActivity().getIntent().getExtras().getString("type");
        }
        Log.e("type", type);
        switch (type) {
            case "similar_product":
                datafetchProduct(Constants.SIMILAER_PRODUCT, arguments.getString("product_id"));
                break;
            case "product":
                datafetchProduct(Constants.ALL_PRODUCT, arguments.getString("item"));
                ;
                break;
            case "vendorStoreAllProduct":
                VendorStore(Constants.VENDOR_STORE_ALL_PRODUCT, getActivity().getIntent().getExtras().getString("vendor_id"));
                break;
            case "Category_by_product":
                datafetchProduct(Constants.CATEGORY_BY_PRODUUCT, getActivity().getIntent().getExtras().getString("category_id"));
                break;
            case "hotproduct":
                hotproducts(Constants.HOT_PRODUCT);
                break;
            case "searchActivity":
                datafetchProduct(Constants.SEARCH_PRODUCT_BY_NAME, getActivity().getIntent().getStringExtra("name"));
                break;
            case "FilterActivity":
                FilterProduct(Constants.FILTER_PRODUCT, getActivity().getIntent().getStringExtra("item_id"));
                break;
            case "prepaid":
                prepaid(Constants.PREPAID_PRODUCT, "prepaid");
                break;
            case "Price":
                if (arguments.getString("item").equals("30")) {
                    datafetchProduct(Constants.PREPAIDPRICESHORT, "prepaid");
                } else {
                    datafetchProduct(Constants.SORTBYPRICE, arguments.getString("item"));
                }

                break;
            case "short":
                if (arguments.getString("item").equals("30")) {
                    datafetchProduct(Constants.PREPAIDSHORT, "prepaid");
                } else {
                    datafetchProduct(Constants.SORTBYREVIEW, arguments.getString("item"));
                }

                break;
            case "silder_load":
                datafetchProduct(Constants.SILDER_OPEN, arguments.getString("item"));
                break;
            case "both_category_open":
                silder_open_both(Constants.SILDER_OPEN_BOTH, arguments.getString("item"), arguments.getString("category_id"));
                break;
        }

        return view;
    }

    private void VendorStore(String Api, String item_id) {

        allOfferProductAdapter = new AllOfferProductAdapter(getActivity(), ProductListF.productFake(), item_id, progressbarStartProduct);
        product_recycleview.setAdapter(allOfferProductAdapter);

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

                            no_products.setVisibility(View.GONE);
                            product_recycleview.setVisibility(View.VISIBLE);

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
                                        jsonHelper.GetResult("flash_sale"),
                                        jsonHelper.GetResult("extraoffer"),
                                        jsonHelper.GetResult("startdate"),
                                        jsonHelper.GetResult("enddate")
                                ));

                            }

                            if (getActivity() != null) {
                                getActivity().runOnUiThread(() -> {

                                    ProductAdapter productAdapter = new ProductAdapter(getActivity(), productListList, item_id, progressbarStartProduct);
                                    product_recycleview.setAdapter(productAdapter);

                                });
                            }

                        }

                    }
                }
                response.close();
            }
        });


    }

    private void prepaid(String Api, String prepaid) {

        allOfferProductAdapter = new AllOfferProductAdapter(getActivity(), ProductListF.productFake(), prepaid, progressbarStartProduct);
        product_recycleview.setAdapter(allOfferProductAdapter);

        OkHttpClient okHttpClient = new OkHttpClient();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("item_id", prepaid);
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

                            no_products.setVisibility(View.GONE);
                            product_recycleview.setVisibility(View.VISIBLE);

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
                                        jsonHelper.GetResult("flash_sale"),
                                        jsonHelper.GetResult("extraoffer"),
                                        jsonHelper.GetResult("startdate"),
                                        jsonHelper.GetResult("enddate")
                                ));

                            }

                            new Handler(Looper.getMainLooper()).post(() -> {
                                product_recycleview.setAdapter(new ProductAdapter(getActivity(), productListList, "prepaid", progressbarStartProduct));
                            });

                        }
                    }
                }
            }
        });


    }


    private void FilterProduct(String Api, String item_id) {


        shareSession = new Share_session(Objects.requireNonNull(getActivity()));
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
                                        jsonHelper.GetResult("flash_sale"),
                                        jsonHelper.GetResult("extraoffer"),
                                        jsonHelper.GetResult("startdate"),
                                        jsonHelper.GetResult("enddate")
                                ));

                            }

                            if (getActivity() != null) {
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        ProductAdapter productAdapter = new ProductAdapter(getActivity(), productListList, item_id, progressbarStartProduct);
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


        allOfferProductAdapter = new AllOfferProductAdapter(getActivity(), ProductListF.productFake(), item_id, progressbarStartProduct);
        product_recycleview.setAdapter(allOfferProductAdapter);

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

                            no_products.setVisibility(View.GONE);
                            product_recycleview.setVisibility(View.VISIBLE);

                            JSONArray jsonArray = jsonHelper.setChildjsonArray(jsonHelper.getCurrentJsonObj(), "data");

                            for (int i = 0; i < jsonArray.length(); i++) {

                                jsonHelper.setChildjsonObj(jsonArray, i);
                                Log.e("trrdgfhjb", Api);
                                if (Api.equals(Constants.ALL_PRODUCT) || Api.equals(Constants.SIMILAER_PRODUCT)) {
                                    productOfferlists.add(new ProductOfferlist(jsonHelper.GetResult("product_id"),
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
                                            jsonHelper.GetResult("flash_sale"),
                                            jsonHelper.GetResult("discount")

                                    ));

                                } else {
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
                                            jsonHelper.GetResult("flash_sale"),
                                            jsonHelper.GetResult("extraoffer"),
                                            jsonHelper.GetResult("startdate"),
                                            jsonHelper.GetResult("enddate")
                                    ));


                                }
                            }

                            new Handler(Looper.getMainLooper()).post(() -> {
                                if (Api.equals(Constants.ALL_PRODUCT) || Api.equals(Constants.SIMILAER_PRODUCT)) {
                                    product_recycleview.setAdapter(new AllOfferProductAdapter(getActivity(), productOfferlists, item_id, progressbarStartProduct));
                                } else {
                                    product_recycleview.setAdapter(new ProductAdapter(getActivity(), productListList, item_id, progressbarStartProduct));
                                }
                            });
                        }

                    }
                }
                response.close();
            }
        });
    }

    private void silder_open_both(String Api, String item_id, String category_id) {


        OkHttpClient okHttpClient = new OkHttpClient();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("item_id", item_id);
            jsonObject.put("catagory_id", category_id);
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
                                if (Api.equals(Constants.ALL_PRODUCT) || Api.equals(Constants.SIMILAER_PRODUCT)) {
                                    productOfferlists.add(new ProductOfferlist(jsonHelper.GetResult("product_id"),
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
                                            jsonHelper.GetResult("flash_sale"),
                                            jsonHelper.GetResult("discount")

                                    ));

                                    if (getActivity() != null) {
                                        getActivity().runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                AllOfferProductAdapter allOfferProductAdapter = new AllOfferProductAdapter(getActivity(), productOfferlists, item_id, progressbarStartProduct);
                                                product_recycleview.setAdapter(allOfferProductAdapter);
//                                    product_recycleview.addItemDecoration(new GridSpacingItemDecoration(50));
//                                        productAdapter.notifyDataSetChanged();

                                            }
                                        });
                                    }
                                } else {
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
                                            jsonHelper.GetResult("flash_sale"),
                                            jsonHelper.GetResult("extraoffer"),
                                            jsonHelper.GetResult("startdate"),
                                            jsonHelper.GetResult("enddate")
                                    ));


                                    if (getActivity() != null) {
                                        getActivity().runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                ProductAdapter productAdapter = new ProductAdapter(getActivity(), productListList, item_id, progressbarStartProduct);
                                                product_recycleview.setAdapter(productAdapter);
//                                    product_recycleview.addItemDecoration(new GridSpacingItemDecoration(50));
//                                        productAdapter.notifyDataSetChanged();

                                            }
                                        });
                                    }
                                }
                            }
                        }
                    }
                }
                response.close();
            }
        });
    }

    private void hotproducts(String Api) {


/*        final OkHttpClient client = new OkHttpClient();

        final Request request = new Request.Builder()
                .url(Api)
                .build();

        AsyncTask<Void, Void, String> asyncTask = new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                try {
                    Response response = client.newCall(request).execute();
                    if (!response.isSuccessful()) {
                        return null;
                    }
                    return response.body().string();
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                if (s != null) {

                    Log.e("output", s);
                    JsonHelper jsonHelper = new JsonHelper(s);
                    if (jsonHelper.isValidJson()) {
                        productListList.clear();
                        String responses = jsonHelper.GetResult("response");
                        if (responses.equals("TRUE")) {
                            JSONArray jsonArray = jsonHelper.setChildjsonArray(jsonHelper.getCurrentJsonObj(), "data");

                            for (int i = 0; i < jsonArray.length(); i++) {

                                jsonHelper.setChildjsonObj(jsonArray, i);
                                Log.e("product_id",jsonHelper.GetResult("product_id"));
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
                                        jsonHelper.GetResult("flash_sale"),
                                        jsonHelper.GetResult("extraoffer")
                                ));

                            }

                            ProductAdapter productAdapter = new ProductAdapter(getActivity(), productListList, "");
                            product_recycleview.setAdapter(productAdapter);
//                                            product_recycleview.addItemDecoration(new GridSpacingItemDecoration(50));
                            productAdapter.notifyDataSetChanged();


                        }
                    }

                }
            }
        };

        asyncTask.execute();
    }*/


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
                                            jsonHelper.GetResult("flash_sale"),
                                            jsonHelper.GetResult("extraoffer"),
                                            jsonHelper.GetResult("startdate"),
                                            jsonHelper.GetResult("enddate")
                                    ));

                                }


//                                Handler mainHandler = new Handler(getActivity().getMainLooper());
//
//                                Runnable myRunnable = new Runnable() {
//                                    @Override
//                                    public void run() {
//
//                                        ProductAdapter productAdapter = new ProductAdapter(getActivity(), productListList, "");
//                                        product_recycleview.setAdapter(productAdapter);
//                                        productAdapter.notifyDataSetChanged();
//
//                                    } // This is your code
//                                };
//                                mainHandler.post(myRunnable);


                                if (getActivity() != null) {
                                    Handler mainHandler = new Handler(getContext().getMainLooper());
                                    mainHandler.post(() -> {
                                        ProductAdapter productAdapter = new ProductAdapter(getActivity(), productListList, "", progressbarStartProduct);
                                        product_recycleview.setAdapter(productAdapter);
//                                            product_recycleview.addItemDecoration(new GridSpacingItemDecoration(50));
                                        productAdapter.notifyDataSetChanged();
                                    });

                                }

                            }
                        }
                    }
                }
                response.close();
            }
        });


    }

}


























