package tech.iwish.pickmall.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;

import java.util.List;

import tech.iwish.pickmall.R;
import tech.iwish.pickmall.activity.ProductDetailsActivity;
import tech.iwish.pickmall.other.ProductDetailsImageList;

public class ProductDetailsImageAdapter extends PagerAdapter {

    private List<ProductDetailsImageList> productDetailsListImageList;
    private Context context ;
    private LayoutInflater layoutInflater;

    public ProductDetailsImageAdapter(ProductDetailsActivity productDetailsActivity, List<ProductDetailsImageList> productDetailsListImageList) {
        this.productDetailsListImageList = productDetailsListImageList;
        this.context = productDetailsActivity ;
    }

    @Override
    public int getCount() {
        return productDetailsListImageList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return (view==(LinearLayout)object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View item_view = layoutInflater.inflate(R.layout.row_bottom_product_details,container,false);
        ImageView imageView =item_view.findViewById(R.id.prooductDetailsImages);


        String a = "http://173.212.226.143:8086/img/"+ productDetailsListImageList.get(position).getImage();
        Glide.with(context).load(a).into(imageView);
        container.addView(item_view);
        return item_view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
//        destroyItem(container, position, object);
        container.removeView((LinearLayout)object);
    }
}
