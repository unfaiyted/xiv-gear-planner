package com.xiv.gearplanner.models;


// Model to return error or success responses
public class Response {
    protected boolean success;

    public Response() {
    }

    public Response(boolean success) {
        this.success = success;
    }

    public boolean isSuccess() {
        return this.success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}