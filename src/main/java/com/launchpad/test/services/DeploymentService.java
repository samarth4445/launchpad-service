package com.launchpad.test.services;

import com.launchpad.test.entities.Port;
import com.launchpad.test.entities.Service;
import com.launchpad.test.entities.Volume;
import com.launchpad.test.models.ServiceModel;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public interface DeploymentService {
    String createService(ServiceModel service) throws URISyntaxException, IOException;
    void runService(String id);
    List<String> listServiceIds() throws URISyntaxException, IOException, InterruptedException;
    String getServiceIdFromName(String name);
    void stopService(String id);
    void removeService(String id);
}