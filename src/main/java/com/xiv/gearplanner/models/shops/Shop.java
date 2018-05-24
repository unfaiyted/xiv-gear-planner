package com.xiv.gearplanner.models.shops;

import javax.annotation.Generated;
import javax.persistence.*;

public class Shop {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column
    String name;


}
