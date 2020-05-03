package tech.iwish.pickmall.other;

public class OrderList  {
    String costomer_id,delivery_add,ooder_amountt,payment_mathodd,status,date_time,order_id,Prodeuct_id,quantity,type;

    public OrderList(String customer_id, String delhivery_address, String order_amount, String payment_method, String status, String datetime, String orderid, String product_id, String qty, String type) {
    this.costomer_id=customer_id;
    this.delivery_add=delhivery_address;
    this.ooder_amountt=order_amount;
    this.payment_mathodd=payment_method;
    this.status=status;
    this.date_time= datetime;
    this.order_id=orderid;
    this.Prodeuct_id=product_id;
    this.quantity=qty;
    this.type=type;
    }


    public void  setCostomer_id(String costomer_id) {
        this.costomer_id=costomer_id;
    }

    public void  setDelivery_add(String delivery_add) {
        this.delivery_add=delivery_add;
    }

    public void  setOoder_amountt(String ooder_amountt) {
        this.ooder_amountt=ooder_amountt;
    }

    public void  setPayment_mathodd(String payment_mathodd) {
        this.payment_mathodd=payment_mathodd;
    }

    public void  setStatus(String status) {
        this.status=status;
    }

    public void  setDate_time(String date_time) {
        this.date_time=date_time;
    }

    public void  setOrder_id(String order_id) {
        this.order_id=order_id;
    }

    public void  setProdeuct_id(String prodeuct_id) {
        this.Prodeuct_id=prodeuct_id;
    }

    public void  setQuantity(String quantity) {
        this.quantity=quantity;
    }

    public void  setType(String type) {
        this.type=type;
    }

    public String getCostomer_id(){
         return costomer_id;
    }

    public String getDelivery_add(){
        return delivery_add;
    }

    public String getOoder_amountt(){
        return ooder_amountt;
    }

    public String getPayment_mathodd(){
        return payment_mathodd;
    }

    public String getStatus(){
        return status;
    }

    public String getDate_time(){
        return date_time;
    }

    public String getOrder_id(){
        return order_id;
    }

    public String getProdeuct_id(){
        return Prodeuct_id;
    }

    public String getQuantity(){
        return quantity;
    }

    public String getType(){
        return type;
    }
}
