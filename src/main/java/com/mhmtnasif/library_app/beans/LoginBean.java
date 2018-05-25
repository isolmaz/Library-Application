package com.mhmtnasif.library_app.beans;

import com.mhmtnasif.library_app.dao.UsersDao;
import com.mhmtnasif.library_app.daoImpl.UserDaoImpl;
import com.mhmtnasif.library_app.entities.Users;
import com.mhmtnasif.library_app.util.Util;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@SessionScoped
public class LoginBean {

    private UsersDao usersDao=new UserDaoImpl();
    private String username;
    private String password;
    private String passwordHash;

    public String login(){

        if (username.equals("") ||
                username == null ||
                password == null ||
                password.equals("")){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_ERROR, "There are empty spaces", "There are empty spaces"));
        }else{
            try{
               passwordHash= Util.hashMD5(password);
            }catch (Exception e){
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Unexpected error occurred!", "Unexpected error occurred!"));
                username="";
                return "";
            }
            if (usersDao.login(username,passwordHash)==0){
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                        FacesMessage.SEVERITY_ERROR, "wrong username password combination", "wrong username password combination"));
            }else{
                Users users=usersDao.findUserByUserName(username);
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("valid_user",users);
                if (users.isAdmin()){
                    return "index.xhtml?faces-redirect=true";
                }else{
                    return "user.xhtml?faces-redirect=true";
                }
            }
        }
        username="";
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
