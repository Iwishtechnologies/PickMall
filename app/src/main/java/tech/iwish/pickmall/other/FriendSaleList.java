package tech.iwish.pickmall.other;

public class FriendSaleList {

    private String product_id;
    private String productName;
    private String item_id;
    private String catagory_id;
    private String actual_price;
    private String discount_price;
    private String discount_price_per;
    private String status;
    private String pimg;
    private String vendor_id;
    private String type;
    private String datetime;
    private String FakeRating;
    private String req_users_shares;
    private String new_users_atleast;


    public FriendSaleList(String product_id, String productName, String item_id, String catagory_id, String actual_price, String discount_price, String discount_price_per, String status, String pimg, String vendor_id, String type, String datetime, String fakeRating, String req_users_shares, String new_users_atleast) {
        this.product_id = product_id;
        this.productName = productName;
        this.item_id = item_id;
        this.catagory_id = catagory_id;
        this.actual_price = actual_price;
        this.discount_price = discount_price;
        this.discount_price_per = discount_price_per;
        this.status = status;
        this.pimg = pimg;
        this.vendor_id = vendor_id;
        this.type = type;
        this.datetime = datetime;
        FakeRating = fakeRating;
        this.req_users_shares = req_users_shares;
        this.new_users_atleast = new_users_atleast;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getItem_id() {
        return item_id;
    }

    public void setItem_id(String item_id) {
        this.item_id = item_id;
    }

    public String getCatagory_id() {
        return catagory_id;
    }

    public void setCatagory_id(String catagory_id) {
        this.catagory_id = catagory_id;
    }

    public String getActual_price() {
        return actual_price;
    }

    public void setActual_price(String actual_price) {
        this.actual_price = actual_price;
    }

    public String getDiscount_price() {
        return discount_price;
    }

    public void setDiscount_price(String discount_price) {
        this.discount_price = discount_price;
    }

    public String getDiscount_price_per() {
        return discount_price_per;
    }

    public void setDiscount_price_per(String discount_price_per) {
        this.discount_price_per = discount_price_per;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPimg() {
        return pimg;
    }

    public void setPimg(String pimg) {
        this.pimg = pimg;
    }

    public String getVendor_id() {
        return vendor_id;
    }

    public void setVendor_id(String vendor_id) {
        this.vendor_id = vendor_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getFakeRating() {
        return FakeRating;
    }

    public void setFakeRating(String fakeRating) {
        FakeRating = fakeRating;
    }

    public String getReq_users_shares() {
        return req_users_shares;
    }

    public void setReq_users_shares(String req_users_shares) {
        this.req_users_shares = req_users_shares;
    }

    public String getNew_users_atleast() {
        return new_users_atleast;
    }

    public void setNew_users_atleast(String new_users_atleast) {
        this.new_users_atleast = new_users_atleast;
    }
}

























