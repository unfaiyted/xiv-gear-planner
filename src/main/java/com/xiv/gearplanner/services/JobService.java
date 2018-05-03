package com.xiv.gearplanner.services;

import com.xiv.gearplanner.models.Job;
import com.xiv.gearplanner.models.JobType;
import com.xiv.gearplanner.repositories.JobTypes;
import com.xiv.gearplanner.repositories.Jobs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JobService {
    private Jobs jobs;
    private JobTypes types;


    @Autowired
    public JobService(Jobs jobs, JobTypes types) {
        this.jobs = jobs;
        this.types = types;
    }

    public Jobs getJobs() {
        return jobs;
    }

    public JobTypes getTypes() {
        return types;
    }

    // Edit Job Type
    public JobType edit(JobType type) {
            return type;
    }

    // Edit Job
    public Job edit(Job job) {
            return job;
    }

    public void save(JobType type) {
        types.save(type);
    }


    public void save(Job job) {
        jobs.save(job);
    }

}
