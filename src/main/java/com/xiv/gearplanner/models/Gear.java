package com.xiv.gearplanner.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
public class Gear {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Item item;

    @OneToOne
    private GearSlot slot;

    @OneToMany(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "gear_id")
    private List<GearStat> gearStats = new ArrayList<>();

    @OneToMany
    private List<Job> jobs = new ArrayList<>();

    public Gear() { }

    public Gear(Item item, GearSlot slot) {
        this.item = item;
        this.slot = slot;
    }

    public Gear(Gear copy) {
        this.item = copy.item;
        this.gearStats = copy.gearStats;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<GearStat> getGearStats() {
        return gearStats;
    }

    public void setGearStats(List<GearStat> gearStats) {
        this.gearStats = gearStats;
    }


    // TODO: Does a stat already exist, replace stat of the same type.
    public void addGearStat(GearStat stat) {
        gearStats.add(stat);
    }

    public void addGearStat(List<GearStat> stats) {

        for (GearStat stat : stats) {
            stat.setGear(this);
            addGearStat(stat);
         }

    }

    @Override
    public String toString() {
        return "Gear{" +
                "id=" + id +
                ", gearStats=" + gearStats +
                '}';
    }
}
