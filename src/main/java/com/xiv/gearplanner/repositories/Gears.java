package com.xiv.gearplanner.repositories;

import com.xiv.gearplanner.models.Gear;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Gears extends CrudRepository<Gear, Long> {

}
