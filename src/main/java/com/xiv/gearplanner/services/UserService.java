package com.xiv.gearplanner.services;

import com.xiv.gearplanner.models.User;
import com.xiv.gearplanner.models.UserProfile;
import com.xiv.gearplanner.repositories.Users;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Repository;


@Repository
public class UserService {
    private Users users;

    @Autowired
    public UserService(Users users) {
        this.users = users;
    }

    public Users getUsers() {
        return users;
    }

    //    public UserProfile getUserProfile(final String userId) {
//        LOG.debug("SQL SELECT ON UserProfile: {}", userId);
//
//        return jdbcTemplate.queryForObject("select * from UserProfile where userId = ?",
//            new RowMapper<UserProfile>() {
//                public UserProfile mapRow(ResultSet rs, int rowNum) throws SQLException {
//                return new UserProfile(
//                    userId,
//                    rs.getString("name"),
//                    rs.getString("firstName"),
//                    rs.getString("lastName"),
//                    rs.getString("email"),
//                    rs.getString("username"));
//                }
//            }, userId);
//    }

//    public UserConnection getUserConnection(final String userId) {
//        LOG.debug("SQL SELECT ON UserConnection: {}", userId);
//
//        return jdbcTemplate.queryForObject("select * from UserConnection where userId = ?",
//            new RowMapper<UserConnection>() {
//                public UserConnection mapRow(ResultSet rs, int rowNum) throws SQLException {
//                return new UserConnection(
//                    userId,
//                    rs.getString("providerId"),
//                    rs.getString("providerUserId"),
//                    rs.getInt("rank"),
//                    rs.getString("displayName"),
//                    rs.getString("profileUrl"),
//                    rs.getString("imageUrl"),
//                    rs.getString("accessToken"),
//                    rs.getString("secret"),
//                    rs.getString("refreshToken"),
//                    rs.getLong("expireTime"));
//                }
//            }, userId);
//    }

    public void createUser(String username, UserProfile profile) {
        users.addUser(username, RandomStringUtils.randomAlphanumeric(8));
        // get user ID
        User user = users.findByUsername(username);
        users.addDefaultRole(user.getId());
        users.addProfile(user.getId(),
                profile.getEmail(),
                profile.getFirstName(),
                profile.getLastName(),
                profile.getName(),
                profile.getUsername());

    }
}
