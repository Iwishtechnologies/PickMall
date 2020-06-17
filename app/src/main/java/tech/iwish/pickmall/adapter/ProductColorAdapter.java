package tech.iwish.pickmall.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import tech.iwish.pickmall.Interface.ProductColorInterFace;
import tech.iwish.pickmall.R;
import tech.iwish.pickmall.config.Constants;
import tech.iwish.pickmall.connection.JsonHelper;
import tech.iwish.pickmall.other.ChechColorList;
import tech.iwish.pickmall.other.ProductColorList;
import tech.iwish.pickmall.other.ProductSizeColorList;

public class ProductColorAdapter extends RecyclerView.Adapter<ProductColorAdapter.Viewholder> {

    private Context context;
    private List<ProductColorList> productColorLists;
    private List<ProductSizeColorList> productSizeColorLists;
    private ImageView product_image;
    private ProductColorInterFace productColorInterFace;
    private String dubledata, sizecolor, color, passcolor, sizeColor;
    private int currentSelectedPosition = RecyclerView.NO_POSITION;
    private int sizePosition, sizecount;
    private List<ChechColorList> chechColorLists;


//    public ProductColorAdapter(FragmentActivity activity, List<ProductColorList> productColorLists, ImageView product_image, ProductColorInterFace productColorInterFace, int sizePosition, List<ProductSizeColorList> productSizeColorLists) {
//        this.context = activity;
//        this.productColorLists = productColorLists;
//        this.product_image = product_image;
//        this.productColorInterFace = productColorInterFace;
//        this.sizePosition = sizePosition;
//        this.productSizeColorLists = productSizeColorLists;
//    }
    public ProductColorAdapter(FragmentActivity activity, List<ProductColorList> productColorLists, ImageView product_image, ProductColorInterFace productColorInterFace, int sizePosition, List<ProductSizeColorList> productSizeColorLists) {
        this.context = activity;
        this.productColorLists = productColorLists;
        this.product_image = product_image;
        this.productColorInterFace = productColorInterFace;
        this.sizePosition = sizePosition;
        this.productSizeColorLists = productSizeColorLists;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_product_color, null);
        Viewholder viewholder = new Viewholder(view);
        return viewholder;

    }

    @Override
    public void onBindViewHolder(@NonNull final Viewholder holder, final int position) {


        int productqty = Integer.parseInt(productColorLists.get(position).getQty());
        if (productqty > 0) {
            if (currentSelectedPosition == position) {
                if (color != null) {
                    if (sizecolor.equals(color)) {
                        holder.product_color.setBackground(context.getResources().getDrawable(R.drawable.size_click_design));
                    }
                } else {
                    Toast.makeText(context, "Size Select first", Toast.LENGTH_SHORT).show();
                }
            } else {
                holder.product_color.setBackground(context.getResources().getDrawable(R.drawable.size_design));
            }
        } else {
            Toast.makeText(context, "out of stock", Toast.LENGTH_SHORT).show();
        }


        if (currentSelectedPosition == position) {
            holder.product_color.setBackground(context.getResources().getDrawable(R.drawable.size_click_design));
        } else {
            holder.product_color.setBackground(context.getResources().getDrawable(R.drawable.size_design));
        }


    }

    @Override
    public int getItemCount() {
        return productColorLists.size();
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
                    String a = Constants.IMAGES + productColorLists.get(getAdapterPosition()).getImgname();
                    Glide.with(context).load(a).into(product_image);
                    passcolor = productColorLists.get(getAdapterPosition()).getColor();
                    productColorInterFace.productcolorresponse(passcolor, productColorLists.get(getAdapterPosition()).getImgname());
                    currentSelectedPosition = getAdapterPosition();
                    notifyDataSetChanged();

                    break;
            }
        }
    }
}




















