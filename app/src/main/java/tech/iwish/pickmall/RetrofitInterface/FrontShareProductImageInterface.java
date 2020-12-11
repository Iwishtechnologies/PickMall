package tech.iwish.pickmall.RetrofitInterface;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import tech.iwish.pickmall.RetrofitModel.FrontProductShareList;
import tech.iwish.pickmall.config.Constants;

public class FrontShareProductImageInterface {

    public static ProductShareInterface service = null;

    public static ProductShareInterface ProductFrontShare() {
        if (service == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            service = retrofit.create(ProductShareInterface.class);
        }
        return service;
    }

    public interface ProductShareInterface {
        @FormUrlEncoded
        @POST("FrontProductShare")
        Call<FrontProductShareList> getData(
                @Field("product_id") String vendor_sno
        );
    }

}
