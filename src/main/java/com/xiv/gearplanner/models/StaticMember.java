package com.xiv.gearplanner.models;


import javax.persistence.*;

@Entity
@Table
public class StaticMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String nickname;

    @OneToOne
    private Player player;

    @OneToOne
    private Job assignedJob;

    @ManyToOne
    private Static oneStatic;

    public StaticMember() { }

    public StaticMember(Player player, Job assignedJob, Static oneStatic) {
        this.player = player;
        this.assignedJob = assignedJob;
        this.oneStatic = oneStatic;

    }

    public StaticMember(StaticMember copy) {
        this.id = copy.id;
        this.player = copy.player;
        this.nickname = copy.nickname;
        this.player = copy.player;
        this.assignedJob = copy.assignedJob;
        this.oneStatic = copy.oneStatic;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Job getAssignedJob() {
        return assignedJob;
    }

    public void setAssignedJob(Job assignedJob) {
        this.assignedJob = assignedJob;
    }

    public Static getOneStatic() {
        return oneStatic;
    }

    public void setOneStatic(Static oneStatic) {
        this.oneStatic = oneStatic;
    }
}
