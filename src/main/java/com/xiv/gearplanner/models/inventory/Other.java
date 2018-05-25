package com.xiv.gearplanner.models.inventory;

import javax.persistence.Entity;
import javax.persistence.Table;

@Table
@Entity
public class Other extends Item {

    public Other () {}

    public Other(String name, String description, Integer iLvl, Integer icon, Integer originalId, ItemCategory category) {
        super(name, description, iLvl, icon, originalId, category);
    }

    public Other(String name, Integer iLvl) {
        super(name, iLvl);
    }
}
