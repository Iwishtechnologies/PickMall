package tech.iwish.pickmall.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import tech.iwish.pickmall.R;
import tech.iwish.pickmall.other.ProductDescriptionlist;

public class ProductDescriptionAdapter extends RecyclerView.Adapter<ProductDescriptionAdapter.Viewholder> {

    private Context context ;
    private List<ProductDescriptionlist> productDescriptionlists ;

    public ProductDescriptionAdapter(FragmentActivity activity, List<ProductDescriptionlist> productDescriptionlists) {
        this.context = activity ;
        this.productDescriptionlists = productDescriptionlists ;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.row_product_description , null);
        Viewholder viewholder = new Viewholder(view);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        holder.title_des.setText(productDescriptionlists.get(position).getDescription_title());
        holder.des_details.setText(productDescriptionlists.get(position).getDescription_data());
    }

    @Override
    public int getItemCount() {
        return productDescriptionlists.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {

        private TextView title_des ,des_details;

        public Viewholder(@NonNull View itemView) {
            super(itemView);

            title_des = (TextView)itemView.findViewById(R.id.title_des);
            des_details = (TextView)itemView.findViewById(R.id.des_details);

        }
    }
}
















