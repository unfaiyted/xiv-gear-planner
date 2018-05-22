package com.xiv.gearplanner.repositories;

import com.xiv.gearplanner.models.Job;
import com.xiv.gearplanner.models.JobBIS;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface JobBISs extends CrudRepository<JobBIS, Long> {

    List<JobBIS> findAllByJob(Job job);

    JobBIS findFirstById(Long id);


    @Modifying
    @Transactional
    @Query(value= "update static_member SET assignedbis_id = ?2 WHERE id = ?1", nativeQuery = true)
    void updateStaticMemberBIS(Long memberId, Long BisId);

}
