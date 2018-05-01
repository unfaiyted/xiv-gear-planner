package com.xiv.gearplanner.models;

import javax.persistence.Entity;
import java.util.List;

@Entity
public class Gear {
    private Long id;
    private String name;
    private Integer iLvl;
    private String description;

    private List<GearStat> gearStats;

    public Gear(String name, Integer iLvl) {

    }
}
