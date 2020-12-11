package tech.iwish.pickmall.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.StrikethroughSpan;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.List;
import java.util.Map;

import tech.iwish.pickmall.Interface.ProductColorInterFace;
import tech.iwish.pickmall.Interface.ProductCountInterface;
import tech.iwish.pickmall.Interface.ProductSizeInterFace;
import tech.iwish.pickmall.R;
import tech.iwish.pickmall.activity.AddressActivity;
import tech.iwish.pickmall.activity.CardActivity;
import tech.iwish.pickmall.activity.Register1Activity;
import tech.iwish.pickmall.activity.SaveAddressActivity;
import tech.iwish.pickmall.activity.LoginActivity;
import tech.iwish.pickmall.adapter.ProductColorAdapter;
import tech.iwish.pickmall.adapter.ProductSizeAdapter;
import tech.iwish.pickmall.config.Constants;
import tech.iwish.pickmall.other.ProductColorList;
import tech.iwish.pickmall.other.ProductSizeColorList;
import tech.iwish.pickmall.session.Share_session;
import tech.iwish.pickmall.sqlconnection.MyhelperSql;

import static tech.iwish.pickmall.session.Share_session.HOUSE_NO_ADDRESS;
import static tech.iwish.pickmall.session.Share_session.LOGIN_CHECk;
import static tech.iwish.pickmall.session.Share_session.NAME_ADDRESS;
import static tech.iwish.pickmall.session.Share_session.USERMOBILE;


public class ProductSideColorBottomFragment extends BottomSheetDialogFragment implements View.OnClickListener,
        ProductSizeInterFace {

    private List<ProductSizeColorList> productSizeColorLists;
    private RecyclerView size_product_recycleview, color_product_recycleview;
    private ImageView product_image;
    private ImageView sub_button, add_button;
    private TextView quty_value, product_names, actual_prices, dicount_price, percent;
    private Button confirm_add_to_card, go_to_card, buy_now;
    private String select_color, select_size, product_id, gst, vendor_id,
            product_dicount_percent, product_name, actual_price, imagename,
            product_qty, discount_price, product_type, type, prepaid;
    private Share_session shareSession;
    private Map data;
    private ProductCountInterface productCountInterface;
    private ProductColorInterFace productColorInterFace;
    private List<ProductColorList> productColorLists;
    private int sizeselectPosition;
    private LinearLayout orderLayout;


    public ProductSideColorBottomFragment(List<ProductSizeColorList> productSizeColorLists, ProductCountInterface productCountInterface) {
        this.productSizeColorLists = productSizeColorLists;
        this.productCountInterface = productCountInterface;
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
        dicount_price = (TextView) view.findViewById(R.id.dicount_price);
        percent = (TextView) view.findViewById(R.id.percent);


        confirm_add_to_card = (Button) view.findViewById(R.id.confirm_add_to_card);
        go_to_card = (Button) view.findViewById(R.id.go_to_card);
        buy_now = (Button) view.findViewById(R.id.buy_now);

        orderLayout = (LinearLayout) view.findViewById(R.id.orderLayout);


        add_button.setOnClickListener(this);
        sub_button.setOnClickListener(this);
        confirm_add_to_card.setOnClickListener(this);
        go_to_card.setOnClickListener(this);
        buy_now.setOnClickListener(this);

        productColorInterFace = new ProductColorInterFace() {
            @Override
            public void productcolorresponse(String color, String imagenames) {
                select_color = color;
                imagename = imagenames;
            }
        };

        Bundle bundle = getArguments();


        product_name = bundle.getString("product_name");
        actual_price = bundle.getString("actual_price");
        product_id = bundle.getString("product_id");
        discount_price = bundle.getString("discount_price");
        product_type = bundle.getString("product_type");
        type = bundle.getString("type");
        gst = bundle.getString("gst");
        vendor_id = bundle.getString("vendor_id");
        product_dicount_percent = bundle.getString("product_dicount_percent");
        imagename = bundle.getString("imagename");
        product_qty = bundle.getString("product_qty");
        prepaid = bundle.getString("prepaid");


        product_names.setText(product_name);
        actual_prices.setText(getResources().getString(R.string.rs_symbol) + actual_price);
        percent.setText(product_dicount_percent + "% OFF");


        SpannableString content = new SpannableString(getResources().getString(R.string.rs_symbol) + discount_price);
        content.setSpan(new StrikethroughSpan(), 0, content.length(), 0);
        dicount_price.setText(content);


        String a = Constants.IMAGES + bundle.get("imagename");
        Glide.with(ProductSideColorBottomFragment.this).load(a).into(product_image);


        MyhelperSql myhelperSql = new MyhelperSql(getActivity());
        SQLiteDatabase sqLiteDatabase = myhelperSql.getWritableDatabase();
        String query = "Select *  from PRODUCT_CARD WHERE PRODUCT_ID = ?";
        Cursor cursor = sqLiteDatabase.rawQuery(query, new String[]{product_id});
        cursor.moveToFirst();
        if (cursor.getCount() != 0) {
            go_to_card.setVisibility(View.VISIBLE);
            orderLayout.setVisibility(View.GONE);

        } else {
            go_to_card.setVisibility(View.GONE);
            orderLayout.setVisibility(View.VISIBLE);
        }


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        size_product_recycleview.setLayoutManager(linearLayoutManager);


        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getActivity());
        linearLayoutManager1.setOrientation(RecyclerView.HORIZONTAL);
        color_product_recycleview.setLayoutManager(linearLayoutManager1);

        ProductSizeAdapter productSizeAdapter = new ProductSizeAdapter(getActivity(), productSizeColorLists, this);
        size_product_recycleview.setAdapter(productSizeAdapter);

//        ProductColorAdapter productColorAdapter = new ProductColorAdapter(getActivity(), productColorLists, product_image, productColorInterFace, -1, productSizeColorLists);
//        color_product_recycleview.setAdapter(productColorAdapter);


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
                } else {
                    InsertDataCard();
                }
                break;
            case R.id.go_to_card:
                getActivity().startActivity(new Intent(getActivity(), CardActivity.class));
                break;
            case R.id.buy_now:
                if (select_size == null) {
                    Toast.makeText(getActivity(), "Select Size", Toast.LENGTH_SHORT).show();
                } else {
                    shareSession = new Share_session(getContext());
                    data = shareSession.Fetchdata();

                    if (data.get(USERMOBILE) != null) {
                        if (data.get(NAME_ADDRESS) != null && data.get(HOUSE_NO_ADDRESS) != null) {

                            Intent intent = new Intent(getContext(), SaveAddressActivity.class);
                            intent.putExtra("product_name", product_name);
                            intent.putExtra("actual_price", actual_price);
                            intent.putExtra("product_id", product_id);
                            intent.putExtra("discount_price", discount_price);
                            intent.putExtra("product_qty", product_qty);
                            intent.putExtra("imagename", imagename);
                            intent.putExtra("select_size", select_size);
                            intent.putExtra("prepaid", prepaid);
                            intent.putExtra("product_type", getActivity().getIntent().getStringExtra("product_type"));
                            intent.putExtra("type", "buy_now");
                            getActivity().startActivity(intent);

                        } else {
                            Intent intent1 = new Intent(getActivity(), AddressActivity.class);
                            intent1.putExtra("product_name", product_name);
                            intent1.putExtra("actual_price", actual_price);
                            intent1.putExtra("product_id", product_id);
                            intent1.putExtra("discount_price", discount_price);
                            intent1.putExtra("product_qty", product_qty);
                            intent1.putExtra("imagename", imagename);
                            intent1.putExtra("select_size", select_size);
                            intent1.putExtra("prepaid", prepaid);
                            intent1.putExtra("product_type", getActivity().getIntent().getStringExtra("product_type"));
                            intent1.putExtra("type", "buy_now");
                            startActivity(intent1);
                        }

                    } else {
                        Intent intent = new Intent(getActivity(), Register1Activity.class);
                        startActivity(intent);
                    }

                }
                break;

        }
    }

    private void InsertDataCard() {
        shareSession = new Share_session(getActivity());
        data = shareSession.Fetchdata();
        if (data.get(LOGIN_CHECk) != null) {
            switch (type) {
                case "add_to_card":
//                    prepaid set
                    MyhelperSql myhelperSql = new MyhelperSql(getActivity());
                    SQLiteDatabase sqLiteDatabase = myhelperSql.getWritableDatabase();
                    myhelperSql.dataAddCard(product_id, product_name, product_qty, select_color, select_size, imagename, actual_price, discount_price, product_type, gst, vendor_id, product_dicount_percent,prepaid, sqLiteDatabase);
                    productCountInterface.counntproduct();
                    dismiss();
                    break;
//                case "buy_now":
//                    data = shareSession.Fetchdata();
//                    if ((data.get(NUMBER_ADDRESS) != null) && (data.get(PINCODE_ADDRESS) != null) && (data.get(HOUSE_NO_ADDRESS) != null)) {
//                        Intent intent = new Intent(getActivity(), SaveAddressActivity.class);
//                        intent.putExtra("product_id", product_id);
//                        intent.putExtra("product_name", product_name);
//                        intent.putExtra("select_size", select_size);
//                        intent.putExtra("actual_price", actual_price);
//                        intent.putExtra("discount_price", discount_price);
//                        intent.putExtra("imagename", imagename);
//                        intent.putExtra("select_color", select_color);
//                        intent.putExtra("product_qty", quty_value.getText().toString());
//                        intent.putExtra("product_type", product_type);
//                        intent.putExtra("type", "buy_now");
//                        intent.putExtra("gst", gst);
//                        startActivity(intent);
//                    } else {
//                        Intent intent = new Intent(getActivity(), AddressActivity.class);
//                        intent.putExtra("product_id", product_id);
//                        intent.putExtra("product_name", product_name);
//                        intent.putExtra("select_size", select_size);
//                        intent.putExtra("actual_price", actual_price);
//                        intent.putExtra("discount_price", discount_price);
//                        intent.putExtra("imagename", imagename);
//                        intent.putExtra("product_qty", quty_value.getText().toString());
//                        intent.putExtra("select_color", select_color);
//                        intent.putExtra("product_type", product_type);
//                        intent.putExtra("gst", gst);
//                        intent.putExtra("type", "buy_now");
//                        intent.putExtra("gst", gst);
//                        startActivity(intent);
//                    }
//                    break;
//                case "friendDeal_one_rs":
//                    data = shareSession.Fetchdata();
//                    if ((data.get(NUMBER_ADDRESS) != null) && (data.get(PINCODE_ADDRESS) != null) && (data.get(HOUSE_NO_ADDRESS) != null)) {
//                        Intent intent = new Intent(getActivity(), SaveAddressActivity.class);
//                        intent.putExtra("product_id", product_id);
//                        intent.putExtra("product_name", product_name);
//                        intent.putExtra("select_size", select_size);
//                        intent.putExtra("actual_price", actual_price);
//                        intent.putExtra("discount_price", discount_price);
//                        intent.putExtra("imagename", imagename);
//                        intent.putExtra("select_color", select_color);
//                        intent.putExtra("product_qty", quty_value.getText().toString());
//                        intent.putExtra("product_type", product_type);
//                        intent.putExtra("type", "friendDeal_one_rs");
//                        intent.putExtra("gst", gst);
//                        startActivity(intent);
//                    } else {
//                        Intent intent = new Intent(getActivity(), AddressActivity.class);
//                        intent.putExtra("product_id", product_id);
//                        intent.putExtra("product_name", product_name);
//                        intent.putExtra("select_size", select_size);
//                        intent.putExtra("actual_price", actual_price);
//                        intent.putExtra("discount_price", discount_price);
//                        intent.putExtra("imagename", imagename);
//                        intent.putExtra("product_qty", quty_value.getText().toString());
//                        intent.putExtra("select_color", select_color);
//                        intent.putExtra("product_type", product_type);
//                        intent.putExtra("gst", gst);
//                        intent.putExtra("type", "friendDeal_one_rs");
//                        intent.putExtra("gst", gst);
//                        startActivity(intent);
//                    }
//                    break;
            }
        } else {
            getActivity().startActivity(new Intent(getActivity(), Register1Activity.class));
        }


    }

    @Override
    public void productSizeResponse(String val, final int position) {
        this.select_size = val;
        sizeselectPosition = position;
        ProductColorAdapter productColorAdapter = new ProductColorAdapter(getActivity(), productColorLists, product_image, productColorInterFace, position, productSizeColorLists);
        color_product_recycleview.setAdapter(productColorAdapter);

    }


/*
    @Override
    public void setupDialog(@NonNull Dialog dialog, int style) {
        BottomSheetDialog bottomSheetDialog = (BottomSheetDialog) dialog;
        bottomSheetDialog.setContentView(R.layout.fragment_product_side_color_bottom);

        try {
            Field behaviorField = bottomSheetDialog.getClass().getDeclaredField("behavior");
            behaviorField.setAccessible(true);
            final BottomSheetBehavior behavior = (BottomSheetBehavior) behaviorField.get(bottomSheetDialog);
            behavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {

                @Override
                public void onStateChanged(@NonNull View bottomSheet, int newState) {
                    if (newState == BottomSheetBehavior.STATE_DRAGGING){
                        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                    }
                }

                @Override
                public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                }
            });
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
*/


    //     hight set bottom
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                BottomSheetDialog bottomSheetDialog = (BottomSheetDialog) dialogInterface;
                setupFullHeight(bottomSheetDialog);
            }
        });
        return dialog;
    }


    private void setupFullHeight(BottomSheetDialog bottomSheetDialog) {
        FrameLayout bottomSheet = (FrameLayout) bottomSheetDialog.findViewById(R.id.design_bottom_sheet);
        BottomSheetBehavior behavior = BottomSheetBehavior.from(bottomSheet);
        behavior.setPeekHeight(1000);


        ViewGroup.LayoutParams layoutParams = bottomSheet.getLayoutParams();
//        int windowHeight = getWindowHeight();
        int windowHeight = getWindowHeight() - 300;
        if (layoutParams != null) {
            layoutParams.height = windowHeight;
        }
        bottomSheet.setLayoutParams(layoutParams);
        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);


    }

    private int getWindowHeight() {
        // Calculate window height for fullscreen use
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }


}



















