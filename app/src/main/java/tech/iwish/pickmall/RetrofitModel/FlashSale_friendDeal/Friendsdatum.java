
package tech.iwish.pickmall.RetrofitModel.FlashSale_friendDeal;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Friendsdatum {

    @SerializedName("product_id")
    @Expose
    private Integer productId;
    @SerializedName("productName")
    @Expose
    private String productName;
    @SerializedName("item_id")
    @Expose
    private Integer itemId;
    @SerializedName("catagory_id")
    @Expose
    private Integer catagoryId;
    @SerializedName("actual_price")
    @Expose
    private String actualPrice;
    @SerializedName("discount_price")
    @Expose
    private String discountPrice;
    @SerializedName("discount_price_per")
    @Expose
    private String discountPricePer;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("pimg")
    @Expose
    private String pimg;
    @SerializedName("vendor_id")
    @Expose
    private Integer vendorId;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("datetime")
    @Expose
    private String datetime;
    @SerializedName("FakeRating")
    @Expose
    private Integer fakeRating;
    @SerializedName("req_users_shares")
    @Expose
    private Integer reqUsersShares;
    @SerializedName("new_users_atleast")
    @Expose
    private Integer newUsersAtleast;
    @SerializedName("SKU")
    @Expose
    private String sKU;
    @SerializedName("gst")
    @Expose
    private Integer gst;
    @SerializedName("hsn_no")
    @Expose
    private Integer hsnNo;
    @SerializedName("length")
    @Expose
    private Integer length;
    @SerializedName("width")
    @Expose
    private Integer width;
    @SerializedName("height")
    @Expose
    private Integer height;
    @SerializedName("weight")
    @Expose
    private Double weight;

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public Integer getCatagoryId() {
        return catagoryId;
    }

    public void setCatagoryId(Integer catagoryId) {
        this.catagoryId = catagoryId;
    }

    public String getActualPrice() {
        return actualPrice;
    }

    public void setActualPrice(String actualPrice) {
        this.actualPrice = actualPrice;
    }

    public String getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(String discountPrice) {
        this.discountPrice = discountPrice;
    }

    public String getDiscountPricePer() {
        return discountPricePer;
    }

    public void setDiscountPricePer(String discountPricePer) {
        this.discountPricePer = discountPricePer;
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

    public Integer getVendorId() {
        return vendorId;
    }

    public void setVendorId(Integer vendorId) {
        this.vendorId = vendorId;
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

    public Integer getFakeRating() {
        return fakeRating;
    }

    public void setFakeRating(Integer fakeRating) {
        this.fakeRating = fakeRating;
    }

    public Integer getReqUsersShares() {
        return reqUsersShares;
    }

    public void setReqUsersShares(Integer reqUsersShares) {
        this.reqUsersShares = reqUsersShares;
    }

    public Integer getNewUsersAtleast() {
        return newUsersAtleast;
    }

    public void setNewUsersAtleast(Integer newUsersAtleast) {
        this.newUsersAtleast = newUsersAtleast;
    }

    public String getSKU() {
        return sKU;
    }

    public void setSKU(String sKU) {
        this.sKU = sKU;
    }

    public Integer getGst() {
        return gst;
    }

    public void setGst(Integer gst) {
        this.gst = gst;
    }

    public Integer getHsnNo() {
        return hsnNo;
    }

    public void setHsnNo(Integer hsnNo) {
        this.hsnNo = hsnNo;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

}
