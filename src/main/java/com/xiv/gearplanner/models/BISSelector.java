package com.xiv.gearplanner.models;

public class BISSelector {
    private Long id;
    private Long jobId;
    private String name;


    public BISSelector(Long id, Long jobId, String name) {
        this.id = id;
        this.jobId = jobId;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getJobId() {
        return jobId;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
