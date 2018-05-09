package com.xiv.gearplanner.services;

import com.xiv.gearplanner.models.Job;
import com.xiv.gearplanner.models.JobType;
import com.xiv.gearplanner.repositories.Jobs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JobService {
    private Jobs jobs;

    @Autowired
    public JobService(Jobs jobs) {
        this.jobs = jobs;
    }

    public Jobs getJobs() {
        return jobs;
    }

    public void save(Job job) {
        jobs.save(job);
    }

}
