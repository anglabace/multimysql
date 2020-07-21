package com.multimysql.demo.pojo;

public class Category {
    private String category_id;
    public String getCategory_id() {
        return category_id;
    }


    private String category_name;
    public String getCategory_name() {
        return category_name;
    }
    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public String toString(){
        return " Goods:category_id=" + category_id +" category_name=" + category_name;
    }
}
