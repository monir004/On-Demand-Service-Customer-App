package com.dhakasetup.sakib.dhakasetupprototype.model;

/**
 * Created by Nazmus Sakib on 03,April,2018
 * sakib6900@gmail.com
 * Project Name: DhakaSetupPrototype
 */
public class CategoryGridItem {
    public String title;
    public String imageLink;
    public int cat_id;

    public CategoryGridItem(String title, String imageLink, int id) {
        this.title = title;
        this.imageLink = imageLink;
        this.cat_id  = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }
}
