package com.xiv.gearplanner.models;


import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
public class Static {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @OneToOne
    private Player leader;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @OneToMany
    private List<StaticMember> members = new ArrayList<>();

    public Static () {}

    public Static (String name, Player leader) {
        this.name = name;
        this.leader = leader;
    }



}
