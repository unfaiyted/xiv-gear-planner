package com.xiv.gearplanner.models;


import javax.persistence.*;

@Entity
@Table
public class StaticMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column
    String nickname;

    @OneToOne
    Player player;

    @OneToOne
    Job assignedJob;

    public StaticMember() { }

    public StaticMember(Player player, Job assignedJob) {
        this.player = player;
        this.assignedJob = assignedJob;

    }

}
