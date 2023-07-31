package com.lianaa98.roomy.models;

import jakarta.persistence.*;

@Entity
@Table(name = "user_spaces")
public class UserSpace {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    public User user;

    @ManyToOne
    @JoinColumn(name = "space_id", nullable = false)
    public Space space;
}
