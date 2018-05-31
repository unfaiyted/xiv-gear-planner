package com.xiv.gearplanner.models.shops;

import com.fasterxml.jackson.annotation.JsonView;
import com.xiv.gearplanner.models.View;
import com.xiv.gearplanner.models.inventory.Item;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.List;


@Entity
public class GilShop extends Shop {

    @ManyToMany(cascade = CascadeType.ALL)
  //  @JoinTable(name = "gil_shop_items")
    @JsonView(View.SummaryWithItems.class)
    private List<Item> items = new ArrayList<>();
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

    public void addItems(Item item) {
        this.items.add(item);
    }


    @JsonView(View.Summary.class)
    public String getShopType() {
        return "GilShop";
    }

    @Override
    public String toString() {
        return "GilShop{" +
                "items=" + items +
                "} " + super.toString();
    }
}
