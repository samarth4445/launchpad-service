package com.launchpad.test.strategies;

import com.launchpad.test.dao.port.PortDAO;
import com.launchpad.test.dao.service.ServiceDAO;
import com.launchpad.test.dao.volume.VolumeDAO;
import com.launchpad.test.entities.Microservice;
import com.launchpad.test.entities.Port;
import com.launchpad.test.entities.Service;
import com.launchpad.test.entities.Volume;
import com.launchpad.test.enums.DeploymentServiceEnum;
import com.launchpad.test.factories.DeploymentServiceFactory;
import com.launchpad.test.models.ContainerServiceModel;
import com.launchpad.test.models.ServiceModel;
import com.launchpad.test.services.deployment.DeploymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DockerServiceDeploymentAdapterStrategy implements ServiceDeploymentAdapterStrategy{
    private DeploymentService deploymentService;
    private ServiceDAO serviceDAO;
    private PortDAO portDAO;
    private VolumeDAO volumeDAO;

    @Autowired
    public DockerServiceDeploymentAdapterStrategy(ServiceDAO serviceDAO, PortDAO portDAO, VolumeDAO volumeDAO) {
        this.serviceDAO = serviceDAO;
        this.portDAO = portDAO;
        this.volumeDAO = volumeDAO;
        this.deploymentService = DeploymentServiceFactory.getDeploymentService(DeploymentServiceEnum.DOCKER);
    }

    @Override
    public Service createService(ServiceModel serviceModel) {
        if (!(serviceModel instanceof ContainerServiceModel containerServiceModel)) {
            throw new IllegalArgumentException("ServiceModel must be a ContainerServiceModel");
        }
        serviceModel = deploymentService.createService(serviceModel);
        Service service = new Service(
                serviceModel.getServiceName(),
                serviceModel.getServiceImage(),
                serviceModel.getServiceDescription()
        );
        service.setId(serviceModel.getId());
        serviceDAO.save(service);

        handlePorts(service, containerServiceModel);
        handleVolumes(service, containerServiceModel);
        return service;
    }

    private void handlePorts(Service service, ContainerServiceModel containerServiceModel) {
        Port port = new Port(
                "localhost",
                containerServiceModel.getPrivatePort(),
                containerServiceModel.getPublicPort()
        );
        port.setId(containerServiceModel.getId());
        port.setService(service);
        portDAO.save(port);
        service.addPort(port);
        serviceDAO.update(service);
    }

    private void handleVolumes(Service service, ContainerServiceModel containerServiceModel) {
        Volume volume = new Volume(containerServiceModel.getVolumeName(),
                containerServiceModel.getVolumeSource(),
                containerServiceModel.getVolumeDestination()
        );
        volume.setService(service);
        volumeDAO.save(volume);
        service.addVolume(volume);
        serviceDAO.update(service);
    }

}
