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
import tech.iwish.pickmall.other.ProductOverViewList;

public class ProductOverviewAdapter extends RecyclerView.Adapter<ProductOverviewAdapter.Viewholder> {

    private List<ProductOverViewList> productOverViewLists;
    private Context context;

    public ProductOverviewAdapter(FragmentActivity activity, List<ProductOverViewList> productOverViewLists) {
        this.context = activity;
        this.productOverViewLists = productOverViewLists;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_product_overview, null);
        Viewholder viewholder = new Viewholder(view);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        holder.overview_product.setText(productOverViewLists.get(position).getTitle());
    }

    @Override
    public int getItemCount() {

        return productOverViewLists.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {

        private TextView overview_product;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            overview_product = (TextView) itemView.findViewById(R.id.overview_product);
        }
    }
}
