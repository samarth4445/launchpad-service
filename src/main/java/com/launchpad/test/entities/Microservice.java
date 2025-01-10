package com.launchpad.test.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="microservice")
public class Microservice {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id", nullable = false)
    private int id;

    @Column(name="name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "microservice", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Service> services;
}
