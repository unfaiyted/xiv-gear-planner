package com.xiv.gearplanner.models.inventory;

import com.xiv.gearplanner.models.JobBIS;
import com.xiv.gearplanner.models.inventory.Gear;

import javax.persistence.*;
import java.util.List;

@Entity
@Table
public class GearWithMelds {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade =  CascadeType.ALL)
    private Gear gear;

    @ManyToMany(cascade =  CascadeType.ALL)
    private List<Materia> melds;

    public GearWithMelds() {
    }

    public GearWithMelds( Gear gear, List<Materia> melds) {
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

    public List<Materia> getMelds() {
        return melds;
    }

    public void setMelds(List<Materia> melds) {
        this.melds = melds;
    }

    public void addMeld(Materia meld) { this.melds.add(meld); }

    @Override
    public String toString() {
        return "GearWithMelds{" +
                "id=" + id +
                ", gear=" + gear +
                ", melds=" + melds +
                '}';
    }
}
