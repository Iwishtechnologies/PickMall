package tech.iwish.pickmall.Tracking;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Shiprocket {

    String json = "{\n  \"order_id\": \"224-447\",\n  \"order_date\": \"2019-07-24 11:11\",\n  \"pickup_location\": \"Jammu\",\n  \"channel_id\": \"\",\n  \"comment\": \"Reseller: M/s Goku\",\n  \"billing_customer_name\": \"Naruto\",\n  \"billing_last_name\": \"Uzumaki\",\n  \"billing_address\": \"House 221B, Leaf Village\",\n  \"billing_address_2\": \"Near Hokage House\",\n  \"billing_city\": \"New Delhi\",\n  \"billing_pincode\": \"110002\",\n  \"billing_state\": \"Delhi\",\n  \"billing_country\": \"India\",\n  \"billing_email\": \"naruto@uzumaki.com\",\n  \"billing_phone\": \"9876543210\",\n  \"shipping_is_billing\": true,\n  \"shipping_customer_name\": \"\",\n  \"shipping_last_name\": \"\",\n  \"shipping_address\": \"\",\n  \"shipping_address_2\": \"\",\n  \"shipping_city\": \"\",\n  \"shipping_pincode\": \"\",\n  \"shipping_country\": \"\",\n  \"shipping_state\": \"\",\n  \"shipping_email\": \"\",\n  \"shipping_phone\": \"\",\n  \"order_items\": [\n    {\n      \"name\": \"Kunai\",\n      \"sku\": \"chakra123\",\n      \"units\": 10,\n      \"selling_price\": \"900\",\n      \"discount\": \"\",\n      \"tax\": \"\",\n      \"hsn\": 441122\n    }\n  ],\n  \"payment_method\": \"Prepaid\",\n  \"shipping_charges\": 0,\n  \"giftwrap_charges\": 0,\n  \"transaction_charges\": 0,\n  \"total_discount\": 0,\n  \"sub_total\": 9000,\n  \"length\": 10,\n  \"breadth\": 15,\n  \"height\": 20,\n  \"weight\": 2.5\n}";

    public void request() {

        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType,json );
        Request request = new Request.Builder()
                .url("https://apiv2.shiprocket.in/v1/external/orders/create/adhoc")
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", "Bearer {{token}}")
                .build();

        /*ry {
            Response response = client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        client.newCall(request).enqueue(new okhttp3.Callback() {

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

            }

            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }
        });

    }

}
