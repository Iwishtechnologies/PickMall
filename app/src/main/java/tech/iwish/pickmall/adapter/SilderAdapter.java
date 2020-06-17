package tech.iwish.pickmall.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;

import java.util.List;

import tech.iwish.pickmall.R;
import tech.iwish.pickmall.activity.FriendsDealsAllActivity;
import tech.iwish.pickmall.activity.ProductActivity;
import tech.iwish.pickmall.config.Constants;
import tech.iwish.pickmall.other.SilderLists;

public class SilderAdapter extends PagerAdapter {

    private int[] image_res = {R.drawable.pick_mall_image,R.drawable.cart_icon} ;
    private Context context ;
    private LayoutInflater layoutInflater;
    private List<SilderLists> silderLists ;


    public SilderAdapter(Context context, List<SilderLists> silderListsList) {
        this.context = context;
        this.silderLists = silderListsList ;
    }

    @Override
    public int getCount() {

        return silderLists.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return (view==(LinearLayout)object);
    }
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View item_view = layoutInflater.inflate(R.layout.row_silder,container,false);

        ImageView imageView =item_view.findViewById(R.id.silder_image);
        LinearLayout silderLayout = item_view.findViewById(R.id.silderLayout);

//        imageView.setImageResource(image_res[position]);
//        Toast.makeText(context, ""+silderLists.get(position).getImage(), Toast.LENGTH_SHORT).show();
        String a = Constants.IMAGES+silderLists.get(position).getImage();
        Glide.with(context).load(a).into(imageView);

        silderLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
//                switch (silderLists.get(position).getCategoryid()) {
//                    case "friend_deal":
//                    case "90_Rs":
//                        intent = new Intent(new Intent(context, FriendsDealsAllActivity.class));
//                        intent.putExtra("item_id",itemLists.get(position).getItem_id());
//                        intent.putExtra("item_type",itemLists.get(position).getItem_type());
//                        context.startActivity(intent);
//                        break;
//                    case "one_rs":
//                        Toast.makeText(context, "One rs", Toast.LENGTH_SHORT).show();
//                        break;
//                    case "share_and_earn":
//                        Toast.makeText(context, "share_and_earn", Toast.LENGTH_SHORT).show();
//                        break;
//                    case "product":
//                    default:
//                        Bundle bundle = new Bundle();
//                        intent = new Intent(context, ProductActivity.class);
//                        bundle.putString("item_id", itemLists.get(position).getItem_id());
//                        bundle.putString("item_name", itemLists.get(position).getItem_name());
//                        bundle.putString("type", "MainActivity_product");
//                        intent.putExtras(bundle);
//                        context.startActivity(intent);
//                        break;
//                }
            }
        });



        container.addView(item_view);
        return item_view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {

        container.removeView((LinearLayout)object);

        //        super.destroyItem(container, position, object);
    }
}
