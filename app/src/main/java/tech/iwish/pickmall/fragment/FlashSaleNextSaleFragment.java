package tech.iwish.pickmall.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import tech.iwish.pickmall.R;
import tech.iwish.pickmall.activity.FlashSaleProductactivity;
import tech.iwish.pickmall.adapter.FlashSaleAllProductAdapter;
import tech.iwish.pickmall.config.Constants;
import tech.iwish.pickmall.connection.JsonHelper;
import tech.iwish.pickmall.other.FlashsalemainList;

public class FlashSaleNextSaleFragment extends Fragment {

    private RecyclerView flash_sale_recycleview_next_sale;
    private List<FlashsalemainList> flashsalemainLists = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_flash_sale_next_sale , null);

        flash_sale_recycleview_next_sale = (RecyclerView)view.findViewById(R.id.flash_sale_recycleview_next_sale);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        flash_sale_recycleview_next_sale.setLayoutManager(linearLayoutManager);


        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(Constants.FLASH_SALE_ALL)
                .build();
        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    String result = response.body().string();

                    JsonHelper jsonHelper = new JsonHelper(result);
                    if (jsonHelper.isValidJson()) {
                        String responses = jsonHelper.GetResult("response");
                        if (responses.equals("TRUE")) {
                            JSONArray jsonArray = jsonHelper.setChildjsonArray(jsonHelper.getCurrentJsonObj(), "data");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                jsonHelper.setChildjsonObj(jsonArray, i);
                                flashsalemainLists.add(new FlashsalemainList(jsonHelper.GetResult("product_id"), jsonHelper.GetResult("ProductName"), jsonHelper.GetResult("item_id"), jsonHelper.GetResult("catagory_id"), jsonHelper.GetResult("actual_price"), jsonHelper.GetResult("discount_price"), jsonHelper.GetResult("discount_price_per"), jsonHelper.GetResult("status"), jsonHelper.GetResult("pimg"), jsonHelper.GetResult("vendor_id"), jsonHelper.GetResult("type"), jsonHelper.GetResult("datetime"), jsonHelper.GetResult("FakeRating"), jsonHelper.GetResult("saleid"), jsonHelper.GetResult("gst")));

                            }
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    FlashSaleAllProductAdapter flashSaleAllProductAdapter = new FlashSaleAllProductAdapter((FlashSaleProductactivity) getActivity(), flashsalemainLists);
                                    flash_sale_recycleview_next_sale.setAdapter(flashSaleAllProductAdapter);

                                }
                            });

                        }
                    }

                }
            }
        });

        return view ;
    }
}






























