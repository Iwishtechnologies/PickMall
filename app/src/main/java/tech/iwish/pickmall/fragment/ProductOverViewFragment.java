package tech.iwish.pickmall.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
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
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import tech.iwish.pickmall.Interface.PincodeInterFace;
import tech.iwish.pickmall.R;
import tech.iwish.pickmall.activity.ReturnPolicyActivity;
import tech.iwish.pickmall.adapter.ProductDescriptionAdapter;
import tech.iwish.pickmall.adapter.ProductOverviewAdapter;
import tech.iwish.pickmall.config.Constants;
import tech.iwish.pickmall.connection.JsonHelper;
import tech.iwish.pickmall.other.ProductDescriptionlist;
import tech.iwish.pickmall.other.ProductOverViewList;
import tech.iwish.pickmall.session.Share_session;

import static tech.iwish.pickmall.session.Share_session.PINCODR_SERVICE_CHECK;


public class ProductOverViewFragment extends Fragment implements View.OnClickListener, PincodeInterFace {

    private RecyclerView product_overview, product_description;
    private List<ProductOverViewList> productOverViewLists = new ArrayList<>();
    private List<ProductDescriptionlist> productDescriptionlists = new ArrayList<>();
    private String product_id, vendor_id;
    private TextView select_pincode, checker_pincode;
    private TableLayout tableLayout;
    private LinearLayout return_policy , venodr_layout;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_over_view, null);

        product_overview = (RecyclerView) view.findViewById(R.id.product_overview);
        product_description = (RecyclerView) view.findViewById(R.id.product_description);
        select_pincode = (TextView) view.findViewById(R.id.select_pincode);
        checker_pincode = (TextView) view.findViewById(R.id.checker_pincode);
        return_policy = (LinearLayout) view.findViewById(R.id.return_policy);
        venodr_layout = (LinearLayout) view.findViewById(R.id.venodr_layout);

        tableLayout = (TableLayout) view.findViewById(R.id.tableLayout);

        select_pincode.setOnClickListener(this);
        return_policy.setOnClickListener(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        product_overview.setLayoutManager(linearLayoutManager);

        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getContext());
        linearLayoutManager1.setOrientation(RecyclerView.VERTICAL);
        product_description.setLayoutManager(linearLayoutManager1);

        Bundle bundle = getArguments();
        product_id = bundle.getString("product_id");
        vendor_id = bundle.getString("vendor_id");

        productoverview();
        productdescription();

        if(!vendor_id.equals("0") || vendor_id.equals("1")){
            vendorstore();
        }else {
            venodr_layout.setVisibility(View.GONE);
        }


        Share_session share_session = new Share_session(getContext());
        Map data = share_session.Fetchdata();
        if (data.get(PINCODR_SERVICE_CHECK) != null) {
            checker_pincode.setText(data.get(PINCODR_SERVICE_CHECK).toString());
            checker_pincode.setTextColor(getResources().getColor(R.color.black));
        }


        Bundle bundle1 = new Bundle();
        ProductFragment productFragment = new ProductFragment();
        bundle1.putString("product_id",product_id);
        bundle1.putString("type","similar_product");
        productFragment.setArguments(bundle1);
        getChildFragmentManager().beginTransaction().add(R.id.similer_product_framelayout, productFragment).commit();

        return view;
    }

    private void vendorstore() {

//        not ready

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
                            JSONArray jsonArray = jsonHelper.setChildjsonArray(jsonHelper.getCurrentJsonObj(), "data");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                jsonHelper.setChildjsonObj(jsonArray, i);
//                                productOverViewLists.add(new ProductOverViewList(jsonHelper.GetResult("sno"), jsonHelper.GetResult("product_id"), jsonHelper.GetResult("overview"), jsonHelper.GetResult("title")));
                            }
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                }
                            });

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
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    ProductOverviewAdapter productOverviewAdapter = new ProductOverviewAdapter(getActivity(), productOverViewLists);
                                    product_overview.setAdapter(productOverviewAdapter);
                                }
                            });

                        }
                    }
                }
            }
        });

    }

    private void productdescription() {

        OkHttpClient okHttpClient = new OkHttpClient();
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

//                          \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\


                                tableLayout.setStretchAllColumns(true);
//        tableLayout.bringToFront();

                                final int finalI = i;
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {

                                        TableRow tr = new TableRow(getContext());
                                        tr.setWeightSum(2);
                                        tr.getResources().getDrawable(R.color.silderColor);


                                        TextView c1 = new TextView(getContext());

                                        c1.setText(productDescriptionlists.get(finalI).getDescription_title());
                                        c1.setPadding(0, 4, 0, 4);
                                        c1.setTextSize(14);

                                        TextView c2 = new TextView(getContext());
                                        c2.setText(productDescriptionlists.get(finalI).getDescription_data());
                                        c2.setPadding(0, 4, 0, 4);
                                        c2.setTextSize(14);


                                        tr.addView(c1);
                                        tr.addView(c2);
                                        tableLayout.addView(tr);


                                    }
                                });

//                          \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

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
                getActivity().startActivity(new Intent(new Intent(getContext(), ReturnPolicyActivity.class)));
                break;
        }
    }


    @Override
    public void pincodefetch(String city, String state) {
        Share_session share_session = new Share_session(getContext());
        Map data = share_session.Fetchdata();
        if (data.get(PINCODR_SERVICE_CHECK) != null) {
            checker_pincode.setText(data.get(PINCODR_SERVICE_CHECK).toString());
            checker_pincode.setTextColor(getResources().getColor(R.color.black));
        }
    }
}























