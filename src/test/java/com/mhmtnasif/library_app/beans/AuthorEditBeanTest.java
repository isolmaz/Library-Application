package com.mhmtnasif.library_app.beans;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.mhmtnasif.library_app.dao.AuthorsDao;
import com.mhmtnasif.library_app.daoImpl.AuthorsDaoImpl;
import com.mhmtnasif.library_app.entities.Authors;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.api.mockito.PowerMockito;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

@RunWith(PowerMockRunner.class)
@PrepareForTest(FacesContext.class)
public class AuthorEditBeanTest {

    private AuthorsDao authorsDao;
    private AuthorEditBean authorEditBean;
    private int totalRowSize;

    @Mock
    private FacesContext facesContext;
    @Mock
    private ExternalContext externalContext;

    @Before
    public void setUp() {
        authorsDao = new AuthorsDaoImpl();
        authorEditBean = new AuthorEditBean();
        authorEditBean.setSearchText("");
        init();
        totalRowSize = authorEditBean.getTotalRowSize();

        PowerMockito.mockStatic(FacesContext.class);

        when(FacesContext.getCurrentInstance()).thenReturn(facesContext);
        when(facesContext.getExternalContext())
                .thenReturn(externalContext);
    }

    @Test
    public void init() {
        authorEditBean.init();
        assertTrue("init method failed size of list authors should be <=5", authorEditBean.getAuthorList().size() <= 5);
    }

    @Test
    public void edit() {
        Authors author = authorsDao.findById(33);
        authorEditBean.edit(author);
        assertTrue("edit method failed popup should be true", authorEditBean.isPopup());
        assertSame("edit method failed objects should be same", author, authorEditBean.getAuthorPopModel());
    }

    @Test
    public void updateAuthor() {
        ArgumentCaptor<String> clientIdCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<FacesMessage> facesMessageCaptor = ArgumentCaptor.forClass(FacesMessage.class);
        init();
        Authors author = authorEditBean.getAuthorList().get(3);
        authorEditBean.edit(author);
        authorEditBean.getAuthorPopModel().setAuthors_desc("updated");
        authorEditBean.updateAuthor();
        verify(facesContext).addMessage(clientIdCaptor.capture(), facesMessageCaptor.capture());
        assertNull(clientIdCaptor.getValue());
        FacesMessage captured = facesMessageCaptor.getValue();
        assertEquals(FacesMessage.SEVERITY_INFO, captured.getSeverity());
        assertEquals("Successful", captured.getSummary());
        assertEquals("Successful", captured.getDetail());
    }

    @Test
    public void updateAuthorNullValue() {
        ArgumentCaptor<String> clientIdCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<FacesMessage> facesMessageCaptor = ArgumentCaptor.forClass(FacesMessage.class);
        init();
        Authors author = authorEditBean.getAuthorList().get(3);
        authorEditBean.edit(author);
        authorEditBean.getAuthorPopModel().setAuthors_desc("");
        authorEditBean.updateAuthor();
        verify(facesContext).addMessage(clientIdCaptor.capture(), facesMessageCaptor.capture());
        assertNull(clientIdCaptor.getValue());
        FacesMessage captured = facesMessageCaptor.getValue();
        assertEquals(FacesMessage.SEVERITY_INFO, captured.getSeverity());
        assertEquals("There are empty spaces", captured.getSummary());
        assertEquals("There are empty spaces", captured.getDetail());
    }


    @Test
    public void cancelEdit() {
        authorEditBean.cancelEdit();
        assertFalse("cancelEdit method failed popup should be false", authorEditBean.isPopup());
    }

    @Test
    public void remove() {
        ArgumentCaptor<String> clientIdCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<FacesMessage> facesMessageCaptor = ArgumentCaptor.forClass(FacesMessage.class);
        init();
        Authors author = authorEditBean.getAuthorList().get(3);
        authorEditBean.remove(author);
        verify(facesContext).addMessage(clientIdCaptor.capture(), facesMessageCaptor.capture());
        assertNull(clientIdCaptor.getValue());
        FacesMessage captured = facesMessageCaptor.getValue();
        assertEquals(FacesMessage.SEVERITY_INFO, captured.getSeverity());
        assertEquals("Successful", captured.getSummary());
        assertEquals("Successful", captured.getDetail());
    }



    @Test
    public void isListEmpty() {
        init();
        int totalPage = authorEditBean.getTotalPageSize();
        authorEditBean.setCurrentPage(2);
        authorEditBean.getAuthorList().clear();
        authorEditBean.isListEmpty();
        assertEquals("isListEmpty method failed currentPage should be 1", authorEditBean.getCurrentPage(), 1);
        assertEquals("isListEmpty method failed totalPageSize should be " + (totalPage - 1), authorEditBean.getTotalPageSize(), totalPage - 1);
        init();
        int totalPage2 = authorEditBean.getTotalPageSize();
        authorEditBean.setCurrentPage(1);
        authorEditBean.getAuthorList().clear();
        authorEditBean.isListEmpty();
        assertEquals("isListEmpty method failed totalPage and totalPage2 are should be equals", totalPage, totalPage2);

    }

    @Test
    public void next() {
        authorEditBean.setCurrentPage(2);
        authorEditBean.setTotalPageSize(2);
        authorEditBean.next();
        assertEquals("next method failed currentPage should be 2", authorEditBean.getCurrentPage(), 2);
        authorEditBean.setCurrentPage(2);
        authorEditBean.setTotalPageSize(4);
        authorEditBean.next();
        assertEquals("next method failed currentPage should be 3", authorEditBean.getCurrentPage(), 3);
    }

    @Test
    public void prev() {
        authorEditBean.setCurrentPage(1);
        authorEditBean.prev();
        assertEquals("prev method failed currentPage should be 1", authorEditBean.getCurrentPage(), 1);
        authorEditBean.setCurrentPage(2);
        authorEditBean.prev();
        assertEquals("prev method failed currentPage should be 1", authorEditBean.getCurrentPage(), 1);
    }

    @Test
    public void first() {
        authorEditBean.setCurrentPage(1);
        authorEditBean.first();
        assertEquals("first method failed currentPage should be 1", authorEditBean.getCurrentPage(), 1);
        authorEditBean.setCurrentPage(5);
        authorEditBean.first();
        assertEquals("first method failed currentPage should be 1", authorEditBean.getCurrentPage(), 1);
    }

    @Test
    public void last() {
        authorEditBean.setCurrentPage(1);
        authorEditBean.setTotalPageSize(3);
        authorEditBean.last();
        assertEquals("last method failed currentPage should be 3", authorEditBean.getCurrentPage(), 3);
        authorEditBean.setCurrentPage(5);
        authorEditBean.setTotalPageSize(5);
        authorEditBean.last();
        assertEquals("last method failed currentPage should be 5", authorEditBean.getCurrentPage(), 5);
    }

    @Test
    public void searchResultList() {
        authorEditBean.searchResultList();
        assertEquals("searchResultList method failed currentPage should be 1", authorEditBean.getCurrentPage(), 1);
        assertEquals("searchResultList method failed totalRowSize  should be " + totalRowSize, authorEditBean.getTotalRowSize(), totalRowSize);
    }

    @Test
    public void searchResultListISEmpty() {
        ArgumentCaptor<String> clientIdCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<FacesMessage> facesMessageCaptor = ArgumentCaptor.forClass(FacesMessage.class);
        authorEditBean.setSearchText("asdasdasd");
        authorEditBean.searchResultList();
        verify(facesContext).addMessage(clientIdCaptor.capture(), facesMessageCaptor.capture());
        assertNull(clientIdCaptor.getValue());
        FacesMessage captured = facesMessageCaptor.getValue();
        assertEquals(FacesMessage.SEVERITY_INFO, captured.getSeverity());
        String message = "There aren't any author like asdasdasd in the database. ";
        assertEquals(message, captured.getSummary());
        assertEquals(message, captured.getDetail());
    }
}

