package tech.iwish.pickmall.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import tech.iwish.pickmall.RetrofitModel.silderCategory.Slider;
import tech.iwish.pickmall.activity.FriendsDealsAllActivity;
import tech.iwish.pickmall.activity.HomeActivity;
import tech.iwish.pickmall.activity.MainActivity;
import tech.iwish.pickmall.activity.ProductActivity;
import tech.iwish.pickmall.config.Constants;
import tech.iwish.pickmall.other.SilderLists;

public class SilderAdapter extends PagerAdapter {


    private int[] image_res = {R.drawable.pick_mall_image, R.drawable.cart_icon};
    private Context context;
    private LayoutInflater layoutInflater;
    //    private List<SilderLists> silderLists;
    private final List<Slider> silderLists;


//    public SilderAdapter(Context context, List<SilderLists> silderListsList) {
//        this.context = context;
//        this.silderLists = silderListsList;
//    }

    public SilderAdapter(MainActivity context, List<Slider> slider) {
        this.context = context;
        this.silderLists = slider;
    }

    @Override
    public int getCount() {

        return silderLists.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return (view == (LinearLayout) object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View item_view = layoutInflater.inflate(R.layout.row_silder, container, false);

        ImageView imageView = item_view.findViewById(R.id.silder_image);
        LinearLayout silderLayout = item_view.findViewById(R.id.silderLayout);

//        imageView.setImageResource(image_res[position]);
//        Toast.makeText(context, ""+silderLists.get(position).getImage(), Toast.LENGTH_SHORT).show();
        String a = Constants.IMAGES + silderLists.get(position).getImage();
        Glide.with(context).load(a).into(imageView);


        if (silderLists.get(position).getCategoryid() != null) {
            silderLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    if (silderLists.get(position).getCategoryid().isEmpty()) {


                        Intent intent = new Intent(context, ProductActivity.class);
                        intent.putExtra("type", "silder_load");
                        intent.putExtra("item_id", silderLists.get(position).getItemName());
                        context.startActivity(intent);

                    } else {

                        Intent intent = new Intent(context, ProductActivity.class);
                        intent.putExtra("type", "both_category_open");
                        intent.putExtra("item_id", silderLists.get(position).getItemName());
                        intent.putExtra("category_id", silderLists.get(position).getCategoryid());
                        context.startActivity(intent);

                    }

                }
            });
        }


        container.addView(item_view);
        return item_view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {

        container.removeView((LinearLayout) object);

        //        super.destroyItem(container, position, object);
    }
}
