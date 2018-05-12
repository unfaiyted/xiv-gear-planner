package com.xiv.gearplanner.repositories;

import com.xiv.gearplanner.models.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface Items extends CrudRepository<Item, Long> {

    Page<Item> findAll(Pageable pageable);



}
