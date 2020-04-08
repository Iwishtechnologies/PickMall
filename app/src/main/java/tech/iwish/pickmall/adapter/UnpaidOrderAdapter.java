package tech.iwish.pickmall.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.HashMap;

import cn.gavinliu.android.lib.shapedimageview.ShapedImageView;
import tech.iwish.pickmall.R;
import tech.iwish.pickmall.extended.TextViewFont;

public class UnpaidOrderAdapter extends RecyclerView.Adapter<UnpaidOrderAdapter.ViewHolder> {
    private ArrayList<HashMap<String, String>> datalist;
    private Context context;


    public UnpaidOrderAdapter(Context context, ArrayList<HashMap<String, String>> product_data) {
        this.datalist=product_data;
        this.context=context;

    }

    @NonNull
    @Override
    public UnpaidOrderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_unpaid_order_design, null);
        ViewHolder viewHolder= new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull UnpaidOrderAdapter.ViewHolder holder, int position) {
        Glide.with(context).load(datalist.get(position).get("PRODUCT_IMAGE")).into(holder.product_image);
        holder.unpaid_product_name.setText(datalist.get(position).get("PRODUCT_NAME"));
        holder.unpaid_product_size.setText(datalist.get(position).get("PRODUCT_SIZE"));
        holder.unpaid_product_amount.setText(datalist.get(position).get("PRODUCT_AMOUNT"));
        holder.unpaid_product_qty.setText(datalist.get(position).get("PRODUCT_QTY"));
        holder.unpaid_product_seller.setText(datalist.get(position).get("PRODUCT_SELLER_NAME"));
        holder.unpaid_product_color.setText(datalist.get(position).get("PRODUCT_COLOR"));
    }

    @Override
    public int getItemCount() {
        return datalist.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder{
        ShapedImageView product_image;
        TextViewFont unpaid_product_name,unpaid_product_size,unpaid_product_amount,unpaid_product_qty,unpaid_product_seller,unpaid_product_color;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            product_image= itemView.findViewById(R.id.unpaid_product_image);
            unpaid_product_name= itemView.findViewById(R.id.unpaid_product_name);
            unpaid_product_size= itemView.findViewById(R.id.unpaid_product_size);
            unpaid_product_amount= itemView.findViewById(R.id.unpaid_product_size);
            unpaid_product_qty= itemView.findViewById(R.id.unpaid_product_size);
            unpaid_product_seller= itemView.findViewById(R.id.unpaid_product_seller);
            unpaid_product_color= itemView.findViewById(R.id.unpaid_product_color);
        }
    }
}
