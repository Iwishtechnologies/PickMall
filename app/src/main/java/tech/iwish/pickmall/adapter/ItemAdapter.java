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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;
import java.util.Map;

import tech.iwish.pickmall.Interface.ItemCategoryInterface;
import tech.iwish.pickmall.R;
import tech.iwish.pickmall.RetrofitModel.silderCategory.Category;
import tech.iwish.pickmall.activity.Account;
import tech.iwish.pickmall.activity.FriendsDealsAllActivity;
import tech.iwish.pickmall.activity.HomeActivity;
import tech.iwish.pickmall.activity.InviteActivity;
import tech.iwish.pickmall.activity.MainActivity;
import tech.iwish.pickmall.activity.One_winActivity;
import tech.iwish.pickmall.activity.ProductActivity;
import tech.iwish.pickmall.activity.Register1Activity;
import tech.iwish.pickmall.activity.WinningDetailActivity;
import tech.iwish.pickmall.config.Constants;
import tech.iwish.pickmall.other.ItemList;
import tech.iwish.pickmall.session.Share_session;

import static tech.iwish.pickmall.session.Share_session.USERMOBILE;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.Viewholder> {

    private final List<Category> itemLists;
    //    private List<ItemList> itemLists;
    private Context context;
    private Share_session share_session;
    private int lastposition = -1;
    private Viewholder viewholder;
    private ItemCategoryInterface itemCategoryInterface;
    private String checker = null;
    private int cuurentposition = RecyclerView.NO_POSITION;

//    public ItemAdapter(Context context, List<ItemList> itemLists, ItemCategoryInterface itemCategoryInterface) {
//        this.context = context;
//        this.itemLists = itemLists;
//        this.itemCategoryInterface = itemCategoryInterface;
//    }

    public ItemAdapter(Context context, List<Category> category, ItemCategoryInterface itemCategoryInterface) {
        this.context = context;
        this.itemLists = category;
        this.itemCategoryInterface = itemCategoryInterface;
    }


    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (context instanceof MainActivity) {
            View view = LayoutInflater.from(context).inflate(R.layout.row_item_cat, parent, false);
            viewholder = new Viewholder(view);
//            if (itemLists.get(viewType).getItemName().equals("")) {
//                Animation animation = AnimationUtils.loadAnimation(viewholder.itemView.getContext(), R.anim.fade_item_animation);
//                viewholder.itemView.setAnimation(animation);
//                lastposition = viewType;
//            }
        }


        return viewholder;


    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, final int position) {

        if (!itemLists.get(position).getItemName().equals("")) {
            if (context instanceof MainActivity) {

                holder.layoutItem.setOnClickListener(view -> {
                    Intent intent;
                    switch (itemLists.get(position).getItemType()) {
                        case "friend_deal":
                        case "90_Rs":
                            intent = new Intent(context, FriendsDealsAllActivity.class);
                            intent.putExtra("item_id",String.valueOf(itemLists.get(position).getItemId()));
                            intent.putExtra("item_type",itemLists.get(position).getItemType());
                            context.startActivity(intent);
                            break;
                        case "one_win":
                            Intent intent1 = new Intent(context, One_winActivity.class);
                            intent1.putExtra("item_id",String.valueOf(itemLists.get(position).getItemId()));
                            intent1.putExtra("item_type",itemLists.get(position).getItemType());
                            context.startActivity(intent1);
                            break;
                        case "winner":
                            Intent intent2 = new Intent(context, WinningDetailActivity.class);
                            intent2.putExtra("item_id",String.valueOf(itemLists.get(position).getItemId()));
                            intent2.putExtra("item_type",itemLists.get(position).getItemType());
                            context.startActivity(intent2);
                            break;
                        case "share":
                             Map data = null;
                            share_session = new Share_session(context);
                            data = share_session.Fetchdata();
                            if (data.get(USERMOBILE) != null) {
                                intent = new Intent(context, InviteActivity.class);
                                context.startActivity(intent);
                            } else {
                                intent = new Intent(context, Register1Activity.class);
                                context.startActivity(intent);
                            }
                            break;
                        case "product":
                        default:
                            Bundle bundle = new Bundle();
                            intent = new Intent(context, ProductActivity.class);
                            bundle.putString("item_id", String.valueOf(itemLists.get(position).getItemId()));
                            bundle.putString("item_name", itemLists.get(position).getItemName());
                            bundle.putString("type", "MainActivity_product");
                            intent.putExtras(bundle);
                            context.startActivity(intent);
                            break;
                    }

                });
                String a = Constants.IMAGES + itemLists.get(position).getIconImg();
                Glide.with(context).load(a).into(holder.image);
                holder.nameCat.setText(itemLists.get(position).getItemName());


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
