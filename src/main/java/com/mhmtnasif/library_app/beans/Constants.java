package com.mhmtnasif.library_app.beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class Constants {
    public static String client_id="786aq8cym4h88a";
    public static String redirect_url="http://localhost:8080/Library-Application_war/user.xhtml";
    //todo public static String redirect_url="http://localhost:8080/user.xhtml";
    public static String client_secret="MYuwNmlVlKG1NC9o";

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    public String getRedirect_url() {
        return redirect_url;
    }

    public void setRedirect_url(String redirect_url) {
        this.redirect_url = redirect_url;
    }

    public String getClient_secret() {
        return client_secret;
    }

    public void setClient_secret(String client_secret) {
        this.client_secret = client_secret;
    }
}
