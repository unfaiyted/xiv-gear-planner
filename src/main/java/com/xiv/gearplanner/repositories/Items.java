package com.xiv.gearplanner.repositories;

import com.xiv.gearplanner.models.inventory.*;
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

    @Query("select f from Food f where f.originalId = ?1")
    Food findFoodByOriginalId(Integer originalId);

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

    @Query(value = "Select m.* from materia m" +
            "  inner join item_stat i ON (i.item_id = m.id)" +
            " inner join gear_stat_type gst ON (gst.id = i.stat_type_id)" +
            " where gst.name LIKE ?1 and i.value = ?2", nativeQuery = true)
    Materia getMateriaByStat(String statTypeName, Long value);


    @Query("Select m from Materia m WHERE m.name LIKE ?1")
    Materia getMateriaByName(String name);


    /* Updates the materia import to have colors added to data set*/
    @Modifying
    @Query(value ="UPDATE Materia" +
            "    SET" +
            "      color =" +
            "      case" +
            "      when name like '%Lightning%' then 'white'" +
            "      when name like '%Fire%' then 'white'" +
            "      when name like '%Wind%' then 'white'" +
            "      when name like '%Water%' then 'white'" +
            "      when name like '%Ice%' then 'white'" +
            "      when name like '%Earth%' then 'white'" +
            "      when name like '%Mind%' then 'skyblue'" +
            "      when name like '%Vitality%' then 'skyblue'" +
            "      when name like '%Strength%' then 'skyblue'" +
            "      when name like '%Intelligence%' then 'skyblue'" +
            "      when name like '%Dexterity%' then 'skyblue'" +
            "      when name like '%Savage%' then 'red'" +
            "      when name like '%Heaven%' then 'red'" +
            "      when name like '%Battle%' then 'yellow'" +
            "      when name like '%Piety%' then 'yellow'" +
            "      when name like '%Quick%' then 'purple'" +
            "      when name like '%Craft%' then 'drkblue'" +
            "      when name like '%Gatherer%' then 'green'" +
            "      else null end", nativeQuery = true)
    @Transactional
    void updateMateriaColors();


    /* Match stats to materia when imported*/
    @Modifying
    @Query(value ="update materia set materia.stat_id = (Select item_stat.id from item_stat where item_stat.item_id = materia.id)"
            , nativeQuery= true)
    @Transactional
    void matchStatsToMateria();


}
