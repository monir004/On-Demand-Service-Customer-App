package com.dhakasetup.sakib.dhakasetupprototype.model.datamodel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Order {
    int order_sl,total_am,disc_am,net_am,paid_am,due_am;
    String oauth_uid,order_id,status,d_address,d_date,d_timerange;
    Date open_time,close_time;
    List<OrderItem> orderItems = new ArrayList<>();

    public Order(int order_sl, int total_am, int disc_am, int net_am, int paid_am, int due_am, String oauth_uid, String order_id, String status, String d_address, String d_date, String d_timerange, Date open_time, Date close_time) {
        this.order_sl = order_sl;
        this.total_am = total_am;
        this.disc_am = disc_am;
        this.net_am = net_am;
        this.paid_am = paid_am;
        this.due_am = due_am;
        this.oauth_uid = oauth_uid;
        this.order_id = order_id;
        this.status = status;
        this.d_address = d_address;
        this.d_date = d_date;
        this.d_timerange = d_timerange;
        this.open_time = open_time;
        this.close_time = close_time;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public void addOrderItem(OrderItem orderItem) {
        this.orderItems.add(orderItem);
    }

    public int getOrder_sl() {
        return order_sl;
    }

    public void setOrder_sl(int order_sl) {
        this.order_sl = order_sl;
    }

    public int getTotal_am() {
        return total_am;
    }

    public void setTotal_am(int total_am) {
        this.total_am = total_am;
    }

    public int getDisc_am() {
        return disc_am;
    }

    public void setDisc_am(int disc_am) {
        this.disc_am = disc_am;
    }

    public int getNet_am() {
        return net_am;
    }

    public void setNet_am(int net_am) {
        this.net_am = net_am;
    }

    public int getPaid_am() {
        return paid_am;
    }

    public void setPaid_am(int paid_am) {
        this.paid_am = paid_am;
    }

    public int getDue_am() {
        return due_am;
    }

    public void setDue_am(int due_am) {
        this.due_am = due_am;
    }

    public String getOauth_uid() {
        return oauth_uid;
    }

    public void setOauth_uid(String oauth_uid) {
        this.oauth_uid = oauth_uid;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getD_address() {
        return d_address;
    }

    public void setD_address(String d_address) {
        this.d_address = d_address;
    }

    public String getD_date() {
        return d_date;
    }

    public void setD_date(String d_date) {
        this.d_date = d_date;
    }

    public String getD_timerange() {
        return d_timerange;
    }

    public void setD_timerange(String d_timerange) {
        this.d_timerange = d_timerange;
    }

    public Date getOpen_time() {
        return open_time;
    }

    public void setOpen_time(Date open_time) {
        this.open_time = open_time;
    }

    public Date getClose_time() {
        return close_time;
    }

    public void setClose_time(Date close_time) {
        this.close_time = close_time;
    }
}
