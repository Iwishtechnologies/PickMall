package tech.iwish.pickmall.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.facebook.shimmer.ShimmerFrameLayout;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import cn.gavinliu.android.lib.shapedimageview.ShapedImageView;
import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import tech.iwish.pickmall.R;
import tech.iwish.pickmall.activity.MyOederActitvity;
import tech.iwish.pickmall.activity.OrderDetailActivity;
import tech.iwish.pickmall.activity.ProductDetailsActivity;
import tech.iwish.pickmall.config.Constants;
import tech.iwish.pickmall.connection.JsonHelper;
import tech.iwish.pickmall.extended.TextViewFont;
import tech.iwish.pickmall.other.OrderList;
import tech.iwish.pickmall.other.WishlistList;

public class MyOrderAdapter extends RecyclerView.Adapter<MyOrderAdapter.ViewHolder> {
    private MyOederActitvity context ;
    private List<OrderList> orderLists ;
    HashMap<String,String>product= new HashMap<>();

    public  MyOrderAdapter(MyOederActitvity  context,List<OrderList>orderLists) {
        this.context= context;
        this.orderLists=orderLists;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_design_myorder , null);
        ViewHolder viewHolder= new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        GetProduct(orderLists.get(position).getProdeuct_id(),orderLists.get(position).getType(),holder,position);
    }

    @Override
    public int getItemCount() {
        return orderLists.size();
    }

    public class ViewHolder extends  RecyclerView.ViewHolder {
        ShapedImageView image,dot;
        TextViewFont name,status;
        LinearLayout product;
        private ShimmerFrameLayout shimmerLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image=itemView.findViewById(R.id.image);
            dot=itemView.findViewById(R.id.dot);
            name=itemView.findViewById(R.id.name);
            status=itemView.findViewById(R.id.status);
            product=itemView.findViewById(R.id.product);
            shimmerLayout=itemView.findViewById(R.id.shimmerLayout);
        }
    }



    private void GetProduct(final String product_id, String product_type, final ViewHolder viewHolder, final int position){
        OkHttpClient client = new OkHttpClient();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id",product_id);
            jsonObject.put("type",product_type);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(JSON, jsonObject.toString());
        Request request = new Request.Builder().post(body)
                .url("http://173.212.226.143:8086/api/orderProducts")
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

                    final JsonHelper jsonHelper = new JsonHelper(result);
                    if (jsonHelper.isValidJson()) {
                        String responses = jsonHelper.GetResult("response");
                        if (responses.equals("TRUE")) {
                            JSONArray jsonArray = jsonHelper.setChildjsonArray(jsonHelper.getCurrentJsonObj(), "data");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                jsonHelper.setChildjsonObj(jsonArray, i);

                                context.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Glide.with(context).load(Constants.IMAGES+jsonHelper.GetResult("pimg")).into(viewHolder.image);
                                        viewHolder.name.setText(jsonHelper.GetResult("ProductName"));
                                        viewHolder.status.setText(orderLists.get(position).getStatus());
//                                      viewHolder.status.setText(jsonHelper.GetResult("status"));
                                        viewHolder.name.setText(jsonHelper.GetResult("ProductName"));
                                        viewHolder.shimmerLayout.stopShimmer();
                                        viewHolder.shimmerLayout.setShimmer(null);
                                        viewHolder.product.setAlpha(1);
                                        final Intent intent= new Intent(context, OrderDetailActivity.class);
                                        intent.putExtra("ProductName",jsonHelper.GetResult("ProductName"));
                                        intent.putExtra("item_id",jsonHelper.GetResult("item_id"));
                                        intent.putExtra("catagory_id",jsonHelper.GetResult("catagory_id"));
                                        intent.putExtra("actual_price",jsonHelper.GetResult("actual_price"));
                                        intent.putExtra("discount_price",jsonHelper.GetResult("discount_price"));
                                        intent.putExtra("discount_price_per",jsonHelper.GetResult("discount_price_per"));
                                        intent.putExtra("status",jsonHelper.GetResult("status"));
                                        intent.putExtra("pimg",jsonHelper.GetResult("pimg"));
                                        intent.putExtra("vendor_id",jsonHelper.GetResult("vendor_id"));
                                        intent.putExtra("FakeRating",jsonHelper.GetResult("FakeRating"));
                                        intent.putExtra("orderid",orderLists.get(position).getOrder_id());
                                        intent.putExtra("address",orderLists.get(position).getDelivery_add());
                                        intent.putExtra("orderamt",orderLists.get(position).getOoder_amountt());
                                        intent.putExtra("orderStatus",orderLists.get(position).getStatus());
                                        intent.putExtra("orderdate",orderLists.get(position).getDate_time());
                                        intent.putExtra("product_id",product_id);
                                        viewHolder.product.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                              context.startActivity(intent);
                                            }
                                        });


                                    }
                                });




                            }


                        }
                    }

                }
            }
        });

    }
}
