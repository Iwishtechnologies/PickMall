package tech.iwish.pickmall.other;

public class ProductColorList {

    private String sno;
    private String product_id;
    private String imgname;
    private String size;
    private String color;
    private String qty;
    private boolean aBoolean = true;

    public ProductColorList(String sno, String product_id, String imgname, String size, String color, String qty) {
        this.sno = sno;
        this.product_id = product_id;
        this.imgname = imgname;
        this.size = size;
        this.color = color;
        this.qty = qty;
        this.aBoolean = aBoolean;
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

    public String getImgname() {
        return imgname;
    }

    public void setImgname(String imgname) {
        this.imgname = imgname;
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

    public boolean isaBoolean() {
        return aBoolean;
    }

    public void setaBoolean(boolean aBoolean) {
        this.aBoolean = aBoolean;
    }
}
