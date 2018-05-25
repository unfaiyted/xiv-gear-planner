package com.xiv.gearplanner.models.shops;

import com.xiv.gearplanner.models.inventory.Item;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table
public class Purchasable {

    @ManyToOne
    private Item item;
    @Column
    private Integer count;
    @Column
    private Integer HQ;
    @Column
    private Integer collectabilityRating = 0;

    public Purchasable() {}

    public Purchasable(Item item, Integer count, Integer HQ, Integer collectabilityRating) {
        this.item = item;
        this.count = count;
        this.HQ = HQ;
        this.collectabilityRating = collectabilityRating;
    }


    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getHQ() {
        return HQ;
    }

    public void setHQ(Integer HQ) {
        this.HQ = HQ;
    }

    public Integer getCollectabilityRating() {
        return collectabilityRating;
    }

    public void setCollectabilityRating(Integer collectabilityRating) {
        this.collectabilityRating = collectabilityRating;
    }
}
