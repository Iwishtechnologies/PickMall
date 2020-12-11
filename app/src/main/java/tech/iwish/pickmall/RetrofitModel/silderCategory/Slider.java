
package tech.iwish.pickmall.RetrofitModel.silderCategory;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Slider {

    @SerializedName("sno")
    @Expose
    private Integer sno;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("categoryid")
    @Expose
    private String categoryid;
    @SerializedName("item_name")
    @Expose
    private String itemName;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("startdate")
    @Expose
    private String startdate;
    @SerializedName("enddate")
    @Expose
    private String enddate;

    public Integer getSno() {
        return sno;
    }

    public void setSno(Integer sno) {
        this.sno = sno;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCategoryid() {
        return categoryid;
    }

    public void setCategoryid(String categoryid) {
        this.categoryid = categoryid;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStartdate() {
        return startdate;
    }

    public void setStartdate(String startdate) {
        this.startdate = startdate;
    }

    public String getEnddate() {
        return enddate;
    }

    public void setEnddate(String enddate) {
        this.enddate = enddate;
    }

}
