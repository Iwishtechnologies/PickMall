package tech.iwish.pickmall.other;

public class TrackorderList {
    String date,location,activity;
    public TrackorderList(String date,String location,String activity){
        this.date=date;
        this.location=location;
        this.activity=activity;
    }

    public void setDate(String date){
        this.date=date;
    }
    public void setLocation(String location){
        this.location=location;
    }
    public void setActivity(String activity){
        this.activity=activity;
    }
    public String getDate(){
        return date;
    }
    public String getLocation(){
        return location;
    }
    public String getActivity(){
        return activity;
    }
}
