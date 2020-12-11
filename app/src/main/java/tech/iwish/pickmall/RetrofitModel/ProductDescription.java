
package tech.iwish.pickmall.RetrofitModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductDescription {

    @SerializedName("sno")
    @Expose
    private Integer sno;
    @SerializedName("product_id")
    @Expose
    private Integer productId;
    @SerializedName("description_title")
    @Expose
    private String descriptionTitle;
    @SerializedName("description_data")
    @Expose
    private String descriptionData;

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

    public String getDescriptionTitle() {
        return descriptionTitle;
    }

    public void setDescriptionTitle(String descriptionTitle) {
        this.descriptionTitle = descriptionTitle;
    }

    public String getDescriptionData() {
        return descriptionData;
    }

    public void setDescriptionData(String descriptionData) {
        this.descriptionData = descriptionData;
    }

}
