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
public class IndexBeanTest {

    private IndexBean indexBean;
    private BooksDao booksDao;


    @Mock
    private FacesContext facesContext;
    @Mock
    private ExternalContext externalContext;

    @Before
    public void setUp() {
        indexBean=new IndexBean();
        booksDao=new BooksDaoImpl();
        PowerMockito.mockStatic(FacesContext.class);

        when(FacesContext.getCurrentInstance()).thenReturn(facesContext);
        when(facesContext.getExternalContext())
                .thenReturn(externalContext);
    }

    @Test
    public void show() {
        Books books = booksDao.findAll("").get(1);
        indexBean.show(books);
        assertTrue("show method failed popup should be true", indexBean.isPopup());
        assertSame("show method failed objects should be same", books, indexBean.getBooksPopModel());
    }

    @Test
    public void cancel() {
        indexBean.cancel();
        assertFalse("cancel method failed popup should be false", indexBean.isPopup());
    }

    @Test
    public void next() {
        indexBean.setCurrentPage(2);
        indexBean.setTotalPageSize(2);
        indexBean.next();
        assertEquals("next method failed currentPage should be 2", indexBean.getCurrentPage(), 2);
        indexBean.setCurrentPage(2);
        indexBean.setTotalPageSize(4);
        indexBean.next();
        assertEquals("next method failed currentPage should be 3", indexBean.getCurrentPage(), 3);
    }

    @Test
    public void prev() {
        indexBean.setCurrentPage(1);
        indexBean.prev();
        assertEquals("prev method failed currentPage should be 1", indexBean.getCurrentPage(), 1);
        indexBean.setCurrentPage(2);
        indexBean.prev();
        assertEquals("prev method failed currentPage should be 1", indexBean.getCurrentPage(), 1);
    }

    @Test
    public void first() {
        indexBean.setCurrentPage(1);
        indexBean.first();
        assertEquals("first method failed currentPage should be 1", indexBean.getCurrentPage(), 1);
        indexBean.setCurrentPage(5);
        indexBean.first();
        assertEquals("first method failed currentPage should be 1", indexBean.getCurrentPage(), 1);
    }

    @Test
    public void last() {
        indexBean.setCurrentPage(1);
        indexBean.setTotalPageSize(3);
        indexBean.last();
        assertEquals("last method failed currentPage should be 3", indexBean.getCurrentPage(), 3);
        indexBean.setCurrentPage(5);
        indexBean.setTotalPageSize(5);
        indexBean.last();
        assertEquals("last method failed currentPage should be 5", indexBean.getCurrentPage(), 5);
    }

    @Test
    public void searchResultList() {
        indexBean.setSearchText("a");
        indexBean.searchResultList();
        int totalRowSize=indexBean.getTotalRowSize();
        assertEquals("searchResultList method failed currentPage should be 1", indexBean.getCurrentPage(), 1);
        assertEquals("searchResultList method failed totalRowSize  should be " + totalRowSize, indexBean.getTotalRowSize(), totalRowSize);
    }

    @Test
    public void searchResultListISEmpty() {
        ArgumentCaptor<String> clientIdCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<FacesMessage> facesMessageCaptor = ArgumentCaptor.forClass(FacesMessage.class);
        indexBean.setSearchText("asdasdasd");
        indexBean.searchResultList();
        verify(facesContext).addMessage(clientIdCaptor.capture(), facesMessageCaptor.capture());
        assertNull(clientIdCaptor.getValue());
        FacesMessage captured = facesMessageCaptor.getValue();
        assertEquals(FacesMessage.SEVERITY_INFO, captured.getSeverity());
        String message = "There aren't any book like asdasdasd in the database. ";
        assertEquals(message, captured.getSummary());
        assertEquals(message, captured.getDetail());
    }
}