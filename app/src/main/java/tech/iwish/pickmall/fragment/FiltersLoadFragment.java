package tech.iwish.pickmall.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import tech.iwish.pickmall.adapter.ProductFilterAdapter;
import tech.iwish.pickmall.config.Constants;
import tech.iwish.pickmall.connection.JsonHelper;
import tech.iwish.pickmall.other.ProductSizeColorList;

public class FiltersLoadFragment extends Fragment {

    private List<ProductSizeColorList> productSizeColorLists = new ArrayList<>();
    private RecyclerView filterRecycview ;
    private ProgressBar progressBar;

    public FiltersLoadFragment(ProgressBar progressbar) {
        this.progressBar = progressbar;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_filters_load, null);

        filterRecycview = (RecyclerView)view.findViewById(R.id.filterRecycview);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        filterRecycview.setLayoutManager(linearLayoutManager);




//        Toast.makeText(getContext(), ""+getArguments().get("value"), Toast.LENGTH_SHORT).show();
//        Toast.makeText(getContext(), "item_id  "+getArguments().get("item_id"), Toast.LENGTH_SHORT).show();
//        Toast.makeText(getContext(), "item_name  "+getArguments().get("item_name"), Toast.LENGTH_SHORT).show();

        progressBar.setVisibility(View.VISIBLE);

        OkHttpClient okHttpClient = new OkHttpClient();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("item_id", getArguments().get("item_id"));
            jsonObject.put("filter_cat_name", getArguments().get("item_name"));
            jsonObject.put("filter_value", getArguments().get("value"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(JSON, jsonObject.toString());
        Request request1 = new Request.Builder().url(Constants.PRODUCT_FILTER).post(body).build();
        okHttpClient.newCall(request1).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    String result = response.body().string();
                    Log.e("result", result);
                    JsonHelper jsonHelper = new JsonHelper(result);
                    if (jsonHelper.isValidJson()) {
                        String responses = jsonHelper.GetResult("response");
                        if (responses.equals("TRUE")) {
                            JSONArray jsonArray = jsonHelper.setChildjsonArray(jsonHelper.getCurrentJsonObj(), "data");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                jsonHelper.setChildjsonObj(jsonArray, i);
                                productSizeColorLists.add(new ProductSizeColorList(jsonHelper.GetResult("sno"), jsonHelper.GetResult("product_id"), jsonHelper.GetResult("imgname"), jsonHelper.GetResult("size"), jsonHelper.GetResult("color"), jsonHelper.GetResult("qty"), jsonHelper.GetResult("count_size")));
                            }

                            if (getActivity()!= null) {

                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        ProductFilterAdapter productFilterAdapter = new ProductFilterAdapter(getActivity() , productSizeColorLists,getArguments().get("value").toString());
                                        filterRecycview.setAdapter(productFilterAdapter);
                                        progressBar.setVisibility(View.GONE);
                                    }
                                });
                            }


                        }
                    }
                }
            }
        });

        return view;
    }
}
























