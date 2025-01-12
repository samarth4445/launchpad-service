package com.launchpad.test.dao.microservice;

import com.launchpad.test.entities.Microservice;
import com.launchpad.test.entities.Service;

import java.util.List;

public interface MicroserviceDAO {
    void save(Microservice microservice);
    List<Service> getAllServices(String microserviceId);
}
