package com.mhmtnasif.library_app.dao.daoImpl;

import com.mhmtnasif.library_app.dao.BooksDao;
import com.mhmtnasif.library_app.entities.Books;
import com.mhmtnasif.library_app.util.JpaFactory;

import javax.persistence.EntityManager;

public class BooksDaoImpl implements BooksDao {

    public boolean addBook(Books books) {
        EntityManager entityManager= JpaFactory.getInstance().getEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(books);
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
