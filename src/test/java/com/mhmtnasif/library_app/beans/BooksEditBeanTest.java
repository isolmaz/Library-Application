package com.mhmtnasif.library_app.beans;


import com.mhmtnasif.library_app.dao.BooksDao;
import com.mhmtnasif.library_app.daoImpl.BooksDaoImpl;
import com.mhmtnasif.library_app.entities.Books;
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

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(FacesContext.class)
public class BooksEditBeanTest {

    @Mock
    private FacesContext facesContext;
    @Mock
    private ExternalContext externalContext;

    private BooksEditBean booksEditBean;
    private BooksDao booksDao;
    private int totalRowSize;


    @Before
    public void setUp() {

        booksEditBean = new BooksEditBean();
        booksDao = new BooksDaoImpl();
        PowerMockito.mockStatic(FacesContext.class);

        when(FacesContext.getCurrentInstance()).thenReturn(facesContext);
        when(facesContext.getExternalContext())
                .thenReturn(externalContext);
        init();
        totalRowSize = booksEditBean.getTotalRowSize();

    }

    @Test
    public void init() {
        booksEditBean.init();
        assertTrue("init method failed size of list authors should be <=5", booksEditBean.getBooksList().size() <= 5);
    }

    @Test
    public void edit() {
        Books books = booksDao.findAll("").get(1);
        booksEditBean.edit(books);
        assertTrue("edit method failed popup should be true", booksEditBean.isPopup());
        assertSame("edit method failed objects should be same", books, booksEditBean.getBooksPopModel());
    }

    @Test
    public void updateBook() {
        booksEditBean.init();
        ArgumentCaptor<String> clientIdCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<FacesMessage> facesMessageCaptor = ArgumentCaptor.forClass(FacesMessage.class);
        booksEditBean.setBooksPopModel(booksEditBean.getBooksList().get(1));
        booksEditBean.getBooksPopModel().setBook_desc("updated");
        booksEditBean.updateBook();
        verify(facesContext).addMessage(clientIdCaptor.capture(), facesMessageCaptor.capture());
        assertNull(clientIdCaptor.getValue());
        FacesMessage captured = facesMessageCaptor.getValue();
        assertEquals(FacesMessage.SEVERITY_INFO, captured.getSeverity());
        assertEquals("Successful", captured.getSummary());
        assertEquals("Successful", captured.getDetail());
    }

    @Test
    public void updateBookNullValue() {
        booksEditBean.init();
        ArgumentCaptor<String> clientIdCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<FacesMessage> facesMessageCaptor = ArgumentCaptor.forClass(FacesMessage.class);
        booksEditBean.setBooksPopModel(booksEditBean.getBooksList().get(1));
        booksEditBean.getBooksPopModel().setBook_desc("");
        booksEditBean.updateBook();
        verify(facesContext).addMessage(clientIdCaptor.capture(), facesMessageCaptor.capture());
        assertNull(clientIdCaptor.getValue());
        FacesMessage captured = facesMessageCaptor.getValue();
        assertEquals(FacesMessage.SEVERITY_INFO, captured.getSeverity());
        assertEquals("There are empty spaces", captured.getSummary());
        assertEquals("There are empty spaces", captured.getDetail());
    }

    @Test
    public void cancelEdit() {
        booksEditBean.cancelEdit();
        assertFalse("cancelEdit method failed popup should be false", booksEditBean.isPopup());
    }

    @Test
    public void remove() {
        ArgumentCaptor<String> clientIdCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<FacesMessage> facesMessageCaptor = ArgumentCaptor.forClass(FacesMessage.class);
        init();
        Books books = booksEditBean.getBooksList().get(3);
        booksEditBean.remove(books);
        verify(facesContext).addMessage(clientIdCaptor.capture(), facesMessageCaptor.capture());
        assertNull(clientIdCaptor.getValue());
        FacesMessage captured = facesMessageCaptor.getValue();
        assertEquals(FacesMessage.SEVERITY_INFO, captured.getSeverity());
        assertEquals("Successful", captured.getSummary());
        assertEquals("Successful", captured.getDetail());
    }

    @Test
    public void next() {
        booksEditBean.setCurrentPage(2);
        booksEditBean.setTotalPageSize(2);
        booksEditBean.next();
        assertEquals("next method failed currentPage should be 2", booksEditBean.getCurrentPage(), 2);
        booksEditBean.setCurrentPage(2);
        booksEditBean.setTotalPageSize(4);
        booksEditBean.next();
        assertEquals("next method failed currentPage should be 3", booksEditBean.getCurrentPage(), 3);
    }

    @Test
    public void prev() {
        booksEditBean.setCurrentPage(1);
        booksEditBean.prev();
        assertEquals("prev method failed currentPage should be 1", booksEditBean.getCurrentPage(), 1);
        booksEditBean.setCurrentPage(2);
        booksEditBean.prev();
        assertEquals("prev method failed currentPage should be 1", booksEditBean.getCurrentPage(), 1);
    }

    @Test
    public void first() {
        booksEditBean.setCurrentPage(1);
        booksEditBean.first();
        assertEquals("first method failed currentPage should be 1", booksEditBean.getCurrentPage(), 1);
        booksEditBean.setCurrentPage(5);
        booksEditBean.first();
        assertEquals("first method failed currentPage should be 1", booksEditBean.getCurrentPage(), 1);
    }

    @Test
    public void last() {
        booksEditBean.setCurrentPage(1);
        booksEditBean.setTotalPageSize(3);
        booksEditBean.last();
        assertEquals("last method failed currentPage should be 3", booksEditBean.getCurrentPage(), 3);
        booksEditBean.setCurrentPage(5);
        booksEditBean.setTotalPageSize(5);
        booksEditBean.last();
        assertEquals("last method failed currentPage should be 5", booksEditBean.getCurrentPage(), 5);
    }

    @Test
    public void searchResultList() {
        booksEditBean.searchResultList();
        assertEquals("searchResultList method failed currentPage should be 1", booksEditBean.getCurrentPage(), 1);
        assertEquals("searchResultList method failed totalRowSize  should be " + totalRowSize, booksEditBean.getTotalRowSize(), totalRowSize);
    }

    @Test
    public void searchResultListISEmpty() {
        ArgumentCaptor<String> clientIdCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<FacesMessage> facesMessageCaptor = ArgumentCaptor.forClass(FacesMessage.class);
        booksEditBean.setSearchText("asdasdasd");
        booksEditBean.searchResultList();
        verify(facesContext).addMessage(clientIdCaptor.capture(), facesMessageCaptor.capture());
        assertNull(clientIdCaptor.getValue());
        FacesMessage captured = facesMessageCaptor.getValue();
        assertEquals(FacesMessage.SEVERITY_INFO, captured.getSeverity());
        String message = "There aren't any book like asdasdasd in the database. ";
        assertEquals(message, captured.getSummary());
        assertEquals(message, captured.getDetail());
    }

    @Test
    public void isListEmpty() {
        init();
        int totalPage = booksEditBean.getTotalPageSize();
        booksEditBean.setCurrentPage(2);
        booksEditBean.getBooksList().clear();
        booksEditBean.isListEmpty();
        assertEquals("isListEmpty method failed currentPage should be 1", booksEditBean.getCurrentPage(), 1);
        assertEquals("isListEmpty method failed totalPageSize should be " + (totalPage - 1), booksEditBean.getTotalPageSize(), totalPage - 1);
        init();
        int totalPage2 = booksEditBean.getTotalPageSize();
        booksEditBean.setCurrentPage(1);
        booksEditBean.getBooksList().clear();
        booksEditBean.isListEmpty();
        assertEquals("isListEmpty method failed totalPage and totalPage2 are should be equals", totalPage, totalPage2);
    }
}
