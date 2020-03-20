package tech.iwish.pickmall.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import tech.iwish.pickmall.R;
import tech.iwish.pickmall.activity.ProductDetailsActivity;
import tech.iwish.pickmall.config.Constants;
import tech.iwish.pickmall.other.ProductDetailsImageList;
import tech.iwish.pickmall.other.ProductSizeColorList;

public class ColorSizeImageAdapter extends RecyclerView.Adapter<ColorSizeImageAdapter.Viewholder> {

    private Context context;
    private String dubledata;
    private List<ProductSizeColorList> productSizeColorLists;
    private List<ProductDetailsImageList> productDetailsListImageList;
    private Boolean checks = true;

    public ColorSizeImageAdapter(ProductDetailsActivity productDetailsActivity, List<ProductSizeColorList> productSizeColorLists, List<ProductDetailsImageList> productDetailsListImageList) {
        this.productSizeColorLists = productSizeColorLists;
        this.context = productDetailsActivity;
        this.productDetailsListImageList = productDetailsListImageList;

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


        if(checks){
            this.checks = false ;
            String a = Constants.IMAGES+ productDetailsListImageList.get(position).getImage();
            Glide.with(context).load(a).into(holder.color_image);
        }

//        String val = productSizeColorLists.get(position).getColor();
//        if (dubledata != null) {
//            if (dubledata.equals(val)) {
//
//                holder.main_layout.setVisibility(View.GONE);
//                holder.main_layout.setLayoutParams(new RecyclerView.LayoutParams(0, 0));
//
//            } else {
//
////                String a = "http://173.212.226.143:8086/img/" + productSizeColorLists.get(position).getImgname();
////                Glide.with(context).load(a).into(holder.color_image);
//
//            }
//        } else {
//
//            String a = "http://173.212.226.143:8086/img/" + productSizeColorLists.get(position).getImgname();
//            Glide.with(context).load(a).into(holder.color_image);
//        }
//        this.dubledata = productSizeColorLists.get(position).getColor();



    }

    @Override
    public int getItemCount() {
        return productSizeColorLists.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {

        private ImageView color_image;
        private LinearLayout main_layout;

        public Viewholder(@NonNull View itemView) {
            super(itemView);

            color_image = (ImageView) itemView.findViewById(R.id.color_image);
            main_layout = (LinearLayout) itemView.findViewById(R.id.main_layout);
        }
    }
}
