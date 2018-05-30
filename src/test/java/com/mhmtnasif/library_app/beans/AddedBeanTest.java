package com.mhmtnasif.library_app.beans;


import com.mhmtnasif.library_app.dao.BooksDao;
import com.mhmtnasif.library_app.dao.UsersDao;
import com.mhmtnasif.library_app.daoImpl.BooksDaoImpl;
import com.mhmtnasif.library_app.daoImpl.UserDaoImpl;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertTrue;


public class AddedBeanTest {

    AddedBean addedBean;
    BooksDao booksDao;
    @Before
    public void initial() {
        addedBean = new AddedBean();
        booksDao=new BooksDaoImpl();
    }
    
    @Test
    public void show() {
        addedBean.setBooksPopModel(booksDao.);
        assertTrue("",1==1);
    }

    @Test
    public void cancel() {
    }

    @Test
    public void next() {
    }

    @Test
    public void prev() {
    }

    @Test
    public void first() {
    }

    @Test
    public void last() {
    }

    @Test
    public void searchResultList() {
    }
}
