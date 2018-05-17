package com.xiv.gearplanner.models.inventory;

import javax.persistence.*;

@Entity
@Table
public class GearStatType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Integer originalId;

    @Column(nullable = false)
    private String name;

    @Column
    private String description;
    public GearStatType() {
    }

    public GearStatType(String name) {
        this.name = name;
    }

    public GearStatType(GearStatType copy) {
        this.id = copy.id;
        this.name = copy.name;
    }

    public GearStatType(Integer originalId, String name, String description) {
        this.originalId = originalId;
        this.name = name;
        this.description = description;
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

    public Integer getOriginalId() {
        return originalId;
    }

    public void setOriginalId(Integer originalId) {
        this.originalId = originalId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "GearStatType{" +
                "id=" + id +
                ", originalId=" + originalId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
