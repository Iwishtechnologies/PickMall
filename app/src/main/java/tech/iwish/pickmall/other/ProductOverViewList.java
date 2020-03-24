package tech.iwish.pickmall.other;

public class ProductOverViewList {

    private String sno;
    private String product_id;
    private String overview;
    private String title;

    public ProductOverViewList(String sno, String product_id, String overview, String title) {
        this.sno = sno;
        this.product_id = product_id;
        this.overview = overview;
        this.title = title;
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

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
