package com.launchpad.test.services.microservice;

import com.launchpad.test.adapters.ServiceDeploymentAdapter;
import com.launchpad.test.dao.microservice.MicroserviceDAO;
import com.launchpad.test.dao.service.ServiceDAO;
import com.launchpad.test.entities.Microservice;
import com.launchpad.test.entities.Service;
import com.launchpad.test.enums.DeploymentServiceEnum;
import com.launchpad.test.factories.ServiceDeploymentAdapterFactory;
import com.launchpad.test.models.ServiceModel;
import com.launchpad.test.services.up.UpService;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

@Component
public class MicroserviceService {
    private Microservice microservice;
    private ServiceDeploymentAdapter deploymentAdapter;
    private TreeMap<Service, List<Service>> serviceDependencyGraph;
    private DeploymentServiceEnum serviceType;
    private ApplicationContext applicationContext;
    private ServiceDAO serviceDAO;
    private MicroserviceDAO microserviceDAO;

    public MicroserviceService(ApplicationContext applicationContext, ServiceDAO serviceDAO, MicroserviceDAO microserviceDAO) {
        this.applicationContext = applicationContext;
        this.serviceDAO = serviceDAO;
        this.microserviceDAO = microserviceDAO;
        this.serviceDependencyGraph = new TreeMap<>();
    }

    public void setService(DeploymentServiceEnum serviceType, Microservice microservice) {
        this.serviceType = serviceType;
        ServiceDeploymentAdapterFactory factory = new ServiceDeploymentAdapterFactory(applicationContext);
        this.deploymentAdapter = factory.getServiceDeploymentAdapter(this.serviceType);
        this.microservice = microservice;
    }

    public Service addService(ServiceModel serviceModel){
        Service service = deploymentAdapter.createService(serviceModel);
        service.setMicroservice(microservice);
        serviceDAO.update(service);
        microservice.addService(service);
        microserviceDAO.update(microservice);
        serviceDependencyGraph.putIfAbsent(service, new ArrayList<>());

        return service;
    }

    private boolean doesServiceHasDependency(Service serviceId){
        if(!serviceDependencyGraph.get(serviceId).isEmpty()){
            return true;
        }

        for(Service dependency: serviceDependencyGraph.keySet()){
            if(serviceDependencyGraph.get(dependency).contains(serviceId)){
                return true;
            }
        }

        return false;
    }

    public void removeService(Service service){
        if(!this.doesServiceHasDependency(service)){
            throw new IllegalArgumentException("Service has dependencies.");
        }
        serviceDependencyGraph.remove(service);
        serviceDAO.delete(service);
    }

    public void addServiceDependency(Service dependentService, Service requiredService) {
        dependentService.addDependency(requiredService);
        serviceDAO.update(dependentService);
        serviceDependencyGraph.get(dependentService).add(requiredService);
    }

    public void removeServiceDependency(Service dependentService, Service requiredService) {
        serviceDependencyGraph.get(dependentService).remove(requiredService);
        dependentService.removeDependency(requiredService);
        serviceDAO.update(dependentService);
    }

    public void serviceUp(){
        UpService upService = applicationContext.getBean(UpService.class);
        upService.setServiceType(this.serviceType);

        upService.runServices(serviceDependencyGraph);
    }

    public void serviceDown(){
        List<Service> services = microserviceDAO.getAllServices(this.microservice.getId());
        for(Service service: services){
            deploymentAdapter.stopService(service.getId());
        }
    }

    public String getName(){
        return microservice.getName();
    }

    public List<Service> getServices(){
        return microserviceDAO.getAllServices(microservice.getId());
    }
}

