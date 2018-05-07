package com.xiv.gearplanner.services;

import com.xiv.gearplanner.repositories.Statics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StaticService {
    private Statics statics;

    @Autowired
    public StaticService(Statics statics) {
        this.statics = statics;
    }



    public boolean getMembersByStatic(long staticId) {
        return false;
    }


}
