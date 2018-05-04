package com.xiv.gearplanner.models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table
public class Job {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Must contain a name")
    @Size(min = 3, message = "Name is too short.")
    @Column(nullable = false)
    private String name;

    @NotBlank(message = "Must contain a abbreviation.")
    @Size(min = 3, max= 3, message = "Abbreviations must be 3 characters.")
    @Column(nullable = false)
    private String abbr;

    @OneToOne(cascade = {CascadeType.ALL})
    private JobType type;

    public Job() {
    }

    public Job(String name, String abbr, JobType type) {
        this.name = name;
        this.abbr = abbr;
        this.type = type;
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

    public String getAbbr() {
        return abbr;
    }

    public void setAbbr(String abbr) {
        this.abbr = abbr;
    }

    public JobType getType() {
        return type;
    }

    public void setType(JobType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Job{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", abbr='" + abbr + '\'' +
                ", type=" + type +
                '}';
    }
}
