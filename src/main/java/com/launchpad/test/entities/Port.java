package com.launchpad.test.entities;

import jakarta.persistence.*;

@Entity
@Table(name="port")
public class Port {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @Column(name="id", insertable = true, updatable = false, unique = true, nullable = false)
    private String id;

    @Column(name="port_ip", nullable = false)
    private String portIp;

    @Column(name="private_port", nullable = false)
    private int privatePort;

    @Column(name="public_port", nullable = false)
    private int publicPort;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name="service_id", nullable = false)
    private Service service;

    public Port(){}

    public Port(String portIp, int privatePort, int publicPort) {
        this.portIp = portIp;
        this.privatePort = privatePort;
        this.publicPort = publicPort;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPortIp() {
        return portIp;
    }

    public void setPortIp(String portIp) {
        this.portIp = portIp;
    }

    public int getPrivatePort() {
        return privatePort;
    }

    public void setPrivatePort(int privatePort) {
        this.privatePort = privatePort;
    }

    public int getPublicPort() {
        return publicPort;
    }

    public void setPublicPort(int publicPort) {
        this.publicPort = publicPort;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }
}
