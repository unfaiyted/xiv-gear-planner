package com.xiv.gearplanner.controllers;


import com.xiv.gearplanner.models.JobType;
import com.xiv.gearplanner.services.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
public class JobTypeController {
    JobService jobs;

    @Autowired
    public JobTypeController(JobService jobs) {
       this.jobs = jobs;
    }

    // List of Job Types in JSON format.
    @GetMapping("/job/types.json")
    public @ResponseBody List<JobType> viewJobTypeInJson() {
        List<JobType> types = new ArrayList<>();

        try {
            jobs.getTypes().findAll().forEach(types::add);
        } catch (NullPointerException e) {
            return new ArrayList<>();
            }
        return types;
    }

    @GetMapping("/job/viewType")
    public String viewJobTypes(Model model) {
        model.addAttribute("types", jobs.getTypes().findAll());
        return "/job/viewType";
    }

    @GetMapping("/job/addType")
    public String addJobType(Model model) {
        model.addAttribute("type", new JobType());
        return "job/addType";
    }

    @PostMapping("/job/addType")
    public String submitJobType(@Valid JobType type, Errors validation, Model model) {
        model.addAttribute("type", type);

        if(validation.hasErrors()) {
            model.addAttribute("errors", validation);
                return "job/addType";
        }

        jobs.save(type);
        return "job/addType";
    }

    @GetMapping("/job/type/{id}/delete")
    public String deleteJobType(Long id) {
        return "redirect:/job/viewType";
    }

    @GetMapping("/job/type/{id}/edit")
    public String editJobType(Long id) {
        return "redirect:/job/viewType";
    }

}
