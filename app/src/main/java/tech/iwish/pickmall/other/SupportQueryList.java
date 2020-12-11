package tech.iwish.pickmall.other;

public class SupportQueryList {
    String id ,query,answer;

    public SupportQueryList(String id,String query, String answer){
        this.id=id;
        this.query=query;
        this.answer=answer;
    }

    public void setId(String  id){
        this.id=id;
    }

    public void setQuery(String  query){
        this.query=query;
    }

    public void setAnswer(String  answer){
        this.answer=answer;
    }

    public String getId(){
        return id;
    }

    public String getQuery(){
        return query;
    }

    public String getAnswer(){
        return answer;
    }
}

