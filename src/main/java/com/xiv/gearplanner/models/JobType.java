package com.xiv.gearplanner.models;

import javax.persistence.*;

@Entity
@Table
public class JobType {
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        @Column(nullable = false)
        private String name;

        public JobType(String name) {
            this.name = name;
        }

}
