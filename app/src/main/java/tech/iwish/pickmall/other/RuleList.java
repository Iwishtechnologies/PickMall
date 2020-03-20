package tech.iwish.pickmall.other;

public class RuleList {

    private String sno;
    private String title;
    private String subtitle;
    private String icon;

    public RuleList(String sno, String title, String subtitle, String icon) {
        this.sno = sno;
        this.title = title;
        this.subtitle = subtitle;
        this.icon = icon;
    }

    public String getSno() {
        return sno;
    }

    public void setSno(String sno) {
        this.sno = sno;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
