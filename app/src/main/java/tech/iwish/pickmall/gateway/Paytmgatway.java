package tech.iwish.pickmall.gateway;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.paytm.pgsdk.PaytmOrder;
import com.paytm.pgsdk.PaytmPGService;
import com.paytm.pgsdk.PaytmPaymentTransactionCallback;
import com.paytm.pgsdk.TransactionManager;

import tech.iwish.pickmall.session.Share_session;

public class Paytmgatway extends AppCompatActivity {


    private Integer ActivityRequestCode = 2;
    private String  TAG ="MainActivity";
    private String midString ="pMwrjE07945349166231", txnAmountString="10", orderIdString="1", txnTokenString="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        startPaytmPayment("nchsidhcisd");
    }


    private void getToken() {


//        ServiceWrapper serviceWrapper = new ServiceWrapper(null);
//        Call<Token_Res> call = serviceWrapper.getTokenCall("12345", midString, orderIdString, txnAmountString);
//        call.enqueue(new Callback<Token_Res>() {
//            @Override
//            public void onResponse(Call<Token_Res> call, Response<Token_Res> response) {
//                Log.e(TAG, " respo " + response.isSuccessful());
//                try {
//
//                    if (response.isSuccessful() && response.body() != null) {
//                        if (response.body().getBody().getTxnToken() != "") {
//                            Log.e(TAG, " transaction token : " + response.body().getBody().getTxnToken());
//                            startPaytmPayment(response.body().getBody().getTxnToken());
//                        } else {
//                            Log.e(TAG, " Token status false");
//                        }
//                    }
//                } catch (Exception e) {
//                    Log.e(TAG, " error in Token Res " + e.toString());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Token_Res> call, Throwable t) {
//                Log.e(TAG, " response error " + t.toString());
//            }
//        });

    }


    public void startPaytmPayment (String token){

        txnTokenString = token;
        // for test mode use it
        // String host = "https://securegw-stage.paytm.in/";
        // for production mode use it
        String host = "https://securegw.paytm.in/";
        String orderDetails = "MID: " + midString + ", OrderId: " + orderIdString + ", TxnToken: " + txnTokenString
                + ", Amount: " + txnAmountString;
        //Log.e(TAG, "order details "+ orderDetails);

        String callBackUrl = host + "theia/paytmCallback?ORDER_ID="+orderIdString;
        Log.e(TAG, " callback URL "+callBackUrl);
        PaytmOrder paytmOrder = new PaytmOrder(orderIdString, midString, txnTokenString, txnAmountString, callBackUrl);
        TransactionManager transactionManager = new TransactionManager(paytmOrder, new PaytmPaymentTransactionCallback(){
            @Override
            public void onTransactionResponse(Bundle bundle) {
                Log.e(TAG, "Response (onTransactionResponse) : "+bundle.toString());
            }

            @Override
            public void networkNotAvailable() {
                Log.e(TAG, "network not available ");
            }

            @Override
            public void onErrorProceed(String s) {
                Log.e(TAG, " onErrorProcess "+s.toString());
            }

            @Override
            public void clientAuthenticationFailed(String s) {
                Log.e(TAG, "Clientauth "+s);
            }

            @Override
            public void someUIErrorOccurred(String s) {
                Log.e(TAG, " UI error "+s);
            }

            @Override
            public void onErrorLoadingWebPage(int i, String s, String s1) {
                Log.e(TAG, " error loading web "+s+"--"+s1);
            }

            @Override
            public void onBackPressedCancelTransaction() {
                Log.e(TAG, "backPress ");
            }

            @Override
            public void onTransactionCancel(String s, Bundle bundle) {
                Log.e(TAG, " transaction cancel "+s);
            }
        });

        transactionManager.setShowPaymentUrl(host + "theia/api/v1/showPaymentPage");
        transactionManager.startTransaction(this, ActivityRequestCode);

    }


}






























