package tech.iwish.pickmall.RetrofitInterface;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import tech.iwish.pickmall.RetrofitModel.hotProduct.HotProductList;
import tech.iwish.pickmall.RetrofitModel.silderCategory.SilderCategoryList;
import tech.iwish.pickmall.config.Constants;

public class HotProductInterface {

    public static HotproductInterface service = null;

    public static HotproductInterface SilderCategory() {
        if (service == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            service = retrofit.create(HotproductInterface.class);
        }
        return service;
    }

    public interface HotproductInterface {
        @FormUrlEncoded
        @POST("hot_product")
        Call<HotProductList> gethot_product(
                @Field("start_point") String start_point
        );
    }

}
