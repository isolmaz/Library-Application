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

    public boolean updatePublisher(Publishers publisher) {
        EntityManager entityManager = JpaFactory.getInstance().getEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(publisher);
            entityManager.getTransaction().commit();
            return true;
        } catch (Exception e) {
            return false;
        } finally {
            entityManager.close();
            JpaFactory.getInstance().CloseFactory();
        }
    }

    public boolean deletePublisher(Publishers publisher) {
        EntityManager entityManager = JpaFactory.getInstance().getEntityManager();
        try {
            entityManager.getTransaction().begin();
            if (!entityManager.contains(publisher)) {
                publisher = entityManager.merge(publisher);
            }
            entityManager.remove(publisher);
            entityManager.getTransaction().commit();
            return true;
        } catch (Exception e) {
            return false;
        } finally {
            entityManager.close();
            JpaFactory.getInstance().CloseFactory();
        }
    }

    public List<Publishers> findAll(String searchText) {
        EntityManager entityManager = JpaFactory.getInstance().getEntityManager();
        TypedQuery<Publishers> PublishersTypedQuery;
        List<Publishers> publishers=null;
        if (searchText != null) {
            PublishersTypedQuery = entityManager.createNamedQuery("Publishers.findAllBySearch", Publishers.class);
            PublishersTypedQuery.setParameter("param", searchText.toLowerCase());
            publishers = PublishersTypedQuery.getResultList();
        }else{
            PublishersTypedQuery = entityManager.createNamedQuery("Publishers.findAll", Publishers.class);
            publishers = PublishersTypedQuery.getResultList();
        }
        entityManager.close();
        JpaFactory.getInstance().CloseFactory();
        return publishers;
    }

    public List<Publishers> findByRange(int first, int max, String searchText) {
        EntityManager entityManager = JpaFactory.getInstance().getEntityManager();
        TypedQuery<Publishers> publishersTypedQuery;
        List<Publishers> publishers=null;
        if (searchText!=null) {
            publishersTypedQuery = entityManager.createNamedQuery("Publishers.findAllBySearch", Publishers.class);
            publishersTypedQuery.setParameter("param", searchText.toLowerCase());
            publishersTypedQuery.setFirstResult(first);
            publishersTypedQuery.setMaxResults(max);
            publishers = publishersTypedQuery.getResultList();
        }else{
            publishersTypedQuery = entityManager.createNamedQuery("Publishers.findAll", Publishers.class);
            publishersTypedQuery.setFirstResult(first);
            publishersTypedQuery.setMaxResults(max);
            publishers = publishersTypedQuery.getResultList();
        }

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
