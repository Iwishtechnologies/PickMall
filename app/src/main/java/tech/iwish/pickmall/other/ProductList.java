package tech.iwish.pickmall.other;

public class ProductList {
    private String product_id;
    private String ProductName;
    private String item_id;
    private String catagory_id;
    private String subcategory_id;
    private String sIze;
    private String colors;
    private String oty;
    private String actual_price;
    private String discount_price;
    private String status;
    private String pimg;

    public ProductList(String product_id, String productName, String item_id, String catagory_id, String subcategory_id, String sIze, String colors, String oty, String actual_price, String discount_price, String status, String pimg) {
        this.product_id = product_id;
        ProductName = productName;
        this.item_id = item_id;
        this.catagory_id = catagory_id;
        this.subcategory_id = subcategory_id;
        this.sIze = sIze;
        this.colors = colors;
        this.oty = oty;
        this.actual_price = actual_price;
        this.discount_price = discount_price;
        this.status = status;
        this.pimg = pimg;
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

    public String getSubcategory_id() {
        return subcategory_id;
    }

    public void setSubcategory_id(String subcategory_id) {
        this.subcategory_id = subcategory_id;
    }

    public String getsIze() {
        return sIze;
    }

    public void setsIze(String sIze) {
        this.sIze = sIze;
    }

    public String getColors() {
        return colors;
    }

    public void setColors(String colors) {
        this.colors = colors;
    }

    public String getOty() {
        return oty;
    }

    public void setOty(String oty) {
        this.oty = oty;
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
}
