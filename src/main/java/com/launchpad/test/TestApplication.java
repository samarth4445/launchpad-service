package com.launchpad.test;

import com.launchpad.test.dao.service.ServiceDAO;
import com.launchpad.test.dao.volume.VolumeDAO;
import com.launchpad.test.entities.Service;
import com.launchpad.test.entities.Volume;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class TestApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(ServiceDAO serviceDAO, VolumeDAO volumeDAO) {
        return runner -> {
            createService(serviceDAO, volumeDAO);
            findService(serviceDAO);
        };
    }

    private void createService(ServiceDAO serviceDAO, VolumeDAO volumeDAO) {
        Service service = new Service("django-app", "ubuntu", "ubuntu-linux-x86_64");
        service.setId("some-id-by-docker-13");
        serviceDAO.save(service);
        Volume volume = new Volume("/home/samarth/",
                               "/home/ubuntu/codes");
        volume.setService(service);
        volumeDAO.save(volume);
        service.addVolume(volume);
        serviceDAO.update(service);
    }

    private void findService(ServiceDAO serviceDAO) {
        Service service = serviceDAO.findServiceWithVolumes("some-id-by-docker-13");
        System.out.println(service.getVolumes().get(0).getVolumeDestination());
    }
}
