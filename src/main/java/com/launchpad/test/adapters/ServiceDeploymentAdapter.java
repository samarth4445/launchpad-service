package com.launchpad.test.adapters;

import com.launchpad.test.dao.port.PortDAO;
import com.launchpad.test.dao.service.ServiceDAO;
import com.launchpad.test.dao.volume.VolumeDAO;
import com.launchpad.test.entities.Port;
import com.launchpad.test.entities.Service;
import com.launchpad.test.entities.Volume;
import com.launchpad.test.enums.DeploymentServiceEnum;
import com.launchpad.test.factories.DeploymentServiceFactory;
import com.launchpad.test.models.ContainerServiceModel;
import com.launchpad.test.models.ServiceModel;
import com.launchpad.test.services.DeploymentService;

public class ServiceDeploymentAdapter {
    private ServiceDAO serviceDAO;
    private PortDAO portDAO;
    private VolumeDAO volumeDAO;
    private DeploymentService deploymentService;
    private DeploymentServiceEnum serviceType;

    public ServiceDeploymentAdapter(ServiceDAO serviceDAO, PortDAO portDAO, VolumeDAO volumeDAO, DeploymentServiceEnum serviceType) {
        this.serviceDAO = serviceDAO;
        this.portDAO = portDAO;
        this.volumeDAO = volumeDAO;
        this.serviceType = serviceType;
        this.deploymentService = DeploymentServiceFactory.getDeploymentService(serviceType);
    }

    public Service createService(ServiceModel serviceModel) {
        serviceModel = deploymentService.createService(serviceModel);
        Service service = new Service(
                serviceModel.getServiceName(),
                serviceModel.getServiceImage(),
                serviceModel.getServiceDescription()
        );
        service.setId(serviceModel.getId());
        serviceDAO.save(service);

        if (serviceModel instanceof ContainerServiceModel containerServiceModel) {
            handlePorts(service, containerServiceModel);
            handleVolumes(service, containerServiceModel);
        } else {
            throw new IllegalArgumentException("Service type not supported: " + serviceModel.getClass().getSimpleName());
        }
        serviceDAO.update(service);
        return service;
    }

    public void runService(String id) {
        deploymentService.runService(id);
    }

    private void handlePorts(Service service, ContainerServiceModel containerServiceModel) {
        Port port = new Port(
                "localhost",
                containerServiceModel.getPrivatePort(),
                containerServiceModel.getPublicPort()
        );
        portDAO.save(port);
        service.addPort(port);
    }

    private void handleVolumes(Service service, ContainerServiceModel containerServiceModel) {
        Volume volume = new Volume(
                containerServiceModel.getVolumeSource(),
                containerServiceModel.getVolumeDestination()
        );
        volumeDAO.save(volume);
        service.addVolume(volume);
    }

}
