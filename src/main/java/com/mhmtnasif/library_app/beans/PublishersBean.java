package com.mhmtnasif.library_app.beans;

import com.mhmtnasif.library_app.dao.PublishersDao;
import com.mhmtnasif.library_app.daoImpl.PublishersDaoImpl;
import com.mhmtnasif.library_app.entities.Publishers;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@SessionScoped
public class PublishersBean {

    private Publishers publishers=new Publishers();
    PublishersDao publishersDao=new PublishersDaoImpl();

    public Publishers getPublishers() {
        return publishers;
    }

    public void setPublishers(Publishers publishers) {
        this.publishers = publishers;
    }

    public void addPublisher(){
        if (this.publishers.getPublisher_name().equals("") ||
                this.publishers.getPublisher_name() == null||
                this.publishers.getPublisher_desc() == null||
                this.publishers.getPublisher_desc().equals("") ) {
            FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(
                    FacesMessage.SEVERITY_INFO, "There are empty spaces","There are empty spaces"
            ));
        }else{
            if (publishersDao.addPublisher(this.publishers)){
                this.publishers.setPublisher_name("");
                this.publishers.setPublisher_desc("");
                FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO,"Successful","Successful"));
            }else{
                FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO,"Unexpected error occurred!","Unexpected error occurred!"));
            }
        }
    }
}
