package com.xiv.gearplanner.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "item_category")
public class ItemCategory {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Integer originalId;
    @Column
    private String name;
    @Column
    private Integer icon;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private ItemCategory parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<ItemCategory> children = new ArrayList<>();

    @Column
    private Integer displayOrder;

    @ManyToOne
    private Job job;

    public ItemCategory() {

    }

    public ItemCategory(String name, Integer originalId, Integer icon, Integer order, Job job) {
        this.originalId = originalId;
        this.name = name;
        this.icon = icon;
        this.displayOrder = order;
        this.job = job;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getOriginalId() {
        return originalId;
    }

    public void setOriginalId(Integer originalId) {
        this.originalId = originalId;
    }

    public Integer getIcon() {
        return icon;
    }

    public void setIcon(Integer icon) {
        this.icon = icon;
    }


    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Integer order) {
        this.displayOrder = order;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ItemCategory getParent() {
        return parent;
    }

    public void setParent(ItemCategory parent) {
        this.parent = parent;
    }

    public List<ItemCategory> getChildren() {
        return children;
    }

    public void setChildren(List<ItemCategory> children) {
        this.children = children;
    }
}
