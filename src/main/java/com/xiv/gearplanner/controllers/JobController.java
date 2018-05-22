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
public class JobController {
    JobService jobs;

    // Add job
    @Autowired
    public JobController(JobService jobs) {
        this.jobs = jobs;
    }

    // List of Job Types in JSON format.
    @GetMapping("/api/jobs")
    public @ResponseBody
    List<Job> viewJobTypeInJson(@RequestParam(name="job", defaultValue = "true") boolean job) {

        List<Job> jobList = new ArrayList<>();

        try {
            jobs.getJobs().findAll().forEach((value) -> {
                if (value.isJob() == job) jobList.add(value);
            });
        } catch (NullPointerException e) {
            return new ArrayList<>();
        }

        return jobList;
    }


    @GetMapping("/job/view")
    public String viewJobTypes(Model model) {
        model.addAttribute("types", jobs.getJobs().findAll());
        return "/job/view";
    }

    @GetMapping("/job/add")
    public String addJobType(Model model) {
        model.addAttribute("types", jobs.getJobs().getTypes());
        model.addAttribute("job",  new Job());
        return "job/add";
    }

    @PostMapping("/job/add")
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

    @GetMapping("/api/bis/{jobId}")
    public @ResponseBody   List<BISSelector> viewBISListByJob(@PathVariable Long jobId) {

        List<BISSelector> BISList = new ArrayList<>();

        if(jobs.getJobs().findById(jobId).isPresent()) {
           Job job = jobs.getJobs().findById(jobId).get();
            try {
                jobs.getSets().findAllByJob(job).forEach((value) -> {
                    BISList.add(new BISSelector(value.getId(), value.getJob().getId(), value.getName()));
                });
            } catch (NullPointerException e) {
                return new ArrayList<>();
            }

        return BISList;

        }

        return  new ArrayList<>();


    }


    @RequestMapping(
            value = "/api/bis/assign/",
            method= RequestMethod.POST,
            headers = "Accept=*/*",
            produces = "application/json",
            consumes="application/json")
    @ResponseBody
    public Response updateBIS (@RequestBody String jsonStr) {
        try {
            JsonNode data = strToJsonNode(jsonStr);
            //Long staticId = staticDao.getStatics().getStaticIdByOwner(userUtil.getLoggedInUser().getId());

             Long memberId =  data.path("memberId").asLong();
             Long bisId =  data.path("bisId").asLong();

            System.out.println(memberId);
            System.out.println(bisId);

            jobs.getSets().updateStaticMemberBIS(memberId, bisId);

        } catch(IOException err) {

            ResponseError error = new ResponseError();
            // fill map with errors here
            return error;
        }

        Response res = new Response();
        res.setSuccess(true);
        return res;



    }



    // package json node mapping from string
    private JsonNode strToJsonNode(String jsonStr)  throws IOException {

        jsonStr = jsonStr.replaceAll("^\"|\"$|\\\\", "");
        ObjectMapper mapper = new ObjectMapper();
        JsonNode actualNode = mapper.readTree(jsonStr);

        return actualNode;
    }



}

