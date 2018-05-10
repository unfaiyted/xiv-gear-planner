package com.xiv.gearplanner.models;

import java.util.Map;

public class ResponseError  extends Response {
    private Map<String, String> errors;

    public Map<String, String> getErrors() {
        return errors;
    }

    public void setErrors(Map<String, String> value) {
        errors = value;

    }


}
