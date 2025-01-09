package com.launchpad.test.dao.volume;

import com.launchpad.test.entities.Volume;

public interface VolumeDAO {
    void save(Volume volume);
    Volume findByName(String name);
}
