package com.xiv.gearplanner.repositories;

import com.xiv.gearplanner.models.shops.SpecialShop;
import org.springframework.data.repository.CrudRepository;

public interface SpecialShops extends CrudRepository<SpecialShop, Long> {

    SpecialShop findAllByOriginalId(Integer originalId);

}
