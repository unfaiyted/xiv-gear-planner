package com.xiv.gearplanner.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xiv.gearplanner.models.*;
import com.xiv.gearplanner.services.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/job")
public class JobController {
    private JobService jobs;

    // Add job
    @Autowired
    public JobController(JobService jobs) {
        this.jobs = jobs;
    }


    @GetMapping("/view")
    public String viewJobTypes(Model model) {
        model.addAttribute("types", jobs.getJobs().findAll());
        return "/view";
    }

    @GetMapping("/add")
    public String addJobType(Model model) {
        model.addAttribute("types", jobs.getJobs().getTypes());
        model.addAttribute("job",  new Job());
        return "job/add";
    }

    @PostMapping("/add")
    public String submitJobType(@RequestParam("job-type") String type, @Valid Job job,  Errors validation, Model model) {

        if(validation.hasErrors()) {
            model.addAttribute("job", job);
            model.addAttribute("types", jobs.getJobs().getTypes());
            model.addAttribute("errors", validation);
            return "job/add";
        }

        job.setType(jobs.getJobs().findTypeById(Long.parseLong(type)));
        jobs.save(job);
        return "redirect:/job/add";
    }


    // An overview of all stored BIS in list format
    @GetMapping("/bis")
    public String viewStoredBISList(Model model) {

        model.addAttribute("jobs", jobs.getJobs().findAll());
        model.addAttribute("bis",jobs.getSets().findAll());
            return "job/bis/index";
    }

    @GetMapping("/bis/view/{id}")
    public String viewBIS(@PathVariable Long id, Model model) {
        model.addAttribute("bis",jobs.getSets().findFirstById(id));
        return "job/bis/view";
    }





}

