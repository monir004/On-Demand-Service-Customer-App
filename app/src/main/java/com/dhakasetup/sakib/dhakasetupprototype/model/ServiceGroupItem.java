package com.dhakasetup.sakib.dhakasetupprototype.model;

/**
 * Created by Nazmus Sakib on 04,April,2018
 * sakib6900@gmail.com
 * Project Name: DhakaSetupPrototype
 */
public class ServiceGroupItem {
    private String imageLink;
    private String title;

    public ServiceGroupItem(String imageLink, String title) {
        this.imageLink = imageLink;
        this.title = title;
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
