package tech.iwish.pickmall.other;

public class ProductSizeColorList {
    private String sno;
    private String product_id;
    private String imgname;
    private String size;
    private String color;
    private String qty;
    private String count_size;
    private boolean isSelected = false;

    public ProductSizeColorList(String sno, String product_id, String imgname, String size, String color, String qty, String count_size) {
        this.sno = sno;
        this.product_id = product_id;
        this.imgname = imgname;
        this.size = size;
        this.color = color;
        this.qty = qty;
        this.count_size = count_size;

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

    public String getCount_size() {
        return count_size;
    }

    public void setCount_size(String count_size) {
        this.count_size = count_size;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
