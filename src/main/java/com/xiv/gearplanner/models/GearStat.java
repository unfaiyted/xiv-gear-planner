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

    @OneToOne
    private GearStatType statType;

    @OneToOne
    private Gear gear;

    public GearStat(GearStatType statType, Long value) {

    }

}
