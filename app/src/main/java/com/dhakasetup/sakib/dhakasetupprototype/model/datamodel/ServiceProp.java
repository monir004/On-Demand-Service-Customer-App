package com.dhakasetup.sakib.dhakasetupprototype.model.datamodel;

public class ServiceProp {
    String PropName,Min,Max,Count,Price;

    public ServiceProp(String propName, String min, String max, String count, String price) {
        PropName = propName;
        Min = min;
        Max = max;
        Count = count;
        Price = price;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getPropName() {
        return PropName;
    }

    public void setPropName(String propName) {
        PropName = propName;
    }

    public String getMin() {
        return Min;
    }

    public void setMin(String min) {
        Min = min;
    }

    public String getMax() {
        return Max;
    }

    public void setMax(String max) {
        Max = max;
    }

    public String getCount() {
        return Count;
    }

    public void setCount(String count) {
        Count = count;
    }
}
