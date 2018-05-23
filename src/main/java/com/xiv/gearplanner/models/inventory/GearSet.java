package com.xiv.gearplanner.models.inventory;

import com.xiv.gearplanner.models.Job;
import com.xiv.gearplanner.models.Player;

import javax.persistence.*;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Entity
@Table
public class GearSet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String name;

    @ManyToOne
    private Job job;

    @ManyToOne
    private Player player;
    @ManyToMany
    private List<Gear> gears;

    public GearSet() {

    }

    public GearSet(Job job, Player player, List<Gear> gears) {
        this.job = job;
        this.player = player;
        this.gears = gears;
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

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public List<Gear> getGears() {

        if (gears.size() > 0) {
            Collections.sort(gears, new Comparator<Gear>() {
                @Override
                public int compare(final Gear object1, final Gear object2) {
                    return object1.getSlot().getSlot().getId().compareTo(object2.getSlot().getSlot().getId());
                }
            });
        }

        return gears;
    }

    public void setGears(List<Gear> gears) {
        this.gears = gears;
    }
}
