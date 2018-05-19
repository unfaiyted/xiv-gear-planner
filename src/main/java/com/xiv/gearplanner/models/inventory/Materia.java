package com.xiv.gearplanner.models.inventory;

import javax.persistence.*;


//    "slot_equip": 16,
//    "slot_name": "Materia",

@Entity
@Table
public class Materia extends Item {

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "stat_id")
    private ItemStat itemStat;

    public Materia() { }

    public Materia(String name, String description, Integer iLvl, Integer icon, Integer originalId, ItemCategory category) {
        super(name, description, iLvl, icon, originalId, category);

    }

    public ItemStat getItemStat() {
        return itemStat;
    }

    public void setItemStat(ItemStat stat) {
        stat.setItem(this);
        this.itemStat = stat;
    }

    @Override
    public String toString() {
        return "Materia{" +
                "itemStat=" + itemStat +
                ", category=" + category +
                '}';
    }
}
