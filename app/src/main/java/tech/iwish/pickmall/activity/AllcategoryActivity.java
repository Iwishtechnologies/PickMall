package tech.iwish.pickmall.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

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
import tech.iwish.pickmall.Interface.ItemCategoryInterface;
import tech.iwish.pickmall.ProgressBar.CustomProgressbar;
import tech.iwish.pickmall.R;
import tech.iwish.pickmall.adapter.CategoryAdapter;
import tech.iwish.pickmall.adapter.ItemAdapter;
import tech.iwish.pickmall.config.Constants;
import tech.iwish.pickmall.connection.JsonHelper;
import tech.iwish.pickmall.fragment.Categoryfragment;
import tech.iwish.pickmall.other.CategoryList;
import tech.iwish.pickmall.other.GridSpacingItemDecoration;
import tech.iwish.pickmall.other.ItemList;

public class AllcategoryActivity extends AppCompatActivity  {

    private RecyclerView all_itemrecycleview ;
    private List<ItemList> itemLists = new ArrayList<>();
    private ItemAdapter itemAdapter;
    private ItemCategoryInterface itemCategoryInterface;
    private List<CategoryList> categoryLists = new ArrayList<>();
    private ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allcategory);

        all_itemrecycleview = (RecyclerView) findViewById(R.id.all_item);
        back= findViewById(R.id.back);


        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(AllcategoryActivity.this);
        linearLayoutManager1.setOrientation(RecyclerView.VERTICAL);
        all_itemrecycleview.setLayoutManager(linearLayoutManager1);

        allItemCategory();

        itemCategoryInterface = new ItemCategoryInterface() {
            @Override
            public void itemcatinterface(String value) {

                Bundle bundle = new Bundle();
                Categoryfragment categoryfragment = new Categoryfragment();
                bundle.putString("value",value);
                categoryfragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.categorylayout , categoryfragment).commit();

            }
        };

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }


    private void allItemCategory() {

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(Constants.ALL_CATEGORY_ITEM)
                .build();
        client.newCall(request).enqueue(new Callback() {
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
                                itemLists.add(new ItemList(jsonHelper.GetResult("item_id"), jsonHelper.GetResult("item_name"), jsonHelper.GetResult("icon_img"), jsonHelper.GetResult("type"), jsonHelper.GetResult("item_type")));
                            }
                            AllcategoryActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    itemAdapter = new ItemAdapter(AllcategoryActivity.this, itemLists, itemCategoryInterface);
                                    all_itemrecycleview.setAdapter(itemAdapter);
//                                    all_itemrecycleview.addItemDecoration(new GridSpacingItemDecoration(50));

                                }
                            });

                        }
                    }
                }
            }
        });

    }




}



























