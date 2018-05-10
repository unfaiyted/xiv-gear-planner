package com.xiv.gearplanner.models;

import javax.persistence.*;

@Entity
@Table
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private String icon;

    @Column
    private Integer iLvl;

    @Column
    private Integer equipLevel;

    @Column
    private Long xivdbID;

    @Column
    private String lodestoneId;

//    @OneToOne
//    private Patch patch;

    public Item() {

    }

    public Item(String name, String icon, Integer iLvl, Integer equipLevel, Long xivdbID, String lodestoneId) {
        this.name = name;
        this.icon = icon;
        this.iLvl = iLvl;
        this.equipLevel = equipLevel;
        this.xivdbID = xivdbID;
        this.lodestoneId = lodestoneId;;
    }

    public Item(String name, Integer iLvl) {
        this.name = name;
        this.iLvl = iLvl;

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

    public Integer getiLvl() {
        return iLvl;
    }

    public void setiLvl(Integer iLvl) {
        this.iLvl = iLvl;
    }

    public Integer getEquipLevel() {
        return equipLevel;
    }

    public void setEquipLevel(Integer equipLevel) {
        this.equipLevel = equipLevel;
    }

    public Long getXivdbID() {
        return xivdbID;
    }

    public void setXivdbID(Long xivdbID) {
        this.xivdbID = xivdbID;
    }

    public String getLodestoneId() {
        return lodestoneId;
    }

    public void setLodestoneId(String lodestoneId) {
        this.lodestoneId = lodestoneId;
    }

//    public Patch getPatch() {
//        return patch;
//    }
//
//    public void setPatch(Patch patch) {
//        this.patch = patch;
//    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
