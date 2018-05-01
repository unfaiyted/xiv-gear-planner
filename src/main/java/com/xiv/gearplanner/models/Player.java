package com.xiv.gearplanner.models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table
public class Player {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name must not be blank")
    @Column(nullable = false, length = 255)
    private String name;
    @Column
    private LocalDateTime dateAdded;
    @Column
    private boolean gender;

    @OneToMany
    private List<Job> jobs;
    @OneToMany
    private List<GearSet> gearSets;

    public Player(String name) {
        this.name = name;
        this.dateAdded = LocalDateTime.now();
    }

    public Player(String name, boolean gender) {
        this.name = name;
        this.gender = gender;
        this.dateAdded = LocalDateTime.now();
    }

    public Player(String name, Job job) {
        this.name = name;
        jobs.add(job);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getDateAdded() {
        return dateAdded;
    }

    public boolean getGender() {
        return gender;
    }

    public List<Job> getJobs() {
        return jobs;
    }

    public List<GearSet> getGearSets() {
        return gearSets;
    }

    public void addJob(Job job) {
        jobs.add(job);
    }

    public void addGearSet(GearSet set) {
        gearSets.add(set);
    }



}
