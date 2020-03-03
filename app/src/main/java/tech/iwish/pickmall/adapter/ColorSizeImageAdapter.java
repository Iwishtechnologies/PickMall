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
import tech.iwish.pickmall.activity.ProductDetailsActivity;
import tech.iwish.pickmall.other.ProductDetailsList;

public class ColorSizeImageAdapter extends RecyclerView.Adapter<ColorSizeImageAdapter.Viewholder> {

    private Context context;
    private List<ProductDetailsList> productDetailsListList;

    public ColorSizeImageAdapter(ProductDetailsActivity productDetailsActivity, List<ProductDetailsList> productDetailsListList) {
        this.context = productDetailsActivity;
        this.productDetailsListList = productDetailsListList;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_color_size_image, null);
        Viewholder viewholder = new Viewholder(view);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        String a = "http://173.212.226.143:8086/img/" + productDetailsListList.get(position).getImgname();
        Glide.with(context).load(a).into(holder.color_image);
    }

    @Override
    public int getItemCount() {
        return productDetailsListList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {

        private ImageView color_image;

        public Viewholder(@NonNull View itemView) {
            super(itemView);

            color_image = (ImageView) itemView.findViewById(R.id.color_image);

        }
    }
}
