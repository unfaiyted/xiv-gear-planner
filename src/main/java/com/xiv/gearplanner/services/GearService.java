package com.xiv.gearplanner.services;

import com.xiv.gearplanner.models.Gear;
import com.xiv.gearplanner.models.GearStat;
import com.xiv.gearplanner.models.GearStatType;
import com.xiv.gearplanner.models.GearType;
import com.xiv.gearplanner.repositories.GearStatTypes;
import com.xiv.gearplanner.repositories.GearStats;
import com.xiv.gearplanner.repositories.GearTypes;
import com.xiv.gearplanner.repositories.Gears;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GearService {
    private Gears gears;
    private GearStats stats;
    private GearStatTypes statTypes;
    private GearTypes types;

    @Autowired
    public GearService(Gears gears, GearStats stats,
                       GearStatTypes statTypes, GearTypes types) {

            this.gears = gears;
            this.stats = stats;
            this.statTypes = statTypes;
            this.types = types;
    }

    public Gears getGears() {
        return gears;
    }

    public GearStatTypes getStatTypes() {
        return statTypes;
    }

    public GearStats getStats() {
        return stats;
    }

    public GearTypes getTypes() { return types; }

    public void save(Gear gear) {

        gears.save(gear);

        for(GearStat stat : gear.getGearStats()) {
                    stat.setGear(gear);
                    saveStat(stat);
                }
    }

    // Converts to user input list to gear stats object
    public List<GearStat> convertStatList(String type, String value) {
        List<GearStat> gearStatList = new ArrayList<>();

        String[] types = type.split(",");
        String[] values = value.split(",");

        for(int i = 0; i < types.length; i++ ) {
            GearStatType statType = statTypes.findById(Long.parseLong(types[i])).get();
            gearStatList.add(new GearStat(statType, Long.parseLong(values[i])));
        }

        return gearStatList;
    }

    public void saveStat(GearStat stat) { stats.save(stat); }

    public void saveStatType(GearStatType statType) { statTypes.save(statType); }

}
