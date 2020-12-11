package tech.iwish.pickmall.other;

public class SilderLists {
    private String sno ;
    private String image;
    private String categoryid;
    private String item_name;
    private String status;

    public SilderLists(String sno, String image, String categoryid, String item_name, String status) {
        this.sno = sno;
        this.image = image;
        this.categoryid = categoryid;
        this.item_name = item_name;
        this.status = status;
    }

    public String getSno() {
        return sno;
    }

    public void setSno(String sno) {
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

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
