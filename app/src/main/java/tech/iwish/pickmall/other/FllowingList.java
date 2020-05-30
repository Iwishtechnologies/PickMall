package tech.iwish.pickmall.other;

public class FllowingList {
    String name,image,id;

    public FllowingList(String name, String id, String image) {
        this.name=name;
        this.id=id;
        this.image=image;
    }

    public void  setName(String name){
        this.name=name;
    }

    public void  setImage(String image){
        this.image=image;
    }

    public void  setId(String id){
        this.id=id;
    }

    public String getName(){
        return name;
    }
    public String getImage(){
        return image;
    }
    public String getId(){
        return id;
    }
}
