package com.xiv.gearplanner.models;

import javax.persistence.*;

@Entity
@Table
public class GearType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String Name;

    public GearType() {

    }
}
