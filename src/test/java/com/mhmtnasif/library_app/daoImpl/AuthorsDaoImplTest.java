package com.mhmtnasif.library_app.daoImpl;

import com.mhmtnasif.library_app.dao.AuthorsDao;
import com.mhmtnasif.library_app.entities.Authors;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class AuthorsDaoImplTest {

    AuthorsDao authorsDao;
    private int size;

    @Before
    public void setUp() {
        authorsDao = new AuthorsDaoImpl();
        size=authorsDao.findAll("").size();
    }
    @Test
    public void addAuthor() {
        Authors author = new Authors("example", "example");
        assertTrue("addAuthor method failed result of this method should be true", authorsDao.addAuthor(author));
    }

    @Test
    public void updateAuthor() {
        Authors author=authorsDao.findById(1);
        author.setAuthors_desc("updated");
        authorsDao.updateAuthor(author);
        Authors authorUpdated=authorsDao.findById(1);
        assertTrue("updateAuthor method failed Author name should  be 'updated' ", authorUpdated.getAuthors_desc().equals("updated"));
    }

    @Test
    public void deleteAuthor() {
        Authors author=new Authors("example","example");
        authorsDao.deleteAuthor(author);
        assertTrue("deleteAuthor method failed result of this method should  be true", authorsDao.deleteAuthor(author));
    }

    @Test
    public void findAll() {
        assertTrue("findAll method failed size of the list should  be "+size, authorsDao.findAll("").size()==size);
        assertTrue("findAll method failed size of the list with text(asd) should  be 0", authorsDao.findAll("asd").size()==0);
    }

    @Test
    public void findByRange() {
        assertTrue("findByRange(first:0,max:10) method failed size of the list should  be <=10", authorsDao.findByRange(0,10,"").size()<=10);
        assertTrue("findByRange(first:10,max:10) method failed size of the list should  be <=20", authorsDao.findByRange(10,10,"").size()<=20);
        assertTrue("findByRange(first:0,max:2) method failed size of the list should  be <=2", authorsDao.findByRange(0,2,"").size()<=2);
        assertTrue("findByRange(first:0,max:10,text:ex) method failed size of the list should  be <=10", authorsDao.findByRange(0,10,"ex").size()<=10);
        assertTrue("findByRange(first:10,max:10,text:ex) method failed size of the list should  be <=20", authorsDao.findByRange(10,10,"ex").size()<=20);
        assertTrue("findByRange(first:0,max:10,text:asd) method failed size of the list should  be 0", authorsDao.findByRange(0,10,"asd").size()==0);
    }

    @Test
    public void findById() {
        assertTrue("finById method failed. the Author which get with id(4) should has id==4", authorsDao.findById(1).getId()==1);
        assertTrue("findById method failed there isn't any Author which has id=100", authorsDao.findById(100)==null);
    }
}