package com.xiv.gearplanner.models.inventory;

import javax.persistence.*;

@Table
@Entity
public class FoodStat {
    @Id  @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private boolean isRelative;

    // Base Param & max
    @ManyToOne
    private ItemStat stat;
    // value is %% based
    @Column
    private Integer percent;

    @ManyToOne
    private ItemStat hqStat;
    @Column
    private Integer percentHq;

    public FoodStat() {}

    public FoodStat(boolean isRelative, ItemStat stat, Integer percent, ItemStat hqStat, Integer percentHq) {
        this.isRelative = isRelative;
        this.stat = stat;
        this.percent = percent;
        this.hqStat = hqStat;
        this.percentHq = percentHq;
    }

    public boolean isRelative() {
        return isRelative;
    }

    public void setRelative(boolean relative) {
        isRelative = relative;
    }

    public ItemStat getStat() {
        return stat;
    }

    public void setStat(ItemStat stat) {
        this.stat = stat;
    }

    public Integer getPercent() {
        return percent;
    }

    public void setPercent(Integer percent) {
        this.percent = percent;
    }

    public ItemStat getHqStat() {
        return hqStat;
    }

    public void setHqStat(ItemStat hqStat) {
        this.hqStat = hqStat;
    }

    public Integer getPercentHq() {
        return percentHq;
    }

    public void setPercentHq(Integer percentHq) {
        this.percentHq = percentHq;
    }

    @Override
    public String toString() {
        return "FoodStat{" +
                "isRelative=" + isRelative +
                ", stat=" + stat +
                ", percent=" + percent +
                ", hqStat=" + hqStat +
                ", percentHq=" + percentHq +
                '}';
    }
}
