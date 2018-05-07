package com.xiv.gearplanner.models;

import org.hibernate.annotations.Cascade;

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
    @Column
    private String avatar;
    @Column
    private String portrait;
    @Column
    private String server;
    @Column
    private Long loadstone_id;

    @ManyToMany(cascade = CascadeType.ALL)
    private List<Job> jobs = new ArrayList<>();
    @OneToMany(cascade = CascadeType.ALL)
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


    public Player(Player copy) {
        this.id = copy.id;
        this.name = copy.name;
        this.dateAdded = copy.dateAdded;
        this.gender = copy.gender;
        this.avatar = copy.avatar;
        this.server = copy.server;
        this.portrait = copy.portrait;
        this.loadstone_id = copy.loadstone_id;
        this.jobs = copy.jobs;
        this.gearSets = copy.gearSets;
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

    public String getGender() {  return (gender) ? "Female" : "Male";  }

    // I should probably condense
    public String displayGender() {
        return getGender();
    }

    public boolean getGenderAsBoolean() {
        return gender;
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

    public boolean isGender() {
        return gender;
    }

    public String getAvatar() {
        return avatar;
    }


    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public Long getLoadstone_id() {
        return loadstone_id;
    }

    public void setLoadstone_id(Long loadstone_id) {
        this.loadstone_id = loadstone_id;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    @Override
    public String toString() {
        return "Player{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", dateAdded=" + dateAdded +
                ", gender=" + gender +
                ", avatar='" + avatar + '\'' +
                ", portrait='" + portrait + '\'' +
                ", server='" + server + '\'' +
                ", loadstone_id=" + loadstone_id +
                ", jobs=" + jobs +
                ", gearSets=" + gearSets +
                '}';
    }
}
