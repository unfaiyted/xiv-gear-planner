package com.xiv.gearplanner.models.shops;

import com.xiv.gearplanner.models.inventory.Item;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.List;


@Entity
public class GilShop extends Shop {

    @ManyToMany
    private List<Item> items;

    //default
    public GilShop() { }

    public GilShop(Integer originalId, String name) {
        super(originalId, name);
    }

    public GilShop(Integer originalId, String name, List<Item> items) {
        super(originalId, name);
        this.items = items;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}
