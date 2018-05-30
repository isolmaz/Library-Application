package com.mhmtnasif.library_app.beans;

import com.mhmtnasif.library_app.dao.BooksDao;
import com.mhmtnasif.library_app.daoImpl.BooksDaoImpl;
import com.mhmtnasif.library_app.entities.Books;
import com.mhmtnasif.library_app.entities.Users;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.util.List;

@ManagedBean
@ViewScoped
public class AddedBean {

    private BooksDao booksDao = new BooksDaoImpl();
    private List<Books> booksList;
    private Books booksPopModel;
    private boolean popup;
    private int rowsPerPage;
    private int totalPageSize;
    private int currentPage;
    private int totalRowSize;
    private String searchText;
    private Users user;


    @PostConstruct
    public void init() {
        user=(Users) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("valid_user");
        if (user != null){
            rowsPerPage = 5;
            totalRowSize = booksDao.findAllByUserId(searchText,user).size();
            totalPageSize = (int) Math.ceil((double) totalRowSize / (double) rowsPerPage);
            currentPage = 1;
            booksList = booksDao.findByRangeForSpecificUser((currentPage - 1) * rowsPerPage, rowsPerPage, searchText,user);
        }

    }


    public void show(Books book) {
        booksPopModel = book;
        this.popup = true;

    }



    public void cancel() {
        popup = false;
    }


    public void next() {
        if (currentPage != totalPageSize) {
            currentPage++;
            booksList = booksDao.findByRangeForSpecificUser((currentPage - 1) * rowsPerPage, rowsPerPage, searchText,user);
        }
    }

    public void prev() {
        if (currentPage != 1) {
            currentPage--;
            booksList = booksDao.findByRangeForSpecificUser((currentPage - 1) * rowsPerPage, rowsPerPage, searchText,user);
        }
    }

    public void first() {
        if (currentPage != 1) {
            currentPage = 1;
            booksList = booksDao.findByRangeForSpecificUser((currentPage - 1) * rowsPerPage, rowsPerPage, searchText,user);
        }
    }

    public void last() {
        currentPage = totalPageSize;
        booksList = booksDao.findByRangeForSpecificUser((currentPage - 1) * rowsPerPage, rowsPerPage, searchText,user);
    }

    public void searchResultList() {
        totalRowSize = booksDao.findAllByUserId(searchText,user).size();
        totalPageSize = (int) Math.ceil((double) totalRowSize / (double) rowsPerPage);
        if (totalPageSize != 0) {
            currentPage = 1;
            booksList = booksDao.findByRangeForSpecificUser((currentPage - 1) * rowsPerPage, rowsPerPage, searchText,user);
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "There aren't any book like " + searchText + " in the database. ", "There aren't any book like " + searchText + " in the database. "));
            searchText = "";
            init();
        }
    }


    public List<Books> getBooksList() {
        return booksList;
    }

    public void setBooksList(List<Books> booksList) {
        this.booksList = booksList;
    }

    public Books getBooksPopModel() {
        return booksPopModel;
    }

    public void setBooksPopModel(Books booksPopModel) {
        this.booksPopModel = booksPopModel;
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

    public int getTotalRowSize() {
        return totalRowSize;
    }

    public void setTotalRowSize(int totalRowSize) {
        this.totalRowSize = totalRowSize;
    }

    public String getSearchText() {
        return searchText;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }
}
