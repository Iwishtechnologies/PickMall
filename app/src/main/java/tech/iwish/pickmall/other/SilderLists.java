package tech.iwish.pickmall.other;

public class SilderLists {
    private String sno ;
    private String image;
    private String categoryid;
    private String status;

    public SilderLists(String sno, String image, String categoryid, String status) {
        this.sno = sno;
        this.image = image;
        this.categoryid = categoryid;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
