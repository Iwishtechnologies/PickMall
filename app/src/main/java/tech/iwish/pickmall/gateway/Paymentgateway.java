package tech.iwish.pickmall.gateway;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONException;
import org.json.JSONObject;

public class Paymentgateway extends AppCompatActivity implements PaymentResultListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        wallet();
    }

    public void wallet(){

        Checkout.preload(Paymentgateway.this);

        Checkout checkout = new Checkout();
        checkout.setKeyID("rzp_test_cqTSMSd3guM3Ej");

        int amount = 100;

        JSONObject object= new JSONObject();
        try {
            object.put("name" ,"vikas");
            object.put("description" ,"Iwish");
            object.put("amount" ,Double.valueOf(amount)*100);
            object.put("current" ,"INR");
            object.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");

            JSONObject preFill = new JSONObject();
            preFill.put("email" , "mailtovikas67@gmail.com");
            preFill.put("contact" , "8871121959");
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
    }

    @Override
    public void onPaymentError(int i, String s) {
        Toast.makeText(Paymentgateway.this, "fail"+s, Toast.LENGTH_SHORT).show();
    }

}
