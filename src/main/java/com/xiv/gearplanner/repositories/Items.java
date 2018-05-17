package com.xiv.gearplanner.repositories;

import com.xiv.gearplanner.models.inventory.Item;
import com.xiv.gearplanner.models.inventory.ItemCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
public interface Items extends CrudRepository<Item, Long> {

    Page<Item> findAll(Pageable pageable);

    @Query("select ic from ItemCategory ic where ic.originalId = ?1")
    ItemCategory findCategoryByOriginalId(Integer originalId);

    Item findByOriginalId(Integer originalId);

    @Modifying
    @Query(value = "insert into item_category(name, display_order, icon, original_id, job_id, parent_id) " +
                    "VALUES (?1, ?2, ?3, ?4, ?5, ?6) ", nativeQuery = true)
    @Transactional
    void addCategory(String name, Integer order, Integer icon, Integer originalId, Long jobId, Long parentId);



}
