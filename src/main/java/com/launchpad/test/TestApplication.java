package com.launchpad.test;

import com.launchpad.test.adapters.ServiceDeploymentAdapter;
import com.launchpad.test.builders.ContainerServiceBuilder;
import com.launchpad.test.dao.microservice.MicroserviceDAO;
import com.launchpad.test.entities.Microservice;
import com.launchpad.test.entities.Service;
import com.launchpad.test.entities.Volume;
import com.launchpad.test.enums.DeploymentServiceEnum;
import com.launchpad.test.factories.ServiceDeploymentAdapterFactory;
import com.launchpad.test.models.ServiceModel;
import com.launchpad.test.services.up.UpService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.*;

@SpringBootApplication
public class TestApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx, MicroserviceDAO microserviceDAO) {
        return runner -> {
            ServiceDeploymentAdapterFactory factory = new ServiceDeploymentAdapterFactory(ctx);
            ServiceDeploymentAdapter adapter = factory.getServiceDeploymentAdapter(DeploymentServiceEnum.DOCKER);
            ContainerServiceBuilder builder = new ContainerServiceBuilder();
            Microservice microservice = new Microservice("myMicroservice");
            microserviceDAO.save(microservice);

            ServiceModel serviceModel = builder.setServiceName("django-app")
                    .setServiceImage("ubuntu")
                    .setServiceDescription("ubuntu-linux-x86_64")
                    .setPrivatePort(8080)
                    .setPublicPort(8080)
                    .setVolumeSource("/home/samarth/")
                    .setVolumeDestination("/home/ubuntu/codes")
                    .setStatus("")
                    .setEnv(List.of("BUDDY=LOL"))
                    .build();

            ServiceModel serviceModel2 = builder.setServiceName("django-app-2")
                    .setServiceImage("ubuntu")
                    .setServiceDescription("ubuntu-linux-x86_64")
                    .setPrivatePort(8080)
                    .setPublicPort(8080)
                    .setVolumeSource("/home/samarth/")
                    .setVolumeDestination("/home/ubuntu/codes")
                    .setStatus("")
                    .setEnv(List.of("BUDDY=LOL"))
                    .build();
            Service service = adapter.createService(serviceModel2, microservice);
            Service service2 = adapter.createService(serviceModel, microservice);

            service.addDependency(service2);

            UpService up = ctx.getBean(UpService.class);
            up.setServiceType(DeploymentServiceEnum.DOCKER);

            TreeMap<Service, List<Service>> dependencies = new TreeMap<>();
            dependencies.put(service, service.getDependencies());
            dependencies.put(service2, service2.getDependencies());

            for (Map.Entry<Service, List<Service>> entry : dependencies.entrySet()) {
                System.out.print(entry.getKey() + ": ");
                for (Service dependency : entry.getValue()) {
                    System.out.println(dependency);
                }
            }

            up.runServices(dependencies);
        };
    }
}
