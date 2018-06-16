package com.dhakasetup.sakib.dhakasetupprototype.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nazmus Sakib on 03,April,2018
 * sakib6900@gmail.com
 * Project Name: DhakaSetupPrototype
 */
public class CategoryGrid {
    public List<CategoryGridItem> items;

    public CategoryGrid() {
        items = new ArrayList<CategoryGridItem>();
    }

    public List<CategoryGridItem> getItems() {
        return items;
    }

    public void setItems(List<CategoryGridItem> items) {
        this.items = items;
    }

    public void addItem(CategoryGridItem item){
        items.add(item);
    }
}


