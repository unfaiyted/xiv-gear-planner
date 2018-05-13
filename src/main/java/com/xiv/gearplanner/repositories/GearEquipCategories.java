package com.xiv.gearplanner.repositories;

import com.xiv.gearplanner.models.GearEquipCategory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GearEquipCategories extends CrudRepository<GearEquipCategory, Long>{

    GearEquipCategory findFirstByOriginalId(Integer originalId);

}
