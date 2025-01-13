package com.launchpad.test.factories;

import com.launchpad.test.adapters.ServiceDeploymentAdapter;
import com.launchpad.test.entities.Microservice;
import com.launchpad.test.enums.DeploymentServiceEnum;
import com.launchpad.test.services.microservice.MicroserviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class ServiceDeploymentDependencyFactory {
    private ApplicationContext applicationContext;

    @Autowired
    public ServiceDeploymentDependencyFactory(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public ServiceDeploymentAdapter getServiceDeploymentAdapter(DeploymentServiceEnum serviceType) {
        ServiceDeploymentAdapter serviceDeploymentAdapter = applicationContext.getBean(ServiceDeploymentAdapter.class);
        serviceDeploymentAdapter.setServiceType(serviceType);

        return serviceDeploymentAdapter;
    }

    public MicroserviceService getMicroserviceService(DeploymentServiceEnum serviceType, Microservice microservice) {
        MicroserviceService microserviceService = applicationContext.getBean(MicroserviceService.class);
        microserviceService.setService(serviceType, microservice);

        return microserviceService;
    }
}
