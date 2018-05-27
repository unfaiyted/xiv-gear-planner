package com.xiv.gearplanner.models.inventory;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;

@Entity
@Table
public class ItemStat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long value;

    @OneToOne
    private GearStatType statType;

    @ManyToOne
    @JsonManagedReference
    private Item item;


    public ItemStat() {
    }

    public ItemStat(GearStatType statType, Long value) {
        this.statType = statType;
        this.value = value;
    }

    public ItemStat(Item item, GearStatType statType, Long value) {
        this.item = item;
        this.statType = statType;
        this.value = value;
    }

    public ItemStat(ItemStat copy) {
        this.id = copy.id;
        this.statType = copy.statType;
        this.value = copy.value;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }

    public GearStatType getStatType() {
        return statType;
    }

    public void setStatType(GearStatType statType) {
        this.statType = statType;
    }

    public Item getGear() {
        return item;
    }

    public void setGear(Item gear) {
        this.item = gear;
    }

    @JsonIgnore
    public Item getItem() {
        return item;
    }

    @JsonIgnore
    public void setItem(Item item) {
        this.item = item;
    }

}
