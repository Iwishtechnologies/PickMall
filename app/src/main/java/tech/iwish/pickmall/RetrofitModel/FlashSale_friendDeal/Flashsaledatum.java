
package tech.iwish.pickmall.RetrofitModel.FlashSale_friendDeal;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Flashsaledatum {

    @SerializedName("product_id")
    @Expose
    private Integer productId;
    @SerializedName("ProductName")
    @Expose
    private String productName;
    @SerializedName("SKU")
    @Expose
    private String sKU;
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
    @SerializedName("FakeRating")
    @Expose
    private Double fakeRating;
    @SerializedName("gst")
    @Expose
    private String gst;
    @SerializedName("hot_product")
    @Expose
    private String hotProduct;
    @SerializedName("hsn_no")
    @Expose
    private Integer hsnNo;
    @SerializedName("weight")
    @Expose
    private Double weight;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("flash_sale")
    @Expose
    private String flashSale;
    @SerializedName("length")
    @Expose
    private Integer length;
    @SerializedName("width")
    @Expose
    private Integer width;
    @SerializedName("height")
    @Expose
    private Integer height;
    @SerializedName("AIstatus")
    @Expose
    private String aIstatus;

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

    public String getSKU() {
        return sKU;
    }

    public void setSKU(String sKU) {
        this.sKU = sKU;
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

    public Double getFakeRating() {
        return fakeRating;
    }

    public void setFakeRating(Double fakeRating) {
        this.fakeRating = fakeRating;
    }

    public String getGst() {
        return gst;
    }

    public void setGst(String gst) {
        this.gst = gst;
    }

    public String getHotProduct() {
        return hotProduct;
    }

    public void setHotProduct(String hotProduct) {
        this.hotProduct = hotProduct;
    }

    public Integer getHsnNo() {
        return hsnNo;
    }

    public void setHsnNo(Integer hsnNo) {
        this.hsnNo = hsnNo;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFlashSale() {
        return flashSale;
    }

    public void setFlashSale(String flashSale) {
        this.flashSale = flashSale;
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

    public String getAIstatus() {
        return aIstatus;
    }

    public void setAIstatus(String aIstatus) {
        this.aIstatus = aIstatus;
    }

}
