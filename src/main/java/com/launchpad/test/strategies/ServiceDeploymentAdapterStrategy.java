package com.launchpad.test.strategies;

import com.launchpad.test.entities.Microservice;
import com.launchpad.test.entities.Service;
import com.launchpad.test.models.ServiceModel;

public interface ServiceDeploymentAdapterStrategy{
    Service createService(ServiceModel serviceModel);
}
