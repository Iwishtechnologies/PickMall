package tech.iwish.pickmall.other;

public class NewsList {

    String sno;
    String description;
    String image;
    String likes;
    String date_time;
    String status;


    public NewsList(String sno, String description, String image, String likes, String date_time, String status) {
        this.sno = sno;
        this.description = description;
        this.image = image;
        this.likes = likes;
        this.date_time = date_time;
        this.status = status;
    }


    public String getSno() {
        return sno;
    }

    public void setSno(String sno) {
        this.sno = sno;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLikes() {
        return likes;
    }

    public void setLikes(String likes) {
        this.likes = likes;
    }

    public String getDate_time() {
        return date_time;
    }

    public void setDate_time(String date_time) {
        this.date_time = date_time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
