package com.xiv.gearplanner.repositories;

import com.xiv.gearplanner.models.inventory.Item;
import com.xiv.gearplanner.models.inventory.ItemCategory;
import com.xiv.gearplanner.models.inventory.ItemStat;
import com.xiv.gearplanner.models.inventory.Materia;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.transaction.TransactionScoped;
import java.util.List;


@Repository
public interface Items extends CrudRepository<Item, Long> {

    Page<Item> findAll(Pageable pageable);

    @Query("select ic from ItemCategory ic where ic.originalId = ?1")
    ItemCategory findCategoryByOriginalId(Integer originalId);

    Item findByOriginalId(Integer originalId);

    @Query("select i from ItemStat i where i.item.id = ?1")
    ItemStat findItemStatByItemId(Long id);

    @Query("Select i.originalId from Item i")
    List<Integer> getAllByOriginalId();

    @Query("Select ic from ItemCategory ic")
    List<ItemCategory> getAllCategories();


    @Modifying
    @Query(value = "insert into item_stat(item_id, stat_type_id, value) VALUES (?1, ?2, ?3)", nativeQuery = true)
    @Transactional
    void addItemStat(Long materiaId, Long statTypeId, Long value);

    @Modifying
    @Query(value = "insert into item_category(name, display_order, icon, original_id, job_id, parent_id) " +
                    "VALUES (?1, ?2, ?3, ?4, ?5, ?6) ", nativeQuery = true)
    @Transactional
    void addCategory(String name, Integer order, Integer icon, Integer originalId, Long jobId, Long parentId);

    @Query(value = "select m.* from item_stat i " +
            " inner join materia m ON (i.item_id = m.id) " +
            " inner join gear_stat_type gst ON (gst.id = i.stat_type_id) " +
            " where gst.name = ?1 & i.value = ?2", nativeQuery = true)
    Materia getMateriaByStat(String statTypeName, Long value);

}
