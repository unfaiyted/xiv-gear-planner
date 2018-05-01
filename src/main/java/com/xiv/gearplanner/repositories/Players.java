package com.xiv.gearplanner.repositories;


import com.xiv.gearplanner.models.Player;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Players extends CrudRepository<Player,Long> {

}
