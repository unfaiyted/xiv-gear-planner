package com.xiv.gearplanner.repositories;

import com.xiv.gearplanner.models.GearStatType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GearStatTypes extends CrudRepository<GearStatType, Long> {

}
