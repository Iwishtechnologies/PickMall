package tech.iwish.pickmall.other;

public class BottomFragmentRuleList {
    String sno,dealid,rule;
    public BottomFragmentRuleList(String sno, String dealid, String rule){
        this.dealid=dealid;
        this.rule=rule;
        this.sno=sno;
    }
    public void setSno(String sno){
        this.sno= sno;
    }
    public void setDealid(String dealid){
        this.dealid=dealid;
    }
    public void setRule(String rule){
        this.rule=rule;
    }
    public String getSno(){
        return sno;
    }
    public String getDealid(){
        return dealid;
    }
    public String getRule(){
        return rule;
    }
}
