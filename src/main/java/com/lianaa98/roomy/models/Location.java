package com.lianaa98.roomy.models;

import jakarta.persistence.*;

@Entity
@Table(name = "locations")
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(name = "name", nullable = false)
    public String name;

    @ManyToOne
    @JoinColumn(name = "space_id", nullable = false)
    public Space space;
}
