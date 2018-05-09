package com.xiv.gearplanner.models;


import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table
public class JobBIS {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Must contain a code")
    @Size(min = 5, message = "Code is too short to be valid")
    @Column(nullable = false)
    private String ariyalaCode;

    @NotBlank(message = "Must contain a name")
    @Size(min = 3, message = "Name is too short.")
    @Column(nullable = false)
    private String name;

    @NotBlank(message = "Must contain a patch")
    @OneToOne
    private Patch patch;

    @ManyToOne
    private Job job;

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

    public Patch getPatch() {
        return patch;
    }

    public void setPatch(Patch patch) {
        this.patch = patch;
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
                ", patch=" + patch +
                ", job=" + job +
                '}';
    }
}
