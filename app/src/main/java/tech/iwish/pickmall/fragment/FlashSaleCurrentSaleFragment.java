package tech.iwish.pickmall.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import tech.iwish.pickmall.Interface.FlashsaleTimeIdInterface;
import tech.iwish.pickmall.R;
import tech.iwish.pickmall.activity.FlashSaleProductactivity;
import tech.iwish.pickmall.activity.FollowingActivity;
import tech.iwish.pickmall.adapter.FlashSaleAllProductAdapter;
import tech.iwish.pickmall.adapter.FollowingAdapter;
import tech.iwish.pickmall.config.Constants;
import tech.iwish.pickmall.connection.JsonHelper;
import tech.iwish.pickmall.countdowntime.CountdownTime;
import tech.iwish.pickmall.other.FllowingList;
import tech.iwish.pickmall.other.ProductList;

public class FlashSaleCurrentSaleFragment extends Fragment implements FlashsaleTimeIdInterface {


    private RecyclerView flash_sale_recycleview;
    private TextView time_count;
    private List<ProductList> productListList = new ArrayList<>();
    private ImageView flashImage;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_flash_sale_current_sale, null);

        flash_sale_recycleview = (RecyclerView) view.findViewById(R.id.flash_sale_recycleview);
        time_count = (TextView) view.findViewById(R.id.time_count);

        flashImage = (ImageView)view.findViewById(R.id.flashImage);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        flash_sale_recycleview.setLayoutManager(linearLayoutManager);

        new CountdownTime(time_count);
        image();

//        FlashSaleAllProductAdapter flashSaleAllProductAdapter = new FlashSaleAllProductAdapter((FlashSaleProductactivity) getActivity(), FlashSalefake());
//        flash_sale_recycleview.setAdapter(flashSaleAllProductAdapter);

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
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    FlashSaleAllProductAdapter flashSaleAllProductAdapter = new FlashSaleAllProductAdapter((FlashSaleProductactivity) getActivity(), productListList);
                                    flash_sale_recycleview.setHasFixedSize(true);
                                    flash_sale_recycleview.setAdapter(flashSaleAllProductAdapter);

                                }
                            });

                        }
                    }

                }
            }
        });


        return view;
    }


    private void image() {


        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(Constants.FLASH_SALE_IMAGE_OPTION)
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

                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {

                                        String image = jsonHelper.GetResult("banner_img");
                                        String a = Constants.IMAGES +image;
                                        Glide.with(getActivity()).load(a).into(flashImage);

                                    }
                                });

                            }
                        }
                    }

                }
            }
        });


    }

    @Override
    public void flashsaleId(String saleid) {

    }
}
