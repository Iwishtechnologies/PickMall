package tech.iwish.pickmall.other;

public class RankList {
    String id ,name,reward;

    public RankList(String id, String name, String reward){
            this.id=id;
            this.name=name;
            this.reward=reward;
    }

    public  void setId(String id){
       this.id=id;
    }
    public  void setName(String name){
        this.name=name;
    }
    public  void setReward(String reward){
        this.reward=reward;
    }

    public String getId(){
        return id;
    }
    public String getName(){
        return name;
    }
    public String getReward(){
        return reward;
    }

}
