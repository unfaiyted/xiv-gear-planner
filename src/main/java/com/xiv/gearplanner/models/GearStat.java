package com.xiv.gearplanner.models;

import javax.persistence.*;

@Entity
@Table
public class GearStat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long value;

    @OneToOne(cascade = CascadeType.ALL)
    private GearStatType statType;

    @OneToOne(cascade = CascadeType.ALL)
    private Gear gear;

    public GearStat() {
    }

    public GearStat(GearStatType statType, Long value) {
        this.statType = statType;
        this.value = value;
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
