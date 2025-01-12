package com.launchpad.test.dao.microservice;

import com.launchpad.test.entities.Microservice;
import com.launchpad.test.entities.Service;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MicroserviceDAOImpl implements MicroserviceDAO {
    private EntityManager entityManager;

    public MicroserviceDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public void save(Microservice microservice) {
        this.entityManager.persist(microservice);
    }

    @Override
    public List<Service> getAllServices(int microserviceId) {
        Microservice microservice = (Microservice) entityManager.createQuery("SELECT ms FROM Microservice ms LEFT JOIN FETCH ms.services WHERE ms.id=:microserviceId")
                                    .setParameter("microserviceId", microserviceId)
                                    .getSingleResult();
        return microservice.getServices();
    }

    @Transactional
    @Override
    public void update(Microservice microservice) {
        this.entityManager.merge(microservice);
    }
}
