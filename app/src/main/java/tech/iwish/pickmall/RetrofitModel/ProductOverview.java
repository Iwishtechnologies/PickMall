
package tech.iwish.pickmall.RetrofitModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductOverview {

    @SerializedName("sno")
    @Expose
    private Integer sno;
    @SerializedName("product_id")
    @Expose
    private Integer productId;
    @SerializedName("overview")
    @Expose
    private String overview;
    @SerializedName("title")
    @Expose
    private String title;

    public Integer getSno() {
        return sno;
    }

    public void setSno(Integer sno) {
        this.sno = sno;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
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
