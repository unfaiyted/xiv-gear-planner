package com.xiv.gearplanner.models.importers;

import java.util.List;

public class AriyalaBIS {

    String id;
    String classAbbr;
    List<AriyalaItem> items;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClassAbbr() {
        return classAbbr;
    }

    public void setClassAbbr(String classAbbr) {
        this.classAbbr = classAbbr;
    }

    public List<AriyalaItem> getItems() {
        return items;
    }

    public void setItems(List<AriyalaItem> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "AriyalaBIS{" +
                "id='" + id + '\'' +
                ", classAbbr='" + classAbbr + '\'' +
                ", items=" + items +
                '}';
    }
}
