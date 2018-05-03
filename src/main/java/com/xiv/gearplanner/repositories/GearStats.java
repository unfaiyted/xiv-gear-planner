package com.xiv.gearplanner.repositories;

import com.xiv.gearplanner.models.GearStat;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GearStats extends CrudRepository<GearStat, Long> {

}
