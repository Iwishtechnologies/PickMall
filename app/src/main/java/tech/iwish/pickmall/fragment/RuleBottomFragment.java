package tech.iwish.pickmall.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

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
import tech.iwish.pickmall.Interface.UpdateFinalAmountData;
import tech.iwish.pickmall.R;
import tech.iwish.pickmall.activity.WishListActivity;
import tech.iwish.pickmall.adapter.BottonFriendRuleAdapter;
import tech.iwish.pickmall.adapter.WishListAdapter;
import tech.iwish.pickmall.config.Constants;
import tech.iwish.pickmall.connection.JsonHelper;
import tech.iwish.pickmall.other.BottomFragmentRuleList;
import tech.iwish.pickmall.other.WishlistList;
import tech.iwish.pickmall.session.Share_session;

public class RuleBottomFragment  extends AppCompatActivity {


    private ProgressBar progress_bar;
    Share_session shareSession;
    RecyclerView recycle;
    String sno;
    List<BottomFragmentRuleList>bottomFragmentRuleLists= new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rule_bottom_fragment_design);
        recycle= findViewById(R.id.recycle);
        SetRecycleView();
    }

    private void SetRecycleView(){
        Log.e("dfdg",getIntent().getStringExtra("sno"));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(RuleBottomFragment.this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recycle.setLayoutManager(linearLayoutManager);

        OkHttpClient client = new OkHttpClient();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("sno",getIntent().getStringExtra("sno"));

        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(JSON, jsonObject.toString());
        Request request = new Request.Builder().post(body)
                .url(Constants.FRIENDDEALPLAYRULE)
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
                                bottomFragmentRuleLists.add(new BottomFragmentRuleList(jsonHelper.GetResult("sno"), jsonHelper.GetResult("dealid"), jsonHelper.GetResult("rule")));
                            }
                            RuleBottomFragment.this.runOnUiThread(() -> {
                                if(bottomFragmentRuleLists.size()==0){
//                                        no_product.setVisibility(View.VISIBLE);
//                                        shimmer_view.setVisibility(View.GONE);
//                                        recyclerView.setVisibility(View.GONE);
                                }
                                else {
                                         BottonFriendRuleAdapter bottonFriendRuleAdapter = new BottonFriendRuleAdapter(RuleBottomFragment.this, bottomFragmentRuleLists);
////                                        shimmer_view.setVisibility(View.GONE);
////                                        recyclerView.setVisibility(View.VISIBLE);
                                        recycle.setAdapter(bottonFriendRuleAdapter);
                                }

                            });

                        }
                    }

                }
            }
        });


    }

}
