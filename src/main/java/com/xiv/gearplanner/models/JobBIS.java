package com.xiv.gearplanner.models;


import com.xiv.gearplanner.models.inventory.GearWithMelds;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
public class JobBIS {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    // If imported from ariyala
    @Column
    private String ariyalaCode;

    @NotBlank(message = "Must contain a name")
    @Size(min = 3, message = "Name is too short.")
    @Column(nullable = false)
    private String name;

    @ManyToOne
    private Job job;

    @ManyToMany
    private List<GearWithMelds> gearWithMelds = new ArrayList<>();

    public JobBIS() {
    }

    public JobBIS(String ariyalaCode, String name, Job job) {
        this.ariyalaCode = ariyalaCode;
        this.name = name;
        this.job = job;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAriyalaCode() {
        return ariyalaCode;
    }

    public void setAriyalaCode(String ariyalaCode) {
        this.ariyalaCode = ariyalaCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    @Override
    public String toString() {
        return "JobBIS{" +
                "id=" + id +
                ", ariyalaCode='" + ariyalaCode + '\'' +
                ", name='" + name + '\'' +
                ", job=" + job +
                '}';
    }
}
