package tech.iwish.pickmall.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import tech.iwish.pickmall.R;
import tech.iwish.pickmall.other.ProductDetailsList;

public class ProductSizeAdapter extends RecyclerView.Adapter<ProductSizeAdapter.Viewholder> {

    private Context context ;
    private List<ProductDetailsList> productDetailsListList ;

    public ProductSizeAdapter(FragmentActivity activity, List<ProductDetailsList> productDetailsListList) {
        this.context = activity;
        this.productDetailsListList = productDetailsListList;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.row_product_size , null);
        Viewholder viewholder = new Viewholder(view);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        holder.size_name.setText(productDetailsListList.get(position).getSize());
    }

    @Override
    public int getItemCount() {
        return productDetailsListList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {

        private TextView size_name;

        public Viewholder(@NonNull View itemView) {
            super(itemView);

            size_name = (TextView)itemView.findViewById(R.id.size_name);

        }
    }
}
