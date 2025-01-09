package com.launchpad.test.dao.volume;

import com.launchpad.test.entities.Volume;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

@Repository
public class VolumeDAOImpl implements VolumeDAO {

    private EntityManager entityManager;

    public VolumeDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public void save(Volume volume) {
        entityManager.persist(volume);
    }

    @Override
    public Volume findByName(String name) {
        return entityManager.find(Volume.class, name);
    }
}
