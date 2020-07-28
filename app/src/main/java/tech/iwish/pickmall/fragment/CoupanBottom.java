package tech.iwish.pickmall.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.load.resource.bitmap.FitCenter;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Map;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import tech.iwish.pickmall.Interface.UpdateFinalAmountData;
import tech.iwish.pickmall.R;
import tech.iwish.pickmall.activity.SaveAddressActivity;
import tech.iwish.pickmall.config.Constants;
import tech.iwish.pickmall.connection.JsonHelper;
import tech.iwish.pickmall.session.Share_session;

import static tech.iwish.pickmall.session.Share_session.USERMOBILE;


public class CoupanBottom extends BottomSheetDialogFragment {

    private Button coupon_btn;
    private EditText coupon_check;
    private ProgressBar progress_bar;
    Share_session shareSession;
    String Order_amount;
    Map data;
    UpdateFinalAmountData updateFinalAmountData;

    public CoupanBottom(int order_amount, UpdateFinalAmountData updateFinalAmountData) {
        this.Order_amount = String.valueOf(order_amount);
        this.updateFinalAmountData = updateFinalAmountData;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_coupan_bottom, container, false);

        coupon_btn = view.findViewById(R.id.coupon_btn);
        coupon_check = view.findViewById(R.id.coupon_check);
        progress_bar = view.findViewById(R.id.progress_bar);

        shareSession = new Share_session(getActivity());


        coupon_btn.setOnClickListener(view1 -> {

            coupon_btn.setClickable(false);

            coupon();

            if (coupon_check.getText().toString().trim().isEmpty()) {
                Toast toast;
                Toast.makeText(getActivity(), "Coupon Code Invalid", Toast.LENGTH_SHORT).show();

            } else {
                coupon_btn.setClickable(true);
            }

        });

        return view;
    }

    private void coupon() {

        OkHttpClient okHttpClient = new OkHttpClient();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("client_number", shareSession.getUserDetail().get("UserMobile"));
            jsonObject.put("coupon", coupon_check.getText().toString().trim());
            jsonObject.put("order_amount", Order_amount.trim());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(JSON, jsonObject.toString());
        Request request1 = new Request.Builder().url(Constants.COUPON_CHECK).post(body).build();
        okHttpClient.newCall(request1).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful()) {

                    String result = response.body().string();
                    JsonHelper jsonHelper = new JsonHelper(result);

                    if (jsonHelper.isValidJson()) {
                        String responses = jsonHelper.GetResult("response");
                        if (responses.equals("TRUE")) {
                            getActivity().runOnUiThread(() -> {
                                Toast.makeText(getActivity(), "successfully applied Coupens", Toast.LENGTH_SHORT).show();
                                int coupenamt = Integer.parseInt(jsonHelper.GetResult("data")) - Integer.parseInt(Order_amount.trim());
                                updateFinalAmountData.UpdateAmount(jsonHelper.GetResult("data"), coupon_check.getText().toString().trim(), String.valueOf(Math.abs(coupenamt)));
                            });

                        } else {
                            getActivity().runOnUiThread(() -> {
                                Toast.makeText(getActivity(), jsonHelper.GetResult("data"), Toast.LENGTH_SHORT).show();
                            });
                        }
                    }
                }
            }
        });

    }


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
        int windowHeight = getWindowHeight();
        if (layoutParams != null) {
            layoutParams.height = windowHeight - 300;
        }
        bottomSheet.setLayoutParams(layoutParams);
        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
//


    }

    private int getWindowHeight() {
        // Calculate window height for fullscreen use
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }

}
