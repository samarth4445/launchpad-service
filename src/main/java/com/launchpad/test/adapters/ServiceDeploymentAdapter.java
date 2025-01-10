package com.launchpad.test.adapters;

import com.launchpad.test.dao.service.ServiceDAO;
import com.launchpad.test.entities.Service;
import com.launchpad.test.enums.DeploymentServiceEnum;
import com.launchpad.test.factories.DeploymentServiceFactory;
import com.launchpad.test.models.ServiceModel;
import com.launchpad.test.services.DeploymentService;
import com.launchpad.test.strategies.DockerServiceDeploymentAdapterStrategy;
import com.launchpad.test.strategies.ServiceDeploymentAdapterStrategy;
import org.springframework.context.ApplicationContext;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServiceDeploymentAdapter {
    private DeploymentService deploymentService;
    private DeploymentServiceEnum serviceType;
    private ServiceDAO serviceDAO;
    private Map<DeploymentServiceEnum, Class<? extends ServiceDeploymentAdapterStrategy>> strategies = new HashMap<>();
    private ServiceDeploymentAdapterStrategy strategy;

    public ServiceDeploymentAdapter(DeploymentServiceEnum serviceType,
                                    ApplicationContext applicationContext) {
        this.serviceType = serviceType;
        this.deploymentService = DeploymentServiceFactory.getDeploymentService(serviceType);
        this.serviceDAO = applicationContext.getBean(ServiceDAO.class);

        strategies.put(DeploymentServiceEnum.DOCKER, DockerServiceDeploymentAdapterStrategy.class);
        this.strategy = applicationContext.getBean(strategies.get(serviceType));
    }

    public Service createService(ServiceModel serviceModel) {
        return this.strategy.createService(serviceModel);
    }

    public void runService(String id) {
        deploymentService.runService(id);
    }

    public List<Service> listServices() throws URISyntaxException, IOException, InterruptedException {
        List<String> serviceIds = deploymentService.listServiceIds();
        return serviceDAO.findByIds(serviceIds);
    }
}
