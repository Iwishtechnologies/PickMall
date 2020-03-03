package tech.iwish.pickmall.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.List;

import tech.iwish.pickmall.R;
import tech.iwish.pickmall.activity.ProductDetailsActivity;
import tech.iwish.pickmall.adapter.ProductColorAdapter;
import tech.iwish.pickmall.adapter.ProductSizeAdapter;
import tech.iwish.pickmall.other.ProductDetailsList;


public class ProductSideColorBottomFragment extends BottomSheetDialogFragment implements View.OnClickListener {

    private List<ProductDetailsList> productDetailsListList;
    private RecyclerView size_product_recycleview, color_product_recycleview;
    private ImageView product_image;
    private ImageView sub_button, add_button;
    private TextView quty_value , product_names;

    public ProductSideColorBottomFragment(List<ProductDetailsList> productDetailsListList) {
        this.productDetailsListList = productDetailsListList;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_side_color_bottom, container, false);

        size_product_recycleview = (RecyclerView) view.findViewById(R.id.size_product_recycleview);
        color_product_recycleview = (RecyclerView) view.findViewById(R.id.color_product_recycleview);
        product_image = (ImageView) view.findViewById(R.id.product_image);
        sub_button = (ImageView) view.findViewById(R.id.sub_button);
        add_button = (ImageView) view.findViewById(R.id.add_button);
        quty_value = (TextView) view.findViewById(R.id.quty_value);
        product_names = (TextView) view.findViewById(R.id.product_names);

        add_button.setOnClickListener(this);
        sub_button.setOnClickListener(this);
        Bundle bundle = getArguments();
        String product_name =  bundle.getString("product_name ");
        product_names.setText(product_name);

        String a = "http://173.212.226.143:8086/img/" + productDetailsListList.get(0).getImgname();
        Glide.with(ProductSideColorBottomFragment.this).load(a).into(product_image);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        size_product_recycleview.setLayoutManager(linearLayoutManager);


        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getActivity());
        linearLayoutManager1.setOrientation(RecyclerView.HORIZONTAL);
        color_product_recycleview.setLayoutManager(linearLayoutManager1);


        ProductSizeAdapter productSizeAdapter = new ProductSizeAdapter(getActivity(), productDetailsListList);
        size_product_recycleview.setAdapter(productSizeAdapter);

        ProductColorAdapter productColorAdapter = new ProductColorAdapter(getActivity(), productDetailsListList , product_image);
        color_product_recycleview.setAdapter(productColorAdapter);
        return view;
    }



    @Override
    public void onClick(View view) {
        int id = view.getId();

        switch (id) {
            case R.id.add_button:
                String value = quty_value.getText().toString();
                int a = Integer.parseInt(value);
                int total = a + 1;
                String b = String.valueOf(total);
                quty_value.setText(b);
                break;
            case R.id.sub_button:

                String values = quty_value.getText().toString();
                int as = Integer.parseInt(values);
                if (as != 0) {
                    int totals = as - 1;
                    String bs = String.valueOf(totals);
                    quty_value.setText(bs);
                }
                break;
        }
    }
}



















