package com.launchpad.test.dao.service;

import com.launchpad.test.entities.Service;

public interface ServiceDAO {
    void save(Service service);
    Service findById(String id);
    void update(Service service);
    Service findServiceWithVolumes(String id);
}
