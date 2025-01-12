package com.launchpad.test.dao.service;

import com.launchpad.test.entities.Service;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ServiceDAOImpl implements ServiceDAO {

    private EntityManager entityManager;

    public ServiceDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public void save(Service service) {
        entityManager.persist(service);
    }

    @Override
    public Service findById(String Id){
        return entityManager.find(Service.class, Id);
    }

    @Override
    @Transactional
    public void update(Service service) {
        entityManager.merge(service);
    }

    @Override
    public Service findServiceWithVolumes(String id) {
        return entityManager.createQuery("SELECT s FROM Service s LEFT JOIN FETCH s.volumes WHERE s.id=:id", Service.class)
                .setParameter("id", id)
                .getSingleResult();

    }

    @Override
    public List<Service> findByIds(List<String> ids) {
        return entityManager.createQuery("SELECT s FROM Service s WHERE s.id IN :ids", Service.class)
                .setParameter("ids", ids)
                .getResultList();
    }

    @Override
    public void delete(Service service) {
        entityManager.remove(service);
    }
}
