package com.xiv.gearplanner.repositories;

import com.xiv.gearplanner.models.GearType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GearTypes extends CrudRepository<GearType, Long> {

}
