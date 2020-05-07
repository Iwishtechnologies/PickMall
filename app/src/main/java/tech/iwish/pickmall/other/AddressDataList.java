package tech.iwish.pickmall.other;

public class AddressDataList {

    private String sno;
    private String mobile;
    private String name;
    private String delivery_number;
    private String pincode;
    private String house_no;
    private String colony;
    private String landmark;
    private String city;
    private String state;
    private String address_type;
    private String status;


    public AddressDataList(String sno, String mobile, String name, String delivery_number, String pincode, String house_no, String colony, String landmark, String city, String state, String address_type, String status) {
        this.sno = sno;
        this.mobile = mobile;
        this.name = name;
        this.delivery_number = delivery_number;
        this.pincode = pincode;
        this.house_no = house_no;
        this.colony = colony;
        this.landmark = landmark;
        this.city = city;
        this.state = state;
        this.address_type = address_type;
        this.status = status;
    }


    public String getSno() {
        return sno;
    }

    public void setSno(String sno) {
        this.sno = sno;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDelivery_number() {
        return delivery_number;
    }

    public void setDelivery_number(String delivery_number) {
        this.delivery_number = delivery_number;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getHouse_no() {
        return house_no;
    }

    public void setHouse_no(String house_no) {
        this.house_no = house_no;
    }

    public String getColony() {
        return colony;
    }

    public void setColony(String colony) {
        this.colony = colony;
    }

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getAddress_type() {
        return address_type;
    }

    public void setAddress_type(String address_type) {
        this.address_type = address_type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
