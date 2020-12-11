
package tech.iwish.pickmall.RetrofitModel.FlashSale_friendDeal;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FlashSaleFriendDealList {

    @SerializedName("flashsale")
    @Expose
    private String flashsale;
    @SerializedName("friendsdeal")
    @Expose
    private String friendsdeal;
    @SerializedName("flashsaledata")
    @Expose
    private List<Flashsaledatum> flashsaledata = null;
    @SerializedName("friendsdata")
    @Expose
    private List<Friendsdatum> friendsdata = null;

    public String getFlashsale() {
        return flashsale;
    }

    public void setFlashsale(String flashsale) {
        this.flashsale = flashsale;
    }

    public String getFriendsdeal() {
        return friendsdeal;
    }

    public void setFriendsdeal(String friendsdeal) {
        this.friendsdeal = friendsdeal;
    }

    public List<Flashsaledatum> getFlashsaledata() {
        return flashsaledata;
    }

    public void setFlashsaledata(List<Flashsaledatum> flashsaledata) {
        this.flashsaledata = flashsaledata;
    }

    public List<Friendsdatum> getFriendsdata() {
        return friendsdata;
    }

    public void setFriendsdata(List<Friendsdatum> friendsdata) {
        this.friendsdata = friendsdata;
    }

}
