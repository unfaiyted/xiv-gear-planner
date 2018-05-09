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

    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private Integer iLvl;
    @Column
    private String description;

    @Column
    private Long xivdbId;
    @Column
    private String lodestoneId;

    @OneToMany(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "gear_id")
    private List<GearStat> gearStats = new ArrayList<>();

    @OneToMany
    private List<Job> jobs = new ArrayList<>();

    public Gear() {
    }

    public Gear(String name, Integer iLvl) {
        this.name = name;
        this.iLvl = iLvl;
    }

    public Gear(Gear copy) {
        this.name = copy.name;
        this.iLvl = copy.iLvl;
        this.description = copy.description;
        this.gearStats = copy.gearStats;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getiLvl() {
        return iLvl;
    }

    public void setiLvl(Integer iLvl) {
        this.iLvl = iLvl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
                ", name='" + name + '\'' +
                ", iLvl=" + iLvl +
                ", description='" + description + '\'' +
                ", gearStats=" + gearStats +
                '}';
    }
}
