package com.xiv.gearplanner.models;


import javax.persistence.*;

@Entity
@Table
public class GearSlot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    public GearSlot() {
    }

    public GearSlot(GearSlot copy) {
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

    public void setName(String input) {
        name = input;
    }

}

