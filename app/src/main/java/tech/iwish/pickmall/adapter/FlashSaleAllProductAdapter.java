package tech.iwish.pickmall.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.SpannableString;
import android.text.style.StrikethroughSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.facebook.shimmer.ShimmerFrameLayout;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import tech.iwish.pickmall.R;
import tech.iwish.pickmall.activity.FlashSaleProductactivity;
import tech.iwish.pickmall.activity.ProductDetailsActivity;
import tech.iwish.pickmall.activity.WishListActivity;
import tech.iwish.pickmall.config.Constants;
import tech.iwish.pickmall.connection.JsonHelper;
import tech.iwish.pickmall.other.ProductList;
import tech.iwish.pickmall.other.WishlistList;


public class FlashSaleAllProductAdapter extends RecyclerView.Adapter<FlashSaleAllProductAdapter.Viewholder> {

    private FlashSaleProductactivity context;
    private List<ProductList> productListList ;
    private boolean shimmer = true;
    private int shimmernumber = 5;
    String nextSale;


    public FlashSaleAllProductAdapter(FlashSaleProductactivity flashSaleProductactivity, List<ProductList> productListList , String nextSale) {
        this.context = flashSaleProductactivity;
        this.productListList = productListList;
        this.nextSale = nextSale;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

//        View view = LayoutInflater.from(context).inflate(R.layout.row_flash_sale_all_product , null);
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_flash_sale_all_product, null);

        Viewholder viewholder = new Viewholder(view);
        return viewholder;

    }


    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, final int position) {

        if (!productListList.get(position).getActual_price().equals("")) {

            holder.shimmerLayout.stopShimmer();
            holder.shimmerLayout.setShimmer(null);

            holder.product_name_flash.setBackground(null);
            holder.amount_flash.setBackground(null);


            if(!nextSale.isEmpty()){
                holder.buy_now.setVisibility(View.GONE);
            }else {
                holder.buy_now.setVisibility(View.VISIBLE);
            }

            holder.amount_flash.setText(context.getResources().getString(R.string.rs_symbol) + productListList.get(position).getActual_price());

            SpannableString content = new SpannableString(context.getResources().getString(R.string.rs_symbol) + productListList.get(position).getDiscount_price());
            content.setSpan(new StrikethroughSpan(), 0, content.length(), 0 );
            holder.dicount_price_flash.setText(content);


            String a = Constants.IMAGES + productListList.get(position).getPimg();
            Glide.with(context).load(a).into(holder.image_flash_sale);

            holder.product_name_flash.setText(productListList.get(position).getProductName());
            getStock(productListList.get(position).getProduct_id(),holder);
            //=========================================================
            float actual = Float.valueOf(productListList.get(position).getActual_price());
            float dis = Float.valueOf( productListList.get(position).getDiscount_price());
            float disco = dis - actual;
            float fin = disco / dis * 100;
            int aa = (int) fin;
            holder.off.setText(aa+"% off");
            //=========================================================
            holder.percent_price.setText(productListList.get(position).getDiscount_price_per() +" "+aa+"% OFF");
            holder.percent_price.setVisibility(View.GONE);


            holder.flash_main_layout.setOnClickListener(view -> {
                Intent intent = new Intent(new Intent(context, ProductDetailsActivity.class));
                intent.putExtra("product_name", productListList.get(position).getProductName());
                intent.putExtra("actual_price", productListList.get(position).getActual_price());
                intent.putExtra("discount_price", productListList.get(position).getDiscount_price());
                intent.putExtra("product_id", productListList.get(position).getProduct_id());
                intent.putExtra("product_Image", productListList.get(position).getPimg());
                intent.putExtra("vendor_id", productListList.get(position).getVendor_id());
                intent.putExtra("discount_price_per", productListList.get(position).getDiscount_price_per());
                intent.putExtra("gst", productListList.get(position).getGst());
                intent.putExtra("sku", productListList.get(position).getSKU());
//                intent.putExtra("product_type", "flashSale");
                intent.putExtra("product_type", "allProduct");
                context.startActivity(intent);
            });


        }


    }


    @Override
    public int getItemCount() {
        return productListList.size();
//        return flashsalemainLists.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {

        private ShimmerFrameLayout shimmerLayout;
        private TextView product_name_flash, amount_flash, dicount_price_flash, percent_price,buy_now,off;
        private ImageView image_flash_sale;
        private LinearLayout flash_main_layout;
        ProgressBar progressBar;

        public Viewholder(@NonNull View itemView) {
            super(itemView);

            shimmerLayout = (ShimmerFrameLayout) itemView.findViewById(R.id.shimmerLayout);
            product_name_flash = (TextView) itemView.findViewById(R.id.product_name_flash);
            amount_flash = (TextView) itemView.findViewById(R.id.amount_flash);
            dicount_price_flash = (TextView) itemView.findViewById(R.id.dicount_price_flash);
            percent_price = (TextView) itemView.findViewById(R.id.percent_price);
            image_flash_sale = (ImageView) itemView.findViewById(R.id.image_flash_sale);
            flash_main_layout = (LinearLayout) itemView.findViewById(R.id.flash_main_layout);
            progressBar =  itemView.findViewById(R.id.progress_2);
            buy_now =  itemView.findViewById(R.id.buy_now);
            off =  itemView.findViewById(R.id.off);

        }
    }


    private void getStock(String pid,Viewholder viewholder){
        OkHttpClient client = new OkHttpClient();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("product_id",pid);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(JSON, jsonObject.toString());
        Request request = new Request.Builder().post(body)
                .url(Constants.GETSTOCK)
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
                                context.runOnUiThread(() -> {
                            float stock=Float.valueOf(jsonHelper.GetResult("stock"));
                                   viewholder.progressBar.setProgress((int)stock);
                                });
                            }
                        }
                    }

                }
            }
        });

    }
}






















