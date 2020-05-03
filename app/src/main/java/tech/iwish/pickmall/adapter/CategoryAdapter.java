package tech.iwish.pickmall.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import tech.iwish.pickmall.R;
import tech.iwish.pickmall.activity.AllcategoryActivity;
import tech.iwish.pickmall.activity.ProductActivity;
import tech.iwish.pickmall.config.Constants;
import tech.iwish.pickmall.other.CategoryList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.Viewholder>{


    private Context context ;
    private List<CategoryList> categoryLists ;

    public CategoryAdapter(Context context, List<CategoryList> categoryLists) {

    this.context = context;
    this.categoryLists = categoryLists;

    }


    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_category_show,null);
        Viewholder viewholder = new Viewholder(view);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, final int position) {

        String a = Constants.IMAGES + categoryLists.get(position).getImg();
        Glide.with(context).load(a).into(holder.category_image);
        holder.category_name.setText(categoryLists.get(position).getCategory_name());
        holder.main_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("onClick: ", categoryLists.get(position).getCatagory_id());
                Bundle bundle = new Bundle();
                Intent intent = new Intent(context, ProductActivity.class);
                bundle.putString("category_id", categoryLists.get(position).getCatagory_id());
                bundle.putString("category_name", categoryLists.get(position).getCategory_name());
                bundle.putString("type", "Category_by_product");
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return categoryLists.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {

        private TextView category_name;
        private ImageView category_image;
        private RelativeLayout main_layout;

        public Viewholder(@NonNull View itemView) {
            super(itemView);

            category_name = (TextView)itemView.findViewById(R.id.category_name);
            category_image = (ImageView) itemView.findViewById(R.id.category_image);
            main_layout = (RelativeLayout) itemView.findViewById(R.id.main_layout);

        }
    }




}






















