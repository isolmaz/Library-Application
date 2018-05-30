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

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(FacesContext.class)
public class LoginBeanTest {

    private LoginBean loginBean;
    @Mock
    private FacesContext facesContext;
    @Mock
    private ExternalContext externalContext;

    @Before
    public void setUp() {
        loginBean = new LoginBean();

        PowerMockito.mockStatic(FacesContext.class);

        when(FacesContext.getCurrentInstance()).thenReturn(facesContext);
        when(facesContext.getExternalContext())
                .thenReturn(externalContext);
    }

    @Test
    public void loginWithNullValue() {
        ArgumentCaptor<String> clientIdCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<FacesMessage> facesMessageCaptor = ArgumentCaptor.forClass(FacesMessage.class);
        loginBean.setUsername("");
        loginBean.setPassword("");
        loginBean.login();
        verify(facesContext).addMessage(clientIdCaptor.capture(), facesMessageCaptor.capture());
        assertNull(clientIdCaptor.getValue());
        FacesMessage captured = facesMessageCaptor.getValue();
        assertEquals(FacesMessage.SEVERITY_INFO, captured.getSeverity());
        assertEquals("There are empty spaces", captured.getSummary());
        assertEquals("There are empty spaces", captured.getDetail());
    }
    @Test
    public void loginWithWrongInformation() {
        ArgumentCaptor<String> clientIdCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<FacesMessage> facesMessageCaptor = ArgumentCaptor.forClass(FacesMessage.class);
        loginBean.setUsername("furkan");
        loginBean.setPassword("123123");
        loginBean.login();
        verify(facesContext).addMessage(clientIdCaptor.capture(), facesMessageCaptor.capture());
        assertNull(clientIdCaptor.getValue());
        FacesMessage captured = facesMessageCaptor.getValue();
        assertEquals(FacesMessage.SEVERITY_INFO, captured.getSeverity());
        assertEquals("wrong username password combination", captured.getSummary());
        assertEquals("wrong username password combination", captured.getDetail());
    }
    @Test
    public void loginIsAdmin() throws NoSuchAlgorithmException {
        loginBean.setUsername("mhmtnasif");
        loginBean.setPassword("123123123");
        assertEquals("index.xhtml?faces-redirect=true", loginBean.login());
    }
    @Test
    public void loginIsStandartUser() throws NoSuchAlgorithmException {
        loginBean.setUsername("furkan");
        loginBean.setPassword("123123123");
        assertEquals("user.xhtml?faces-redirect=true", loginBean.login());
    }
}