package com.xiv.gearplanner.models;

import javax.persistence.*;
import java.util.List;

@Entity
@Table
public class GearSet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String name;

    @OneToOne
    private Job job;
    @ManyToOne
    private Player player;
    @OneToMany
    private List<Gear> gears;

    public GearSet() {

    }
}
