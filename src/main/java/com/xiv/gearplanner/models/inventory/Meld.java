package com.xiv.gearplanner.models.inventory;


import javax.persistence.*;

@Entity
@Table
public class Meld {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Item materia;


    public Meld(Item materia) {
        this.materia = materia;
    }

}