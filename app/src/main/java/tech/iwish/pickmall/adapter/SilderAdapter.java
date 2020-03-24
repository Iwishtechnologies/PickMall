package tech.iwish.pickmall.adapter;

import android.content.Context;
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
//        imageView.setImageResource(image_res[position]);
//        Toast.makeText(context, ""+silderLists.get(position).getImage(), Toast.LENGTH_SHORT).show();
        String a = "http://173.212.226.143:8086/img/"+silderLists.get(position).getImage();
        Glide.with(context).load(a).into(imageView);

        container.addView(item_view);
        return item_view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {

        container.removeView((LinearLayout)object);

        //        super.destroyItem(container, position, object);
    }
}
