package com.xiv.gearplanner.repositories;

import com.xiv.gearplanner.models.Gear;
import com.xiv.gearplanner.models.GearStat;
import com.xiv.gearplanner.models.GearStatType;
import com.xiv.gearplanner.models.GearType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

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


    

}
