package tech.iwish.pickmall.activity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
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

    private RelativeLayout rayru_wallet_relative, online_payment, cases, amountAddWallet, paytm;
    private RadioButton rayruWallet, onlinePayments, caseRadios;
    private TextView paymentButton, shippingCharge;
    String Checker;
    private Map data;
    LinearLayout paymentAvailable;
    private TextView edit_amount, pricr, total_amount_tax, gst_price, walletAmtTextview, coupenAmount;
    private String type;
    private MyhelperSql myhelperSql;
    private SQLiteDatabase sqLiteDatabase;
    private List<JSONObject> jsonObjects = new ArrayList<>();
    private ArrayList<HashMap<String, String>> list = new ArrayList<>();
    private String product_amt, shippingCharege, WalletAmount;
    private int shippinsAmt = -1, productsAmt, grandTotal, gstint, removeDout;
    private String product_name, select_size, product_type, discount_price, prepaid,
            imagename, product_qty, product_id, select_color, productgst, referCode, item_type, coupen, coupenamount;
    private Share_session shareSession;
    private ProgressBar progressbar;
    TableRow coupenview;
    private int finalamountsInt, shippingchargebuy_now, couponamtInt;
    String COD_ShippinfCheck = null;
    String tokens = "";
    public final static String TAG = "PaymentOptionActivity";

    private Integer ActivityRequestCode = 2;
    private String midString = "GtyipR16237755156153",
            txnAmountString = "10",
            orderIdString = "4",
            txnTokenString = "";
//    private String midString = "GtyipR16237755156153", txnAmountString = "10", orderIdString = "2", txnTokenString = "uMnQqlhwXXBJBVx5sDC2ALyuzC6arz3ec1YhCxF56sUs6V+SpfxWRRwR2A8NEflqnAxgg0HTX69Hkuh2Ys4r8ATAYK8y8Zqv5Rl1DIU6+pg=";

    URL url;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_option);


        rayru_wallet_relative = (RelativeLayout) findViewById(R.id.rayru_wallet_relative);
        online_payment = (RelativeLayout) findViewById(R.id.online_payment);
        cases = (RelativeLayout) findViewById(R.id.cases);
        amountAddWallet = (RelativeLayout) findViewById(R.id.amountAddWallet);
        paytm = (RelativeLayout) findViewById(R.id.paytm);


        coupenview = findViewById(R.id.coupenview);
        coupenAmount = findViewById(R.id.coupenAmount);

        rayruWallet = (RadioButton) findViewById(R.id.rayruWallet);
        onlinePayments = (RadioButton) findViewById(R.id.onlinePayments);
        caseRadios = (RadioButton) findViewById(R.id.caseRadios);

        paymentButton = (TextView) findViewById(R.id.paymentButton);
        pricr = (TextView) findViewById(R.id.pricr);
        total_amount_tax = (TextView) findViewById(R.id.total_amount_tax);

        paymentAvailable = (LinearLayout) findViewById(R.id.paymentAvailable);

        edit_amount = (TextView) findViewById(R.id.amount);
        shippingCharge = (TextView) findViewById(R.id.shippingCharge);
        gst_price = (TextView) findViewById(R.id.gst_price);
        walletAmtTextview = (TextView) findViewById(R.id.walletAmtTextview);

        progressbar = (ProgressBar) findViewById(R.id.progressbar);

        shareSession = new Share_session(this);
        data = shareSession.Fetchdata();


        paymentAvailable.setVisibility(View.GONE);

        paymentButton.setOnClickListener(this);
        rayru_wallet_relative.setOnClickListener(this);
        online_payment.setOnClickListener(this);
        cases.setOnClickListener(this);
        amountAddWallet.setOnClickListener(this);
        paytm.setOnClickListener(this);
        Intent intent = getIntent();
        type = intent.getStringExtra("type");


        switch (type) {
            case "CardActivity":
                product_amt = intent.getStringExtra("amounts");

//                productgst = intent.getStringExtra("gst");
//                gstint = Integer.parseInt(productgst);
//                grandTotal = Integer.parseInt(product_amt) + gstint;
//                gst_price.setText(productgst);
//                finalamountsInt = grandTotal;

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


                prepaid = intent.getStringExtra("prepaid");
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
                            Online_payment();
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
                paytmMethod();
                break;
            case R.id.amountAddWallet:
                Intent intent = new Intent(new Intent(PaymentOptionActivity.this, WalletActivity.class));
                intent.putExtra("check", true);
                startActivity(intent);
                break;
        }
    }

/*      private void paytmMethods() {


        String mid = "GtyipR16237755156153";
        String orderid = "4410";


        JSONObject paytmParams = new JSONObject();

        *//* for Production *//*
// URL url = new URL("https://securegw.paytm.in/theia/api/v1/initiateTransaction?mid=YOUR_MID_HERE&orderId=ORDERID_98765");

        try {

            JSONObject body = new JSONObject();
            body.put("requestType", "Payment");
            body.put("mid", mid);
            body.put("websiteName", "WEBSTAGING");
            body.put("orderId", orderid);
            body.put("callbackUrl", "https://merchant.com/callback");

            JSONObject txnAmount = new JSONObject();
            txnAmount.put("value", "1.00");
            txnAmount.put("currency", "INR");

            JSONObject userInfo = new JSONObject();
            userInfo.put("custId", "CUST_001");
            body.put("txnAmount", txnAmount);
            body.put("userInfo", userInfo);

            *//*
             * Generate checksum by parameters we have in body
             * You can get Checksum JAR from https://developer.paytm.com/docs/checksum/
             * Find your Merchant Key in your Paytm Dashboard at https://dashboard.paytm.com/next/apikeys
             *//*

            String checksum = PaytmChecksum.generateSignature(body.toString(), "xWa_4MkQub51GSZ2");
//
            JSONObject head = new JSONObject();
            head.put("signature", checksum);

            paytmParams.put("body", body);
            paytmParams.put("head", head);

            String post_data = paytmParams.toString();

            *//* for Staging *//*
            url = new URL("https://securegw-stage.paytm.in/theia/api/v1/initiateTransaction?mid=YOUR_MID_HERE&orderId=ORDERID_98765");


            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            DataOutputStream requestWriter = new DataOutputStream(connection.getOutputStream());
            requestWriter.writeBytes(post_data);
            requestWriter.close();
            String responseData = "";
            InputStream is = connection.getInputStream();
            BufferedReader responseReader = new BufferedReader(new InputStreamReader(is));
            if ((responseData = responseReader.readLine()) != null) {
                System.out.append("Response: " + responseData);
            }
            responseReader.close();
        } catch (Exception exception) {
            exception.printStackTrace();
        }


    }
*/


    private void paytmMethod() {

        getToken();

        txnTokenString = tokens;
        // for test mode use it
        String host = "https://securegw-stage.paytm.in/";
//        for production mode use it
//        String host = "https://securegw.paytm.in/";

        String orderDetails = "MID: " + midString + ", OrderId: " + orderIdString + ", TxnToken: " + txnTokenString
                + ", Amount: " + txnAmountString;

        Log.e(TAG, "order details " + orderDetails);

        String callBackUrl = host + "theia/paytmCallback?ORDER_ID=" + orderIdString;
        Log.e(TAG, " callback URL " + callBackUrl);
        PaytmOrder paytmOrder = new PaytmOrder(orderIdString, midString, txnTokenString, txnAmountString, callBackUrl);
        TransactionManager transactionManager = new TransactionManager(paytmOrder, new PaytmPaymentTransactionCallback() {
            @Override
            public void onTransactionResponse(Bundle bundle) {
                Log.e(TAG, "Response (onTransactionResponse) : " + bundle.toString());
            }

            @Override
            public void networkNotAvailable() {
                Log.e(TAG, "network not available ");
            }

            @Override
            public void onErrorProceed(String s) {
                Log.e(TAG, " onErrorProcess " + s.toString());
            }

            @Override
            public void clientAuthenticationFailed(String s) {
                Log.e(TAG, "Clientauth " + s);
            }

            @Override
            public void someUIErrorOccurred(String s) {
                Log.e(TAG, " UI error " + s);
            }

            @Override
            public void onErrorLoadingWebPage(int i, String s, String s1) {
                Log.e(TAG, " error loading web " + s + "--" + s1);
            }

            @Override
            public void onBackPressedCancelTransaction() {
                Log.e(TAG, "backPress ");
            }

            @Override
            public void onTransactionCancel(String s, Bundle bundle) {
                Log.e(TAG, " transaction cancel " + s);
            }
        });

        transactionManager.setShowPaymentUrl(host + "theia/api/v1/showPaymentPage");
        transactionManager.startTransaction(this, ActivityRequestCode);


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


    private String getToken() {

        OkHttpClient okHttpClient = new OkHttpClient();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("amount", "10");
            jsonObject.put("orderId", "4");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(JSON, jsonObject.toString());
        Request request1 = new Request.Builder().url(Constants.PAYTMS).post(body).build();
        okHttpClient.newCall(request1).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    String result = response.body().string();
                    Log.e("result", result);
                    JsonHelper jsonHelper = new JsonHelper(result);
                    if (jsonHelper.isValidJson()) {
                        String responses = jsonHelper.GetResult("response");
                        if (responses.equals("TRUE")) {
                            JSONArray jsonArray = jsonHelper.setChildjsonArray(jsonHelper.getCurrentJsonObj(), "token");
                            tokens = jsonHelper.GetResult("token");
                        }
                    }
                }
            }
        });


        return tokens;
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
/*

    private void productChehckFriendeal() {

        progressbar.setVisibility(View.VISIBLE);
        OkHttpClient okHttpClient = new OkHttpClient();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("client_number", data.get(USERMOBILE).toString());
            jsonObject.put("client_address", "");
            jsonObject.put("product_id", product_id);
            jsonObject.put("product_type", product_type);
            jsonObject.put("product_color", select_color);
            jsonObject.put("product_size", select_size);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(JSON, jsonObject.toString());
        Request request1 = new Request.Builder().url(Constants.FRIENDDEAL_PRODUCT_CHECK).post(body).build();
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
                                        if (jsonHelper.GetResult("message").equals("all_ready")) {
//                                            WalletAmountUpdate("friendDeal_one_rs_ord");

                                            Intent intent = new Intent(PaymentOptionActivity.this, OneRsShareActivity.class);
                                            intent.putExtra("product_name", product_name);
                                            intent.putExtra("product_image", imagename);
                                            intent.putExtra("discount_price", discount_price);
                                            startActivity(intent);


                                        } else {
                                            friendDeal_one_rs_order_place("wallet", "WALLET");
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
*/


    private void setclick(View view) {

        int id = view.getId();
        switch (id) {
            case R.id.rayru_wallet_relative:
                this.Checker = "pickmall_wallet";
                rayruWallet.setChecked(true);
                onlinePayments.setChecked(false);
                caseRadios.setChecked(false);
                paymentAvailable.setVisibility(View.VISIBLE);
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
                rayruWallet.setChecked(false);
                caseRadios.setChecked(false);
                paymentAvailable.setVisibility(View.GONE);
                shippingCharge.setText("Free");
                total_amount_tax.setText(getResources().getString(R.string.rs_symbol) + grandTotal);
                break;
            case R.id.cases:
                this.Checker = "cod";
                caseRadios.setChecked(true);
                rayruWallet.setChecked(false);
                onlinePayments.setChecked(false);
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
                Toast.makeText(this, "paytm", Toast.LENGTH_SHORT).show();
                break;
        }

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
                if (PaymentOptionActivity.this != null) {
                    PaymentOptionActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            progressbar.setVisibility(View.GONE);
                        }
                    });
                }
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

                            if (PaymentOptionActivity.this != null) {
                                PaymentOptionActivity.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        progressbar.setVisibility(View.GONE);
                                    }
                                });
                            }

                        }
                    }

                }
            }
        });

    }

    private void Online_payment() {


        Intent intent;
        intent = new Intent(PaymentOptionActivity.this, Paymentgateway.class);
        String f = String.valueOf(grandTotal);

        switch (type) {
            case "CardActivity":
//                intent = new Intent(PaymentOptionActivity.this, Paymentgateway.class);
                intent.putExtra("type", "CardActivity");
                intent.putExtra("finalamountsInt", f);
                intent.putExtra("coupon_amt", coupen);
//                startActivity(intent);
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
                intent.putExtra("new_user_request", getIntent().getStringExtra("new_user_request"));
                break;
        }
        startActivity(intent);


        //        product_name = intent.getStringExtra("product_name");
//        select_size = intent.getStringExtra("select_size");
//        product_amt = intent.getStringExtra("actual_price");
//        discount_price = intent.getStringExtra("discount_price");
//        imagename = intent.getStringExtra("imagename");
//        product_qty = intent.getStringExtra("product_qty");
//        product_id = intent.getStringExtra("product_id");
//        select_color = intent.getStringExtra("select_color");
//        product_type = intent.getStringExtra("product_type");
//        productgst = intent.getStringExtra("gst");
//        item_type = intent.getStringExtra("item_type");
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































