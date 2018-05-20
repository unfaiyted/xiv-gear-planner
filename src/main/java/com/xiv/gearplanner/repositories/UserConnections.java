package com.xiv.gearplanner.repositories;

import com.xiv.gearplanner.models.UserConnection;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserConnections extends CrudRepository<UserConnection, Long> {

}