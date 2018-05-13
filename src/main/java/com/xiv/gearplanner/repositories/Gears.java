package com.xiv.gearplanner.repositories;

import com.xiv.gearplanner.models.Gear;
import com.xiv.gearplanner.models.GearStat;
import com.xiv.gearplanner.models.GearStatType;
import com.xiv.gearplanner.models.GearType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface Gears extends JpaRepository<Gear, Long> {

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


    @Modifying
    @Transactional
    @Query(value = "insert into gear_stat_type(name, description, original_id) VALUES (?1, ?2, ?3) ", nativeQuery = true)
    void addGearStat(String name, String description, Integer originalId);

    @Query("select gst from GearStatType gst where gst.originalId = ?1")
    GearStatType findStatTypeByOriginalId(Integer originalId);

}
