package com.dhakasetup.sakib.dhakasetupprototype.model;

/**
 * Created by Nazmus Sakib on 04,April,2018
 * sakib6900@gmail.com
 * Project Name: DhakaSetupPrototype
 */
public class ServiceGroupItem {
    private String imageLink;
    private String title;
    private String srv_sl;

    public ServiceGroupItem(String imageLink, String title, String srv_sl) {
        this.imageLink = imageLink;
        this.title = title;
        this.srv_sl = srv_sl;
    }

    public ServiceGroupItem(String imageLink, String title) {
        this.imageLink = imageLink;
        this.title = title;
    }

    public String getSrv_sl() {
        return srv_sl;
    }

    public void setSrv_sl(String srv_sl) {
        this.srv_sl = srv_sl;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
