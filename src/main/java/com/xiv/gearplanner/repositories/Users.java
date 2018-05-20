package com.xiv.gearplanner.repositories;

import com.xiv.gearplanner.models.User;
import com.xiv.gearplanner.models.UserConnection;
import com.xiv.gearplanner.models.UserProfile;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface Users extends CrudRepository<User, Long> {

    User findByUsername(String username);


    @Query("select up from UserProfile up where up.userId = ?1")
    UserProfile getUserProfile(Long userId);

    @Query("select uc from UserConnection uc where userId = ?1")
    UserConnection getUserConnection(Long userId);

    @Modifying
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @Query(value = "INSERT into users(username,password) values(?1,?2)", nativeQuery = true)
    void addUser(String username, String password);

    @Modifying
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @Query(value = "INSERT into user_roles(user_id,role) values(?1,?2)", nativeQuery = true)
    void addRole(Long userId, String role);

    @Modifying
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @Query(value = "INSERT into user_roles(user_id,role) values(?1,'USER')", nativeQuery = true)
    void addDefaultRole(Long userId);

    @Modifying
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @Query(value = "INSERT into user_profile(user_id, email, first_name, last_name, name, username)" +
            "VALUES(?1,?2,?3,?4,?5,?6)", nativeQuery = true)
    void addProfile(Long id, String email, String firstName, String lastName, String name, String username);



}
