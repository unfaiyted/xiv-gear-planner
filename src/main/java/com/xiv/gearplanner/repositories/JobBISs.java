package com.xiv.gearplanner.repositories;

import com.xiv.gearplanner.models.Job;
import com.xiv.gearplanner.models.JobBIS;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobBISs extends CrudRepository<JobBIS, Long> {

    List<JobBIS> findAllByJob(Job job);

}
