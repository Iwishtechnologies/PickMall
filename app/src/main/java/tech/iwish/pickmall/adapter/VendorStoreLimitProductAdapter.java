package tech.iwish.pickmall.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import tech.iwish.pickmall.R;
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

    }

    @Override
    public int getItemCount() {
        return productListList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {

        ImageView image;

        public Viewholder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.image);

        }
    }
}



































