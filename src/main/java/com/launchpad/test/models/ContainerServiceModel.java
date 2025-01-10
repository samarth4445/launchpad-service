package com.launchpad.test.models;

import java.util.List;

public class ContainerServiceModel extends ServiceModel {
    private String status;
    private String volumeName;
    private String volumeSource;
    private String volumeDestination;
    private int privatePort;
    private int publicPort;

    public ContainerServiceModel(String serviceName, String serviceImage, String serviceDescription,
                                 String status, String volumeSource, String volumeDestination,
                                 int privatePort, int publicPort, List<String> env) {
        super(serviceName, serviceImage, serviceDescription, env);
        this.status = status;
        this.volumeSource = volumeSource;
        this.volumeDestination = volumeDestination;
        this.privatePort = privatePort;
        this.publicPort = publicPort;
    }

    public String getStatus() {
        return status;
    }

    public String getVolumeSource() {
        return volumeSource;
    }

    public String getVolumeDestination() {
        return volumeDestination;
    }

    public int getPrivatePort() {
        return privatePort;
    }

    public int getPublicPort() {
        return publicPort;
    }

    public String getVolumeName() {
        return volumeName;
    }

    public void setVolumeName(String volumeName) {
        this.volumeName = volumeName;
    }
}
