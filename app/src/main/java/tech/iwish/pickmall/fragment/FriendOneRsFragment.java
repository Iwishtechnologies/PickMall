package tech.iwish.pickmall.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

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
import tech.iwish.pickmall.activity.FriendsDealsAllActivity;
import tech.iwish.pickmall.adapter.FriendSaleAllProductAdapter;
import tech.iwish.pickmall.connection.JsonHelper;
import tech.iwish.pickmall.other.FriendSale;

public class FriendOneRsFragment extends Fragment implements View.OnClickListener {

    private ImageView rule_image;
    private RecyclerView friend_deal_all_recycleview;
    private List<FriendSale> friendSaleLists = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_friend_one_rs, null);

        friend_deal_all_recycleview = (RecyclerView) view.findViewById(R.id.friend_deal_all_recycleview);
        rule_image = (ImageView) view.findViewById(R.id.rule_image);

        rule_image.setOnClickListener(this);

        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        friend_deal_all_recycleview.setLayoutManager(layoutManager);


        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("http://173.212.226.143:8086/api/friendSaleOneRs")
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
                                friendSaleLists.add(new FriendSale(jsonHelper.GetResult("product_id"),jsonHelper.GetResult("productName"),jsonHelper.GetResult("item_id"),jsonHelper.GetResult("catagory_id"),jsonHelper.GetResult("actual_price"),jsonHelper.GetResult("discount_price"),jsonHelper.GetResult("discount_price_per"),jsonHelper.GetResult("status"),jsonHelper.GetResult("pimg"),jsonHelper.GetResult("vendor_id"),jsonHelper.GetResult("type"),jsonHelper.GetResult("datetime"),jsonHelper.GetResult("FakeRating"),jsonHelper.GetResult("req_users_shares"),jsonHelper.GetResult("new_users_atleast")));
                            }

                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    FriendSaleAllProductAdapter friendSaleAllProductAdapter = new FriendSaleAllProductAdapter((FriendsDealsAllActivity) getActivity(), friendSaleLists);
                                    friend_deal_all_recycleview.setAdapter(friendSaleAllProductAdapter);
                                }
                            });

                        }
                    }
                }
            }
        });


        return view;
    }


    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.rule_image:
                FriendDealRuleFragment friendDealRuleFragment = new FriendDealRuleFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameLayoutFriendDeal, friendDealRuleFragment).commit();
                break;
        }
    }


}
























