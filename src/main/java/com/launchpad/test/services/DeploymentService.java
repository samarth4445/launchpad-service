package com.launchpad.test.services;

import com.launchpad.test.entities.Service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public interface DeploymentService {
    String createService(Service service);
    void runService(String id);
    List<Service> listServices() throws URISyntaxException, IOException, InterruptedException;
    Service getServiceFromId(String id);
    String getServiceIdFromName(String name);
    void stopService(String id);
    void removeService(String id);
}