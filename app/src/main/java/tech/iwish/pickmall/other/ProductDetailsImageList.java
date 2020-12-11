package tech.iwish.pickmall.other;

public class ProductDetailsImageList {

    private String sno;
    private String product_id;
    private String image;

    public ProductDetailsImageList(String sno, String product_id, String image) {
        this.sno = sno;
        this.product_id = product_id;
        this.image = image;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
