package tech.iwish.pickmall.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import tech.iwish.pickmall.R;
import tech.iwish.pickmall.activity.AllcategoryActivity;
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
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        holder.category_name.setText(categoryLists.get(position).getCategory_name());
    }

    @Override
    public int getItemCount() {
        return categoryLists.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {

        private TextView category_name;

        public Viewholder(@NonNull View itemView) {
            super(itemView);

            category_name = (TextView)itemView.findViewById(R.id.category_name);

        }
    }




}






















