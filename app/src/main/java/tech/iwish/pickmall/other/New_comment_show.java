package tech.iwish.pickmall.other;

public class New_comment_show {

    String sno;
    String user_number;
    String new_post_sno;
    String comment;
    String name;

    public New_comment_show(String sno, String user_number, String new_post_sno, String comment, String name) {
        this.sno = sno;
        this.user_number = user_number;
        this.new_post_sno = new_post_sno;
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

    public String getNew_post_sno() {
        return new_post_sno;
    }

    public void setNew_post_sno(String new_post_sno) {
        this.new_post_sno = new_post_sno;
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

