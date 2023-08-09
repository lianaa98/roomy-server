package com.lianaa98.roomy.models;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "statuses")
public class Status {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(name = "name", nullable = false)
    public String name;

    @Column(name = "created_at", nullable = false)
    public Date createdAt;

    @Column(name = "updated_at", nullable = false)
    public Date updatedAt;
}
