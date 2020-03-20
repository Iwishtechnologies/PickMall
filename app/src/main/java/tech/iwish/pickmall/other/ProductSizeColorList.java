package tech.iwish.pickmall.other;

public class ProductSizeColorList {
    private String sno;
    private String product_id;
    private String size;
    private String color;
    private String qty;


    public ProductSizeColorList(String sno, String product_id, String size, String color, String qty) {
        this.sno = sno;
        this.product_id = product_id;
        this.size = size;
        this.color = color;
        this.qty = qty;
    }

    public String getSno() {
        return sno;
    }

    public void setSno(String sno) {
        this.sno = sno;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }
}
