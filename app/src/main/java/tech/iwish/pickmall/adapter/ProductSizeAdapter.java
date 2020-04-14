package tech.iwish.pickmall.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import tech.iwish.pickmall.Interface.ProductSizeInterFace;
import tech.iwish.pickmall.R;
import tech.iwish.pickmall.other.ProductDetailsImageList;
import tech.iwish.pickmall.other.ProductSizeColorList;

public class ProductSizeAdapter extends RecyclerView.Adapter<ProductSizeAdapter.Viewholder> {

    private Context context;
    private List<ProductSizeColorList> productSizeColorLists;
    private ProductSizeInterFace productSizeInterFace;
    private String dubledata;
    private int currentSelectedPosition = RecyclerView.NO_POSITION;


    public ProductSizeAdapter(FragmentActivity activity, List<ProductSizeColorList> productSizeColorLists, ProductSizeInterFace productSizeInterFace) {
        this.context = activity;
        this.productSizeColorLists = productSizeColorLists;
        this.productSizeInterFace = productSizeInterFace;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.row_product_size, null);
        Viewholder viewholder = new Viewholder(view);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, final int position) {


//
//        String val = productSizeColorLists.get(position).getSize();
//        if (dubledata != null) {
//            if (dubledata.equals(val)) {
//                holder.main_layout_size.setVisibility(View.GONE);
//                holder.main_layout_size.setLayoutParams(new RecyclerView.LayoutParams(0, 0));
//            } else {
//                holder.main_layout_size.setEnabled(true);
//                holder.size_name.setText(productSizeColorLists.get(position).getSize());
//            }
//        } else {
//
//            holder.main_layout_size.setEnabled(false);
//            holder.size_name.setText(productSizeColorLists.get(position).getSize());
//        }
//
//        this.dubledata = productSizeColorLists.get(position).getSize();

        holder.size_name.setText(productSizeColorLists.get(position).getSize());

        if (currentSelectedPosition == position) {
            holder.size_name.setBackground(context.getResources().getDrawable(R.drawable.size_click_design));
        } else {
            holder.size_name.setBackground(context.getResources().getDrawable(R.drawable.size_design));
        }


    }

    @Override
    public int getItemCount() {
        return productSizeColorLists.size();
    }


    public class Viewholder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView size_name;
        private LinearLayout main_layout_size;

        public Viewholder(@NonNull View itemView) {
            super(itemView);

            size_name = (TextView) itemView.findViewById(R.id.size_name);
            main_layout_size = (LinearLayout) itemView.findViewById(R.id.main_layout_size);
            main_layout_size.setOnClickListener(this);

        }


        @Override
        public void onClick(View view) {

            int id = view.getId();

            switch (id) {
                case R.id.main_layout_size:
                    productSizeInterFace.productSizeResponse(productSizeColorLists.get(getAdapterPosition()).getSize());
                    currentSelectedPosition = getAdapterPosition();
                    notifyDataSetChanged();
                    break;
            }
        }
    }
}















