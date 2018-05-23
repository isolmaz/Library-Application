package com.mhmtnasif.library_app.daoImpl;

import com.mhmtnasif.library_app.dao.AuthorsDao;
import com.mhmtnasif.library_app.entities.Authors;
import com.mhmtnasif.library_app.util.JpaFactory;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class AuthorsDaoImpl implements AuthorsDao {

    public boolean addAuthor(Authors author) {
        EntityManager entityManager = JpaFactory.getInstance().getEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(author);
            entityManager.getTransaction().commit();
            return true;
        } catch (Exception e) {
            return false;
        } finally {
            entityManager.close();
            JpaFactory.getInstance().CloseFactory();
        }
    }

    public boolean updateAuthor(Authors author) {
        EntityManager entityManager = JpaFactory.getInstance().getEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(author);
            entityManager.getTransaction().commit();
            return true;
        } catch (Exception e) {
            return false;
        } finally {
            entityManager.close();
            JpaFactory.getInstance().CloseFactory();
        }
    }

    public boolean deleteAuthor(Authors author) {
        EntityManager entityManager = JpaFactory.getInstance().getEntityManager();
        try {
            entityManager.getTransaction().begin();
            if (!entityManager.contains(author)) {
                author = entityManager.merge(author);
            }
            entityManager.remove(author);
            entityManager.getTransaction().commit();
            return true;
        } catch (Exception e) {
            return false;
        } finally {
            entityManager.close();
            JpaFactory.getInstance().CloseFactory();
        }
    }

    public List<Authors> findAll(String searchText) {
        EntityManager entityManager = JpaFactory.getInstance().getEntityManager();
        TypedQuery<Authors> authorsTypedQuery;
        List<Authors> authors=null;
        if (searchText != null) {
            authorsTypedQuery = entityManager.createNamedQuery("Authors.findAllBySearch", Authors.class);
            authorsTypedQuery.setParameter("param", searchText.toLowerCase());
            authors = authorsTypedQuery.getResultList();
        }else{
            authorsTypedQuery = entityManager.createNamedQuery("Authors.findAll", Authors.class);
            authors = authorsTypedQuery.getResultList();
        }
        entityManager.close();
        JpaFactory.getInstance().CloseFactory();
        return authors;
    }

    public List<Authors> findByRange(int first, int max, String searchText) {
        EntityManager entityManager = JpaFactory.getInstance().getEntityManager();
        TypedQuery<Authors> authorsTypedQuery;
        List<Authors> authors=null;
        if (searchText!=null) {
            authorsTypedQuery = entityManager.createNamedQuery("Authors.findAllBySearch", Authors.class);
            authorsTypedQuery.setParameter("param", searchText.toLowerCase());
            authorsTypedQuery.setFirstResult(first);
            authorsTypedQuery.setMaxResults(max);
            authors = authorsTypedQuery.getResultList();
        }else{
            authorsTypedQuery = entityManager.createNamedQuery("Authors.findAll", Authors.class);
            authorsTypedQuery.setFirstResult(first);
            authorsTypedQuery.setMaxResults(max);
            authors = authorsTypedQuery.getResultList();
        }

        entityManager.close();
        JpaFactory.getInstance().CloseFactory();
        return authors;
    }

    public Authors findById(long id) {
        EntityManager entityManager = JpaFactory.getInstance().getEntityManager();
        Authors authors = entityManager.find(Authors.class, id);
        entityManager.close();
        JpaFactory.getInstance().CloseFactory();
        return authors;
    }

}
