package com.xiv.gearplanner.models.shops;

import com.fasterxml.jackson.annotation.JsonView;
import com.xiv.gearplanner.models.View;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Shop {


    @JsonView(View.Summary.class)
    @Id @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    @Column
    @JsonView(View.Summary.class)
    private Integer originalId;

    @Column
    @JsonView(View.Summary.class)
    private String name;

    public Shop() {}

    public Shop(Integer originalId, String name) {
        this.originalId = originalId;
        this.name = name;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Shop{" +
                "id=" + id +
                ", originalId=" + originalId +
                ", name='" + name + '\'' +
                '}';
    }
}
