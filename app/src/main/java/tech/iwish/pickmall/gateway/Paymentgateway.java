package tech.iwish.pickmall.gateway;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import tech.iwish.pickmall.session.Share_session;

import static tech.iwish.pickmall.session.Share_session.NAME_ADDRESS;
import static tech.iwish.pickmall.session.Share_session.NUMBER_ADDRESS;

public class Paymentgateway extends AppCompatActivity implements PaymentResultListener {

    String type , grandTotal ;
    Share_session shareSession ;
    Map data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        shareSession = new Share_session(this);
        data = shareSession.Fetchdata();

        wallet();
    }

    public void wallet(){

        Intent intent = getIntent();
        type = intent.getStringExtra("type");
        grandTotal = intent.getStringExtra("grandTotal");


        Checkout.preload(Paymentgateway.this);

        Checkout checkout = new Checkout();
        checkout.setKeyID("rzp_test_cqTSMSd3guM3Ej");

        int amount = Integer.parseInt(grandTotal);

        JSONObject object= new JSONObject();
        try {
            object.put("name" ,data.get(NAME_ADDRESS).toString());
            object.put("description" ,"Pickmall");
            object.put("amount" ,Double.valueOf(amount)*100);
            object.put("current" ,"INR");
            object.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");

            JSONObject preFill = new JSONObject();
//            preFill.put("email" , "mailtovikas67@gmail.com");
            preFill.put("contact" , data.get(NUMBER_ADDRESS).toString());
            object.put("prfill" ,preFill);

            checkout.open(Paymentgateway.this, object);


        } catch (JSONException e) {
//            Toast.makeText(context, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

    }

    @Override
    public void onPaymentSuccess(String s) {
        Toast.makeText(Paymentgateway.this, "success"+s, Toast.LENGTH_SHORT).show();

        switch (type){
            case "CardActivity":

                break;
            case "buy_now":
                break;
        }
    }

    @Override
    public void onPaymentError(int i, String s) {
        Toast.makeText(Paymentgateway.this, "fail"+s, Toast.LENGTH_SHORT).show();
    }

}
