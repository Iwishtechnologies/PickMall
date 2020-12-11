package tech.iwish.pickmall.other;

public class WalletList {
    String id,clientid,amount,date,description,type,status;

    public  WalletList(String id,String clientid,String amount,String date,String description, String type,String Status){
          this.id=id;
          this.clientid=clientid;
          this.amount=amount;
          this.date=date;
          this.description=description;
          this.type=type;
          this.status=Status;
    }

    public void setId(String id){
        this.id=id;
    }
    public void setClientid(String clientid){
        this.clientid=clientid;
    }
    public void setAmount(String amount){
        this.amount=amount;
    }
    public void setDate(String date){
        this.date=date;
    }
    public void setDescription(String description){
        this.description=description;
    }
    public void setType(String type){
        this.type=type;
    }
    public void setStatus(String status){
        this.status=status;
    }

    public String getId(){
        return id;
    }
    public String getClientid(){
        return clientid;
    }
    public String getAmount(){
        return amount;
    }
    public String getDate(){
        return date;
    }
    public String getDescription(){
        return description;
    }
    public String getType(){
        return type;
    }
    public String getStatus(){
        return status;
    }

}
