package tech.iwish.pickmall.other;

public class Comment_list {

    String sno;
    String user_number;
    String news_sno;
    String comment;
    String name;

    public Comment_list(String sno, String user_number, String news_sno, String comment, String name) {
        this.sno = sno;
        this.user_number = user_number;
        this.news_sno = news_sno;
        this.comment = comment;
        this.name = name;
    }

    public String getSno() {
        return sno;
    }

    public void setSno(String sno) {
        this.sno = sno;
    }

    public String getUser_number() {
        return user_number;
    }

    public void setUser_number(String user_number) {
        this.user_number = user_number;
    }

    public String getNews_sno() {
        return news_sno;
    }

    public void setNews_sno(String news_sno) {
        this.news_sno = news_sno;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
