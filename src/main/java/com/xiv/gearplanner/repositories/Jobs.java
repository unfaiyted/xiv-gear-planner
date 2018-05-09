package com.xiv.gearplanner.repositories;

import com.xiv.gearplanner.models.Job;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Jobs extends CrudRepository<Job, Long> {


}
