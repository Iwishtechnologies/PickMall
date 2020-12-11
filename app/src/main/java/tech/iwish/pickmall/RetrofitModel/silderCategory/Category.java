
package tech.iwish.pickmall.RetrofitModel.silderCategory;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Category {

    @SerializedName("item_id")
    @Expose
    private Integer itemId;
    @SerializedName("item_name")
    @Expose
    private String itemName;
    @SerializedName("icon_img")
    @Expose
    private String iconImg;
    @SerializedName("banner_img")
    @Expose
    private String bannerImg;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("item_type")
    @Expose
    private String itemType;
    @SerializedName("shows")
    @Expose
    private String shows;
    @SerializedName("aline")
    @Expose
    private Integer aline;

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getIconImg() {
        return iconImg;
    }

    public void setIconImg(String iconImg) {
        this.iconImg = iconImg;
    }

    public String getBannerImg() {
        return bannerImg;
    }

    public void setBannerImg(String bannerImg) {
        this.bannerImg = bannerImg;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public String getShows() {
        return shows;
    }

    public void setShows(String shows) {
        this.shows = shows;
    }

    public Integer getAline() {
        return aline;
    }

    public void setAline(Integer aline) {
        this.aline = aline;
    }

}
