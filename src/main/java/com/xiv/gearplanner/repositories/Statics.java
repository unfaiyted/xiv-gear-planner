package com.xiv.gearplanner.repositories;

import com.xiv.gearplanner.models.Static;
import com.xiv.gearplanner.models.StaticMember;
import com.xiv.gearplanner.models.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface Statics extends CrudRepository<Static,Long> {

    @Query("select new StaticMember(sm) from StaticMember sm where sm.oneStatic.id = ?1")
    List<StaticMember> getMembers(Long staticId);

    @Query("select sm from StaticMember sm where sm.id = ?1")
    StaticMember getMember(Long memberId);

    @Query("select s.id from Static s where s.owner.id = ?1")
    Long getStaticIdByOwner(Long userId);

    @Query("select sm.oneStatic.id from StaticMember sm where sm.player.id = ?1")
    Long getStaticIdByMember(Long playerId);

    @Query("select new Static(s) from Static s where s.name = ?1")
    Static getStaticByName(String name);

    @Query("select new Static(s) from Static s where s.owner.id = ?1")
    Static getStaticByOwner(Long ownerId);

    @Query("select new StaticMember(sm) from StaticMember sm where sm.oneStatic.owner.id = ?1")
    List<StaticMember> getMembersByOwnerId(Long ownerId);


    @Modifying
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @Query("update StaticMember sm set sm.assignedJob.id =:jobId where sm.id =:memberId")
    void updateMemberJob(@Param("memberId") Long memberId, @Param("jobId") Long jobId);

    @Modifying
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @Query("delete from StaticMember sm where sm.id =:memberId")
    void deleteMember(@Param("memberId") Long memberId);


    @Modifying
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @Query(value = "insert into static_member(one_static_id, player_id) VALUE(?1, ?2)", nativeQuery = true)
    void addMember(Long staticId, Long playerId);



}
