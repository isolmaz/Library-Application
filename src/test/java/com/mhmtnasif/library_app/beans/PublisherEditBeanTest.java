package com.mhmtnasif.library_app.beans;

import com.mhmtnasif.library_app.dao.PublishersDao;
import com.mhmtnasif.library_app.daoImpl.PublishersDaoImpl;
import com.mhmtnasif.library_app.entities.Publishers;
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
public class PublisherEditBeanTest {

    @Mock
    private FacesContext facesContext;
    @Mock
    private ExternalContext externalContext;
    private PublisherEditBean publisherEditBean;
    private PublishersDao publishersDao;

    @Before
    public void setUp()  {
        publisherEditBean=new PublisherEditBean();
        publishersDao=new PublishersDaoImpl();
        PowerMockito.mockStatic(FacesContext.class);

        when(FacesContext.getCurrentInstance()).thenReturn(facesContext);
        when(facesContext.getExternalContext())
                .thenReturn(externalContext);
    }

    @Test
    public void init() {
        publisherEditBean.init();
        assertTrue("init method failed size of list authors should be <=5", publisherEditBean.getPublishersList().size() <= 5);
    }

    @Test
    public void edit() {
        Publishers publisher = publishersDao.findById(1);
        publisherEditBean.edit(publisher);
        assertTrue("edit method failed popup should be true", publisherEditBean.isPopup());
        assertSame("edit method failed objects should be same", publisher, publisherEditBean.getPublishersPopModel());
    }

    @Test
    public void updatePublisher() {
        ArgumentCaptor<String> clientIdCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<FacesMessage> facesMessageCaptor = ArgumentCaptor.forClass(FacesMessage.class);
        init();
        Publishers publisher= publisherEditBean.getPublishersList().get(3);
        publisherEditBean.edit(publisher);
        publisherEditBean.getPublishersPopModel().setPublisher_desc("updated");
        publisherEditBean.updatePublisher();
        verify(facesContext).addMessage(clientIdCaptor.capture(), facesMessageCaptor.capture());
        assertNull(clientIdCaptor.getValue());
        FacesMessage captured = facesMessageCaptor.getValue();
        assertEquals(FacesMessage.SEVERITY_INFO, captured.getSeverity());
        assertEquals("Successful", captured.getSummary());
        assertEquals("Successful", captured.getDetail());
    }

    @Test
    public void updatePublisherWithNullValues() {
        ArgumentCaptor<String> clientIdCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<FacesMessage> facesMessageCaptor = ArgumentCaptor.forClass(FacesMessage.class);
        init();
        Publishers publisher= publisherEditBean.getPublishersList().get(3);
        publisherEditBean.edit(publisher);
        publisherEditBean.getPublishersPopModel().setPublisher_desc("");
        publisherEditBean.updatePublisher();
        verify(facesContext).addMessage(clientIdCaptor.capture(), facesMessageCaptor.capture());
        assertNull(clientIdCaptor.getValue());
        FacesMessage captured = facesMessageCaptor.getValue();
        assertEquals(FacesMessage.SEVERITY_INFO, captured.getSeverity());
        assertEquals("There are empty spaces", captured.getSummary());
        assertEquals("There are empty spaces", captured.getDetail());
    }

    @Test
    public void cancelEdit() {
        publisherEditBean.cancelEdit();
        assertFalse("cancelEdit method failed popup should be false", publisherEditBean.isPopup());
    }
    @Test
    public void remove() {
        ArgumentCaptor<String> clientIdCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<FacesMessage> facesMessageCaptor = ArgumentCaptor.forClass(FacesMessage.class);
        init();
        Publishers publishers = publisherEditBean.getPublishersList().get(3);
        publisherEditBean.remove(publishers);
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
        int totalPage = publisherEditBean.getTotalPageSize();
        publisherEditBean.setCurrentPage(2);
        publisherEditBean.getPublishersList().clear();
        publisherEditBean.isListEmpty();
        assertEquals("isListEmpty method failed currentPage should be 1", publisherEditBean.getCurrentPage(), 1);
        assertEquals("isListEmpty method failed totalPageSize should be " + (totalPage - 1), publisherEditBean.getTotalPageSize(), totalPage - 1);
        init();
        int totalPage2 = publisherEditBean.getTotalPageSize();
        publisherEditBean.setCurrentPage(1);
        publisherEditBean.getPublishersList().clear();
        publisherEditBean.isListEmpty();
        assertEquals("isListEmpty method failed totalPage and totalPage2 are should be equals", totalPage, totalPage2);

    }

    @Test
    public void next() {
        publisherEditBean.setCurrentPage(2);
        publisherEditBean.setTotalPageSize(2);
        publisherEditBean.next();
        assertEquals("next method failed currentPage should be 2", publisherEditBean.getCurrentPage(), 2);
        publisherEditBean.setCurrentPage(2);
        publisherEditBean.setTotalPageSize(4);
        publisherEditBean.next();
        assertEquals("next method failed currentPage should be 3", publisherEditBean.getCurrentPage(), 3);
    }

    @Test
    public void prev() {
        publisherEditBean.setCurrentPage(1);
        publisherEditBean.prev();
        assertEquals("prev method failed currentPage should be 1", publisherEditBean.getCurrentPage(), 1);
        publisherEditBean.setCurrentPage(2);
        publisherEditBean.prev();
        assertEquals("prev method failed currentPage should be 1", publisherEditBean.getCurrentPage(), 1);
    }

    @Test
    public void first() {
        publisherEditBean.setCurrentPage(1);
        publisherEditBean.first();
        assertEquals("first method failed currentPage should be 1", publisherEditBean.getCurrentPage(), 1);
        publisherEditBean.setCurrentPage(5);
        publisherEditBean.first();
        assertEquals("first method failed currentPage should be 1", publisherEditBean.getCurrentPage(), 1);
    }

    @Test
    public void last() {
        publisherEditBean.setCurrentPage(1);
        publisherEditBean.setTotalPageSize(3);
        publisherEditBean.last();
        assertEquals("last method failed currentPage should be 3", publisherEditBean.getCurrentPage(), 3);
        publisherEditBean.setCurrentPage(5);
        publisherEditBean.setTotalPageSize(5);
        publisherEditBean.last();
        assertEquals("last method failed currentPage should be 5", publisherEditBean.getCurrentPage(), 5);
    }

    @Test
    public void searchResultList() {
        publisherEditBean.searchResultList();
        assertEquals("searchResultList method failed currentPage should be 1", publisherEditBean.getCurrentPage(), 1);
    }



    @Test
    public void searchResultListISEmpty() {
        ArgumentCaptor<String> clientIdCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<FacesMessage> facesMessageCaptor = ArgumentCaptor.forClass(FacesMessage.class);
        publisherEditBean.setSearchText("asdasdasd");
        publisherEditBean.searchResultList();
        verify(facesContext).addMessage(clientIdCaptor.capture(), facesMessageCaptor.capture());
        assertNull(clientIdCaptor.getValue());
        FacesMessage captured = facesMessageCaptor.getValue();
        assertEquals(FacesMessage.SEVERITY_INFO, captured.getSeverity());
        String message = "There aren't any publisher like asdasdasd in the database. ";
        assertEquals(message, captured.getSummary());
        assertEquals(message, captured.getDetail());
    }
}