package com.xiv.gearplanner.models.shops;

import com.xiv.gearplanner.models.inventory.Item;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.List;


@Entity
public class GilShop extends Shop {

    @ManyToMany(cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
  //  @JoinTable(name = "gil_shop_items")
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


    @Override
    public String toString() {
        return "GilShop{" +
                "items=" + items +
                "} " + super.toString();
    }
}
