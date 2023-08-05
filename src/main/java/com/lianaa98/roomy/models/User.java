package com.lianaa98.roomy.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(name = "first_name", nullable = false)
    public String firstName;

    @Column(name = "last_name", nullable = false)
    public String lastName;

    @Column(name = "email", nullable = false)
    public String email;

    @Column(name = "username", nullable = false)
    public String username;

    @Column(name = "password_hash", nullable = false)
    public String passwordHash;

    @ManyToMany
    @JoinTable(
            name = "user_spaces",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "space_id")
    )
    @JsonIgnoreProperties("users")
    public List<Space> spaces;

    public Long getId() {
        return id;
    }
}
