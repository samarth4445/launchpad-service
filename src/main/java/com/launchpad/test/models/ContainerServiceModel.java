package com.launchpad.test.models;

public class ContainerServiceModel extends ServiceModel {
    private String status;
    private String volumeSource;
    private String volumeDestination;
    private int privatePort;
    private int publicPort;

    public ContainerServiceModel(String serviceName, String serviceImage, String serviceDescription,
                                 String status, String volumeSource, String volumeDestination,
                                 int privatePort, int publicPort) {
        super(serviceName, serviceImage, serviceDescription);
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
}
