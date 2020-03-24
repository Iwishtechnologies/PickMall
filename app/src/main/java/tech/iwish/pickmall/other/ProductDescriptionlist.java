package tech.iwish.pickmall.other;

public class ProductDescriptionlist {

    private String sno;
    private String product_id;
    private String description_title;
    private String description_data;


    public ProductDescriptionlist(String sno, String product_id, String description_title, String description_data) {
        this.sno = sno;
        this.product_id = product_id;
        this.description_title = description_title;
        this.description_data = description_data;
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

    public String getDescription_title() {
        return description_title;
    }

    public void setDescription_title(String description_title) {
        this.description_title = description_title;
    }

    public String getDescription_data() {
        return description_data;
    }

    public void setDescription_data(String description_data) {
        this.description_data = description_data;
    }
}
