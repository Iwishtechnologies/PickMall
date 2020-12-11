package tech.iwish.pickmall.other;

public class OneWinShareList {
    String top_client , client_name;

    public OneWinShareList(String top_client, String client_name) {
        this.top_client = top_client;
        this.client_name = client_name;
    }

    public String getTop_client() {
        return top_client;
    }

    public void setTop_client(String top_client) {
        this.top_client = top_client;
    }

    public String getClient_name() {
        return client_name;
    }

    public void setClient_name(String client_name) {
        this.client_name = client_name;
    }
}
