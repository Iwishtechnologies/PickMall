package tech.iwish.pickmall.RetrofitInterface;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import tech.iwish.pickmall.RetrofitModel.FlashSale_friendDeal.FlashSaleFriendDealList;
import tech.iwish.pickmall.config.Constants;

public class FlashSale_FriendDealInterface {

    public static FlashSale_FriendDeal service = null;

    public static FlashSale_FriendDeal ProductFrontShare() {
        if (service == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            service = retrofit.create(FlashSale_FriendDeal.class);
        }
        return service;
    }


    public interface FlashSale_FriendDeal {
        @FormUrlEncoded
        @POST("Bottomdata")
        Call<FlashSaleFriendDealList> getflashSale_friend_deal(
                @Field("stop") String stop
        );
    }

}
