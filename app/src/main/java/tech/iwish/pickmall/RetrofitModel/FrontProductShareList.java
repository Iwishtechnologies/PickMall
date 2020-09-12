
package tech.iwish.pickmall.RetrofitModel;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FrontProductShareList {

    @SerializedName("response")
    @Expose
    private String response;
    @SerializedName("product_description")
    @Expose
    private List<ProductDescription> productDescription = null;
    @SerializedName("product_image")
    @Expose
    private List<ProductImage> productImage = null;
    @SerializedName("product_overview")
    @Expose
    private List<ProductOverview> productOverview = null;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public List<ProductDescription> getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(List<ProductDescription> productDescription) {
        this.productDescription = productDescription;
    }

    public List<ProductImage> getProductImage() {
        return productImage;
    }

    public void setProductImage(List<ProductImage> productImage) {
        this.productImage = productImage;
    }

    public List<ProductOverview> getProductOverview() {
        return productOverview;
    }

    public void setProductOverview(List<ProductOverview> productOverview) {
        this.productOverview = productOverview;
    }

}
