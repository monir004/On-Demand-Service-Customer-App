package com.dhakasetup.sakib.dhakasetupprototype.model.datamodel;

public class OrderItem {
    String id,order_id,srv_sl,srvQty,srvPrice,srvTotal,prop_id,prop_qty,prop_price,prop_total;
    String srvice,srvImage,prop_name;

    public OrderItem(String id, String order_id, String srv_sl, String srvQty, String srvPrice, String srvTotal, String prop_id, String prop_qty, String prop_price, String prop_total, String srvice, String srvImage, String prop_name) {
        this.id = id;
        this.order_id = order_id;
        this.srv_sl = srv_sl;
        this.srvQty = srvQty;
        this.srvPrice = srvPrice;
        this.srvTotal = srvTotal;
        this.prop_id = prop_id;
        this.prop_qty = prop_qty;
        this.prop_price = prop_price;
        this.prop_total = prop_total;
        this.srvice = srvice;
        this.srvImage = srvImage;
        this.prop_name = prop_name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getSrv_sl() {
        return srv_sl;
    }

    public void setSrv_sl(String srv_sl) {
        this.srv_sl = srv_sl;
    }

    public String getSrvQty() {
        return srvQty;
    }

    public void setSrvQty(String srvQty) {
        this.srvQty = srvQty;
    }

    public String getSrvPrice() {
        return srvPrice;
    }

    public void setSrvPrice(String srvPrice) {
        this.srvPrice = srvPrice;
    }

    public String getSrvTotal() {
        return srvTotal;
    }

    public void setSrvTotal(String srvTotal) {
        this.srvTotal = srvTotal;
    }

    public String getProp_id() {
        return prop_id;
    }

    public void setProp_id(String prop_id) {
        this.prop_id = prop_id;
    }

    public String getProp_qty() {
        return prop_qty;
    }

    public void setProp_qty(String prop_qty) {
        this.prop_qty = prop_qty;
    }

    public String getProp_price() {
        return prop_price;
    }

    public void setProp_price(String prop_price) {
        this.prop_price = prop_price;
    }

    public String getProp_total() {
        return prop_total;
    }

    public void setProp_total(String prop_total) {
        this.prop_total = prop_total;
    }

    public String getSrvice() {
        return srvice;
    }

    public void setSrvice(String srvice) {
        this.srvice = srvice;
    }

    public String getSrvImage() {
        return srvImage;
    }

    public void setSrvImage(String srvImage) {
        this.srvImage = srvImage;
    }

    public String getProp_name() {
        return prop_name;
    }

    public void setProp_name(String prop_name) {
        this.prop_name = prop_name;
    }
}
