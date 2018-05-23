package com.mhmtnasif.library_app.beans;

import com.mhmtnasif.library_app.dao.AuthorsDao;
import com.mhmtnasif.library_app.dao.BooksDao;
import com.mhmtnasif.library_app.dao.daoImpl.AuthorsDaoImpl;
import com.mhmtnasif.library_app.dao.daoImpl.BooksDaoImpl;
import com.mhmtnasif.library_app.entities.Authors;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.util.List;

@ManagedBean
@ViewScoped
public class AuthorEditBean {

    private AuthorsDao authorsDao = new AuthorsDaoImpl();
    private BooksDao booksDao = new BooksDaoImpl();
    private List<Authors> authorList;
    private Authors authorPopModel;
    private Authors temp;
    private boolean popup;
    private int rowsPerPage;
    private int totalPageSize;
    private int currentPage;
    private int totalRowSize;
    private String searchText;


    @PostConstruct
    public void init() {
        rowsPerPage = 5;
        totalRowSize = authorsDao.findAll(searchText).size();
        totalPageSize = (int) Math.ceil((double) totalRowSize/ (double) rowsPerPage);
        currentPage = 1;
        authorList = authorsDao.findByRange((currentPage - 1) * rowsPerPage, rowsPerPage, searchText);

    }


    public void edit(Authors authors) {
        authorPopModel = authors;
        temp = authors;
        this.popup = true;

    }

    public void updateAuthor() {
        if (authorPopModel.getAuthors_name().equals("") ||
                authorPopModel.getAuthors_name() == null ||
                authorPopModel.getAuthors_desc().equals("") ||
                authorPopModel.getAuthors_desc() == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "There are empty spaces", "There are empty spaces"));
            authorList.set(authorList.indexOf(temp), temp);
            authorPopModel = temp;
        } else {
            if (authorsDao.updateAuthor(authorPopModel)) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Successful", "Successful"));
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Unexpected error occurred!", "Unexpected error occurred!"));
                authorList.set(authorList.indexOf(temp), temp);
            }
        }

        popup = false;
    }

    public void cancelEdit() {
        popup = false;
    }

    public void remove(Authors authors) {
        if (booksDao.deleteBooks(booksDao.findByAuthorId(authors))) {
            if (authorsDao.deleteAuthor(authors)) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Successful", "Successful"));
                authorList.remove(authors);
                if (authorList.isEmpty() && currentPage != 1) {
                    currentPage--;
                    totalPageSize--;
                    authorList = authorsDao.findByRange((currentPage - 1) * rowsPerPage, rowsPerPage, searchText);
                } else if (authorList.isEmpty() && currentPage == 1) {
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
        if (currentPage != totalPageSize ) {
            currentPage++;
            authorList = authorsDao.findByRange((currentPage - 1) * rowsPerPage, rowsPerPage, searchText);
        }
    }

    public void prev() {
        if (currentPage != 1) {
            currentPage--;
            authorList = authorsDao.findByRange((currentPage - 1) * rowsPerPage, rowsPerPage, searchText);
        }
    }

    public void first() {
        if (currentPage != 1) {
            currentPage = 1;
            authorList = authorsDao.findByRange((currentPage - 1) * rowsPerPage, rowsPerPage, searchText);
        }
    }

    public void last() {
        currentPage = totalPageSize;
        authorList = authorsDao.findByRange((currentPage - 1) * rowsPerPage, rowsPerPage, searchText);
    }

    public void searchResultList() {
        totalRowSize = authorsDao.findAll(searchText).size();
        totalPageSize = (int) Math.ceil((double) totalRowSize / (double) rowsPerPage);
        if (totalPageSize!=0){
            currentPage = 1;
            authorList = authorsDao.findByRange((currentPage - 1) * rowsPerPage, rowsPerPage, searchText);
        }else{
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "There aren't any author like "+searchText+" in the database. ", "There aren't any author like "+searchText+" in the database. "));
            searchText = "";
            init();
        }


    }

    public List<Authors> getAuthorList() {
        return authorList;
    }

    public void setAuthorList(List<Authors> authorList) {
        this.authorList = authorList;
    }

    public Authors getAuthorPopModel() {
        return authorPopModel;
    }

    public void setAuthorPopModel(Authors authorPopModel) {
        this.authorPopModel = authorPopModel;
    }

    public boolean isPopup() {
        return popup;
    }

    public void setPopup(boolean popup) {
        this.popup = popup;
    }

    public AuthorsDao getAuthorsDao() {
        return authorsDao;
    }

    public void setAuthorsDao(AuthorsDao authorsDao) {
        this.authorsDao = authorsDao;
    }

    public BooksDao getBooksDao() {
        return booksDao;
    }

    public void setBooksDao(BooksDao booksDao) {
        this.booksDao = booksDao;
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

