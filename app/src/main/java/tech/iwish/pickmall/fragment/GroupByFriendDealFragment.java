package tech.iwish.pickmall.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.shimmer.ShimmerFrameLayout;

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
import tech.iwish.pickmall.adapter.GroupbyAdapter;
import tech.iwish.pickmall.config.Constants;
import tech.iwish.pickmall.connection.JsonHelper;
import tech.iwish.pickmall.other.FriendSale;


public class GroupByFriendDealFragment extends Fragment {

    private RecyclerView groupFrienDeal;
    private List<FriendSale> friendSaleLists = new ArrayList<>();
    private com.facebook.shimmer.ShimmerFrameLayout shimmerFrameLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_group_by_friend_deal, null);

        groupFrienDeal = (RecyclerView) view.findViewById(R.id.groupFrienDeal);
        shimmerFrameLayout = view.findViewById(R.id.shimmerFrameLayout);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        groupFrienDeal.setLayoutManager(linearLayoutManager);


        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(Constants.FRIEND_SALE_ONE_RS)
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
                                friendSaleLists.add(new FriendSale(jsonHelper.GetResult("product_id"), jsonHelper.GetResult("productName"), jsonHelper.GetResult("item_id"), jsonHelper.GetResult("catagory_id"), jsonHelper.GetResult("actual_price"), jsonHelper.GetResult("discount_price"), jsonHelper.GetResult("discount_price_per"), jsonHelper.GetResult("status"), jsonHelper.GetResult("pimg"), jsonHelper.GetResult("vendor_id"), jsonHelper.GetResult("type"), jsonHelper.GetResult("datetime"), jsonHelper.GetResult("FakeRating"), jsonHelper.GetResult("req_users_shares"), jsonHelper.GetResult("new_users_atleast")));
                            }
                            if (getActivity() != null) {
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {

                                        GroupbyAdapter groupbyAdapter = new GroupbyAdapter(getActivity(), friendSaleLists);
                                        groupFrienDeal.setAdapter(groupbyAdapter);
                                        shimmerFrameLayout.stopShimmer();
                                        shimmerFrameLayout.setShimmer(null);

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