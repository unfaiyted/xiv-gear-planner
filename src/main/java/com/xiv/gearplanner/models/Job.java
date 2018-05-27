package com.xiv.gearplanner.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table
public class Job {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @JsonIgnore
    private Integer originalId;

    @NotBlank(message = "Must contain a name")
    @Size(min = 3, message = "Name is too short.")
    @Column(nullable = false)
    private String name;

    @NotBlank(message = "Must contain a abbreviation.")
    @Size(min = 3, max= 3, message = "Abbreviations must be 3 characters.")
    @Column(nullable = false)
    private String abbr;

    @Column
    @JsonIgnore
    private String icon;

    @OneToOne
    @JsonIgnore
    private Job parentJob;

    @OneToOne(cascade = {CascadeType.ALL})
    @JsonIgnore
    private JobType type;

    public Job() {
    }

    public Job(String name, String abbr) {
        this.name = name;
        this.abbr = abbr;
    }


    public Job(String name, String abbr, Integer importId) {
        this.name = name;
        this.abbr = abbr;
        this.originalId = importId;
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

    public String getIcon() {
        return icon;
    }

    public String getImg() {
        return  "/img/jobs/" + abbr + ".png";
    }

    public Long getParentJobId() {
        return parentJob.getId();
    }

    // ids great than 30 are always post expansion
    // not really the best way to do this probably...
    public boolean isJob() {
        return (this.id != parentJob.getId() || this.id > 30)? true : false;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Integer getOriginalId() {
        return originalId;
    }

    public void setOriginalId(Integer originalId) {
        this.originalId = originalId;
    }

    public Job getParentJob() {
        return parentJob;
    }

    public void setParentJob(Job parentJob) {
        this.parentJob = parentJob;
    }

    @Override
    public String toString() {
        return "Job{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", abbr='" + abbr + '\'' +
                ", icon='" + icon + '\'' +
                ", type=" + type +
                '}';
    }
}
