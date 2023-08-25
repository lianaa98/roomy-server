package com.lianaa98.roomy.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "spaces")
public class Space {

    public Space() {}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(name = "name", nullable = false)
    public String name;

    @Column(name = "created_at", nullable = false)
    public Date createdAt;

    @Column(name = "updated_at", nullable = false)
    public Date updatedAt;

    @ManyToMany(mappedBy = "spaces")
    @JsonIgnoreProperties("spaces")
    public List<User> users;

}
