package com.mhmtnasif.library_app.dao.daoImpl;

import com.mhmtnasif.library_app.dao.PublishersDao;
import com.mhmtnasif.library_app.entities.Publishers;
import com.mhmtnasif.library_app.util.JpaFactory;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class PublishersDaoImpl implements PublishersDao {

    public boolean addPublisher(Publishers publishers) {
        EntityManager entityManager= JpaFactory.getInstance().getEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(publishers);
            entityManager.flush();
            entityManager.getTransaction().commit();
            return true;
        }catch (Exception e){
            return false;
        }finally {
            entityManager.close();
            JpaFactory.getInstance().CloseFactory();
        }
    }

    public List<Publishers> findAll() {
        EntityManager entityManager = JpaFactory.getInstance().getEntityManager();
        TypedQuery<Publishers> publishersTypedQuery = entityManager.createNamedQuery("Publishers.findAll", Publishers.class);
        List<Publishers> publishers=publishersTypedQuery.getResultList();
        entityManager.close();
        JpaFactory.getInstance().CloseFactory();
        return publishers;
    }

    public Publishers findById(long id) {
        EntityManager entityManager = JpaFactory.getInstance().getEntityManager();
        Publishers publishers=entityManager.find(Publishers.class,id);
        entityManager.close();
        JpaFactory.getInstance().CloseFactory();
        return publishers;
    }
}
