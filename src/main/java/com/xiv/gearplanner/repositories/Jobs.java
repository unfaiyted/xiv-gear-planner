package com.xiv.gearplanner.repositories;

import com.xiv.gearplanner.models.Job;
import com.xiv.gearplanner.models.JobType;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface Jobs extends CrudRepository<Job, Long> {

    @Query("select new JobType(jt) from JobType jt")
    List<JobType> getTypes();

    @Query("select jt from JobType jt where jt.id = ?1")
    JobType findTypeById(Long id);

   // @Query("select j from Job j where j.originalId = ?1")
    Job findFirstByOriginalId(Integer id);

    Job findJobByAbbr(String Abbr);

    @Modifying
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @Query("update Job j set j.parentJob.id =:parent where j.id =:id")
    void updateParentJob(@Param("id") Long id,  @Param("parent") Long parent);
}
