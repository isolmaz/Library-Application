package com.mhmtnasif.library_app.beans;

import com.mhmtnasif.library_app.dao.AuthorsDao;
import com.mhmtnasif.library_app.dao.daoImpl.AuthorsDaoImpl;
import com.mhmtnasif.library_app.entities.Authors;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@SessionScoped
public class AuthorsBean {

    AuthorsDao authorsDao =new AuthorsDaoImpl();
    private Authors authors = new Authors();

    public Authors getAuthors() {
        return authors;
    }

    public void setAuthors(Authors authors) {
        this.authors = authors;
    }

    public void addAuthor() {
        if (this.authors.getAuthors_name().equals("") ||
                this.authors.getAuthors_name() == null||
                this.authors.getAuthors_desc() == null||
                this.authors.getAuthors_desc().equals("") ) {
            FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(
                    FacesMessage.SEVERITY_ERROR, "There are empty spaces","There are empty spaces"
            ));
        }else{
            if (authorsDao.addAuthor(this.authors)){
                this.authors.setAuthors_name("");
                this.authors.setAuthors_desc("");
                FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO,"Successful","Successful"));
            }else{
                FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,"Unexpected error occurred!","Unexpected error occurred!"));
            }
        }
    }
}
