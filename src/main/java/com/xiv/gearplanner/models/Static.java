package com.xiv.gearplanner.models;


import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
public class Static {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @OneToOne
    private User owner;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @OneToMany
    private List<StaticMember> members = new ArrayList<>();

    public Static () {}

    public Static (String name, User owner) {
        this.name = name;
        this.owner = owner;
    }

    public Static(Static copy) {
        this.id = copy.id;
        this.name = copy.name;
        this.owner = copy.owner;
        this.createdAt = copy.createdAt;
        this.members = copy.members;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public List<StaticMember> getMembers() {
        return members;
    }

    public void setMembers(List<StaticMember> members) {
        this.members = members;
    }

    @Override
    public String toString() {
        return "Static{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", owner=" + owner +
                ", createdAt=" + createdAt +
                ", members=" + members +
                '}';
    }
}
