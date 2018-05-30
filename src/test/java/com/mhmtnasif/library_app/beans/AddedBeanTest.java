package com.mhmtnasif.library_app.beans;


import com.mhmtnasif.library_app.dao.BooksDao;
import com.mhmtnasif.library_app.dao.UsersDao;
import com.mhmtnasif.library_app.daoImpl.BooksDaoImpl;
import com.mhmtnasif.library_app.daoImpl.UserDaoImpl;
import com.mhmtnasif.library_app.entities.Books;
import com.mhmtnasif.library_app.entities.Users;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import java.awt.print.Book;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(FacesContext.class)
public class AddedBeanTest {

    private AddedBean addedBean;
    private BooksDao booksDao;
    private Users user;
    @Mock
    private FacesContext facesContext;
    @Mock
    private ExternalContext externalContext;


    @Before
    public void initial() {
        addedBean = new AddedBean();
        booksDao = new BooksDaoImpl();
        user = new UserDaoImpl().findUserByUserName("furkan");
        addedBean.setUser(user);
        addedBean.setRowsPerPage(5);
        addedBean.setSearchText("");
        PowerMockito.mockStatic(FacesContext.class);

        when(FacesContext.getCurrentInstance()).thenReturn(facesContext);
        when(facesContext.getExternalContext())
                .thenReturn(externalContext);
    }


    @Test
    public void show() {
        Books popup = booksDao.findAll("").get(0);
        addedBean.show(popup);
        assertTrue("popup should be true", addedBean.isPopup());
        assertSame("objects should be same", popup, addedBean.getBooksPopModel());
    }

    @Test
    public void cancel() {
        addedBean.cancel();
        assertFalse("popup should be false", addedBean.isPopup());
    }

    @Test
    public void next() {
        addedBean.setCurrentPage(2);
        addedBean.setTotalPageSize(2);
        addedBean.next();
        assertTrue("currentPage should be 2", addedBean.getCurrentPage() == 2);
        addedBean.setCurrentPage(2);
        addedBean.setTotalPageSize(4);
        addedBean.next();
        assertTrue("currentPage should be 3", addedBean.getCurrentPage() == 3);

    }

    @Test
    public void prev() {
        addedBean.setCurrentPage(1);
        addedBean.prev();
        assertTrue("currentPage should be 1", addedBean.getCurrentPage() == 1);
        addedBean.setCurrentPage(2);
        addedBean.prev();
        assertTrue("currentPage should be 1", addedBean.getCurrentPage() == 1);
    }

    @Test
    public void first() {
        addedBean.setCurrentPage(1);
        addedBean.first();
        assertTrue("currentPage should be 1", addedBean.getCurrentPage() == 1);
        addedBean.setCurrentPage(5);
        addedBean.first();
        assertTrue("currentPage should be 1", addedBean.getCurrentPage() == 1);
    }

    @Test
    public void last() {
        addedBean.setCurrentPage(1);
        addedBean.setTotalPageSize(3);
        addedBean.last();
        assertTrue("currentPage should be 3", addedBean.getCurrentPage() == 3);
        addedBean.setCurrentPage(5);
        addedBean.setTotalPageSize(5);
        addedBean.last();
        assertTrue("currentPage should be 5", addedBean.getCurrentPage() == 5);
    }

    @Test
    public void searchResultList() {
        addedBean.searchResultList();
        assertTrue("currentPage should be 1", addedBean.getCurrentPage() == 1);
        assertTrue("totalPageSize  should be 1", addedBean.getTotalPageSize() == 1);
    addedBean.setUser(new UserDaoImpl().findUserByUserName("test"));
        addedBean.searchResultList();
    }
    @Test
    public void searchResultListISEmpty() {
        ArgumentCaptor<String> clientIdCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<FacesMessage> facesMessageCaptor = ArgumentCaptor.forClass(FacesMessage.class);
        addedBean.setSearchText("asdasdasd");
        addedBean.searchResultList();
        verify(facesContext).addMessage(clientIdCaptor.capture(), facesMessageCaptor.capture());
        assertNull(clientIdCaptor.getValue());
        FacesMessage captured = facesMessageCaptor.getValue();
        assertEquals(FacesMessage.SEVERITY_INFO, captured.getSeverity());
        String message ="There aren't any book like " + "asdasdasd" + " in the database. ";
        assertEquals(message, captured.getSummary());
        assertEquals(message, captured.getDetail());
    }
}
