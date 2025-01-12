package com.launchpad.test.adapters;

import com.launchpad.test.dao.service.ServiceDAO;
import com.launchpad.test.entities.Microservice;
import com.launchpad.test.entities.Service;
import com.launchpad.test.enums.DeploymentServiceEnum;
import com.launchpad.test.factories.DeploymentServiceFactory;
import com.launchpad.test.models.ServiceModel;
import com.launchpad.test.services.deployment.DeploymentService;
import com.launchpad.test.strategies.DockerServiceDeploymentAdapterStrategy;
import com.launchpad.test.strategies.ServiceDeploymentAdapterStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@org.springframework.stereotype.Service
public class ServiceDeploymentAdapter {
    private DeploymentService deploymentService;
    private DeploymentServiceEnum serviceType;
    private ServiceDAO serviceDAO;
    private Map<DeploymentServiceEnum, Class<? extends ServiceDeploymentAdapterStrategy>> strategies = new HashMap<>();
    private ServiceDeploymentAdapterStrategy strategy;
    private ApplicationContext applicationContext;

    @Autowired
    public ServiceDeploymentAdapter(ApplicationContext applicationContext, ServiceDAO serviceDAO) {
        this.applicationContext = applicationContext;
        this.serviceDAO = serviceDAO;
        strategies.put(DeploymentServiceEnum.DOCKER, DockerServiceDeploymentAdapterStrategy.class);
    }

    public void setServiceType(DeploymentServiceEnum serviceType) {
        this.serviceType = serviceType;
        this.deploymentService = DeploymentServiceFactory.getDeploymentService(serviceType);
        this.strategy = applicationContext.getBean(strategies.get(serviceType));
    }

    public Service createService(ServiceModel serviceModel, Microservice microservice) {
        return this.strategy.createService(serviceModel, microservice);
    }

    public void runService(String id) {
        deploymentService.runService(id);
    }

    public List<Service> listServices() throws URISyntaxException, IOException, InterruptedException {
        List<String> serviceIds = deploymentService.listServiceIds();
        return serviceDAO.findByIds(serviceIds);
    }

    public Service getServiceFromId(String id) {
        return serviceDAO.findById(id);
    }

    public String getServiceIdFromName(String name) {
        return deploymentService.getServiceIdFromName(name);
    }

    public void stopService(String id) {
        deploymentService.stopService(id);
    }

    public void removeService(String id) {
        deploymentService.removeService(id);
    }
}
