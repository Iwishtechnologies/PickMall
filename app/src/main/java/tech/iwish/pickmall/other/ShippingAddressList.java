package tech.iwish.pickmall.other;

public class ShippingAddressList {
    String Id,Name,Address,Pin,City_State,Type;

  public ShippingAddressList(String Id,String Name,String address,String pin,String city_state,String type ) {
        this.Id=Id;
        this.Name=Name;
        this.Address=address;
        this.Pin=pin;
        this.City_State=city_state;
        this.Type=type;
  }

    public void setId(String id){
      this.Id=id;
  }

    public void setName(String name){
      this.Name=name;
  }

    public void setAddress(String address){
        this.Address=address;
    }

    public void setPin(String  pin){
        this. Pin=pin;
    }

    public void setCity_State(String city_state){
        this.City_State=city_state;
    }

    public void setType(String type){
        this.Type=type;
    }

    public String getId(){
      return Id;
    }

    public String getName(){
        return Name;
    }

    public String getAddress(){
        return Address;
    }

    public String getPin(){
        return Pin;
    }

    public String getCity_State(){
        return City_State;
    }

    public String getType(){
        return Type;
    }
}
