package com.xiv.gearplanner.models.shops;

import com.xiv.gearplanner.models.inventory.Item;

import javax.persistence.*;

@Entity
@Table
public class Purchasable {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(cascade = CascadeType.ALL)
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

    public Purchasable(Item item, Integer count, Integer HQ) {
        this.item = item;
        this.count = count;
        this.HQ = HQ;
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


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
