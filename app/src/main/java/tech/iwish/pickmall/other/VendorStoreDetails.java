package tech.iwish.pickmall.other;

public class VendorStoreDetails {

    String id;
    String shopname;
    String product_count;
    String store_follow;

    public VendorStoreDetails(String id, String shopname, String product_count, String store_follow) {
        this.id = id;
        this.shopname = shopname;
        this.product_count = product_count;
        this.store_follow = store_follow;
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

    public void setStore_follow(String store_follow) {
        this.store_follow = store_follow;
    }
}
