package com.xiv.gearplanner.models.inventory;

import com.xiv.gearplanner.models.inventory.Gear;

import javax.persistence.*;
import java.util.List;

@Entity
@Table
public class GearWithMelds {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Gear gear;

    @ManyToMany
    private List<Meld> melds;

    public GearWithMelds(Long id, Gear gear, List<Meld> melds) {
        this.id = id;
        this.gear = gear;
        this.melds = melds;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Gear getGear() {
        return gear;
    }

    public void setGear(Gear gear) {
        this.gear = gear;
    }

    public List<Meld> getMelds() {
        return melds;
    }

    public void setMelds(List<Meld> melds) {
        this.melds = melds;
    }
}