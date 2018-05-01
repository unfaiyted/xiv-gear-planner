package com.xiv.gearplanner.controllers;


import com.xiv.gearplanner.models.JobType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class JobTypeController {


    // List of Job Types in JSON format.
    @GetMapping("/jobTypes.json")
    public List<JobType> viewJobTypeInJson() {
        List<JobType> jobTypes = new ArrayList<>();
        return jobTypes;
    }

    @GetMapping("/job/viewType")
    public String viewJobTypes() {
        return "/job/typeView";
    }

    @GetMapping("/job/addType")
    public String addJobType() {
        return "job/typeAdd";
    }
    @GetMapping("/job/type/{id}/delete")
    public String deleteJobType(Long id) {
        return "job/typeView";
    }

    @GetMapping("/job/type/{id}/edit")
    public String editJobType(Long id) {
        return "job/typeView";
    }

}
