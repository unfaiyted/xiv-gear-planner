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

    public GearStatType() {
    }

    public GearStatType(String name) {
        this.name = name;
    }

    public GearStatType(GearStatType copy) {
        this.id = copy.id;
        this.name = copy.name;
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

    @Override
    public String toString() {
        return "GearStatType{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
