package com.launchpad.test.services.deployment;

import com.launchpad.test.models.ServiceModel;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public interface DeploymentService {
    ServiceModel createService(ServiceModel service);
    void runService(String id);
    List<String> listServiceIds() throws URISyntaxException, IOException, InterruptedException;
    String getServiceIdFromName(String name);
    void stopService(String id);
    void removeService(String id);
}