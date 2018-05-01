package com.xiv.gearplanner.models;


import javax.persistence.Entity;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Player {

    private Long id;
    private String name;
    private LocalDateTime dateAdded;
    private String gender;

    private List<Job> jobs;

    public Player() {

    }
}
