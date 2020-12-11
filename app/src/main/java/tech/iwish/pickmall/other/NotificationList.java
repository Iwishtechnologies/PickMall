package tech.iwish.pickmall.other;

public class NotificationList {

    String sno;
    String title;
    String body;
    String start_date;
    String end_date;
    String status;
    String image;

    public NotificationList(String sno, String title, String body, String start_date, String end_date, String status, String image) {
        this.sno = sno;
        this.title = title;
        this.body = body;
        this.start_date = start_date;
        this.end_date = end_date;
        this.status = status;
        this.image = image;
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

    public void setImage(String sno) {
        this.image = sno;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
