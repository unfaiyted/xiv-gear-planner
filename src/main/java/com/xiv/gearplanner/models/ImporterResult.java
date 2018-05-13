package com.xiv.gearplanner.models;

// Stores importer results in model to be displayed on page.
public class ImporterResult {
    private String name;
    private Long value;


    public ImporterResult(String name, Long value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "ImporterResult{" +
                "name='" + name + '\'' +
                ", value=" + value +
                '}';
    }
}
