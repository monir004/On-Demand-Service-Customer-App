package com.dhakasetup.sakib.dhakasetupprototype.model.datamodel;

import java.util.ArrayList;
import java.util.List;

public class Category {
    String cat_id;
    String cat_name;
    String cat_image;
    int ser_counter;
    List<Subcat> subcats = new ArrayList<>();

    public Category() {
    }

    public Category(String cat_id, String cat_name, int ser_counter, List<Subcat> subcats) {
        this.cat_id = cat_id;
        this.cat_name = cat_name;
        this.ser_counter = ser_counter;
        this.subcats = subcats;
    }

    public String getCat_image() {
        return cat_image;
    }

    public void setCat_image(String cat_image) {
        this.cat_image = cat_image;
    }

    public String getCat_id() {
        return cat_id;
    }

    public void setCat_id(String cat_id) {
        this.cat_id = cat_id;
    }

    public String getCat_name() {
        return cat_name;
    }

    public void setCat_name(String cat_name) {
        this.cat_name = cat_name;
    }

    public int getSer_counter() {
        return ser_counter;
    }

    public void setSer_counter(int ser_counter) {
        this.ser_counter = ser_counter;
    }

    public List<Subcat> getSubcats() {
        return subcats;
    }

    public void setSubcats(List<Subcat> subcats) {
        this.subcats = subcats;
    }
}
