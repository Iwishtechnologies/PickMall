package tech.iwish.pickmall.other;

import android.widget.ImageView;

public class VendorStoreDetails {

    String id;
    String shopname;
    String product_count;
    String store_follow;
    String Image;
    String rating;

    public VendorStoreDetails(String id, String shopname, String product_count, String store_follow,String image,String rating) {
        this.id = id;
        this.shopname = shopname;
        this.product_count = product_count;
        this.store_follow = store_follow;
        this.Image = image;
        this.rating = rating;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getShopname() {
        return shopname;
    }

    public void setShopname(String shopname) {
        this.shopname = shopname;
    }

    public String getProduct_count() {
        return product_count;
    }

    public void setProduct_count(String product_count) {
        this.product_count = product_count;
    }

    public String getStore_follow() {
        return store_follow;
    }
    public String getImage() {
        return Image;
    }
    public String getRating() {
        return rating;
    }

    public void setStore_follow(String store_follow) {
        this.store_follow = store_follow;
    }
    public void setImage(String image) {
        this.Image = image;
    }
    public void setRating(String image) {
        this.rating = image;
    }
}
