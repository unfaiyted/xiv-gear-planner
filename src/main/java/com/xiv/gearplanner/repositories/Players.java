package com.xiv.gearplanner.repositories;


import com.xiv.gearplanner.models.Player;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Players extends CrudRepository<Player,Long> {


    @Query("select new Player(p) from Player p where  UPPER(p.name) " +
            "LIKE CONCAT('%',UPPER(:name),'%')")
    List<Player> findPlayersByNameContaining(@Param("name") String name);

}
