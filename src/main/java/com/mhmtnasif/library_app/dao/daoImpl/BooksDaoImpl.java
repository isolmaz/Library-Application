package com.mhmtnasif.library_app.dao.daoImpl;

import com.mhmtnasif.library_app.dao.BooksDao;
import com.mhmtnasif.library_app.entities.Authors;
import com.mhmtnasif.library_app.entities.Books;
import com.mhmtnasif.library_app.entities.Publishers;
import com.mhmtnasif.library_app.util.JpaFactory;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class BooksDaoImpl implements BooksDao {

    public boolean addBook(Books books) {
        EntityManager entityManager = JpaFactory.getInstance().getEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(books);
            entityManager.getTransaction().commit();
            return true;
        } catch (Exception e) {
            return false;
        } finally {
            entityManager.close();
            JpaFactory.getInstance().CloseFactory();
        }
    }

    public List<Books> findByAuthorId(Authors author) {
        EntityManager entityManager = JpaFactory.getInstance().getEntityManager();
        TypedQuery<Books> booksTypedQuery = entityManager.createNamedQuery("Books.findByAuthorId", Books.class);
        booksTypedQuery.setParameter("param", author.getId());
        List<Books> booksList = booksTypedQuery.getResultList();
        entityManager.close();
        JpaFactory.getInstance().CloseFactory();
        return booksList;
    }

    public List<Books> findPublisherId(Publishers publisher) {
        EntityManager entityManager = JpaFactory.getInstance().getEntityManager();
        TypedQuery<Books> booksTypedQuery = entityManager.createNamedQuery("Books.findByPublisherId", Books.class);
        booksTypedQuery.setParameter("param", publisher.getId());
        List<Books> booksList = booksTypedQuery.getResultList();
        entityManager.close();
        JpaFactory.getInstance().CloseFactory();
        return booksList;
    }

    public boolean deleteBooks(List<Books> booksList) {
        EntityManager entityManager = JpaFactory.getInstance().getEntityManager();
        try {
            if (booksList.size() != 0) {
                entityManager.getTransaction().begin();
                for (Books book : booksList) {
                    if (!entityManager.contains(book)) {
                        book = entityManager.merge(book);
                    }
                    entityManager.remove(book);
                }
                entityManager.getTransaction().commit();
            }
            return true;
        } catch (Exception e) {
            return false;
        } finally {
            entityManager.close();
            JpaFactory.getInstance().CloseFactory();
        }
    }
}

