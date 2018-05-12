package com.xiv.gearplanner.models;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
public class GearEquipCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String name;

    @Column
    private Integer originalId;

    @OneToMany
    private List<Job> equippableJobs = new ArrayList<>();

    public GearEquipCategory() {
    }

    public GearEquipCategory(String name) {
        this.name = name;
    }

    public GearEquipCategory(String name, Integer originalId) {
        this.name = name;
        this.originalId = originalId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getOriginalId() {
        return originalId;
    }

    public void setOriginalId(Integer originalId) {
        this.originalId = originalId;
    }

    public List<Job> getEquippableJobs() {
        return equippableJobs;
    }

    public void setEquippableJobs(List<Job> equippableJobs) {
        this.equippableJobs = equippableJobs;
    }

    public void addJob(Job job) {
        this.equippableJobs.add(job);
    }
}



