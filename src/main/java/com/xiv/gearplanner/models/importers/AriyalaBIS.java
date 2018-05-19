package com.xiv.gearplanner.models.importers;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;

public class AriyalaBIS {

    String id;
    String classAbbr;
    List<AriyalaItem> items = new ArrayList<>();

    public AriyalaBIS() {
    }

    public AriyalaBIS(String id, String classAbbr, List<AriyalaItem> items) {
        this.id = id;
        this.classAbbr = classAbbr;
        this.items = items;
    }

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

    @JsonIgnore
    public Integer getTotalItems() {

        Integer total = items.size();

        return total;
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
