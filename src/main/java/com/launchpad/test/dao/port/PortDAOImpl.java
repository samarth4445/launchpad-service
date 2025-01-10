package com.launchpad.test.dao.port;

import com.launchpad.test.entities.Port;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

@Repository
public class PortDAOImpl implements PortDAO {

    private EntityManager entityManager;

    public PortDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public void save(Port port) {
        entityManager.persist(port);
    }

    @Override
    public Port findById(String name) {
        return entityManager.find(Port.class, name);
    }
}
