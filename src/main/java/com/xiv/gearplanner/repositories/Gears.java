package com.xiv.gearplanner.repositories;

import com.xiv.gearplanner.models.inventory.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface Gears extends JpaRepository<Gear, Long> {

    Page<Gear> findAll(Pageable pageable);


    Gear findGearByName(String name);

    // Stats for a specific piece of gear
    @Query("select new GearStat(gs) from GearStat gs WHERE  gs.gear.id = ?1")
    List<GearStat> getGearStats(Long gearId);

    // Get all Stat Types List
    @Query("select new GearStatType(gst) from GearStatType gst")
    List<GearStatType> getStatTypes();

    // Get Stat Types by stat Id
    @Query("select new GearStatType(gst) from GearStatType gst where gst.id = ?1")
    GearStatType getStatTypeById(Long id);

    // Get all Gear Types List
    @Query("select new GearType(gt) from GearType gt")
    List<GearType> getGearTypes();


    @Query("select gs from GearSlot gs where gs.name = ?1")
    GearSlot getGearSlotByName(String name);

    @Query("select gsc from GearSlotCategory gsc where gsc.originalId = ?1")
    GearSlotCategory getGearSlotCategoryByOriginalId(Integer originalId);

    @Modifying
    @Transactional
    @Query(value = "insert into gear_slot_category(slot_id, original_id) VALUES (?1, ?2)", nativeQuery = true)
    void addGearSlotCategory(Long slotId, Integer originalId);

    @Modifying
    @Transactional
    @Query(value = "insert into gear_slot(name) VALUES (?1)", nativeQuery = true)
    void addGearSlot(String name);

    @Modifying
    @Transactional
    @Query(value = "insert into gear_stat_type(name, description, original_id) VALUES (?1, ?2, ?3) ", nativeQuery = true)
    void addGearStat(String name, String description, Integer originalId);

    @Query("select gst from GearStatType gst where gst.originalId = ?1")
    GearStatType findStatTypeByOriginalId(Integer originalId);

}
