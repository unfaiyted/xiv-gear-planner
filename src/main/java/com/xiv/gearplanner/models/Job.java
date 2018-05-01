package com.xiv.gearplanner.models;

import javax.persistence.*;

@Entity
@Table
public class Job {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String abbr;

    @OneToOne
    private JobType type;

    public Job(String name, String abbr, JobType type) {
        this.name = name;
        this.abbr = abbr;
        this.type = type;
    }
}
