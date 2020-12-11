package tech.iwish.pickmall.other;

public class CategoryList {

    private String catagory_id;
    private String item_id;
    private String category_name;
    private String type;
    private String img;

    public CategoryList(String catagory_id, String item_id, String category_name, String type, String img) {
        this.catagory_id = catagory_id;
        this.item_id = item_id;
        this.category_name = category_name;
        this.type = type;
        this.img = img;
    }

    public String getCatagory_id() {
        return catagory_id;
    }

    public void setCatagory_id(String catagory_id) {
        this.catagory_id = catagory_id;
    }

    public String getItem_id() {
        return item_id;
    }

    public void setItem_id(String item_id) {
        this.item_id = item_id;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
