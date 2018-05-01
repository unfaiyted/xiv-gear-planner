package com.xiv.gearplanner.repositories;

import com.xiv.gearplanner.models.JobType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobTypes extends CrudRepository<JobType, Long> {

}
