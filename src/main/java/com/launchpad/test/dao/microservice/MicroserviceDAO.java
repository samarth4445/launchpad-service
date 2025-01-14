package com.launchpad.test.dao.microservice;

import com.launchpad.test.entities.Microservice;
import com.launchpad.test.entities.Service;
import jakarta.transaction.Transactional;

import java.util.List;

public interface MicroserviceDAO {
    void save(Microservice microservice);
    void update(Microservice microservice);
    List<Service> getAllServices(int microservice);
    Microservice getMicroserviceByName(String name);
}
