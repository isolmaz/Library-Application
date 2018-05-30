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

import java.security.NoSuchAlgorithmException;
import java.util.Date;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(FacesContext.class)
public class RegisterBeanTest {

    @Mock
    private FacesContext facesContext;
    @Mock
    private ExternalContext externalContext;
    private RegisterBean registerBean;

    @Before
    public void setUp() {
        registerBean=new RegisterBean();
        PowerMockito.mockStatic(FacesContext.class);

        when(FacesContext.getCurrentInstance()).thenReturn(facesContext);
        when(facesContext.getExternalContext())
                .thenReturn(externalContext);
    }

    @Test
    public void signUpWithNullValue() {
        ArgumentCaptor<String> clientIdCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<FacesMessage> facesMessageCaptor = ArgumentCaptor.forClass(FacesMessage.class);
        registerBean.setUsername("");
        registerBean.setPassword("");
        registerBean.signUp();
        verify(facesContext).addMessage(clientIdCaptor.capture(), facesMessageCaptor.capture());
        assertNull(clientIdCaptor.getValue());
        FacesMessage captured = facesMessageCaptor.getValue();
        assertEquals(FacesMessage.SEVERITY_INFO, captured.getSeverity());
        assertEquals("There are empty spaces", captured.getSummary());
        assertEquals("There are empty spaces", captured.getDetail());
    }
    @Test
    public void signUpWeAlreadyHasUser() {
        ArgumentCaptor<String> clientIdCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<FacesMessage> facesMessageCaptor = ArgumentCaptor.forClass(FacesMessage.class);
        registerBean.setUsername("furkan");
        registerBean.setPassword("123123");
        registerBean.signUp();
        verify(facesContext).addMessage(clientIdCaptor.capture(), facesMessageCaptor.capture());
        assertNull(clientIdCaptor.getValue());
        FacesMessage captured = facesMessageCaptor.getValue();
        assertEquals(FacesMessage.SEVERITY_INFO, captured.getSeverity());
        String message="Already there is a user like furkan";
        assertEquals(message, captured.getSummary());
        assertEquals(message, captured.getDetail());
    }

    @Test
    public void signUp() {
        registerBean.setUsername(new Date().toString());
        registerBean.setPassword("123123");
        assertEquals("user.xhtml?faces-redirect=true",registerBean.signUp());
        }

}