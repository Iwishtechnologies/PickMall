package tech.iwish.pickmall.other;

public class WinList {
    String client_name,product_name,product_image,product_type;
    public WinList(String client_name,String product_name,String product_image,String product_type ){
        this.client_name=client_name;
        this.product_name=product_name;
        this.product_image=product_image;
        this.product_type=product_type;
    }
    public void  setClient_name(String type){
        this.client_name=type;
    }
    public void  setProduct_name(String win){
        this.product_name=win;
    }
    public void  setProduct_image(String win){
        this.product_image=win;
    }
    public void  setProduct_type(String win){
        this.product_type=win;
    }
    public String getClient_name(){
        return client_name;
    }
    public String getProduct_name(){
        return product_name;
    }
    public String getProduct_image(){
        return product_image;
    }
    public String getProduct_type(){
        return product_type;
    }
}
