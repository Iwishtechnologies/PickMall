package tech.iwish.pickmall.fragment;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import java.util.Map;

import tech.iwish.pickmall.Interface.ProductColorInterFace;
import tech.iwish.pickmall.Interface.ProductSizeInterFace;
import tech.iwish.pickmall.R;
import tech.iwish.pickmall.activity.Signup;
import tech.iwish.pickmall.adapter.ProductColorAdapter;
import tech.iwish.pickmall.adapter.ProductSizeAdapter;
import tech.iwish.pickmall.config.Constants;
import tech.iwish.pickmall.other.ProductDetailsImageList;
import tech.iwish.pickmall.other.ProductSizeColorList;
import tech.iwish.pickmall.session.Share_session;
import tech.iwish.pickmall.sqlconnection.MyhelperSql;

import static tech.iwish.pickmall.session.Share_session.LOGIN_CHECk;


public class ProductSideColorBottomFragment extends BottomSheetDialogFragment implements View.OnClickListener, ProductSizeInterFace, ProductColorInterFace {

    private List<ProductSizeColorList> productSizeColorLists;
    private RecyclerView size_product_recycleview, color_product_recycleview;
    private ImageView product_image;
    private ImageView sub_button, add_button;
    private TextView quty_value, product_names, actual_prices;
    private Button confirm_add_to_card;
    private String select_color, select_size, product_id, product_name, actual_price, imagename, product_qty;
    private Share_session shareSession;
    private Map data;

    public ProductSideColorBottomFragment(List<ProductSizeColorList> productDetailsListImageList) {
        this.productSizeColorLists = productDetailsListImageList;
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
        actual_prices = (TextView) view.findViewById(R.id.actual_prices);
        confirm_add_to_card = (Button) view.findViewById(R.id.confirm_add_to_card);

        add_button.setOnClickListener(this);
        sub_button.setOnClickListener(this);
        confirm_add_to_card.setOnClickListener(this);
        Bundle bundle = getArguments();
        product_name = bundle.getString("product_name");
        actual_price = bundle.getString("actual_price");
        product_id = bundle.getString("product_id");

        product_names.setText(product_name);
        actual_prices.setText(actual_price);

        String a = Constants.IMAGES + productSizeColorLists.get(0).getImgname();
        Glide.with(ProductSideColorBottomFragment.this).load(a).into(product_image);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        size_product_recycleview.setLayoutManager(linearLayoutManager);


        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getActivity());
        linearLayoutManager1.setOrientation(RecyclerView.HORIZONTAL);
        color_product_recycleview.setLayoutManager(linearLayoutManager1);


        ProductSizeAdapter productSizeAdapter = new ProductSizeAdapter(getActivity(), productSizeColorLists, this);
        size_product_recycleview.setAdapter(productSizeAdapter);

        ProductColorAdapter productColorAdapter = new ProductColorAdapter(getActivity(), productSizeColorLists, product_image, this);
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
                if (as != 1) {
                    int totals = as - 1;
                    String bs = String.valueOf(totals);
                    quty_value.setText(bs);
                }
                break;
            case R.id.confirm_add_to_card:
                if (select_size == null) {
                    Toast.makeText(getActivity(), "Select Size", Toast.LENGTH_SHORT).show();
                } else if (select_color == null) {
                    Toast.makeText(getActivity(), "Select color", Toast.LENGTH_SHORT).show();
                } else {
                    InsertDataCard();

                }
                break;

        }
    }

    private void InsertDataCard() {

        shareSession = new Share_session(getActivity());
        data = shareSession.Fetchdata();

        shareSession.Login_check();

        if (data.get(LOGIN_CHECk) != null) {
            product_qty = quty_value.getText().toString();
            MyhelperSql myhelperSql = new MyhelperSql(getActivity());
            SQLiteDatabase sqLiteDatabase = myhelperSql.getWritableDatabase();
            myhelperSql.dataAddCard(product_id, product_name, product_qty, select_color, select_size, imagename, actual_price, sqLiteDatabase);
            dismiss();
        } else {
            getActivity().startActivity(new Intent(getActivity(), Signup.class));
        }


    }

    @Override
    public void productSizeResponse(String val) {
        this.select_size = val;
    }

    @Override
    public void productcolorresponse(String color, String imagename) {
        this.select_color = color;
        this.imagename = imagename;
    }
}



















