package tech.iwish.pickmall.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import tech.iwish.pickmall.Interface.AllCategoryInterface;
import tech.iwish.pickmall.Interface.ItemCategoryInterface;
import tech.iwish.pickmall.R;
import tech.iwish.pickmall.other.ItemList;

public class AllItemAdapter extends RecyclerView.Adapter<AllItemAdapter.Viewholder> {

    Context context;
    private List<ItemList> itemLists;
    private AllCategoryInterface itemCategoryInterface;
    private String checker = null;
    private int cuurentposition = RecyclerView.NO_POSITION;

    public AllItemAdapter(List<ItemList> itemLists, AllCategoryInterface itemCategoryInterface) {
        this.itemLists = itemLists;
        this.itemCategoryInterface = itemCategoryInterface;
    }


    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        context = parent.getContext().getApplicationContext();
        View view = LayoutInflater.from(parent.getContext().getApplicationContext()).inflate(R.layout.row_all_category_item, parent, false);
        return new Viewholder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {

        holder.nameCat.setText(itemLists.get(position).getItem_name());
        if (checker == null) {
            this.checker = "sdsdcdd";
            itemCategoryInterface.allitemcatinterface(String.valueOf(itemLists.get(0).getItem_id()));
            cuurentposition = 0;

        }
        holder.main_layout_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemCategoryInterface.allitemcatinterface(String.valueOf(itemLists.get(position).getItem_id()));
                cuurentposition = position;
                notifyDataSetChanged();
            }
        });

        if (cuurentposition == position) {
            holder.main_layout_category.setBackgroundColor(context.getResources().getColor(android.R.color.white));
        } else {
            holder.main_layout_category.setBackgroundColor(context.getResources().getColor(R.color.silderColor));
        }


    }

    @Override
    public int getItemCount() {
        return itemLists.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        private ImageView image;
        private LinearLayout layoutItem;
        private TextView nameCat;
        private RelativeLayout main_layout_category;


        public Viewholder(@NonNull View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.image);
            layoutItem = (LinearLayout) itemView.findViewById(R.id.layoutItem);
            main_layout_category = (RelativeLayout) itemView.findViewById(R.id.main_layout_category);
            nameCat = (TextView) itemView.findViewById(R.id.nameCat);


        }
    }
}
