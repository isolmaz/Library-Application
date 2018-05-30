package com.mhmtnasif.library_app.beans;


import com.mhmtnasif.library_app.dao.UsersDao;
import com.mhmtnasif.library_app.daoImpl.UserDaoImpl;
import com.mhmtnasif.library_app.entities.Users;
import com.mhmtnasif.library_app.util.Util;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@SessionScoped
public class RegisterBean {

    private UsersDao usersDao = new UserDaoImpl();
    private String username;
    private String password;
    private String passwordHash;

    public String signUp() {
        if (username.equals("") ||
                username == null ||
                password == null ||
                password.equals("")) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_INFO, "There are empty spaces", "There are empty spaces"));
        } else {
            if (usersDao.checkUserName(username.toLowerCase()) == 0) {
                try {
                    passwordHash = Util.hashMD5(password);
                } catch (Exception e) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Unexpected error occurred!", "Unexpected error occurred!"));
                    username="";
                    return "";
                }
                Users user = usersDao.addUser(new Users(username.toLowerCase(), passwordHash, false));
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("valid_user", user);
                return "/user.xhtml?faces-redirect=true";
                //todo return "user.xhtml?faces-redirect=true";

            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                        FacesMessage.SEVERITY_INFO, "Already there is a user like " + username.toLowerCase(), "Already there is a user like " + username));
            }
        }
        username = "";
        return "";
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
