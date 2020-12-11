package tech.iwish.pickmall.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

import tech.iwish.pickmall.R;
import tech.iwish.pickmall.other.ProductSizeColorList;
import tech.iwish.pickmall.session.Share_session;

import static tech.iwish.pickmall.session.Share_session.FILTER_LIST;
import static tech.iwish.pickmall.session.Share_session.FILTER_LIST_COLOR;

public class ProductFilterAdapter extends RecyclerView.Adapter<ProductFilterAdapter.Viewholder> {

    private Context context;
    private List<ProductSizeColorList> productSizeColorLists;
    private String filterName ,checker;

    private Share_session shareSession;
    private Map data;

    private JSONObject SizeFilter = new JSONObject();
    private JSONObject ColorFilter = new JSONObject();



    public ProductFilterAdapter(FragmentActivity activity, List<ProductSizeColorList> productSizeColorLists, String filterName) {
        this.context = activity;
        this.productSizeColorLists = productSizeColorLists;
        this.filterName = filterName;
        shareSession = new Share_session(context);
        data = shareSession.Fetchdata();
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.row_product_filter, null);
        Viewholder viewholder = new Viewholder(view);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull final Viewholder holder, final int position) {


        switch (filterName) {
            case "Size":

                if (data.get(FILTER_LIST) != null) {
                    Log.e( "dataa",data.get(FILTER_LIST).toString() );
                    try {
                        JSONObject jsonObjec1 = new JSONObject(data.get(FILTER_LIST).toString());
                        SizeFilter = jsonObjec1;

                        if(productSizeColorLists.get(position).getSize().equals(SizeFilter.getString(productSizeColorLists.get(position).getSize()))){
                            productSizeColorLists.get(position).setSelected(true);
                            if (productSizeColorLists.get(position).isSelected()) {
                                holder.sizeLinearLayout.setBackgroundColor(Color.CYAN);
                            } else {
                                holder.sizeLinearLayout.setBackgroundColor(Color.WHITE);
                            }
                            Log.e("array", SizeFilter.getString(productSizeColorLists.get(position).getSize()));
                        }else {
                            Log.e("array", "lolo");
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }


                holder.filterSizeName.setText(productSizeColorLists.get(position).getSize());
                holder.sizeLinearLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        productSizeColorLists.get(position).setSelected(!productSizeColorLists.get(position).isSelected());
                        if (productSizeColorLists.get(position).isSelected()) {

//                            if (data.get(FILTER_LIST) != null) {
//
//                                holder.sizeLinearLayout.setBackgroundColor(Color.CYAN);
//
//                                if(checker == null){
//                                    checker = "scdscsdcdsc";
//                                    try {
//                                        JSONObject jsonObjec1 = new JSONObject(data.get(FILTER_LIST).toString());
//                                        SizeFilter = jsonObjec1;
//                                        SizeFilter.put(productSizeColorLists.get(position).getSize(), productSizeColorLists.get(position).getSize());
//                                        shareSession.filterMethod(SizeFilter);
//                                        Log.e("array", SizeFilter.toString());
//                                    } catch (JSONException e) {
//                                        e.printStackTrace();
//                                    }
//
//                                }else {
//                                    holder.sizeLinearLayout.setBackgroundColor(Color.CYAN);
//                                    try {
//                                        SizeFilter.put(productSizeColorLists.get(position).getSize(), productSizeColorLists.get(position).getSize());
//                                        shareSession.filterMethod(SizeFilter);
//                                        Log.e("array", SizeFilter.toString());
//                                    } catch (JSONException e) {
//                                        e.printStackTrace();
//                                    }
//
//                                }
//
//                            }
//
//                            else {
                                holder.sizeLinearLayout.setBackgroundColor(Color.CYAN);
                                try {

                                    SizeFilter.put(productSizeColorLists.get(position).getSize(), productSizeColorLists.get(position).getSize());
                                    shareSession.filterMethod(SizeFilter);
                                    Log.e("array", SizeFilter.toString());
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

//                            }
                        } else {

//                            if (data.get(FILTER_LIST) != null) {
//
//                                holder.sizeLinearLayout.setBackgroundColor(Color.WHITE);
//                                JSONObject jsonObjec1 = null;
//                                try {
//                                    jsonObjec1 = new JSONObject(data.get(FILTER_LIST).toString());
//                                    SizeFilter = jsonObjec1;
//                                    SizeFilter.remove(productSizeColorLists.get(position).getSize());
//                                    Log.e("onClick:", SizeFilter.toString());
//                                    shareSession.filterMethod(SizeFilter);
//
//                                } catch (JSONException e) {
//                                    e.printStackTrace();
//                                }
//
//                            } else {
                                holder.sizeLinearLayout.setBackgroundColor(Color.WHITE);
                                SizeFilter.remove(productSizeColorLists.get(position).getSize());
                                Log.e("onClick:", SizeFilter.toString());
                                shareSession.filterMethod(SizeFilter);
//                            }
                        }
                    }
                });
                break;
            case "Color":

                if (data.get(FILTER_LIST_COLOR) != null) {
                    Log.e( "dataa",data.get(FILTER_LIST_COLOR).toString() );
                    try {
                        JSONObject jsonObjec1 = new JSONObject(data.get(FILTER_LIST_COLOR).toString());
                        ColorFilter = jsonObjec1;

                        if(productSizeColorLists.get(position).getColor().equals(ColorFilter.getString(productSizeColorLists.get(position).getColor()))){
                            productSizeColorLists.get(position).setSelected(true);
                            if (productSizeColorLists.get(position).isSelected()) {
                                holder.sizeLinearLayout.setBackgroundColor(Color.CYAN);
                            } else {
                                holder.sizeLinearLayout.setBackgroundColor(Color.WHITE);
                            }
                            Log.e("array", ColorFilter.getString(productSizeColorLists.get(position).getSize()));
                        }else {
                            Log.e("array", "lolo");
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

                holder.filterSizeName.setText(productSizeColorLists.get(position).getColor());

                holder.sizeLinearLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        productSizeColorLists.get(position).setSelected(!productSizeColorLists.get(position).isSelected());
                        if (productSizeColorLists.get(position).isSelected()) {
                            holder.sizeLinearLayout.setBackgroundColor(Color.CYAN);

//                            if (data.get(FILTER_LIST_COLOR) != null) {
//                                try {
//                                    JSONObject colorJson = new JSONObject(data.get(FILTER_LIST_COLOR).toString());
//                                    ColorFilter = colorJson;
//                                    ColorFilter.put(productSizeColorLists.get(position).getSize(), productSizeColorLists.get(position).getSize());
//                                    shareSession.filterColor(ColorFilter);
//                                } catch (JSONException e) {
//                                    e.printStackTrace();
//                                }
//
//                            } else {
                                try {
                                    ColorFilter.put(productSizeColorLists.get(position).getColor(), productSizeColorLists.get(position).getColor());
                                    shareSession.filterColor(ColorFilter);
                                    Log.e("ColorFilter add ",ColorFilter.toString());
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
//                            }

                        } else {

                            holder.sizeLinearLayout.setBackgroundColor(Color.WHITE);
                            ColorFilter.remove(productSizeColorLists.get(position).getColor());
                            shareSession.filterColor(ColorFilter);
                            Log.e("ColorFilter add ",ColorFilter.toString());
                        }
                    }
                });
                break;
        }


    }
    @Override
    public int getItemCount() {
        return productSizeColorLists.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {

        private TextView filterSizeName;
        private LinearLayout sizeLinearLayout;

        public Viewholder(@NonNull View itemView) {
            super(itemView);

            filterSizeName = (TextView) itemView.findViewById(R.id.filterSizeName);
            sizeLinearLayout = (LinearLayout) itemView.findViewById(R.id.sizeLinearLayout);

        }
    }
}
