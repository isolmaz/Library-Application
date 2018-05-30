package com.mhmtnasif.library_app.beans;

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
public class PublishersBeanTest {

    @Mock
    private FacesContext facesContext;
    @Mock
    private ExternalContext externalContext;
    private PublishersBean publishersBean;

    @Before
    public void setUp() {
        publishersBean = new PublishersBean();
        PowerMockito.mockStatic(FacesContext.class);

        when(FacesContext.getCurrentInstance()).thenReturn(facesContext);
        when(facesContext.getExternalContext())
                .thenReturn(externalContext);
    }

    @Test
    public void addPublisher() {
        ArgumentCaptor<String> clientIdCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<FacesMessage> facesMessageCaptor = ArgumentCaptor.forClass(FacesMessage.class);
        publishersBean.getPublishers().setPublisher_name("example");
        publishersBean.getPublishers().setPublisher_desc("example");
        publishersBean.addPublisher();
        verify(facesContext).addMessage(clientIdCaptor.capture(), facesMessageCaptor.capture());
        assertNull(clientIdCaptor.getValue());
        FacesMessage captured = facesMessageCaptor.getValue();
        assertEquals(FacesMessage.SEVERITY_INFO, captured.getSeverity());
        assertEquals("Successful", captured.getSummary());
        assertEquals("Successful", captured.getDetail());
    }

    @Test
    public void addPublisherNullValue() {
        ArgumentCaptor<String> clientIdCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<FacesMessage> facesMessageCaptor = ArgumentCaptor.forClass(FacesMessage.class);
        publishersBean.getPublishers().setPublisher_name("");
        publishersBean.getPublishers().setPublisher_desc("example");
        publishersBean.addPublisher();
        verify(facesContext).addMessage(clientIdCaptor.capture(), facesMessageCaptor.capture());
        assertNull(clientIdCaptor.getValue());
        FacesMessage captured = facesMessageCaptor.getValue();
        assertEquals(FacesMessage.SEVERITY_INFO, captured.getSeverity());
        assertEquals("There are empty spaces", captured.getSummary());
        assertEquals("There are empty spaces", captured.getDetail());
    }

}
