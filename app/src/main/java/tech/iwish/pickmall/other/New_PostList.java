package tech.iwish.pickmall.other;

public class New_PostList {

    String sno;
    String image;
    String user_number;
    String description;
    String date_time;
    String likes;

    public New_PostList(String sno, String image, String user_number, String description, String date_time, String likes) {
        this.sno = sno;
        this.image = image;
        this.user_number = user_number;
        this.description = description;
        this.date_time = date_time;
        this.likes = likes;
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

    public String getUser_number() {
        return user_number;
    }

    public void setUser_number(String user_number) {
        this.user_number = user_number;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate_time() {
        return date_time;
    }

    public void setDate_time(String date_time) {
        this.date_time = date_time;
    }
    public String getLikes() {
        return likes;
    }

    public void setLikes(String likes) {
        this.likes = likes;
    }
}
