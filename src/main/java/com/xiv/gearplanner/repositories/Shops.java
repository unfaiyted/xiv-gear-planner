package com.xiv.gearplanner.repositories;

import com.xiv.gearplanner.models.shops.Shop;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Repository
public interface Shops extends CrudRepository<Shop, Long> {

    @Query("select originalId from Shop")
    List<Integer> getAllOriginalIds();

    @Override
    Optional<Shop> findById(Long aLong);

    @Query("select s from Shop s where s.originalId = ?1")
    Shop findByOriginalId(Integer originalId);

    @Query("select s from Shop s left join fetch s.purchasables where s.id = ?1 ")
    Shop findByIdWithPurchasable(Long id);


    @Query(value= "select CASE WHEN COUNT(g) > 0 THEN true ELSE false END " +
            "from Shop g inner join " +
            "g.items i  WHERE g.id = ?1 AND i.id = ?2")
    boolean existsInShop(Long shopId, Long itemId);


    @Modifying
    @Query(value = "insert into gil_shop_items(gil_shop_id, items_id) " +
            "VALUES (?1, ?2) ", nativeQuery = true)
    @Transactional
    void addGilShopItem(Long shopId, Long itemId);


    @Query("select s from GilShop s inner join s.items i where i.id = ?1")
    List<Shop> findGilShopWithItemId(Long id);

    @Query("select s from SpecialShop s inner join s.purchasables p " +
            "inner join p.receivables r " +
            "where r.item.id = ?1")
    List<Shop> findSpecialShopsWithItemId(Long id);


//    @Query(value = "select  s from special_shop s" +
//            " inner join ", nativeQuery = true)
//    List<Shop> find(Long id);



}
