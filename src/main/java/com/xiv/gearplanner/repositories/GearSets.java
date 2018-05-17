package com.xiv.gearplanner.repositories;

import com.xiv.gearplanner.models.inventory.GearSet;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GearSets extends CrudRepository<GearSet, Long> {

}
