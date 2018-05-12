package com.xiv.gearplanner.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name="gear")
public class Gear extends Item {

    @OneToOne
    private GearSlot slot;

    @OneToMany(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "gear_id")
    private List<GearStat> gearStats = new ArrayList<>();

    @OneToMany
    private List<Job> jobs = new ArrayList<>();

    public Gear() { }

    public Gear(GearSlot slot) {
        this.slot = slot;
    }

    public Gear(Gear copy) {
        this.gearStats = copy.gearStats;
    }

    public List<GearStat> getGearStats() {
        return gearStats;
    }

    public void setGearStats(List<GearStat> gearStats) {
        this.gearStats = gearStats;
    }

    public GearSlot getSlot() {
        return slot;
    }

    public void setSlot(GearSlot slot) {
        this.slot = slot;
    }

    public List<Job> getJobs() {
        return jobs;
    }

    public void setJobs(List<Job> jobs) {
        this.jobs = jobs;
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
                "slot=" + slot +
                ", gearStats=" + gearStats +
                ", jobs=" + jobs +
                '}';
    }
}
