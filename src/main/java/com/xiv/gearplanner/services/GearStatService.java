package com.xiv.gearplanner.services;

import com.xiv.gearplanner.repositories.GearStatTypes;
import com.xiv.gearplanner.repositories.GearStats;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GearStatService {
    private GearStats stats;
    private GearStatTypes types;

    @Autowired
    public GearStatService(GearStats stats, GearStatTypes types) {
            this.stats = stats;
            this.types = types;
    }

    public GearStats getStats() {
        return stats;
    }

    public GearStatTypes getTypes() {
        return types;
    }



}
