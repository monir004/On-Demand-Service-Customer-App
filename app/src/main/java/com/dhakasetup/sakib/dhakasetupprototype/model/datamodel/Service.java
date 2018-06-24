package com.dhakasetup.sakib.dhakasetupprototype.model.datamodel;

import java.util.List;

/**
 * Created by Nazmus Sakib on 05,April,2018
 * sakib6900@gmail.com
 * Project Name: DhakaSetupPrototype
 */
public class Service {
    private String imgLink,title,price;
    String srv_sl,srvCategory,srvSubCategory,
            srvice,srvDetails,srvQty,srvPrice,srvStatus,srvImage,
            created,modified,vendor,vendor_mobile;
    int qty,count;
    List<ServiceProp> serviceProps;

    public Service() {
    }

    public Service(String imgLink, String title, String price) {
        this.imgLink = imgLink;
        this.title = title;
        this.price = price;
    }

    public Service(String srv_sl, String srvice, String srvDetails, String srvPrice, String srvImage) {
        this.srv_sl = srv_sl;
        this.srvice = srvice;
        this.srvDetails = srvDetails;
        this.srvPrice = srvPrice;
        this.srvImage = srvImage;
    }

    public void incCount(){
        count++;
    }
    public void decCount(){
        count--;
    }
    public int getCount() {
        return count;
    }
    public void setCount(int count) {
        this.count = count;
    }

    public List<ServiceProp> getServiceProps() {
        return serviceProps;
    }

    public void setServiceProps(List<ServiceProp> serviceProps) {
        this.serviceProps = serviceProps;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public String getSrv_sl() {
        return srv_sl;
    }

    public void setSrv_sl(String srv_sl) {
        this.srv_sl = srv_sl;
    }

    public String getSrvCategory() {
        return srvCategory;
    }

    public void setSrvCategory(String srvCategory) {
        this.srvCategory = srvCategory;
    }

    public String getSrvSubCategory() {
        return srvSubCategory;
    }

    public void setSrvSubCategory(String srvSubCategory) {
        this.srvSubCategory = srvSubCategory;
    }

    public String getSrvice() {
        return srvice;
    }

    public void setSrvice(String srvice) {
        this.srvice = srvice;
    }

    public String getSrvDetails() {
        return srvDetails;
    }

    public void setSrvDetails(String srvDetails) {
        this.srvDetails = srvDetails;
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

    public String getSrvStatus() {
        return srvStatus;
    }

    public void setSrvStatus(String srvStatus) {
        this.srvStatus = srvStatus;
    }

    public String getSrvImage() {
        return srvImage;
    }

    public void setSrvImage(String srvImage) {
        this.srvImage = srvImage;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public String getVendor_mobile() {
        return vendor_mobile;
    }

    public void setVendor_mobile(String vendor_mobile) {
        this.vendor_mobile = vendor_mobile;
    }



    public String getImgLink() {
        return imgLink;
    }

    public void setImgLink(String imgLink) {
        this.imgLink = imgLink;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
