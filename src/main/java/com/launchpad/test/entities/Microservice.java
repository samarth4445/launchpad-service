package com.launchpad.test.entities;

import jakarta.persistence.*;

@Entity
@Table(name="microservice")
public class Microservice {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id", nullable = false)
    private int id;
}
