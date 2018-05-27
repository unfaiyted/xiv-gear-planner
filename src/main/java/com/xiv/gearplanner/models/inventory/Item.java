package com.xiv.gearplanner.models.inventory;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    @Column /* Imported Id */
    private Integer originalId;

    @Column
    private String name;


    @Column
    @JsonIgnore
    private Integer icon;

    @Column
    private Integer iLvl;

    @Column
    private Long xivdbID;

    @Column(columnDefinition = "text")
    private String description;

    @Column
    @JsonIgnore
    private String lodestoneId;

    @ManyToOne
    @JsonManagedReference
    ItemCategory category;

    @Column
    private boolean craftable = false;

    public Item() { }


    public Item(String name, String description, Integer iLvl, Integer icon, Integer originalId, ItemCategory category) {
        this.name = name;
        this.description = description;
        this.icon = icon;
        this.iLvl = iLvl;
        this.originalId = originalId;
        this.category = category;

    }

    public Item(String name, Integer icon, Integer iLvl, Integer equipLevel, String lodestoneId) {
        this.name = name;
        this.icon = icon;
        this.iLvl = iLvl;
        this.lodestoneId = lodestoneId;
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

    public Integer getIcon() {
        return icon;
    }

    public void setIcon(Integer icon) {
        this.icon = icon;
    }

    public Integer getOriginalId() {
        return originalId;
    }

    public void setOriginalId(Integer originalId) {
        this.originalId = originalId;
    }

    public String getXivDBLink() {
        return "http://xivdb.com/item/" + this.originalId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ItemCategory getCategory() {
        return category;
    }

    public void setCategory(ItemCategory category) {
        this.category = category;
    }

    public boolean isCraftable() {
        return craftable;
    }

    public void setCraftable(boolean craftable) {
        this.craftable = craftable;
    }

}
