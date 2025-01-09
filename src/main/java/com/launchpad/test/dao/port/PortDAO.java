package com.launchpad.test.dao.port;

import com.launchpad.test.entities.Port;
import com.launchpad.test.entities.Volume;

public interface PortDAO {
    void save(Port port);
    Port findById(String id);
}
