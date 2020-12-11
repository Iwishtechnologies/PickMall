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

import java.util.ArrayList;
import java.util.List;

import tech.iwish.pickmall.R;
import tech.iwish.pickmall.activity.ProductDetailsActivity;
import tech.iwish.pickmall.config.Constants;
import tech.iwish.pickmall.other.ProductList;

public class VendorStoreLimitProductAdapter extends RecyclerView.Adapter<VendorStoreLimitProductAdapter.Viewholder> {

    private List<ProductList> productListList;
    Context context;

    public VendorStoreLimitProductAdapter(List<ProductList> productListList) {
        this.productListList = productListList;
    }

    @NonNull
    @Override
    public VendorStoreLimitProductAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.row_vendor_product_limit,null);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VendorStoreLimitProductAdapter.Viewholder holder, int position) {

        ProductList list = productListList.get(position);

        String a = Constants.IMAGES + list.getPimg();
        Glide.with(context).load(a).into(holder.image);
        holder.Actualprice.setText(context.getResources().getString(R.string.rs_symbol)+list.getActual_price());
        SpannableString content = new SpannableString(context.getResources().getString(R.string.rs_symbol) + list.getDiscount_price());
        content.setSpan(new StrikethroughSpan(), 0, content.length(), 0);
        holder.discountprice.setText(content);
        holder.mainview.setOnClickListener(v -> {
            Intent intent = new Intent(context, ProductDetailsActivity.class);
            intent.putExtra("product_name", list.getProductName());
            intent.putExtra("actual_price", list.getActual_price());
            intent.putExtra("discount_price", list.getDiscount_price());
            intent.putExtra("product_id", list.getProduct_id());
            intent.putExtra("product_Image", list.getPimg());
            intent.putExtra("vendor_id", list.getVendor_id());
            intent.putExtra("gst", list.getGst());
            intent.putExtra("prepaid", "");
            intent.putExtra("product_type", "allProduct");
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return productListList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView Actualprice,discountprice;
        LinearLayout mainview;

        public Viewholder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.image);
            Actualprice = itemView.findViewById(R.id.actual_price_flash);
            discountprice = itemView.findViewById(R.id.dicount_price_flash);
            mainview = itemView.findViewById(R.id.mainview);

        }
    }
}



































