package com.xiv.gearplanner.repositories;

import com.xiv.gearplanner.models.shops.SpecialShop;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface SpecialShops extends CrudRepository<SpecialShop, Long> {

    SpecialShop findAllByOriginalId(Integer originalId);

    @Override
    Optional<SpecialShop> findById(Long aLong);

    Page<SpecialShop> findAll(Pageable pageable);

}
