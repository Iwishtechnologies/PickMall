
package tech.iwish.pickmall.RetrofitModel.hotProduct;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HotProductList {

    @SerializedName("response")
    @Expose
    private String response;
    @SerializedName("data")
    @Expose
    private List<Datum> data = null;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }

}
