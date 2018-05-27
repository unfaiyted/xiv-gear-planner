package com.xiv.gearplanner.controllers.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xiv.gearplanner.models.BISSelector;
import com.xiv.gearplanner.models.Job;
import com.xiv.gearplanner.models.Response;
import com.xiv.gearplanner.models.ResponseError;
import com.xiv.gearplanner.services.JobService;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/job")
public class ApiJobController {
    private JobService jobs;

    // Add job
    public ApiJobController(JobService jobs) {
        this.jobs = jobs;
    }


    // List of Job Types in JSON format.
    @GetMapping("/")
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


    @GetMapping("/bis/{jobId}")
    public @ResponseBody
    List<BISSelector> viewBISListByJob(@PathVariable Long jobId) {

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
            value = "/bis/assign/",
            method= RequestMethod.POST,
            headers = "Accept=*/*",
            produces = "application/json",
            consumes="application/json")
    @ResponseBody
    public Response updateBIS (@RequestBody String jsonStr) {
        try {
            JsonNode data = strToJsonNode(jsonStr);

            Long memberId =  data.path("memberId").asLong();
            Long bisId =  data.path("bisId").asLong();

            jobs.getSets().updateStaticMemberBIS(memberId, bisId);

        } catch(IOException err) {
            // fill map with errors here
            return new ResponseError();
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
