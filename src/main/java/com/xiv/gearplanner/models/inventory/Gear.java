package com.xiv.gearplanner.models.inventory;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.xiv.gearplanner.models.Job;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name="gear")
public class Gear extends Item {

    @Column
    @JsonIgnore
    private boolean advancedMelding;

    @Column
    @JsonIgnore
    private Integer materiaSlotCount;

    @Column
    private Integer equipLevel;

    @ManyToOne
    private Job useJob;

    @OneToOne
    private GearSlotCategory slot;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "item_id")
    @JsonBackReference
    private List<ItemStat> gearStats = new ArrayList<>();

    @ManyToOne
    private GearEquipCategory equipCategory;

    public Gear() { }

    public Gear(GearSlotCategory slot) {
        this.slot = slot;
    }

    public Gear(String name, Integer originalId, String description, Integer iLvl,
                Integer equipLevel, Integer icon, Boolean advancedMelding, Integer materiaSlotCount,
                GearSlotCategory slot, GearEquipCategory equipCategory, Job useJob, ItemCategory category,
                List<ItemStat> gearStats) {
        super(name, description, iLvl, icon, originalId, category);
        this.slot = slot;
        this.advancedMelding = advancedMelding;
        this.materiaSlotCount = materiaSlotCount;
        this.equipCategory = equipCategory;
        this.equipLevel = equipLevel;
        this.useJob = useJob;


        gearStats.forEach(gearStat -> {
            gearStat.setGear(this);
        });

        this.gearStats = gearStats;
    }


    public Gear(Gear copy) {
        this.gearStats = copy.gearStats;
    }

    public List<ItemStat> getGearStats() {
        return gearStats;
    }

    public void setGearStats(List<ItemStat> gearStats) {
        this.gearStats = gearStats;
    }

    public GearSlotCategory getSlot() {
        return slot;
    }

    public void setSlot(GearSlotCategory slot) {
        this.slot = slot;
    }


    public boolean isAdvancedMelding() {
        return advancedMelding;
    }

    public void setAdvancedMelding(boolean advancedMelding) {
        this.advancedMelding = advancedMelding;
    }

    public Integer getMateriaSlotCount() {
        return materiaSlotCount;
    }

    public void setMateriaSlotCount(Integer materiaSlotCount) {
        this.materiaSlotCount = materiaSlotCount;
    }

    public Integer getEquipLevel() {
        return equipLevel;
    }

    public void setEquipLevel(Integer equipLevel) {
        this.equipLevel = equipLevel;
    }

    public Job getUseJob() {
        return useJob;
    }

    public void setUseJob(Job useJob) {
        this.useJob = useJob;
    }

    public GearEquipCategory getEquipCategory() {
        return equipCategory;
    }

    public void setEquipCategory(GearEquipCategory equipCategory) {
        this.equipCategory = equipCategory;
    }

    // TODO: Does a stat already exist, replace stat of the same type.
    public void addGearStat(ItemStat stat) {
        gearStats.add(stat);
    }

    public void addGearStat(List<ItemStat> stats) {

        for (ItemStat stat : stats) {
            stat.setGear(this);
            addGearStat(stat);
         }

    }

    @Override
    public String toString() {
        return "Gear{" +
                "slot=" + slot +
                ", gearStats=" + gearStats +
                ", equipCategory=" + equipCategory +
                ", category=" + category +
                '}';
    }
}
