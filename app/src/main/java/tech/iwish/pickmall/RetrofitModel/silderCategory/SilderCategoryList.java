
package tech.iwish.pickmall.RetrofitModel.silderCategory;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SilderCategoryList {

    @SerializedName("response")
    @Expose
    private String response;
    @SerializedName("category")
    @Expose
    private List<Category> category = null;
    @SerializedName("slider")
    @Expose
    private List<Slider> slider = null;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public List<Category> getCategory() {
        return category;
    }

    public void setCategory(List<Category> category) {
        this.category = category;
    }

    public List<Slider> getSlider() {
        return slider;
    }

    public void setSlider(List<Slider> slider) {
        this.slider = slider;
    }

}
