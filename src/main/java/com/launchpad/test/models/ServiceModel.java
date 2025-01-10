package com.launchpad.test.models;

import java.util.List;

public class ServiceModel {
    private String id;
    private String serviceName;
    private String serviceImage;
    private String serviceDescription;
    private List<String> env;

    public ServiceModel(String serviceName, String serviceImage, String serviceDescription, List<String> env) {
        this.serviceName = serviceName;
        this.serviceImage = serviceImage;
        this.serviceDescription = serviceDescription;
        this.env = env;
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

    public List<String> getEnv() {
        return env;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
