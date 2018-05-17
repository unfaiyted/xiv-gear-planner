package com.xiv.gearplanner.services;

import com.xiv.gearplanner.models.inventory.Gear;
import com.xiv.gearplanner.models.inventory.GearStat;
import com.xiv.gearplanner.models.inventory.GearStatType;
import com.xiv.gearplanner.repositories.GearEquipCategories;
import com.xiv.gearplanner.repositories.Gears;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GearService {
    private Gears gears;
    private GearEquipCategories equipCategories;

    @Autowired
    public GearService(Gears gears, GearEquipCategories equipCategories) {
            this.gears = gears;
            this.equipCategories = equipCategories;
    }

    public Gears getGears() {
        return gears;
    }

    public GearEquipCategories getEquipCategories() { return equipCategories; }

    public void save(Gear gear) {
        gears.save(gear);
    }

    // Converts to user input list to gear stats object
    public List<GearStat> convertStatList(String type, String value) {
        List<GearStat> gearStatList = new ArrayList<>();

        String[] types = type.split(",");
        String[] values = value.split(",");

        for(int i = 0; i < types.length; i++ ) {
            GearStatType statType = gears.getStatTypeById(Long.parseLong(types[i]));
            gearStatList.add(new GearStat(statType, Long.parseLong(values[i])));
        }

        return gearStatList;
    }

    public Gear addStatsByString(Gear gear, String type, String value) {

        String[] types = type.split(",");
        String[] values = value.split(",");

        for(int i = 0; i < types.length; i++ ) {
            GearStatType statType = gears.getStatTypeById(Long.parseLong(types[i]));
            gear.addGearStat(new GearStat(statType, Long.parseLong(values[i])));
        }

        return gear;
    }


}
