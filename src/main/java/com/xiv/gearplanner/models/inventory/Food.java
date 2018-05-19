package com.xiv.gearplanner.models.inventory;

import com.xiv.gearplanner.models.inventory.Item;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.List;


@Table
@Entity
public class Food extends Item {

    // Foods all have 3 "Item Stats"
    // foods have an HQ and NQ version of the max values
    // foods value may also be relative
    @ManyToMany
    private List<FoodStat> stats;


    public Food() {}

    public Food(String name, String description, Integer iLvl, Integer icon, Integer originalId, ItemCategory category) {
        super(name, description, iLvl, icon, originalId, category);
    }

    public Food(String name, String description, Integer iLvl, Integer icon, Integer originalId, ItemCategory category, List<FoodStat> stats) {
        super(name, description, iLvl, icon, originalId, category);
        this.stats = stats;
    }

    public List<FoodStat> getStats() {
        return stats;
    }

    public void setStats(List<FoodStat> stats) {

        this.stats = stats;
    }
}
