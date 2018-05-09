package com.xiv.gearplanner.repositories;

import com.xiv.gearplanner.models.Job;
import com.xiv.gearplanner.models.JobType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Jobs extends CrudRepository<Job, Long> {

    @Query("select new JobType(jt) from JobType jt")
    List<JobType> getTypes();

    @Query("select jt from JobType jt where jt.id = ?1")
    JobType findTypeById(Long id);
}
