package com.company;

/**
 * Created by adimn on 2017/5/27.
 */
public class IndustryModel {
    private String id;
    private String category_code;
    private String categpry_str;

    public IndustryModel(String id, String category_code, String categpry_str) {
        this.id = id;
        this.category_code = category_code;
        this.categpry_str = categpry_str;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategory_code() {
        return category_code;
    }

    public void setCategory_code(String category_code) {
        this.category_code = category_code;
    }

    public String getCategpry_str() {
        return categpry_str;
    }

    public void setCategpry_str(String categpry_str) {
        this.categpry_str = categpry_str;
    }

    @Override
    public String toString() {
        return "IndustryModel{" +
                "id='" + id + '\'' +
                ", category_code='" + category_code + '\'' +
                ", categpry_str='" + categpry_str + '\'' +
                '}';
    }
}
