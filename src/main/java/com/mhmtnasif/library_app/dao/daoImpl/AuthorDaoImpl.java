package com.mhmtnasif.library_app.dao.daoImpl;

import com.mhmtnasif.library_app.dao.AuthorDao;
import com.mhmtnasif.library_app.entities.Authors;
import com.mhmtnasif.library_app.util.JpaFactory;

import javax.persistence.EntityManager;

public class AuthorDaoImpl implements AuthorDao {

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
}
