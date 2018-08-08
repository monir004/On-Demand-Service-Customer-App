package com.dhakasetup.sakib.dhakasetupprototype.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nazmus Sakib on 04,April,2018
 * sakib6900@gmail.com
 * Project Name: DhakaSetupPrototype
 */
public class ServiceGroup {
    private List<ServiceGroupItem> items;
    private String service_group_title;
    int trend_id;
    public ServiceGroup() {
        items = new ArrayList<ServiceGroupItem>();
    }

    public String getService_group_title() {
        return service_group_title;
    }

    public void setService_group_title(String service_group_title) {
        this.service_group_title = service_group_title;
    }

    public int getTrend_id() {
        return trend_id;
    }

    public void setTrend_id(int trend_id) {
        this.trend_id = trend_id;
    }

    public void addItem(ServiceGroupItem item){
        items.add(item);
    }

    public List<ServiceGroupItem> getItems() {
        return items;
    }

    public void setItems(List<ServiceGroupItem> items) {
        this.items = items;
    }
}
