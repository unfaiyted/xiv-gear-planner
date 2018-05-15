package com.xiv.gearplanner.services;


import com.xiv.gearplanner.repositories.GearSets;
import org.springframework.stereotype.Service;

@Service
public class GearSetService {
    private GearSets gearSets;

    public GearSetService(GearSets gearSets) {
        this.gearSets = gearSets;
    }

    public GearSets getGearSets() {
        return gearSets;
    }
}
