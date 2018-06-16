package com.dhakasetup.sakib.dhakasetupprototype.model.datamodel;

import java.util.ArrayList;
import java.util.List;

public class Subcat {
    String subCat_id="",subCat_name="";
    List<Service> services = new ArrayList<>();

    public Subcat(String subCat_id, String subCat_name, List<Service> services) {
        this.subCat_id = subCat_id;
        this.subCat_name = subCat_name;
        this.services = services;
    }

    public Subcat(String subCat_id, String subCat_name) {
        this.subCat_id = subCat_id;
        this.subCat_name = subCat_name;
    }

    public Subcat() {
    }

    public List<Service> getServices() {
        return services;
    }

    public void setServices(List<Service> services) {
        this.services = services;
    }

    public String getSubCat_id() {
        return subCat_id;
    }

    public void setSubCat_id(String subCat_id) {
        this.subCat_id = subCat_id;
    }

    public String getSubCat_name() {
        return subCat_name;
    }

    public void setSubCat_name(String subCat_name) {
        this.subCat_name = subCat_name;
    }
}
