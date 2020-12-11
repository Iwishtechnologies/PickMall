package tech.iwish.pickmall.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.gavinliu.android.lib.shapedimageview.ShapedImageView;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import tech.iwish.pickmall.Interface.PincodeInterFace;
import tech.iwish.pickmall.Interface.Progressbar_product_inteface;
import tech.iwish.pickmall.R;
import tech.iwish.pickmall.activity.ReturnPolicyActivity;
import tech.iwish.pickmall.activity.VendorStoreActivity;
import tech.iwish.pickmall.adapter.ProductDescriptionAdapter;
import tech.iwish.pickmall.adapter.ProductOverviewAdapter;
import tech.iwish.pickmall.adapter.VendorStoreLimitProductAdapter;
import tech.iwish.pickmall.config.Constants;
import tech.iwish.pickmall.connection.JsonHelper;
import tech.iwish.pickmall.extended.TextViewFont;
import tech.iwish.pickmall.other.ProductDescriptionlist;
import tech.iwish.pickmall.other.ProductList;
import tech.iwish.pickmall.other.ProductOverViewList;
import tech.iwish.pickmall.other.VendorStoreDetails;
import tech.iwish.pickmall.session.Share_session;

import static tech.iwish.pickmall.session.Share_session.PINCODR_SERVICE_CHECK;


public class ProductOverViewFragment extends Fragment implements View.OnClickListener, PincodeInterFace , Progressbar_product_inteface {

    private RecyclerView product_overview, product_description, vendor_product_recycleview;
    private List<ProductOverViewList> productOverViewLists = new ArrayList<>();
    private List<ProductDescriptionlist> productDescriptionlists = new ArrayList<>();
    private List<VendorStoreDetails> vendorStoreDetailsList = new ArrayList<>();
    private String product_id, vendor_id;
    private TextView select_pincode, checker_pincode, shopName, show_product_cunt,wringrating;
    private TableLayout tableLayout, tableLayout1;
    ImageView fulldetails;
    private LinearLayout return_policy, venodr_layout, viewlayout, open_store;
    Activity activity;
    CircleImageView profile_image;
    RatingBar vendor_rating;
    private List<ProductList> productListList = new ArrayList<>();


    public ProductOverViewFragment(Activity activity) {
        this.activity = activity;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_over_view, null);

        vendor_rating =  view.findViewById(R.id.vendor_rating);
        wringrating =  view.findViewById(R.id.wringrating);
        product_overview = (RecyclerView) view.findViewById(R.id.product_overview);
        product_description = (RecyclerView) view.findViewById(R.id.product_description);
        select_pincode = (TextView) view.findViewById(R.id.select_pincode);
        checker_pincode = (TextView) view.findViewById(R.id.checker_pincode);
        return_policy = (LinearLayout) view.findViewById(R.id.return_policy);
        venodr_layout = (LinearLayout) view.findViewById(R.id.venodr_layout);
        viewlayout = view.findViewById(R.id.viewlayout);
        vendor_product_recycleview = view.findViewById(R.id.vendor_product_recycleview);

        tableLayout = (TableLayout) view.findViewById(R.id.tableLayout);
        tableLayout1 = (TableLayout) view.findViewById(R.id.tableLayout1);
        open_store = view.findViewById(R.id.open_store);
        profile_image = view.findViewById(R.id.profile_image);

        fulldetails = view.findViewById(R.id.fulldetails);

        shopName = view.findViewById(R.id.shopName);
        show_product_cunt = view.findViewById(R.id.show_product_cunt);

        select_pincode.setOnClickListener(this);
        return_policy.setOnClickListener(this);
        fulldetails.setOnClickListener(this);
        open_store.setOnClickListener(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        product_overview.setLayoutManager(linearLayoutManager);

        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(activity);
        linearLayoutManager1.setOrientation(RecyclerView.VERTICAL);
        product_description.setLayoutManager(linearLayoutManager1);

        Bundle bundle = getArguments();
        product_id = bundle.getString("product_id");
        vendor_id = bundle.getString("vendor_id");

        productoverview();
        productdescription();

        if (!vendor_id.equals("1")) {
            LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(activity);
            linearLayoutManager2.setOrientation(RecyclerView.HORIZONTAL);
            vendor_product_recycleview.setLayoutManager(linearLayoutManager2);
            vendorstore();
        } else {
            venodr_layout.setVisibility(View.GONE);
        }


        Share_session share_session = new Share_session(activity);
        Map data = share_session.Fetchdata();
        if (data.get(PINCODR_SERVICE_CHECK) != null) {
            checker_pincode.setText(data.get(PINCODR_SERVICE_CHECK).toString());
            checker_pincode.setTextColor(getResources().getColor(R.color.white));
        }


        Bundle bundle1 = new Bundle();
        ProductFragment productFragment = new ProductFragment(this);
        bundle1.putString("product_id", product_id);
        bundle1.putString("type", "similar_product");
        productFragment.setArguments(bundle1);
        getChildFragmentManager().beginTransaction().add(R.id.similer_product_framelayout, productFragment).commit();
        Overview();
        return view;

    }


    private void vendorstore() {

//        not ready

        venodr_layout.setVisibility(View.VISIBLE);

        OkHttpClient okHttpClient = new OkHttpClient();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("vendor_id", vendor_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(JSON, jsonObject.toString());
        Request request1 = new Request.Builder().url(Constants.VENDOR_STOR_PRODUCT).post(body).build();
        okHttpClient.newCall(request1).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    String result = response.body().string();
                    Log.e("response", result);
                    JsonHelper jsonHelper = new JsonHelper(result);
                    if (jsonHelper.isValidJson()) {
                        String responses = jsonHelper.GetResult("response");
                        if (responses.equals("TRUE")) {

                            JSONObject temp_json = jsonHelper.getCurrentJsonObj();
                            JSONArray jsonArray = jsonHelper.setChildjsonArray(jsonHelper.getCurrentJsonObj(), "shop_details");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                jsonHelper.setChildjsonObj(jsonArray, i);
                                vendorStoreDetailsList.add(new VendorStoreDetails(jsonHelper.GetResult("id"), jsonHelper.GetResult("shopname"), jsonHelper.GetResult("product_count"), jsonHelper.GetResult("store_follow"), jsonHelper.GetResult("img"), jsonHelper.GetResult("rating")));
                                int finalI = i;
                                activity.runOnUiThread(() -> {
                                    shopName.setText(vendorStoreDetailsList.get(finalI).getShopname());
                                    if(!vendorStoreDetailsList.get(finalI).getImage().equals("")){
                                        Glide.with(getActivity()).load(Constants.IMAGES+vendorStoreDetailsList.get(finalI).getImage()).into(profile_image);
                                    }
                                    vendor_rating.setRating(Float.parseFloat(vendorStoreDetailsList.get(finalI).getRating()));
                                    wringrating.setText(vendorStoreDetailsList.get(finalI).getRating());
                                    show_product_cunt.setText(getResources().getString(R.string.followers) + " " + vendorStoreDetailsList.get(finalI).getStore_follow() + " " + getResources().getString(R.string.product) + " " + vendorStoreDetailsList.get(finalI).getProduct_count());
                                });
                            }

//                            product
                            productListList.clear();

                            JSONArray jsonArray1 = jsonHelper.setChildjsonArray(temp_json, "data");
                            for (int j = 0; j < jsonArray1.length(); j++) {
                                jsonHelper.setChildjsonObj(jsonArray1, j);
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

                            if (activity != null) {
                                activity.runOnUiThread(() -> vendor_product_recycleview.setAdapter(new VendorStoreLimitProductAdapter(productListList)));
                            }

                        }
                    }
                }
            }
        });

    }

    private void productoverview() {

        OkHttpClient okHttpClient = new OkHttpClient();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("product_id", product_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(JSON, jsonObject.toString());
        Request request1 = new Request.Builder().url(Constants.PRODUCT_OVERVIEW).post(body).build();
        okHttpClient.newCall(request1).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    String result = response.body().string();
                    Log.e("response", result);
                    JsonHelper jsonHelper = new JsonHelper(result);
                    if (jsonHelper.isValidJson()) {
                        String responses = jsonHelper.GetResult("response");
                        if (responses.equals("TRUE")) {
                            JSONArray jsonArray = jsonHelper.setChildjsonArray(jsonHelper.getCurrentJsonObj(), "data");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                jsonHelper.setChildjsonObj(jsonArray, i);
                                productOverViewLists.add(new ProductOverViewList(jsonHelper.GetResult("sno"), jsonHelper.GetResult("product_id"), jsonHelper.GetResult("overview"), jsonHelper.GetResult("title")));
                            }

                            if (activity != null) {

                                activity.runOnUiThread(() -> {
                                    ProductOverviewAdapter productOverviewAdapter = new ProductOverviewAdapter(activity, productOverViewLists);
                                    product_overview.setAdapter(productOverviewAdapter);
                                });

                            }
                        }
                    }
                }
            }
        });

    }

    private void productdescription() {

        final OkHttpClient okHttpClient = new OkHttpClient();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("product_id", product_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(JSON, jsonObject.toString());
        Request request1 = new Request.Builder().url(Constants.PRODUCT_DESCRIPTION).post(body).build();
        okHttpClient.newCall(request1).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    String result = response.body().string();
                    Log.e("response", result);
                    JsonHelper jsonHelper = new JsonHelper(result);
                    if (jsonHelper.isValidJson()) {
                        String responses = jsonHelper.GetResult("response");
                        if (responses.equals("TRUE")) {
                            JSONArray jsonArray = jsonHelper.setChildjsonArray(jsonHelper.getCurrentJsonObj(), "data");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                jsonHelper.setChildjsonObj(jsonArray, i);
                                productDescriptionlists.add(new ProductDescriptionlist(jsonHelper.GetResult("sno"), jsonHelper.GetResult("product_id"), jsonHelper.GetResult("description_title"), jsonHelper.GetResult("description_data")));
                                tableLayout.setStretchAllColumns(true);
//        tableLayout.bringToFront();


                                if (activity != null) {

                                    final int finalI = i;
                                    activity.runOnUiThread(() -> {


                                        TableRow tr = new TableRow(activity);
                                        tr.setWeightSum(2);
                                        tr.setPadding(10, 10, 10, 10);
                                        tr.getResources().getDrawable(R.color.silderColor);

                                        TextView c1 = new TextView(activity);
                                        TableRow.LayoutParams params1 = new TableRow.LayoutParams(150, TableRow.LayoutParams.WRAP_CONTENT, 1f);
                                        params1.setMargins(0, 0, 5, 0);
                                        c1.setLayoutParams(params1);
                                        c1.setText(productDescriptionlists.get(finalI).getDescription_title() + " ");
                                        c1.setPadding(20, 20, 20, 20);
                                        c1.setTextSize(14);
                                        c1.setBackgroundResource(R.drawable.table_border);
                                        c1.setTextColor(getResources().getColor(R.color.white));

                                        TextView c2 = new TextView(activity);
                                        TableRow.LayoutParams params = new TableRow.LayoutParams(150, TableRow.LayoutParams.WRAP_CONTENT, 1f);
                                        c2.setLayoutParams(params);
                                        c2.setText(productDescriptionlists.get(finalI).getDescription_data());
                                        c2.setPadding(20, 20, 20, 20);
                                        c2.setTextSize(14);
                                        c2.setBackgroundResource(R.drawable.table_border);
                                        c2.setTextColor(getResources().getColor(R.color.white));

                                        tr.addView(c1);
                                        tr.addView(c2);
                                        tableLayout.addView(tr);

                                    });

                                }


                            }
//                            getActivity().runOnUiThread(new Runnable() {
//                                @Override
//                                public void run() {
//                                    ProductDescriptionAdapter productDescriptionAdapter = new ProductDescriptionAdapter(getActivity(), productDescriptionlists);
//                                    product_description.setAdapter(productDescriptionAdapter);
//
//                                }
//                            });

                        }
                    }
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.select_pincode:
                BottomPincodeFragment bottomPincodeFragment = new BottomPincodeFragment(this);
                bottomPincodeFragment.show(getActivity().getSupportFragmentManager(), bottomPincodeFragment.getTag());
                break;
            case R.id.return_policy:
                startActivity(new Intent(new Intent(activity, ReturnPolicyActivity.class)));
                break;
            case R.id.fulldetails:
                break;
            case R.id.open_store:
                startActivity(new Intent(activity, VendorStoreActivity.class).putExtra("vendor_id", vendor_id));
                break;

        }

    }


    @Override
    public void pincodefetch(String city, String state) {
        Share_session share_session = new Share_session(activity);
        Map data = share_session.Fetchdata();
        if (data.get(PINCODR_SERVICE_CHECK) != null) {
            checker_pincode.setText(data.get(PINCODR_SERVICE_CHECK).toString());
            checker_pincode.setTextColor(getResources().getColor(R.color.white));
        }
    }

    protected void Overview() {

//        LinearLayout linearLayout =new LinearLayout(getActivity());
////        linearLayout.setId(finalI+500);
//        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//        linearLayout.setWeightSum(2f);
//        lp.setMargins(0,0,0,10);
//        linearLayout.setOrientation(LinearLayout.VERTICAL);
//        linearLayout.setLayoutParams(lp);

        OkHttpClient okHttpClient = new OkHttpClient();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("pid", product_id);
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
                if (response.isSuccessful()) {
                    String result = response.body().string();
                    Log.e("response", result);
                    JsonHelper jsonHelper = new JsonHelper(result);
                    if (jsonHelper.isValidJson()) {
                        String responses = jsonHelper.GetResult("response");
                        if (responses.equals("TRUE")) {
                            JSONArray jsonArray = jsonHelper.setChildjsonArray(jsonHelper.getCurrentJsonObj(), "data");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                jsonHelper.setChildjsonObj(jsonArray, i);
                                jsonHelper.setChildjsonObj(jsonArray, i);
                                productOverViewLists.add(new ProductOverViewList("1", "1", jsonHelper.GetResult("overview"), jsonHelper.GetResult("title")));
                                tableLayout1.setStretchAllColumns(true);
//        tableLayout.bringToFront();


                                if (activity != null) {

                                    final int finalI = i;
                                    activity.runOnUiThread(() -> {


//                                            LinearLayout linearLayout = new LinearLayout(getActivity());
//                                            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//                                            layoutParams.setMargins(50, 0, 15, 0);
//                                            linearLayout.setLayoutParams(layoutParams);

                                        TableRow tr1 = new TableRow(activity);
                                        tr1.setWeightSum(2);
                                        tr1.setPadding(10, 10, 10, 10);
                                        tr1.getResources().getDrawable(R.color.silderColor);

                                        TextView c3 = new TextView(activity);
                                        TableRow.LayoutParams params1 = new TableRow.LayoutParams(150, TableRow.LayoutParams.WRAP_CONTENT, 1f);
                                        params1.setMargins(0, 0, 5, 0);
                                        c3.setLayoutParams(params1);
                                        c3.setText(productOverViewLists.get(finalI).getTitle() + " ");
                                        c3.setPadding(20, 20, 20, 20);
                                        c3.setTextSize(14);
                                        c3.setBackgroundResource(R.drawable.table_border);
                                        c3.setTextColor(getResources().getColor(R.color.white));

                                        TextView c4 = new TextView(activity);
                                        TableRow.LayoutParams params = new TableRow.LayoutParams(150, TableRow.LayoutParams.WRAP_CONTENT, 1f);
                                        params.setMargins(0, 0, 10, 0);
                                        c4.setLayoutParams(params);
                                        c4.setText(productOverViewLists.get(finalI).getOverview());
                                        c4.setPadding(20, 20, 20, 20);

                                        c4.setTextSize(14);
                                        c4.setBackgroundResource(R.drawable.table_border);
                                        c4.setTextColor(getResources().getColor(R.color.white));

                                        tr1.addView(c3);
                                        tr1.addView(c4);
                                        tableLayout1.addView(tr1);

                                    });

                                }


//                          \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\


                            }
                        }
                    }
                }
            }
        });

    }

    @Override
    public void progressbar_product_intefaces(String val) {

    }
}























