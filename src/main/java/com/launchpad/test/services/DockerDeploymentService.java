package com.launchpad.test.services;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.model.*;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientConfig;
import com.github.dockerjava.core.DockerClientImpl;
import com.github.dockerjava.httpclient5.ApacheDockerHttpClient;
import com.github.dockerjava.transport.DockerHttpClient;
import com.launchpad.test.enums.DockerContainerStatusEnum;
import com.launchpad.test.models.ContainerServiceModel;
import com.launchpad.test.models.ServiceModel;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class DockerDeploymentService implements DeploymentService {
    private static DockerDeploymentService instance;
    private DockerClient dockerClient;

    private DockerDeploymentService() {
        DockerClientConfig config = DefaultDockerClientConfig.createDefaultConfigBuilder().build();
        DockerHttpClient httpClient = new ApacheDockerHttpClient.Builder()
                .dockerHost(config.getDockerHost())
                .sslConfig(config.getSSLConfig())
                .maxConnections(100)
                .connectionTimeout(Duration.ofSeconds(30))
                .responseTimeout(Duration.ofSeconds(45))
                .build();
        this.dockerClient = DockerClientImpl.getInstance(config, httpClient);
    }

    public static DeploymentService newDockerClientInstance(){
        if(instance == null){
            instance = new DockerDeploymentService();
        }

        return instance;
    }

    @Override
    public String createService(ServiceModel service) {
        if (!(service instanceof ContainerServiceModel containerService)) {
            throw new IllegalArgumentException("Invalid service model provided.");
        }

        CreateContainerResponse containerResponse = this.dockerClient.createContainerCmd(containerService.getServiceImage())
                .withHostConfig(HostConfig.newHostConfig()
                        .withBinds(new Bind(containerService.getVolumeSource(), new Volume(containerService.getVolumeDestination()), AccessMode.rw))
                        .withPortBindings(new PortBinding(
                                Ports.Binding.bindPort(containerService.getPrivatePort()),
                                ExposedPort.tcp(containerService.getPublicPort())
                        )))
                .withName(containerService.getServiceName())
                .withEnv(containerService.getEnv())
                .exec();
        return containerResponse.getId();
    }

    @Override
    public void runService(String id) {
        this.dockerClient.startContainerCmd(id).exec();
    }

    @Override
    public List<String> listServiceIds() throws URISyntaxException, IOException, InterruptedException {
        List<Container> containers = this.dockerClient.listContainersCmd().exec();
        ArrayList<String> services = new ArrayList<>();

        for(Container container: containers){
            services.add(container.getId());
        }

        return services;
    }

    @Override
    public String getServiceIdFromName(String name) {
        List<Container> containers = this.dockerClient.listContainersCmd()
                .withStatusFilter(new ArrayList<>(
                        Arrays.asList(DockerContainerStatusEnum.created.toString(),
                                DockerContainerStatusEnum.exited.toString(),
                                DockerContainerStatusEnum.running.toString())
                ))
                .withNameFilter(Collections.singleton(name))
                .exec();
        return containers.getFirst().getId();
    }

    @Override
    public void stopService(String id) {
        this.dockerClient.stopContainerCmd(id).exec();
    }

    @Override
    public void removeService(String id) {
        this.dockerClient.removeContainerCmd(id).exec();
    }
}
