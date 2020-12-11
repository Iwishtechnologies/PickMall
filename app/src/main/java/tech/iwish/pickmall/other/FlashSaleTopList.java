package tech.iwish.pickmall.other;

public class FlashSaleTopList {
    private String product_id;
    private String ProductName;
    private String SKU;
    private String item_id;
    private String catagory_id;
    private String actual_price;
    private String discount_price;
    private String discount_price_per;
    private String status;
    private String pimg;
    private String vendor_id;
    private String FakeRating;
    private String gst;
    private String hot_product;
    private String hsn_no;
    private String weight;
    private String type;
    private String flash_sale;
    private String extraoffer;
    private String startdate;
    private String enddate;

    public FlashSaleTopList(String product_id, String productName, String SKU, String item_id, String catagory_id, String actual_price, String discount_price, String discount_price_per, String status, String pimg, String vendor_id, String fakeRating, String gst, String hot_product, String hsn_no, String weight, String type, String flash_sale, String extraoffer, String startdate, String enddate) {
        this.product_id = product_id;
        ProductName = productName;
        this.SKU = SKU;
        this.item_id = item_id;
        this.catagory_id = catagory_id;
        this.actual_price = actual_price;
        this.discount_price = discount_price;
        this.discount_price_per = discount_price_per;
        this.status = status;
        this.pimg = pimg;
        this.vendor_id = vendor_id;
        FakeRating = fakeRating;
        this.gst = gst;
        this.hot_product = hot_product;
        this.hsn_no = hsn_no;
        this.weight = weight;
        this.type = type;
        this.flash_sale = flash_sale;
        this.extraoffer = extraoffer;
        this.startdate = startdate;
        this.enddate = enddate;
    }


    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public String getSKU() {
        return SKU;
    }

    public void setSKU(String SKU) {
        this.SKU = SKU;
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

    public String getFakeRating() {
        return FakeRating;
    }

    public void setFakeRating(String fakeRating) {
        FakeRating = fakeRating;
    }

    public String getGst() {
        return gst;
    }

    public void setGst(String gst) {
        this.gst = gst;
    }

    public String getHot_product() {
        return hot_product;
    }

    public void setHot_product(String hot_product) {
        this.hot_product = hot_product;
    }

    public String getHsn_no() {
        return hsn_no;
    }

    public void setHsn_no(String hsn_no) {
        this.hsn_no = hsn_no;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFlash_sale() {
        return flash_sale;
    }

    public void setFlash_sale(String flash_sale) {
        this.flash_sale = flash_sale;
    }

    public String getExtraoffer() {
        return extraoffer;
    }

    public void setExtraoffer(String extraoffer) {
        this.extraoffer = extraoffer;
    }

    public String getStartdate() {
        return startdate;
    }

    public void setStartdate(String startdate) {
        this.startdate = startdate;
    }

    public String getEnddate() {
        return enddate;
    }

    public void setEnddate(String enddate) {
        this.enddate = enddate;
    }
}
