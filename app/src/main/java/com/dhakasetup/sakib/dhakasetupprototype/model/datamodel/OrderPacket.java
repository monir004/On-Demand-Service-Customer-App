package com.dhakasetup.sakib.dhakasetupprototype.model.datamodel;

import java.util.ArrayList;
import java.util.List;

public class OrderPacket {
    List<Order> orders;
    List<Order> going,served;

    public OrderPacket(List<Order> orders) {
        this.orders = orders;
    }

    public OrderPacket() {
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
        filterOrders();
    }

    public List<Order> getGoing() {
        return going;
    }

    public List<Order> getServed() {
        return served;
    }

    public void filterOrders(){
        going = new ArrayList<>();
        served = new ArrayList<>();
        for (int i = 0; i<orders.size(); i++){
            Order o = orders.get(i);
            if (o.getStatus().equals("On Going")){
                going.add(o);
            }
            else {
                served.add(o);
            }
        }
    }
}
