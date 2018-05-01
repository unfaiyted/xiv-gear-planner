package com.xiv.gearplanner.services;

import com.xiv.gearplanner.repositories.Players;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlayerService {
    private final Players players;

    @Autowired
    public PlayerService(Players players) {
            this.players = players;
    }


    public Players getPlayers() {
        return this.players;
    }

}
