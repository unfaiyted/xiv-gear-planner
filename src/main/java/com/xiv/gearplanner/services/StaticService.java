package com.xiv.gearplanner.services;

import com.xiv.gearplanner.models.Player;
import com.xiv.gearplanner.models.Static;
import com.xiv.gearplanner.models.StaticMember;
import com.xiv.gearplanner.repositories.Players;
import com.xiv.gearplanner.repositories.Statics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StaticService {
    private Statics statics;
    private Players players;

    @Autowired
    public StaticService(Statics statics, Players players) {
        this.statics = statics;
        this.players = players;
    }

    public boolean getMembersByStatic(long staticId) {
        return false;
    }

    public Statics getStatics() {
        return statics;
    }

    public void save(Static newStatic) {
        statics.save(newStatic);
    }

    public List<StaticMember> createStaticMembersbyPlayerId(Long[] ids) {
        List<StaticMember> staticMembers = new ArrayList<>();

        for(Long id : ids) {
            staticMembers.add(createStaticMemberbyPlayerId(id));
        }
        return staticMembers;
    }

    public StaticMember createStaticMemberbyPlayerId(Long id) {
        Player player = players.getPlayerById(id);
        return new StaticMember(player);
    }

}
