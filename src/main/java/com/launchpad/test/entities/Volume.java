package com.launchpad.test.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UuidGenerator;

@Entity
@Table(name="volume")
public class Volume {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name="volume_name", updatable = false, nullable = false, unique = true)
    private String volumeName;

    @Column(name="volume_source", nullable = false)
    private String volumeSource;

    @Column(name="volume_destination", nullable = false)
    private String volumeDestination;

    @ManyToOne
    @JoinColumn(name="service_id", nullable = false)
    private Service service;

    public Volume(){}

    public Volume(String volumeSource, String volumeDestination) {
        this.volumeName = volumeName;
        this.volumeSource = volumeSource;
        this.volumeDestination = volumeDestination;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public String getVolumeDestination() {
        return volumeDestination;
    }

    public void setVolumeDestination(String volumeDestination) {
        this.volumeDestination = volumeDestination;
    }

    public String getVolumeSource() {
        return volumeSource;
    }

    public void setVolumeSource(String volumeSource) {
        this.volumeSource = volumeSource;
    }

    public String getVolumeName() {
        return volumeName;
    }

    public void setVolumeName(String volumeName) {
        this.volumeName = volumeName;
    }
}
