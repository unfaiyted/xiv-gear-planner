package com.xiv.gearplanner.models.inventory;


import javax.persistence.*;
import java.util.List;

@Entity
@Table
public class GearSlotCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Integer originalId;

    @ManyToOne
    private GearSlot slot;

    @ManyToMany
    private List<GearSlot> disabled;


        public GearSlotCategory() {
        }

        public GearSlotCategory(GearSlot slot) {
            this.slot = slot;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public GearSlot getSlot() {
            return slot;
        }

        public void setSlot(GearSlot slot) {
            this.slot = slot;
        }

        public List<GearSlot> getDisabled() {
            return disabled;
        }

        public void setDisabled(List<GearSlot> disabled) {
            this.disabled = disabled;
        }

        @Override
        public String toString() {
            return "GearSlotCategory{" +
                    "id=" + id +
                    ", slot=" + slot +
                    ", disabled=" + disabled +
                    '}';
        }
}
