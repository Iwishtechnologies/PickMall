package tech.iwish.pickmall.other;

public class OneWinShareList {
    String id,name,product,shares;
  public OneWinShareList(String id,String name,String product,String shares ){
      this.id=id;
      this.name=name;
      this.product=product;
      this.shares=shares;
  }

  public void setId(String id){
      this.id=id;
  }
  public void setName(String name){
      this.name=name;
  }
  public void setProduct(String product){
      this.product=product;
  }
  public void setShares(String shares){
      this.shares=shares;
  }
  public String getId(){
      return id;
  }
    public String getProduct(){
        return product;
    }
    public String getName(){
        return name;
    }
    public String getShares(){
        return shares;
    }
}
