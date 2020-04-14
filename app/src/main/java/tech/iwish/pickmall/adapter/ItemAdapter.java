package tech.iwish.pickmall.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import tech.iwish.pickmall.Interface.ItemCategoryInterface;
import tech.iwish.pickmall.R;
import tech.iwish.pickmall.activity.MainActivity;
import tech.iwish.pickmall.activity.ProductActivity;
import tech.iwish.pickmall.config.Constants;
import tech.iwish.pickmall.other.ItemList;
import tech.iwish.pickmall.session.Share_session;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.Viewholder> {
    private List<ItemList> itemLists;
    private Context context;
    private Share_session share_session;
    private int lastposition = -1;
    private Viewholder viewholder;
    private ItemCategoryInterface itemCategoryInterface;
    private String checker = null;

    public ItemAdapter(Context context, List<ItemList> itemLists, ItemCategoryInterface itemCategoryInterface) {
        this.context = context;
        this.itemLists = itemLists;
        this.itemCategoryInterface = itemCategoryInterface;
    }


    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (context instanceof MainActivity) {
            View view = LayoutInflater.from(context).inflate(R.layout.row_item_cat, parent, false);
            viewholder = new Viewholder(view);
            if (itemLists.get(viewType).getItem_name().equals("")) {
                Animation animation = AnimationUtils.loadAnimation(viewholder.itemView.getContext(), R.anim.fade_item_animation);
                viewholder.itemView.setAnimation(animation);
                lastposition = viewType;
            }
        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.row_all_category_item, parent, false);
            viewholder = new Viewholder(view);

        }


        return viewholder;


    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, final int position) {

        if (!itemLists.get(position).getItem_name().equals("")) {

            if (context instanceof MainActivity) {

                holder.layoutItem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Bundle bundle = new Bundle();
                        Intent intent = new Intent(context, ProductActivity.class);
                        bundle.putString("item", itemLists.get(position).getItem_id());
                        bundle.putString("type", "product");
                        intent.putExtras(bundle);
//                    intent.putExtra("item",itemLists.get(position).getItem_id());
//                    intent.putExtra("type","product");
//                intent.putExtra("image",itemLists.get(position).getIcon_img());
                        context.startActivity(intent);
                    }
                });
                String a = Constants.IMAGES + itemLists.get(position).getIcon_img();
                Glide.with(context).load(a).into(holder.image);
                holder.nameCat.setText(itemLists.get(position).getItem_name());


            } else {

                holder.nameCat.setText(itemLists.get(position).getItem_name());
                if (checker == null) {
                    this.checker = "sdsdcdd";
                    itemCategoryInterface.itemcatinterface(itemLists.get(0).getItem_id());
                }
                holder.main_layout_category.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        itemCategoryInterface.itemcatinterface(itemLists.get(position).getItem_id());
                    }
                });
            }

        } else {
            Glide.with(context).load(context.getResources().getDrawable(R.drawable.placeholder_icon)).into(holder.image);
        }


    }

    @Override
    public int getItemCount() {
        return itemLists.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {

        private ImageView image;
        private LinearLayout layoutItem, main_layout_category;
        private TextView nameCat;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.image);
            layoutItem = (LinearLayout) itemView.findViewById(R.id.layoutItem);
            main_layout_category = (LinearLayout) itemView.findViewById(R.id.main_layout_category);
            nameCat = (TextView) itemView.findViewById(R.id.nameCat);


        }
    }
}
