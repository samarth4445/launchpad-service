package com.launchpad.test.entities;

import jakarta.persistence.*;

import java.util.ArrayList;
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

    public Microservice() {}

    public Microservice(String name) {
        this.name = name;
        this.services = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Service> getServices() {
        return services;
    }

    public void addService(Service services) {
        this.services.add(services);
    }
}
