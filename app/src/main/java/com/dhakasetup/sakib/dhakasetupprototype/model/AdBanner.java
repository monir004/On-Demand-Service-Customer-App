package com.dhakasetup.sakib.dhakasetupprototype.model;

import java.util.List;

/**
 * Created by Nazmus Sakib on 03,April,2018
 * sakib6900@gmail.com
 * Project Name: DhakaSetupPrototype
 */
public class AdBanner {
    private List<String> imageLinks;
    private List<String> titles;

    public AdBanner(List<String> imageLinks, List<String> titles) {
        this.imageLinks = imageLinks;
        this.titles = titles;
    }

    public List<String> getImageLinks() {
        return imageLinks;
    }

    public void setImageLinks(List<String> imageLinks) {
        this.imageLinks = imageLinks;
    }

    public List<String> getTitles() {
        return titles;
    }

    public void setTitles(List<String> titles) {
        this.titles = titles;
    }
}
