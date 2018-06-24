package com.dhakasetup.sakib.dhakasetupprototype.model.datamodel;

public class ServiceProp {
    String PropName;
    int Min,Max,Count;
    Double Price;
    int id;
    Service Service;

    public ServiceProp(int id,String propName, int min, int max, int count, Double price,Service service) {
        this.id = id;
        PropName = propName;
        Min = min;
        Max = max;
        Count = count;
        Price = price;
        Service = service;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Service getService() {
        return Service;
    }

    public void setService(Service service) {
        this.Service = service;
    }

    public Double getPrice() {
        return Price;
    }

    public void setPrice(Double price) {
        Price = price;
    }

    public String getPropName() {
        return PropName;
    }

    public void setPropName(String propName) {
        PropName = propName;
    }

    public int getMin() {
        return Min;
    }

    public void setMin(int min) {
        Min = min;
    }

    public int getMax() {
        return Max;
    }

    public void setMax(int max) {
        Max = max;
    }

    public int getCount() {
        return Count;
    }

    public void setCount(int count) {
        Count = count;
    }
    public void incCount(){
        Count++;
    }
    public void decCount(){
        Count--;
    }
}
