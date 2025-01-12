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
import com.launchpad.test.services.microservice.MicroserviceService;
import com.launchpad.test.services.up.UpService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

@SpringBootApplication
public class TestApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx, MicroserviceDAO microserviceDAO) {
        return runner -> {
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

            MicroserviceService microserviceService = ctx.getBean(MicroserviceService.class);
            microserviceService.setService(DeploymentServiceEnum.DOCKER, microservice);
            Service service = microserviceService.addService(serviceModel);
            Service service2 = microserviceService.addService(serviceModel2);

            microserviceService.addServiceDependency(service, service2);
            microserviceService.serviceUp();
        };
    }

    public static List<String> readFileLines(String filePath) {
        try {
            return Files.readAllLines(Paths.get(filePath));
        } catch (IOException e) {
            System.err.println("An error occurred while reading the file: " + e.getMessage());
            return null;
        }
    }
}
