package tech.iwish.pickmall.other;

public class CouponList {
    String title,subtitle,termscondition,icon,validity,prize,vendorid,code;

    public CouponList(String name_title, String subtitle, String termscon, String icon, String validity, String prize, String code, String vendorid) {
        this.title=name_title;
        this.subtitle= subtitle;
        this.termscondition=termscon;
        this.icon=icon;
        this.validity=validity;
        this.prize=prize;
        this.vendorid=vendorid;
        this.code=code;
    }


    public void setTitle(String title){
        this.title=title;
    }

    public void setSubtitle(String subtitle){
        this.subtitle=subtitle;
    }

    public void setTermscondition(String termscondition){
        this.termscondition=termscondition;
    }

    public void setIcon(String icon){
        this.icon=icon;
    }

    public void setValidity(String validity){
        this.validity=validity;
    }

    public void setPrize(String prize){
        this.prize=prize;
    }

    public void setVendorid(String vendorid){
        this.vendorid=vendorid;
    }

    public void setCode(String code){
        this.code=code;
    }

    public String getTitle(){
        return title;
    }

    public String getSubtitle(){
        return subtitle;
    }

    public String  getTermscondition(){
        return termscondition;
    }

    public String getIcon(){
        return icon;
    }

    public String getValidity(){
        return validity;
    }

    public String getPrize(){
        return prize;
    }

    public String getVendorid(){
        return vendorid;
    }

    public String getCode(){
        return code;
    }

}
