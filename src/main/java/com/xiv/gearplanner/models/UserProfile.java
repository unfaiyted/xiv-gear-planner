package com.xiv.gearplanner.models;


import org.springframework.social.security.SocialUser;

import javax.annotation.Generated;
import javax.persistence.*;

@Entity
@Table
public class UserProfile {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;
    @Column
    private  String firstName;
    @Column
    private  String lastName;
    @Column
    private  String email;
    @Column
    private  String username;

    @ManyToOne(cascade = CascadeType.ALL)
    private User user;

    public UserProfile() {}

    public UserProfile(Long id, User user, String name, String firstName, String lastName, String email, String username) {
        this.id = id;
        this.user = user;
        this.name = name;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.username = username;
    }


    public UserProfile(User user, String name, String firstName, String lastName, String email, String username) {
        this.user = user;
        this.name = name;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.username = username;

        fixName();
    }


    public UserProfile(User user, org.springframework.social.connect.UserProfile up) {
        this.user = user;
        this.name = up.getName();
        this.firstName = up.getFirstName();
        this.lastName = up.getLastName();
        this.email = up.getEmail();
        this.username = up.getUsername();
    }

    public UserProfile(org.springframework.social.connect.UserProfile up) {
        this.name = up.getName();
        this.firstName = up.getFirstName();
        this.lastName = up.getLastName();
        this.email = up.getEmail();
        this.username = up.getUsername();
    }

    private void fixName() {
        // Is the name null?
        if (name == null) {

            // Ok, lets try with first and last name...
            name = firstName;

            if (lastName != null) {
                if (name == null) {
                    name = lastName;
                } else {
                    name += " " + lastName;
                }
            }

            // Try with email if still null
            if (name == null) {
                name = email;
            }

            // Try with username if still null
            if (name == null) {
                name = username;
            }

            // If still null set name to UNKNOWN
            if (name == null) {
                name = "UNKNOWN";
            }
        }
    }



    // TODO: figure out the dumb naming conventions and replace functionality
    public String getUserId() {
        return username;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getUser() {
        return user;
    }

    public String getName() {
        return name;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String toString() {
        return
            "name = " + name +
            ", firstName = " + firstName +
            ", lastName = " + lastName +
            ", email = " + email +
            ", username = " + username;
    }
}