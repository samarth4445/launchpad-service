package com.launchpad.test.builder;

import com.launchpad.test.models.ContainerServiceModel;

public class ContainerServiceBuilder {
    private String serviceName;
    private String serviceImage;
    private String serviceDescription;
    private String status;
    private String volumeSource;
    private String volumeDestination;
    private int privatePort;
    private int publicPort;

    public ContainerServiceBuilder setServiceName(String serviceName) {
        this.serviceName = serviceName;
        return this;
    }

    public ContainerServiceBuilder setServiceImage(String serviceImage) {
        this.serviceImage = serviceImage;
        return this;
    }

    public ContainerServiceBuilder setServiceDescription(String serviceDescription) {
        this.serviceDescription = serviceDescription;
        return this;
    }

    public ContainerServiceBuilder setStatus(String status) {
        this.status = status;
        return this;
    }

    public ContainerServiceBuilder setVolumeSource(String volumeSource) {
        this.volumeSource = volumeSource;
        return this;
    }

    public ContainerServiceBuilder setVolumeDestination(String volumeDestination) {
        this.volumeDestination = volumeDestination;
        return this;
    }

    public ContainerServiceBuilder setPrivatePort(int privatePort) {
        this.privatePort = privatePort;
        return this;
    }

    public ContainerServiceBuilder setPublicPort(int publicPort) {
        this.publicPort = publicPort;
        return this;
    }

    ContainerServiceModel build(){
        return new ContainerServiceModel(serviceName, serviceImage, serviceDescription,
                                         status, volumeSource, volumeDestination,
                                         privatePort, publicPort);
    }
}
