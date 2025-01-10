package com.launchpad.test.dao.service;

import com.launchpad.test.entities.Service;

import java.util.List;

public interface ServiceDAO {
    void save(Service service);
    Service findById(String id);
    void update(Service service);
    Service findServiceWithVolumes(String id);
    List<Service> findByIds(List<String> ids);
}
