package tech.iwish.pickmall.adapter;

import android.content.Context;
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

import tech.iwish.pickmall.Interface.ProductColorInterFace;
import tech.iwish.pickmall.R;
import tech.iwish.pickmall.config.Constants;
import tech.iwish.pickmall.other.ProductDetailsImageList;
import tech.iwish.pickmall.other.ProductSizeColorList;

public class ProductColorAdapter extends RecyclerView.Adapter<ProductColorAdapter.Viewholder> {

    private Context context;
    private List<ProductSizeColorList> productSizeColorLists;
    private ImageView product_image;
    private ProductColorInterFace productColorInterFace;
    private String dubledata;
    private int currentSelectedPosition = RecyclerView.NO_POSITION;

    public ProductColorAdapter(FragmentActivity activity, List<ProductSizeColorList> productSizeColorLists, ImageView product_image, ProductColorInterFace productColorInterFace) {
        this.context = activity;
        this.productSizeColorLists = productSizeColorLists;
        this.product_image = product_image;
        this.productColorInterFace = productColorInterFace;
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

//        String val = productSizeColorLists.get(position).getColor();
//        if (dubledata != null) {
//            if (dubledata.equals(val)) {
//                holder.color_main_layout.setVisibility(View.GONE);
//                holder.color_main_layout.setLayoutParams(new RecyclerView.LayoutParams(0, 0));
//            } else {
//                holder.product_color.setText(productSizeColorLists.get(position).getColor());
//                holder.product_color.setBackground(context.getResources().getDrawable(R.drawable.size_design));
//                holder.color_main_layout.setEnabled(true);
//            }
//        } else {
//            holder.product_color.setText(productSizeColorLists.get(position).getColor());
//            holder.product_color.setBackground(context.getResources().getDrawable(R.drawable.size_click_design));
//            holder.color_main_layout.setEnabled(false);
//        }
//
//        this.dubledata = productSizeColorLists.get(position).getColor();

        if (currentSelectedPosition == position) {
            holder.product_color.setBackground(context.getResources().getDrawable(R.drawable.size_click_design));
        }else {
            holder.product_color.setBackground(context.getResources().getDrawable(R.drawable.size_design));
        }

    }

    @Override
    public int getItemCount() {
        return productSizeColorLists.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView product_color;
        private LinearLayout color_main_layout;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            product_color = (TextView) itemView.findViewById(R.id.product_color);
            color_main_layout = (LinearLayout) itemView.findViewById(R.id.color_main_layout);
            color_main_layout.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            int id = view.getId();
            switch (id) {
                case R.id.color_main_layout:
                    String a = Constants.IMAGES + productSizeColorLists.get(getAdapterPosition()).getImgname();
                    Glide.with(context).load(a).into(product_image);
                    productColorInterFace.productcolorresponse(productSizeColorLists.get(getAdapterPosition()).getColor(), productSizeColorLists.get(getAdapterPosition()).getImgname());
                    currentSelectedPosition = getAdapterPosition();
                    notifyDataSetChanged();
                    break;
            }
        }
    }
}




















