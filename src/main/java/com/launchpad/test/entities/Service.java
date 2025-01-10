package com.launchpad.test.entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="service")
public class Service {
    @Id
    @Column(name="id", insertable = true, updatable = false, unique = true, nullable = false)
    private String id;

    @Column(name="service_name", nullable = false)
    private String serviceName;

    @Column(name="service_image", nullable = false)
    private String serviceImage;

    @Column(name="description", nullable = true)
    private String description;

    @Column(name="status", nullable = true)
    private String status;

    @OneToMany(mappedBy = "service", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Volume> volumes;

    @OneToMany(mappedBy = "service", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Port> ports;

    public Service(){}

    public Service(String serviceName, String serviceImage, String description) {
        this.serviceName = serviceName;
        this.serviceImage = serviceImage;
        this.description = description;
        this.ports = new ArrayList<>();
        this.volumes = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceImage() {
        return serviceImage;
    }

    public void setServiceImage(String serviceImage) {
        this.serviceImage = serviceImage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Volume> getVolumes() {
        return this.volumes;
    }

    public void addVolume(Volume volume) {
        this.volumes.add(volume);
    }

    public List<Port> getPorts() {
        return ports;
    }

    public void addPort(Port port) {
        this.ports.add(port);
    }
}
