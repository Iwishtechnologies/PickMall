package tech.iwish.pickmall.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import tech.iwish.pickmall.R;
import tech.iwish.pickmall.activity.MainActivity;
import tech.iwish.pickmall.adapter.FlashSaleAdapter;
import tech.iwish.pickmall.adapter.FriendRuleAdapter;
import tech.iwish.pickmall.config.Constants;
import tech.iwish.pickmall.connection.JsonHelper;
import tech.iwish.pickmall.other.FlashsalemainList;
import tech.iwish.pickmall.other.RuleList;

public class FriendDealRuleFragment extends Fragment {

    private RecyclerView friend_deal_rule_play_recycleview;
    private List<RuleList> ruleListList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_friend_deal_rule, null);

        friend_deal_rule_play_recycleview = (RecyclerView) view.findViewById(R.id.friend_deal_rule_play_recycleview);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        friend_deal_rule_play_recycleview.setLayoutManager(linearLayoutManager);


        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(Constants.FRIEND_DEAL_RULE)
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
                                ruleListList.add(new RuleList(jsonHelper.GetResult("sno"), jsonHelper.GetResult("title"), jsonHelper.GetResult("subtitle"), jsonHelper.GetResult("icon")));
                            }

                            if (getActivity() != null) {
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        FriendRuleAdapter friendRuleAdapter = new FriendRuleAdapter(getActivity(), ruleListList);
                                        friend_deal_rule_play_recycleview.setAdapter(friendRuleAdapter);
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
