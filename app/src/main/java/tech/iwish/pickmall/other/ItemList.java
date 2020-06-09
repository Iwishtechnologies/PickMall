package tech.iwish.pickmall.other;

public class ItemList {

    private String item_id;
    private String item_name;
    private String icon_img;
    private String type;
    private String item_type;

    public ItemList(String item_id, String item_name, String icon_img, String type, String item_type) {
        this.item_id = item_id;
        this.item_name = item_name;
        this.icon_img = icon_img;
        this.type = type;
        this.item_type = item_type;
    }

    public String getItem_id() {
        return item_id;
    }

    public void setItem_id(String item_id) {
        this.item_id = item_id;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public String getIcon_img() {
        return icon_img;
    }

    public void setIcon_img(String icon_img) {
        this.icon_img = icon_img;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getItem_type() {
        return item_type;
    }

    public void setItem_type(String item_type) {
        this.item_type = item_type;
    }
}
