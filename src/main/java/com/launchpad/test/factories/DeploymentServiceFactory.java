package com.launchpad.test.factories;

import com.launchpad.test.services.deployment.DeploymentService;
import com.launchpad.test.services.deployment.DockerDeploymentService;
import com.launchpad.test.enums.DeploymentServiceEnum;

public class DeploymentServiceFactory {
    public static DeploymentService getDeploymentService(DeploymentServiceEnum serviceType){
        return switch (serviceType) {
            case DOCKER -> DockerDeploymentService.newDockerClientInstance();
            case ECS, ON_PREMISES ->
                    throw new IllegalArgumentException("Service not supported yet.");
            default -> throw new IllegalArgumentException("Unknown service type provided.");
        };
    }
}
