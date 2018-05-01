package com.xiv.gearplanner.models;

import javax.persistence.Entity;

@Entity
public class Job {
    private Long id;
    private String name;
    private String abbr;

    public Job(String name, String abbr) {
        this.name = name;
        this.abbr = abbr;
    }
}
