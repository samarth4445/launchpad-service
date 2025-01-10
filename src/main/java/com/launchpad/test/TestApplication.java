package com.launchpad.test;

import com.launchpad.test.adapters.ServiceDeploymentAdapter;
import com.launchpad.test.builders.ContainerServiceBuilder;
import com.launchpad.test.dao.service.ServiceDAO;
import com.launchpad.test.dao.volume.VolumeDAO;
import com.launchpad.test.entities.Service;
import com.launchpad.test.entities.Volume;
import com.launchpad.test.enums.DeploymentServiceEnum;
import com.launchpad.test.models.ServiceModel;
import com.launchpad.test.strategies.ServiceDeploymentAdapterStrategy;
import jakarta.ws.rs.core.Application;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class TestApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return runner -> {
            ServiceDeploymentAdapter adapter = new ServiceDeploymentAdapter(DeploymentServiceEnum.DOCKER,
                                                                            ctx);
            ContainerServiceBuilder builder = new ContainerServiceBuilder();
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

            Service service = adapter.createService(serviceModel);
            System.out.println(service.getId());
            System.out.println(adapter.listServices());
        };
    }
}
