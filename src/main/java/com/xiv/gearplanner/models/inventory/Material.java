package com.xiv.gearplanner.models.inventory;


import javax.persistence.Entity;
import javax.persistence.Table;

@Table
@Entity
public class Material extends Item {


    public Material () {}

    public Material(String name, String description, Integer iLvl, Integer icon, Integer originalId, ItemCategory category) {
        super(name, description, iLvl, icon, originalId, category);
    }

    public Material(String name, Integer iLvl) {
        super(name, iLvl);
    }
}
