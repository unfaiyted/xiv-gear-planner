package com.xiv.gearplanner.models;

import java.util.HashMap;
import java.util.Map;

public class ResponseError  extends Response {
    private Map<String, String> errors = new HashMap<>();

    public Map<String, String> getErrors() {
        return errors;
    }

    public ResponseError() {
        super(false);
    }

    public ResponseError(String key, String value) {
        super(false);
        this.errors.put(key,value);
    }

    public void setErrors(Map<String, String> value) {
        errors = value;
    }

    public void addError (String key, String value) {
        errors.put(key, value);

    }

}
