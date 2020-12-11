package tech.iwish.pickmall.RetrofitInterface;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import tech.iwish.pickmall.RetrofitModel.FrontProductShareList;
import tech.iwish.pickmall.RetrofitModel.silderCategory.SilderCategoryList;
import tech.iwish.pickmall.config.Constants;

public class Silder_Category_Interface {


    public static Silder_category service = null;

    public static Silder_category SilderCategory() {
        if (service == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            service = retrofit.create(Silder_category.class);
        }
        return service;
    }


    public interface Silder_category {
        @FormUrlEncoded
        @POST("Topdata")
        Call<SilderCategoryList> getSilder_Category(
                @Field("stop") String stop
        );
    }

}
