package com.xiv.gearplanner.services;

import com.xiv.gearplanner.models.Job;
import com.xiv.gearplanner.models.Player;
import com.xiv.gearplanner.repositories.Jobs;
import com.xiv.gearplanner.repositories.Players;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlayerService {
    private final Players players;
    private final Jobs jobs;

    @Autowired
    public PlayerService(Players players, Jobs jobs) {
            this.players = players;
            this.jobs = jobs;
    }

    public Players getPlayers() {
        return this.players;
    }

    public Jobs getJobs() { return this.jobs; }

    public void save(Player player) {
        players.save(player);
    }


}
