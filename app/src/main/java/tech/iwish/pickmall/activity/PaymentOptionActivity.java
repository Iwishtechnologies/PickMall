package tech.iwish.pickmall.activity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.paytm.pg.merchant.PaytmChecksum;
import com.paytm.pgsdk.PaytmOrder;
import com.paytm.pgsdk.PaytmPaymentTransactionCallback;
import com.paytm.pgsdk.TransactionManager;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import tech.iwish.pickmall.R;
import tech.iwish.pickmall.config.Constants;
import tech.iwish.pickmall.connection.JsonHelper;
import tech.iwish.pickmall.gateway.Paymentgateway;
import tech.iwish.pickmall.other.FriendSaleList;
import tech.iwish.pickmall.session.Share_session;
import tech.iwish.pickmall.sqlconnection.MyhelperSql;

import static tech.iwish.pickmall.session.Share_session.USERMOBILE;
import static tech.iwish.pickmall.session.Share_session.WALLET_AMOUNT;


public class PaymentOptionActivity extends Activity implements View.OnClickListener {

    private RelativeLayout cases;
    private RadioButton rayruWallet, onlinePayments, caseRadios , ResellonlinePayments ,ResellpaytmRadios;
    private TextView shippingCharge;
    String Checker;
    private Map data;
    LinearLayout paymentAvailable;
    private TextView total_amount_tax;
    private TextView walletAmtTextview;
    private String type;
    private MyhelperSql myhelperSql;
    private SQLiteDatabase sqLiteDatabase;
    private List<JSONObject> jsonObjects = new ArrayList<>();
    private ArrayList<HashMap<String, String>> list = new ArrayList<>();
    private String shippingCharege;
    private String WalletAmount;
    private int shippinsAmt = -1, productsAmt, grandTotal, gstint, removeDout;
    private String product_name;
    private String select_size;
    private String product_type;
    private String discount_price;
    private String imagename;
    private String product_qty;
    private String product_id;
    private String select_color;
    private String productgst;
    private String referCode;
    private String item_type;
    private String coupen;
    private String coupenamount;
    private Share_session shareSession;
    private ProgressBar progressbar;
    TableRow coupenview;
    private int finalamountsInt;
    private int shippingchargebuy_now;
    String COD_ShippinfCheck = null;
    LinearLayout resellPaymentOption;
    String tokens = "";
    public final static String TAG = "PaymentOptionActivity";
    RelativeLayout ResellLayout;

    private Integer ActivityRequestCode = 2;

    URL url;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_option);


        RelativeLayout rayru_wallet_relative = (RelativeLayout) findViewById(R.id.rayru_wallet_relative);
        RelativeLayout online_payment = (RelativeLayout) findViewById(R.id.online_payment);
        cases = (RelativeLayout) findViewById(R.id.cases);
        RelativeLayout amountAddWallet = (RelativeLayout) findViewById(R.id.amountAddWallet);
        RelativeLayout paytm = (RelativeLayout) findViewById(R.id.paytm);
        RelativeLayout ResellOnlinePayment = (RelativeLayout) findViewById(R.id.ResellOnlinePayment);
        RelativeLayout ResellPaytm = (RelativeLayout) findViewById(R.id.ResellPaytm);
        ResellLayout = (RelativeLayout) findViewById(R.id.ResellLayout);


        coupenview = findViewById(R.id.coupenview);
        TextView coupenAmount = findViewById(R.id.coupenAmount);

        rayruWallet = (RadioButton) findViewById(R.id.rayruWallet);
        onlinePayments = (RadioButton) findViewById(R.id.onlinePayments);
        caseRadios = (RadioButton) findViewById(R.id.caseRadios);
        ResellonlinePayments = (RadioButton) findViewById(R.id.ResellonlinePayments);
        ResellpaytmRadios = (RadioButton) findViewById(R.id.ResellpaytmRadios);

        TextView paymentButton = (TextView) findViewById(R.id.paymentButton);
        TextView pricr = (TextView) findViewById(R.id.pricr);
        total_amount_tax = (TextView) findViewById(R.id.total_amount_tax);

        paymentAvailable = (LinearLayout) findViewById(R.id.paymentAvailable);

        TextView edit_amount = (TextView) findViewById(R.id.amount);
        shippingCharge = (TextView) findViewById(R.id.shippingCharge);
        TextView gst_price = (TextView) findViewById(R.id.gst_price);
        walletAmtTextview = (TextView) findViewById(R.id.walletAmtTextview);

        progressbar = (ProgressBar) findViewById(R.id.progressbar);

        resellPaymentOption = findViewById(R.id.resellPaymentOption);

        shareSession = new Share_session(this);
        data = shareSession.Fetchdata();


        paymentAvailable.setVisibility(View.GONE);

        paymentButton.setOnClickListener(this);
        rayru_wallet_relative.setOnClickListener(this);
        online_payment.setOnClickListener(this);
        cases.setOnClickListener(this);
        amountAddWallet.setOnClickListener(this);
        paytm.setOnClickListener(this);
        ResellLayout.setOnClickListener(this);
        ResellOnlinePayment.setOnClickListener(this);
        ResellPaytm.setOnClickListener(this);

        Intent intent = getIntent();
        type = intent.getStringExtra("type");


        int couponamtInt;
        switch (type) {
            case "CardActivity":
                String product_amt = intent.getStringExtra("amounts");

                coupen = getIntent().getStringExtra("coupen");
                coupenamount = getIntent().getStringExtra("coupenamount");
//                coupon
                if (!coupen.equals("null") && !coupenamount.equals("null")) {
                    coupenview.setVisibility(View.VISIBLE);
                    coupenAmount.setText(coupenamount);
                    couponamtInt = Integer.parseInt(coupenamount);
                    grandTotal = Integer.parseInt(product_amt) - couponamtInt;
                    finalamountsInt = grandTotal;
                } else {
                    coupenview.setVisibility(View.GONE);
                    grandTotal = Integer.parseInt(product_amt);
                    finalamountsInt = grandTotal;
                }

                total_amount_tax.setText(getResources().getString(R.string.rs_symbol) + grandTotal);
                pricr.setText(getResources().getString(R.string.rs_symbol) + product_amt);


                myhelperSql = new MyhelperSql(this);
                String query = "Select * from PRODUCT_CARD";
                sqLiteDatabase = myhelperSql.getWritableDatabase();
                Cursor cursor = sqLiteDatabase.rawQuery(query, new String[]{});
                cursor.moveToFirst();
                if (cursor != null && cursor.moveToFirst()) {
                    for (int i = 0; i < cursor.getCount(); i++) {

                        if (cursor.getString(cursor.getColumnIndex("PRAPAID")).equals("prepaid")) {
                            cases.setVisibility(View.GONE);
                        }
                        cursor.moveToNext();
                    }
                }

                break;
            case "buy_now":

                product_name = intent.getStringExtra("product_name");
                select_size = intent.getStringExtra("select_size");
                product_amt = intent.getStringExtra("actual_price");
                discount_price = intent.getStringExtra("discount_price");
                imagename = intent.getStringExtra("imagename");
                product_qty = intent.getStringExtra("product_qty");
                product_id = intent.getStringExtra("product_id");
                select_color = intent.getStringExtra("select_color");
                product_type = intent.getStringExtra("product_type");
                productgst = intent.getStringExtra("gst");
                coupen = getIntent().getStringExtra("coupen");
                coupenamount = getIntent().getStringExtra("coupenamount");
                pricr.setText(getResources().getString(R.string.rs_symbol) + product_amt);


                String prepaid = intent.getStringExtra("prepaid");
                if (prepaid != null) {
                    if (prepaid.equals("prepaid")) {
                        cases.setVisibility(View.GONE);
                    }
                }
                int qtyprod = Integer.parseInt(product_qty);
                int amtprod = Integer.parseInt(product_amt);
                product_amt = String.valueOf(qtyprod * amtprod);

//              coupon

                if (!coupen.equals("null") && !coupenamount.equals("null")) {
                    coupenview.setVisibility(View.VISIBLE);
                    coupenAmount.setText(coupenamount);
                    couponamtInt = Integer.parseInt(coupenamount);
                    grandTotal = Integer.parseInt(product_amt) - couponamtInt;
                } else {
                    coupenview.setVisibility(View.GONE);
                    grandTotal = Integer.parseInt(product_amt);
                }
                finalamountsInt = grandTotal;

                pricr.setText(getResources().getString(R.string.rs_symbol) + product_amt);
                total_amount_tax.setText(getResources().getString(R.string.rs_symbol) + grandTotal);
                break;
            case "friendDeal_one_rs":
                cases.setVisibility(View.GONE);
                product_name = intent.getStringExtra("product_name");
                select_size = intent.getStringExtra("select_size");
                product_amt = intent.getStringExtra("actual_price");
                discount_price = intent.getStringExtra("discount_price");
                imagename = intent.getStringExtra("imagename");
                product_qty = intent.getStringExtra("product_qty");
                product_id = intent.getStringExtra("product_id");
                select_color = intent.getStringExtra("select_color");
                product_type = intent.getStringExtra("product_type");
                productgst = intent.getStringExtra("gst");
                item_type = intent.getStringExtra("item_type");

//                gst_price.setText("0");
                grandTotal = Integer.parseInt(product_amt);
                finalamountsInt = grandTotal;
                pricr.setText(getResources().getString(R.string.rs_symbol) + product_amt);
                total_amount_tax.setText(getResources().getString(R.string.rs_symbol) + grandTotal);

                if (item_type.equals("one_win")) {
                    friendDeal_one_rs_order_place("", "FREE");
                }

                break;

        }

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.paymentButton:
                if (Checker != null) {
                    switch (Checker) {
                        case "cod":
                            coddelivery();
                            break;
                        case "pickmall_wallet":
                            walletmethod();
                            break;
                        case "online_payment":
                            Online_payment("NORMAL_CARD");
                            break;
                        case "ResellOnlinPayment":
                            Toast.makeText(this, "ResellOnlinPayment", Toast.LENGTH_SHORT).show();
                            break;
                        case "ResellPaytm":
                            Toast.makeText(this, "ResellPaytm", Toast.LENGTH_SHORT).show();
                            break;
                    }
                } else {
                    Toast.makeText(this, "Select Payment Option", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.online_payment:
            case R.id.rayru_wallet_relative:
            case R.id.cases:
                setclick(view);
                break;
            case R.id.paytm:
//                paytmMethod();
                break;
            case R.id.amountAddWallet:
                Intent intent = new Intent(new Intent(PaymentOptionActivity.this, WalletActivity.class));
                intent.putExtra("check", true);
                startActivity(intent);
                break;
            case R.id.ResellLayout:
                ResellPayment();
                break;
            case R.id.ResellOnlinePayment:
                this.Checker = "ResellOnlinPayment";
                ResellonlinePayments.setChecked(true);
                ResellpaytmRadios.setChecked(false);
                rayruWallet.setChecked(false);
                onlinePayments.setChecked(false);
                caseRadios.setChecked(false);
                Toast.makeText(this, "resellPaymentOption", Toast.LENGTH_SHORT).show();
                Online_payment("RESELL");
                break;
            case R.id.ResellPaytm:
                this.Checker = "ResellPaytm";
                ResellonlinePayments.setChecked(false);
                ResellpaytmRadios.setChecked(true);
                rayruWallet.setChecked(false);
                onlinePayments.setChecked(false);
                caseRadios.setChecked(false);
                break;
        }
    }


    private void setclick(View view) {

        int id = view.getId();
        switch (id) {
            case R.id.rayru_wallet_relative:
                this.Checker = "pickmall_wallet";
                rayruWallet.setChecked(true);
                onlinePayments.setChecked(false);
                caseRadios.setChecked(false);
                ResellonlinePayments.setChecked(false);
                ResellpaytmRadios.setChecked(false);
                paymentAvailable.setVisibility(View.VISIBLE);
                resellPaymentOption.setVisibility(View.GONE);
                shippingCharge.setText("Free");
                total_amount_tax.setText(getResources().getString(R.string.rs_symbol) + grandTotal);
                if (data.get(WALLET_AMOUNT) != null) {
                    WalletAmount = data.get(WALLET_AMOUNT).toString();
                    walletAmtTextview.setText(WalletAmount);
                }
                break;
            case R.id.online_payment:
                this.Checker = "online_payment";
                onlinePayments.setChecked(true);
                resellPaymentOption.setVisibility(View.GONE);
                rayruWallet.setChecked(false);
                caseRadios.setChecked(false);
                ResellonlinePayments.setChecked(false);
                ResellpaytmRadios.setChecked(false);
                paymentAvailable.setVisibility(View.GONE);
                shippingCharge.setText("Free");
                total_amount_tax.setText(getResources().getString(R.string.rs_symbol) + grandTotal);
                break;
            case R.id.cases:
                this.Checker = "cod";
                caseRadios.setChecked(true);
                rayruWallet.setChecked(false);
                resellPaymentOption.setVisibility(View.GONE);
                onlinePayments.setChecked(false);
                ResellonlinePayments.setChecked(false);
                ResellpaytmRadios.setChecked(false);
                paymentAvailable.setVisibility(View.GONE);

                if (COD_ShippinfCheck != null) {
                    Log.e("aaaa", COD_ShippinfCheck);
                    shippingCharge.setText(COD_ShippinfCheck);
                    shippingchargebuy_now = shippinsAmt;
                    int totalamt = shippinsAmt + productsAmt;
                    finalamountsInt = totalamt;
                    shippingCharege = String.valueOf(totalamt);
                    total_amount_tax.setText(getResources().getString(R.string.rs_symbol) + shippingCharege);
                    cases.setClickable(true);
                } else {
                    shippingcharge();
                }
                break;
            case R.id.paytm:
                resellPaymentOption.setVisibility(View.GONE);
                break;


        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.e(TAG, " result code " + resultCode);
        // -1 means successful  // 0 means failed
        // one error is - nativeSdkForMerchantMessage : networkError
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ActivityRequestCode && data != null) {
            Bundle bundle = data.getExtras();
            if (bundle != null) {
                for (String key : bundle.keySet()) {
                    Log.e(TAG, key + " : " + (bundle.get(key) != null ? bundle.get(key) : "NULL"));
                }
            }
            Log.e(TAG, " data " + data.getStringExtra("nativeSdkForMerchantMessage"));
            Log.e(TAG, " data response - " + data.getStringExtra("response"));
/*
 data response - {"BANKNAME":"WALLET","BANKTXNID":"1395841115",
 "CHECKSUMHASH":"7jRCFIk6mrep+IhnmQrlrL43KSCSXrmM+VHP5pH0hekXaaxjt3MEgd1N9mLtWyu4VwpWexHOILCTAhybOo5EVDmAEV33rg2VAS/p0PXdk\u003d",
 "CURRENCY":"INR","GATEWAYNAME":"WALLET","MID":"EAc0553138556","ORDERID":"100620202152",
 "PAYMENTMODE":"PPI","RESPCODE":"01","RESPMSG":"Txn Success","STATUS":"TXN_SUCCESS",
 "TXNAMOUNT":"2.00","TXNDATE":"2020-06-10 16:57:45.0","TXNID":"20200610111212800110168328631290118"}
  */
            Toast.makeText(this, data.getStringExtra("nativeSdkForMerchantMessage")
                    + data.getStringExtra("response"), Toast.LENGTH_SHORT).show();
        } else {
            Log.e(TAG, " payment failed");
        }
    }

    private void walletmethod() {

        if (data.get(WALLET_AMOUNT) != null) {
            WalletAmount = data.get(WALLET_AMOUNT).toString();
            walletAmtTextview.setText(WalletAmount);
            int IntWalletamt = Integer.parseInt(WalletAmount);
            if (finalamountsInt <= IntWalletamt) {
                switch (type) {
                    case "CardActivity":
                        card_order_place("wallet", "WALLET");
                        break;
                    case "buy_now":
                        buy_now_order_place("wallet", "WALLET");
                        break;
                    case "friendDeal_one_rs":
//                        productChehckFriendeal();
                        friendDeal_one_rs_order_place("wallet", "WALLET");
                        break;
                }
            } else if (finalamountsInt > IntWalletamt) {
                Toast.makeText(this, "Insufficient Balance", Toast.LENGTH_SHORT).show();
            }

        }

    }

    private void ResellPayment() {
        resellPaymentOption.setVisibility(View.VISIBLE);
        rayruWallet.setChecked(false);
        onlinePayments.setChecked(false);
        caseRadios.setChecked(false);
        shippingCharge.setText("Free");
        total_amount_tax.setText(getResources().getString(R.string.rs_symbol) + grandTotal);

    }

    private void shippingcharge() {

//        cases.setClickable(false);

        progressbar.setVisibility(View.VISIBLE);

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(Constants.SHIPPING_CHARGE)
                .build();
        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
                PaymentOptionActivity.this.runOnUiThread(() -> progressbar.setVisibility(View.GONE));
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful()) {

                    String result = response.body().string();
                    Log.e("response", result);
                    JsonHelper jsonHelper = new JsonHelper(result);
                    if (jsonHelper.isValidJson()) {
                        String responses = jsonHelper.GetResult("response");
                        if (responses.equals("TRUE")) {

                            JSONArray jsonArray = jsonHelper.setChildjsonArray(jsonHelper.getCurrentJsonObj(), "data");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                jsonHelper.setChildjsonObj(jsonArray, i);

                                String minAmt = jsonHelper.GetResult("minAmount");
                                final int minsAmt = Integer.parseInt(minAmt);
                                String maxAmt = jsonHelper.GetResult("maxAmount");
                                final int maxsAmt = Integer.parseInt(maxAmt);
                                final String shippingAmt = jsonHelper.GetResult("amount");
                                shippinsAmt = Integer.parseInt(shippingAmt);
                                productsAmt = grandTotal;

                                if (productsAmt <= maxsAmt && productsAmt >= minsAmt) {
                                    Log.e("onResponse: ", shippingAmt);
                                    PaymentOptionActivity.this.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            shippingCharge.setText(shippingAmt);
                                            COD_ShippinfCheck = shippingAmt;
                                            shippingchargebuy_now = shippinsAmt;
                                            int totalamt = shippinsAmt + productsAmt;
                                            finalamountsInt = totalamt;
                                            shippingCharege = String.valueOf(totalamt);
                                            total_amount_tax.setText(getResources().getString(R.string.rs_symbol) + shippingCharege);
                                            cases.setClickable(true);

                                        }
                                    });
                                    break;
                                } else {
                                    PaymentOptionActivity.this.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            shippingCharge.setText("Free");
                                            cases.setClickable(true);
                                        }
                                    });

                                    continue;
                                }


                            }

                            new Handler(getMainLooper()).post(() -> progressbar.setVisibility(View.GONE));

                        }
                    }

                }
            }
        });

    }

    private void Online_payment(String paymentoption) {


        Intent intent;
        intent = new Intent(PaymentOptionActivity.this, Paymentgateway.class);
        String f = String.valueOf(grandTotal);

        switch (type) {
            case "CardActivity":
                intent.putExtra("type", "CardActivity");
                intent.putExtra("finalamountsInt", f);
                intent.putExtra("coupon_amt", coupen);
                break;
            case "buy_now":
//                intent = new Intent(PaymentOptionActivity.this, Paymentgateway.class);
                intent.putExtra("type", "buy_now");
                intent.putExtra("finalamountsInt", f);
                intent.putExtra("product_name", getIntent().getStringExtra("product_name"));
                intent.putExtra("select_size", getIntent().getStringExtra("select_size"));
                intent.putExtra("product_qty", getIntent().getStringExtra("product_qty"));
                intent.putExtra("product_id", getIntent().getStringExtra("product_id"));
                intent.putExtra("select_color", getIntent().getStringExtra("select_color"));
                intent.putExtra("product_type", getIntent().getStringExtra("product_type"));
                intent.putExtra("coupon_amt", coupen);
                intent.putExtra("paymentOption", paymentoption);
                intent.putExtra("type", "buy_now");
//                startActivity(intent);
                break;
            case "friendDeal_one_rs":
                intent.putExtra("type", "friendDeal_one_rs");
                intent.putExtra("finalamountsInt", f);
                intent.putExtra("product_name", getIntent().getStringExtra("product_name"));
                intent.putExtra("select_size", getIntent().getStringExtra("select_size"));
                intent.putExtra("product_qty", getIntent().getStringExtra("product_qty"));
                intent.putExtra("product_id", getIntent().getStringExtra("product_id"));
                intent.putExtra("select_color", getIntent().getStringExtra("select_color"));
                intent.putExtra("product_type", getIntent().getStringExtra("product_type"));
                intent.putExtra("imagename", imagename);
                intent.putExtra("item_type", item_type);
                intent.putExtra("paymentOption", paymentoption);
                intent.putExtra("new_user_request", getIntent().getStringExtra("new_user_request"));
                break;
        }
        startActivity(intent);

    }

    private void coddelivery() {

        switch (type) {
            case "CardActivity":
//                card_order_place("COD", "COD");
                Intent intent = new Intent(PaymentOptionActivity.this, Cod_mobile_verify_activity.class);
                intent.putExtra("shippingchargebuy_now", String.valueOf(shippingchargebuy_now));
                intent.putExtra("finalamountsInt", String.valueOf(finalamountsInt));
                intent.putExtra("coupen", coupen);
                intent.putExtra("type", type);
                startActivity(intent);
                break;
            case "buy_now":
//                buy_now_order_place("COD", "COD");
                Intent intent1 = new Intent(PaymentOptionActivity.this, Cod_mobile_verify_activity.class);
                intent1.putExtra("shippingchargebuy_now", String.valueOf(shippingchargebuy_now));
                intent1.putExtra("finalamountsInt", String.valueOf(finalamountsInt));
                intent1.putExtra("product_id", product_id);
                intent1.putExtra("product_type", product_type);
                intent1.putExtra("select_size", select_size);
                intent1.putExtra("product_qty", product_qty);
                intent1.putExtra("item_type", item_type);
                intent1.putExtra("type", type);
                intent1.putExtra("coupen", coupen);
                startActivity(intent1);
                break;
        }

    }

    private void card_order_place(final String value, String paymentmethod) {

        progressbar.setVisibility(View.VISIBLE);

        myhelperSql = new MyhelperSql(this);
        String query = "Select * from PRODUCT_CARD";
        sqLiteDatabase = myhelperSql.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(query, new String[]{});
        cursor.moveToFirst();
        if (cursor != null && cursor.moveToFirst()) {
            for (int i = 0; i < cursor.getCount(); i++) {
                JSONObject jsonObject1 = new JSONObject();
                try {
                    jsonObject1.put("product_id", cursor.getString(cursor.getColumnIndex("PRODUCT_ID")));
                    jsonObject1.put("product_qty", cursor.getString(cursor.getColumnIndex("PRODUCT_QTY")));
                    jsonObject1.put("PRODUCT_SIZE", cursor.getString(cursor.getColumnIndex("PRODUCT_SIZE")));
                    jsonObject1.put("PRODUCT_COLOR", cursor.getString(cursor.getColumnIndex("PRODUCT_COLOR")));
                    jsonObject1.put("PRODUCT_AMOUNT", cursor.getString(cursor.getColumnIndex("PRODUCT_AMOUNT")));
                    jsonObject1.put("PRODUCT_TYPE", cursor.getString(cursor.getColumnIndex("PRODUCT_TYPE")));
                    jsonObjects.add(jsonObject1);

                    cursor.moveToNext();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        OkHttpClient okHttpClient = new OkHttpClient();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("client_number", data.get(USERMOBILE).toString());
            jsonObject.put("client_address", shareSession.GetAddrssId());
            jsonObject.put("product", jsonObjects.toString());
            jsonObject.put("payment_option", paymentmethod);
            jsonObject.put("shippingCharge", shippingchargebuy_now);
            jsonObject.put("gst", productgst);
            jsonObject.put("product_amount", finalamountsInt);
            jsonObject.put("offer_id", coupen);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody body = RequestBody.create(JSON, jsonObject.toString());
        Request request1 = new Request.Builder().url(Constants.ORDER_PLACE_CARD).post(body).build();
        okHttpClient.newCall(request1).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful()) {

                    String result = response.body().string();
                    Log.e("response", result);
                    JsonHelper jsonHelper = new JsonHelper(result);
                    if (jsonHelper.isValidJson()) {
                        String responses = jsonHelper.GetResult("response");
                        if (responses.equals("TRUE")) {

                            if (PaymentOptionActivity.this != null) {

                                PaymentOptionActivity.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {

                                        if (value.equals("wallet")) {
                                            WalletAmountUpdate("scsdcdsvd");
                                            progressbar.setVisibility(View.GONE);
                                        } else {
                                            progressbar.setVisibility(View.GONE);
                                            startActivity(new Intent(PaymentOptionActivity.this, SuccessfullyActivity.class));
                                        }


                                    }
                                });

                            }
                        }
                    }

                }
            }
        });


    }

    private void friendDeal_one_rs_order_place(String wallet, String paymentmethod) {

        progressbar.setVisibility(View.VISIBLE);
        OkHttpClient okHttpClient = new OkHttpClient();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("client_number", data.get(USERMOBILE).toString());
            jsonObject.put("client_address", shareSession.GetAddrssId());
            jsonObject.put("product_id", product_id);
            jsonObject.put("product_type", product_type);
            jsonObject.put("product_color", select_color);
            jsonObject.put("product_size", select_size);
            jsonObject.put("product_qty", product_qty);
            jsonObject.put("product_amount", finalamountsInt);
            jsonObject.put("shippingCharge", shippingchargebuy_now);
            jsonObject.put("gst", removeDout);
            jsonObject.put("item_type", item_type);
            jsonObject.put("payment_option", paymentmethod);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(JSON, jsonObject.toString());
        Request request1 = new Request.Builder().url(Constants.FRIEND_DEAL_ORDER).post(body).build();
        okHttpClient.newCall(request1).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful()) {

                    String result = response.body().string();
                    Log.e("response", result);
                    JsonHelper jsonHelper = new JsonHelper(result);
                    if (jsonHelper.isValidJson()) {
                        String responses = jsonHelper.GetResult("response");
                        if (responses.equals("TRUE")) {

                            if (PaymentOptionActivity.this != null) {

                                referCode = jsonHelper.GetResult("data");

                                PaymentOptionActivity.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {

//                                        WalletAmountUpdate("friendDeal_one_rs_ord");
//                                        progressbar.setVisibility(View.GONE);

                                        if (!paymentmethod.equals("FREE")) {
                                            WalletAmountUpdate("friendDeal_one_rs_ord");
                                        } else {
                                            Intent intent = new Intent(PaymentOptionActivity.this, OneRsShareActivity.class);
                                            intent.putExtra("product_name", product_name);
                                            intent.putExtra("product_image", imagename);
                                            intent.putExtra("discount_price", discount_price);
                                            intent.putExtra("refer_code", referCode);
                                            intent.putExtra("item_type", item_type);
                                            intent.putExtra("product_id", product_id);
                                            intent.putExtra("new_user_request", getIntent().getStringExtra("new_user_request"));
                                            startActivity(intent);
                                        }

                                    }
                                });


                            }

                        }
                    }

                }
            }
        });


    }

    private void buy_now_order_place(final String value, String paymentmethod) {

        progressbar.setVisibility(View.VISIBLE);
        OkHttpClient okHttpClient = new OkHttpClient();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("client_number", data.get(USERMOBILE).toString());
            jsonObject.put("client_address", shareSession.GetAddrssId());
            jsonObject.put("product_id", product_id);
            jsonObject.put("product_type", product_type);
            jsonObject.put("product_color", "");
            jsonObject.put("product_size", select_size);
            jsonObject.put("product_qty", product_qty);
            jsonObject.put("product_amount", finalamountsInt);
            jsonObject.put("shippingCharge", shippingchargebuy_now);
            jsonObject.put("gst", "");
            jsonObject.put("item_type", item_type);
            jsonObject.put("offer_id", coupenamount);
            jsonObject.put("payment_option", paymentmethod);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(JSON, jsonObject.toString());
        Request request1 = new Request.Builder().url(Constants.ORDER_PLACE_BUY_NOW).post(body).build();
        okHttpClient.newCall(request1).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful()) {

                    String result = response.body().string();
                    Log.e("response", result);
                    final JsonHelper jsonHelper = new JsonHelper(result);
                    if (jsonHelper.isValidJson()) {
                        String responses = jsonHelper.GetResult("response");
                        if (responses.equals("TRUE")) {

                            if (PaymentOptionActivity.this != null) {
                                PaymentOptionActivity.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {

                                        if (value.equals("wallet")) {
                                            WalletAmountUpdate("");
                                            progressbar.setVisibility(View.GONE);
                                        } else {
                                            progressbar.setVisibility(View.GONE);
                                            startActivity(new Intent(PaymentOptionActivity.this, SuccessfullyActivity.class));
                                        }

                                    }
                                });


                            }

                        }
                    }

                }
            }
        });


    }

    private void WalletAmountUpdate(final String check_friend_deal) {

        int wallint = Integer.parseInt(WalletAmount);
        String remainAmtWallet = String.valueOf(wallint - finalamountsInt);
        shareSession.walletAmount(remainAmtWallet);

        OkHttpClient okHttpClient = new OkHttpClient();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("client_number", data.get(USERMOBILE).toString());
            jsonObject.put("walletAmount", remainAmtWallet);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody body = RequestBody.create(JSON, jsonObject.toString());
        Request request1 = new Request.Builder().url(Constants.WALLET_AMOUNT_UPDATE).post(body).build();
        okHttpClient.newCall(request1).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful()) {

                    String result = response.body().string();
                    Log.e("response", result);
                    JsonHelper jsonHelper = new JsonHelper(result);
                    if (jsonHelper.isValidJson()) {
                        String responses = jsonHelper.GetResult("response");
                        if (responses.equals("TRUE")) {
                            Log.d("ss", check_friend_deal);
                            if (check_friend_deal.equals("friendDeal_one_rs_ord")) {

                                Intent intent = new Intent(PaymentOptionActivity.this, OneRsShareActivity.class);
                                intent.putExtra("product_name", product_name);
                                intent.putExtra("product_image", imagename);
                                intent.putExtra("discount_price", discount_price);
                                intent.putExtra("refer_code", referCode);
                                intent.putExtra("product_id", product_id);
                                intent.putExtra("item_type", item_type);
                                intent.putExtra("new_user_request", getIntent().getStringExtra("new_user_request"));
                                startActivity(intent);

                            } else {
                                startActivity(new Intent(PaymentOptionActivity.this, SuccessfullyActivity.class));
                            }

                        }
                    }

                }
            }
        });

    }


}































