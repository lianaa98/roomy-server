package com.lianaa98.roomy.models;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "owned_items")
public class OwnedItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(name = "name", nullable = false)
    public String name;

    @Column(name = "brand")
    public String brand;

    @Column(name = "description")
    public String description;

    @Column(name = "quantity")
    public Long quantity;

    @Column(name = "created_at", nullable = false)
    public Date createdAt;

    @Column(name = "updated_at", nullable = false)
    public Date updatedAt;

    @Column(name = "purchased_at")
    public Date purchasedAt;

    @Column(name = "status")
    public String status;

    @ManyToOne
    @JoinColumn(name = "location_id", nullable = false)
    public Location location;

    @ManyToOne
    @JoinColumn(name = "created_by", nullable = false)
    public User user;
}
