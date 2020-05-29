package tech.iwish.pickmall.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.SpannableString;
import android.text.style.StrikethroughSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.List;

import tech.iwish.pickmall.R;
import tech.iwish.pickmall.activity.FlashSaleProductactivity;
import tech.iwish.pickmall.activity.ProductDetailsActivity;
import tech.iwish.pickmall.config.Constants;
import tech.iwish.pickmall.other.ProductList;


public class FlashSaleAllProductAdapter extends RecyclerView.Adapter<FlashSaleAllProductAdapter.Viewholder> {

    private Context context;
    private List<ProductList> productListList ;
    private boolean shimmer = true;
    private int shimmernumber = 5;


    public FlashSaleAllProductAdapter(FlashSaleProductactivity flashSaleProductactivity, List<ProductList> productListList) {
        this.context = flashSaleProductactivity;
        this.productListList = productListList;
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


            holder.amount_flash.setText(context.getResources().getString(R.string.rs_symbol) + productListList.get(position).getActual_price());

            SpannableString content = new SpannableString(context.getResources().getString(R.string.rs_symbol) + productListList.get(position).getDiscount_price());
            content.setSpan(new StrikethroughSpan(), 0, content.length(), 0 );
            holder.dicount_price_flash.setText(content);


            String a = Constants.IMAGES + productListList.get(position).getPimg();
            Glide.with(context).load(a).into(holder.image_flash_sale);
            holder.product_name_flash.setText(productListList.get(position).getProductName());
            holder.percent_price.setText(productListList.get(position).getDiscount_price_per() +"%");
            holder.flash_main_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
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
                    intent.putExtra("product_type", "flashSale");
                    context.startActivity(intent);
                }
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
        private TextView product_name_flash, amount_flash, dicount_price_flash, percent_price;
        private ImageView image_flash_sale;
        private LinearLayout flash_main_layout;

        public Viewholder(@NonNull View itemView) {
            super(itemView);

            shimmerLayout = (ShimmerFrameLayout) itemView.findViewById(R.id.shimmerLayout);
            product_name_flash = (TextView) itemView.findViewById(R.id.product_name_flash);
            amount_flash = (TextView) itemView.findViewById(R.id.amount_flash);
            dicount_price_flash = (TextView) itemView.findViewById(R.id.dicount_price_flash);
            percent_price = (TextView) itemView.findViewById(R.id.percent_price);
            image_flash_sale = (ImageView) itemView.findViewById(R.id.image_flash_sale);
            flash_main_layout = (LinearLayout) itemView.findViewById(R.id.flash_main_layout);

        }
    }
}






















