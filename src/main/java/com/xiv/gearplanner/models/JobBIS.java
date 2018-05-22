package com.xiv.gearplanner.models;


import com.xiv.gearplanner.models.inventory.Food;
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
    @Size(min=5, max=5, message = "Ariyala code is supposed to be 5 digits.")
    private String ariyalaCode;

    @NotBlank(message = "Must contain a name")
    @Size(min = 3, message = "Name is too short.")
    @Column(nullable = false)
    private String name;

    @ManyToOne
    private Job job;

    @ManyToMany(cascade =  CascadeType.ALL)
    private List<GearWithMelds> melded = new ArrayList<>();

    @ManyToOne(cascade = CascadeType.ALL)
    private Food food;

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

    public List<GearWithMelds> getMelded() {

        return melded;
    }

    public void setMelded(List<GearWithMelds> melded) {
        for(GearWithMelds melds : melded) {
            melds.setBis(this);
        }
        this.melded = melded;
    }

    public void addMelded(GearWithMelds melded) {
        melded.setBis(this);
        this.melded.add(melded);
    }

    public Food getFood() {
        return food;
    }

    public void setFood(Food food) {
        this.food = food;
    }

    @Override
    public String toString() {
        return "JobBIS{" +
                "id=" + id +
                ", ariyalaCode='" + ariyalaCode + '\'' +
                ", name='" + name + '\'' +
                ", job=" + job +
                ", melded=" + melded +
                ", food=" + food +
                '}';
    }
}
