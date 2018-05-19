package com.xiv.gearplanner.services;

import com.xiv.gearplanner.models.Job;
import com.xiv.gearplanner.models.JobType;
import com.xiv.gearplanner.repositories.JobBISs;
import com.xiv.gearplanner.repositories.Jobs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JobService {
    private Jobs jobs;
    private JobBISs sets;

    @Autowired
    public JobService(Jobs jobs, JobBISs sets) {
        this.jobs = jobs;
        this.sets = sets;
    }

    public Jobs getJobs() {
        return jobs;
    }

    public JobBISs getSets()  {return sets; };

    public void save(Job job) {
        jobs.save(job);
    }

}
