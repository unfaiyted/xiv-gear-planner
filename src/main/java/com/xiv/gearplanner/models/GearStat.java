package com.xiv.gearplanner.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table
public class GearStat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long value;

    @OneToOne
    private GearStatType statType;

    @ManyToOne
    @JoinColumn(name = "gear_id", nullable = false)
    @JsonIgnore
    private Gear gear;

    public GearStat() {
    }

    public GearStat(GearStatType statType, Long value) {
        this.statType = statType;
        this.value = value;
    }

    public GearStat(GearStat copy) {
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

    public Gear getGear() {
        return gear;
    }

    public void setGear(Gear gear) {
        this.gear = gear;
    }

}
