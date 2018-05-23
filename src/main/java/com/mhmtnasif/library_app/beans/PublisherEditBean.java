package com.mhmtnasif.library_app.beans;

import com.mhmtnasif.library_app.dao.BooksDao;
import com.mhmtnasif.library_app.dao.PublishersDao;
import com.mhmtnasif.library_app.dao.daoImpl.BooksDaoImpl;
import com.mhmtnasif.library_app.dao.daoImpl.PublishersDaoImpl;
import com.mhmtnasif.library_app.entities.Publishers;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.util.List;

@ManagedBean
@ViewScoped
public class PublisherEditBean {

    private PublishersDao publishersDao = new PublishersDaoImpl();
    private BooksDao booksDao = new BooksDaoImpl();
    private List<Publishers> publishersList;
    private Publishers publishersPopModel;
    private Publishers temp;
    private boolean popup;
    private int rowsPerPage;
    private int totalPageSize;
    private int currentPage;
    private int totalRowSize;
    private String searchText;


    @PostConstruct
    public void init() {
        rowsPerPage = 5;
        totalRowSize = publishersDao.findAll(searchText).size();
        totalPageSize = (int) Math.ceil((double) totalRowSize / (double) rowsPerPage);
        currentPage = 1;
        publishersList = publishersDao.findByRange((currentPage - 1) * rowsPerPage, rowsPerPage, searchText);

    }


    public void edit(Publishers publisher) {
        publishersPopModel = publisher;
        temp = publisher;
        this.popup = true;

    }

    public void updatePublisher() {
        if (publishersPopModel.getPublisher_name().equals("") ||
                publishersPopModel.getPublisher_name() == null ||
                publishersPopModel.getPublisher_desc().equals("") ||
                publishersPopModel.getPublisher_desc() == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "There are empty spaces", "There are empty spaces"));
            publishersList.set(publishersList.indexOf(temp), temp);
            publishersPopModel = temp;
        } else {
            if (publishersDao.updatePublisher(publishersPopModel)) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Successful", "Successful"));
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Unexpected error occurred!", "Unexpected error occurred!"));
                publishersList.set(publishersList.indexOf(temp), temp);
            }
        }

        popup = false;
    }

    public void cancelEdit() {
        popup = false;
    }

    public void remove(Publishers publisher) {
        if (booksDao.deleteBooks(booksDao.findPublisherId(publisher))) {
            if (publishersDao.deletePublisher(publisher)) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Successful", "Successful"));
                publishersList.remove(publisher);
                if (publishersList.isEmpty() && currentPage != 1) {
                    currentPage--;
                    totalPageSize--;
                    publishersList = publishersDao.findByRange((currentPage - 1) * rowsPerPage, rowsPerPage, searchText);
                } else if (publishersList.isEmpty() && currentPage == 1) {
                    searchText = "";
                    init();
                }
                totalRowSize--;
                totalPageSize = (int) Math.ceil((double) totalRowSize / (double) rowsPerPage);
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Unexpected error occurred!", "Unexpected error occurred!"));
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Unexpected error occurred!", "Unexpected error occurred!"));
        }
    }

    public void next() {
        if (currentPage != totalPageSize) {
            currentPage++;
            publishersList = publishersDao.findByRange((currentPage - 1) * rowsPerPage, rowsPerPage, searchText);
        }
    }

    public void prev() {
        if (currentPage != 1) {
            currentPage--;
            publishersList = publishersDao.findByRange((currentPage - 1) * rowsPerPage, rowsPerPage, searchText);
        }
    }

    public void first() {
        if (currentPage != 1) {
            currentPage = 1;
            publishersList = publishersDao.findByRange((currentPage - 1) * rowsPerPage, rowsPerPage, searchText);
        }
    }

    public void last() {
        currentPage = totalPageSize;
        publishersList = publishersDao.findByRange((currentPage - 1) * rowsPerPage, rowsPerPage, searchText);
    }

    public void searchResultList() {
        totalRowSize = publishersDao.findAll(searchText).size();
        totalPageSize = (int) Math.ceil((double)totalRowSize / (double) rowsPerPage);
        if (totalPageSize != 0) {
            currentPage = 1;
            publishersList = publishersDao.findByRange((currentPage - 1) * rowsPerPage, rowsPerPage, searchText);
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "There aren't any publisher like " + searchText + " in the database. ", "There aren't any publisher like " + searchText + " in the database. "));
            searchText = "";
            init();
        }


    }

    public List<Publishers> getPublishersList() {
        return publishersList;
    }

    public void setPublishersList(List<Publishers> publishersList) {
        this.publishersList = publishersList;
    }

    public Publishers getPublishersPopModel() {
        return publishersPopModel;
    }

    public void setPublishersPopModel(Publishers publishersPopModel) {
        this.publishersPopModel = publishersPopModel;
    }

    public Publishers getTemp() {
        return temp;
    }

    public void setTemp(Publishers temp) {
        this.temp = temp;
    }

    public boolean isPopup() {
        return popup;
    }

    public void setPopup(boolean popup) {
        this.popup = popup;
    }

    public int getRowsPerPage() {
        return rowsPerPage;
    }

    public void setRowsPerPage(int rowsPerPage) {
        this.rowsPerPage = rowsPerPage;
    }

    public int getTotalPageSize() {
        return totalPageSize;
    }

    public void setTotalPageSize(int totalPageSize) {
        this.totalPageSize = totalPageSize;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public String getSearchText() {
        return searchText;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }
}

