package com.xiv.gearplanner.models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table
public class JobType {

        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @NotBlank(message = "Must contain a name")
        @Size(min = 5, message = "Name is too short.")
        @Column(nullable = false)
        private String name;

        public JobType() {
        }

        public JobType(JobType copy) {
            this.id = copy.id;
            this.name= copy.name;
        }

        public JobType(String name) {
            this.name = name;
        }

        public Long getId() {
                return id;
            }

        public void setId(Long id) {
            this.id = id;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getName() {
                return this.name;

        }

}
