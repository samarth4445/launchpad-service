package com.launchpad.test.models;

public class ServiceModel {
    private String serviceName;
    private String serviceImage;
    private String serviceDescription;

    public ServiceModel(String serviceName, String serviceImage, String serviceDescription) {
        this.serviceName = serviceName;
        this.serviceImage = serviceImage;
        this.serviceDescription = serviceDescription;
    }

    public String getServiceName() {
        return serviceName;
    }

    public String getServiceImage() {
        return serviceImage;
    }

    public String getServiceDescription() {
        return serviceDescription;
    }
}
