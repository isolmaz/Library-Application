package com.mhmtnasif.library_app.beans;


import com.mhmtnasif.library_app.dao.AuthorsDao;
import com.mhmtnasif.library_app.dao.PublishersDao;
import com.mhmtnasif.library_app.dao.UsersDao;
import com.mhmtnasif.library_app.daoImpl.AuthorsDaoImpl;
import com.mhmtnasif.library_app.daoImpl.PublishersDaoImpl;
import com.mhmtnasif.library_app.daoImpl.UserDaoImpl;
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(FacesContext.class)
public class BooksBeanTest {

    private AuthorsDao authorsDao;
    private PublishersDao publishersDao;
    private BooksBean booksBean;
    private UsersDao usersDao;

    @Mock
    private FacesContext facesContext;
    @Mock
    private ExternalContext externalContext;

    @Before
    public void init() {
        usersDao=new UserDaoImpl();
        booksBean=new BooksBean();
        authorsDao=new AuthorsDaoImpl();
        publishersDao=new PublishersDaoImpl();
        PowerMockito.mockStatic(FacesContext.class);

        when(FacesContext.getCurrentInstance()).thenReturn(facesContext);
        when(facesContext.getExternalContext())
                .thenReturn(externalContext);
    }

    @Test
    public void addBook() {
        ArgumentCaptor<String> clientIdCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<FacesMessage> facesMessageCaptor = ArgumentCaptor.forClass(FacesMessage.class);
        booksBean.getBooks().setBook_name("example");
        booksBean.getBooks().setBook_sub_name("example");
        booksBean.getBooks().setBook_serial_name("example");
        booksBean.getBooks().setBook_desc("example");
        booksBean.getBooks().setBook_isbn("example");
        booksBean.setAuthor_id(1);
        booksBean.setPublisher_id(1);
        booksBean.setUser(usersDao.findUserByUserName("furkan"));
        booksBean.addBook();
        verify(facesContext).addMessage(clientIdCaptor.capture(), facesMessageCaptor.capture());
        assertNull(clientIdCaptor.getValue());
        FacesMessage captured = facesMessageCaptor.getValue();
        assertEquals(FacesMessage.SEVERITY_INFO, captured.getSeverity());
        assertEquals("Successful", captured.getSummary());
        assertEquals("Successful", captured.getDetail());
    }
    @Test
    public void addBookNullValue() {
        ArgumentCaptor<String> clientIdCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<FacesMessage> facesMessageCaptor = ArgumentCaptor.forClass(FacesMessage.class);
        booksBean.getBooks().setBook_name("example");
        booksBean.getBooks().setBook_sub_name("example");
        booksBean.getBooks().setBook_serial_name("example");
        booksBean.getBooks().setBook_desc("example");
        booksBean.getBooks().setBook_isbn("");
        booksBean.setAuthor_id(1);
        booksBean.setPublisher_id(1);
        booksBean.setUser(usersDao.findUserByUserName("furkan"));
        booksBean.addBook();
        verify(facesContext).addMessage(clientIdCaptor.capture(), facesMessageCaptor.capture());
        assertNull(clientIdCaptor.getValue());
        FacesMessage captured = facesMessageCaptor.getValue();
        assertEquals(FacesMessage.SEVERITY_INFO, captured.getSeverity());
        assertEquals("There are empty spaces", captured.getSummary());
        assertEquals("There are empty spaces", captured.getDetail());
    }


}
