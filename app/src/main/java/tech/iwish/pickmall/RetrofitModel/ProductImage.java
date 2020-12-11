
package tech.iwish.pickmall.RetrofitModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductImage {

    @SerializedName("sno")
    @Expose
    private Integer sno;
    @SerializedName("product_id")
    @Expose
    private Integer productId;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("size")
    @Expose
    private String size;
    @SerializedName("color")
    @Expose
    private String color;
    @SerializedName("qty")
    @Expose
    private String qty;

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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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
