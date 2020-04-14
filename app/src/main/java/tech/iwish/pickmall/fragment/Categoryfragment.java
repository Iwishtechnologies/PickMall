package tech.iwish.pickmall.fragment;

import android.content.Context;
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

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import tech.iwish.pickmall.R;
import tech.iwish.pickmall.activity.AllcategoryActivity;
import tech.iwish.pickmall.adapter.CategoryAdapter;
import tech.iwish.pickmall.adapter.ItemAdapter;
import tech.iwish.pickmall.config.Constants;
import tech.iwish.pickmall.connection.JsonHelper;
import tech.iwish.pickmall.other.CategoryList;
import tech.iwish.pickmall.other.ItemList;


public class Categoryfragment extends Fragment {

    private List<CategoryList> categoryLists = new ArrayList<>();
    private RecyclerView categoryrecycleview;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_categoryfragemnt,null);

        categoryrecycleview = (RecyclerView) view.findViewById(R.id.categoryrecycleview);

        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL);
        categoryrecycleview.setLayoutManager(staggeredGridLayoutManager);

        String value = getArguments().getString("value");

        category(value);

        return view;
    }



    private void category(String value) {
        categoryLists.clear();
        Log.e("value", value);

        OkHttpClient okHttpClient = new OkHttpClient();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("item_id", value);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(JSON, jsonObject.toString());
        Request request1 = new Request.Builder().url(Constants.CATEGORY).post(body).build();
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
                        String responses = jsonHelper.GetResult("response");
                        if (responses.equals("TRUE")) {
                            JSONArray jsonArray = jsonHelper.setChildjsonArray(jsonHelper.getCurrentJsonObj(), "data");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                jsonHelper.setChildjsonObj(jsonArray, i);
                                categoryLists.add(new CategoryList(jsonHelper.GetResult("catagory_id"), jsonHelper.GetResult("item_id"), jsonHelper.GetResult("category_name"), jsonHelper.GetResult("type")));
                            }
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    CategoryAdapter categoryAdapter = new CategoryAdapter(getActivity(),categoryLists);
                                    categoryrecycleview.setAdapter(categoryAdapter);
//                                    categoryrecycleview.addItemDecoration();
                                }
                            });

                        }
                    }
                }
            }
        });


    }

}
