package com.xiv.gearplanner.repositories;

import com.xiv.gearplanner.models.Static;
import com.xiv.gearplanner.models.StaticMember;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Statics extends CrudRepository<Static,Long> {

    @Query("select new StaticMember(sm) from StaticMember sm where sm.oneStatic.id = ?1")
    List<StaticMember> getMembers(Long staticId);

    @Query("select s.id from Static s where s.owner.id = ?1")
    Long getStaticIdByOwner(Long userId);

    @Query("select sm.oneStatic.id from StaticMember sm where sm.player.id = ?1")
    Long getStaticIdByMember(Long playerId);

    @Query("select new Static(s) from Static s where s.name = ?1")
    Static getStaticByName(String name);


}
