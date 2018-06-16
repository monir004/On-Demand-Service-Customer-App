package com.dhakasetup.sakib.dhakasetupprototype.model;

import com.dhakasetup.sakib.dhakasetupprototype.model.datamodel.Service;

import java.util.ArrayList;
import java.util.List;

public class CategoryOld {
    String cat_id;
    String cat_name;
    int ser_counter;
    List<Service> serviceList;

    public CategoryOld() {
        cat_id = "21";
        cat_name = "category";
        ser_counter = 2;
        serviceList = new ArrayList<>();
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

    public List<Service> getServiceList() {
        return serviceList;
    }

    public void setServiceList(List<Service> serviceList) {
        this.serviceList = serviceList;
    }
}
