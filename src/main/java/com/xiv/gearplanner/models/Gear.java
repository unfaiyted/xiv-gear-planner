package com.xiv.gearplanner.models;

import javax.persistence.*;
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

    @OneToMany(targetEntity = GearStat.class, mappedBy = "gear")
    private List<GearStat> gearStats;

    public Gear(String name, Integer iLvl) {

    }
}
