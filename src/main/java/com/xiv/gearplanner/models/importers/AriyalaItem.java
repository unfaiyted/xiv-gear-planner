package com.xiv.gearplanner.models.importers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AriyalaItem {

    private Long id;
    private String name;
    private Integer iLvl;

    private Map<Integer, String[]> materiaSlot = new HashMap<>();
    private Integer count = 0;

    public AriyalaItem(Long id, String name, Integer iLvl) {
        this.id = id;
        this.name = name;
        this.iLvl = iLvl;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getiLvl() {
        return iLvl;
    }

    public void setiLvl(Integer iLvl) {
        this.iLvl = iLvl;
    }

    public Map<Integer, String[]> getMateriaSlot() {
        return materiaSlot;
    }

    public void addMateria(String string) {

        if(!string.equalsIgnoreCase("No Materia")) {

            String resultString = string.replaceAll("\\P{L}", " ").trim();
            String color = "empty";


            /* TODO: Switch to arrays still missing some types of materia*/
            switch(resultString) {
                case "Strength":
                    color = "blue";
                    break;
                case "Direct Hit Rate":
                    color = "red";
                    break;
                case "Critical Hit":
                    color = "red";
                    break;
                case "Determination":
                    color = "red";
                    break;
                case "Skill Speed":
                    color = "purple";
                    break;
                case "Vitality":
                    color = "blue";
                    break;
                case "Tenacity":
                    color = "yellow";
                    break;
            }

            materiaSlot.put(count, new String[] {string, color});
            count++;
        }
    }

    @Override
    public String toString() {
        return "AriyalaItem{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", iLvl=" + iLvl +
                ", materiaSlot=" + materiaSlot +
                '}';
    }
}
