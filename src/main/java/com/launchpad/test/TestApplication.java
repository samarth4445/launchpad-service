package com.launchpad.test;

import com.launchpad.test.builders.ContainerServiceBuilder;
import com.launchpad.test.dao.microservice.MicroserviceDAO;
import com.launchpad.test.entities.Microservice;
import com.launchpad.test.entities.Service;
import com.launchpad.test.enums.DeploymentServiceEnum;
import com.launchpad.test.factories.ServiceDeploymentDependencyFactory;
import com.launchpad.test.models.ServiceModel;
import com.launchpad.test.services.microservice.MicroserviceService;
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
