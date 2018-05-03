package com.xiv.gearplanner.models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
    private LocalDateTime dateAdded = LocalDateTime.now();
    @Column
    private boolean gender;

    @ManyToMany
    private List<Job> jobs = new ArrayList<>();
    @OneToMany
    private List<GearSet> gearSets = new ArrayList<>();

    public Player(){}
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

    public String displayGender() {
        return (gender) ? "Female" : "Male";
    }

    public List<Job> getJobs() {
        return jobs;
    }

    public String firstJob() {

        for(Job job : jobs) {
            return job.getName();
        }
        return "NoJob";
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

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDateAdded(LocalDateTime dateAdded) {
        this.dateAdded = dateAdded;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public void setJobs(List<Job> jobs) {
        this.jobs = jobs;
    }

    public void setGearSets(List<GearSet> gearSets) {
        this.gearSets = gearSets;
    }
}
