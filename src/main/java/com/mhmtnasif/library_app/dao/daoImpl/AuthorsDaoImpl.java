package com.mhmtnasif.library_app.dao.daoImpl;

import com.mhmtnasif.library_app.dao.AuthorsDao;
import com.mhmtnasif.library_app.entities.Authors;
import com.mhmtnasif.library_app.util.JpaFactory;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class AuthorsDaoImpl implements AuthorsDao {

    public boolean addAuthor(Authors authors) {
        EntityManager entityManager= JpaFactory.getInstance().getEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(authors);
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

    public List<Authors> findAll() {
        EntityManager entityManager = JpaFactory.getInstance().getEntityManager();
        TypedQuery<Authors> authorsTypedQuery = entityManager.createNamedQuery("Authors.findAll", Authors.class);
        List<Authors> authors=authorsTypedQuery.getResultList();
        entityManager.close();
        JpaFactory.getInstance().CloseFactory();
        return authors;
    }

    public Authors findById(long id) {
        EntityManager entityManager = JpaFactory.getInstance().getEntityManager();
        Authors authors=entityManager.find(Authors.class,id);
        entityManager.close();
        JpaFactory.getInstance().CloseFactory();
        return authors;
    }
}
