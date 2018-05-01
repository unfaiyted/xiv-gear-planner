package com.xiv.gearplanner.models;

import javax.persistence.*;

@Entity
@Table
public class GearStatType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;

    public GearStatType(String name) {
        this.name = name;
    }

}
