package tech.iwish.pickmall.adapter;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import tech.iwish.pickmall.R;
import tech.iwish.pickmall.fragment.ProductSideColorBottomFragment;
import tech.iwish.pickmall.other.ProductDetailsList;

public class ProductColorAdapter extends RecyclerView.Adapter<ProductColorAdapter.Viewholder> {

    private Context context;
    private List<ProductDetailsList> productDetailsListList;
    private ImageView product_image;
    private int currentSelectedPosition = RecyclerView.NO_POSITION;

    public ProductColorAdapter(FragmentActivity activity, List<ProductDetailsList> productDetailsListList, ImageView product_image) {
        this.context = activity;
        this.productDetailsListList = productDetailsListList;
        this.product_image = product_image;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_product_color, null);
        Viewholder viewholder = new Viewholder(view);
        return viewholder;

    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, final int position) {
        holder.product_color.setText(productDetailsListList.get(position).getColor());
        holder.color_main_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentSelectedPosition = position;
                notifyDataSetChanged();
            }
        });
        if (currentSelectedPosition == position) {

            String a = "http://173.212.226.143:8086/img/" + productDetailsListList.get(currentSelectedPosition).getImgname();
            Glide.with(context).load(a).into(product_image);
        }
    }

    @Override
    public int getItemCount() {
        return productDetailsListList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {

        private TextView product_color;
        private LinearLayout color_main_layout;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            product_color = (TextView) itemView.findViewById(R.id.product_color);
            color_main_layout = (LinearLayout) itemView.findViewById(R.id.color_main_layout);
        }
    }
}
