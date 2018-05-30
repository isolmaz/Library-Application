package com.mhmtnasif.library_app.beans;

import com.mhmtnasif.library_app.dao.AuthorsDao;
import com.mhmtnasif.library_app.dao.BooksDao;
import com.mhmtnasif.library_app.dao.PublishersDao;
import com.mhmtnasif.library_app.daoImpl.AuthorsDaoImpl;
import com.mhmtnasif.library_app.daoImpl.BooksDaoImpl;
import com.mhmtnasif.library_app.daoImpl.PublishersDaoImpl;
import com.mhmtnasif.library_app.entities.Authors;
import com.mhmtnasif.library_app.entities.Books;
import com.mhmtnasif.library_app.entities.Publishers;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.util.List;

@ManagedBean
@ViewScoped
public class BooksEditBean {

    private BooksDao booksDao = new BooksDaoImpl();
    private AuthorsDao authorsDao = new AuthorsDaoImpl();
    private PublishersDao publishersDao = new PublishersDaoImpl();
    private List<Books> booksList;
    private List<Authors> authorsList;
    private List<Publishers> publishersList;
    private Books booksPopModel;
    private boolean popup;
    private int rowsPerPage;
    private int totalPageSize;
    private int currentPage;
    private int totalRowSize;
    private String searchText;
    private long author_id;
    private long publisher_id;


    @PostConstruct
    public void init() {
        authorsList = authorsDao.findAll("");
        publishersList = publishersDao.findAll("");
        rowsPerPage = 5;
        totalRowSize = booksDao.findAll(searchText).size();
        totalPageSize = (int) Math.ceil((double) totalRowSize / (double) rowsPerPage);
        currentPage = 1;
        booksList = booksDao.findByRange((currentPage - 1) * rowsPerPage, rowsPerPage, searchText);

    }


    public void edit(Books book) {
        booksPopModel = book;
        author_id = book.getBook_author().getId();
        publisher_id = book.getBook_publisher().getId();
        this.popup = true;

    }

    public void updateBook() {
        if (booksPopModel.getBook_name().equals("") ||
                booksPopModel.getBook_name() == null ||
                booksPopModel.getBook_sub_name().equals("") ||
                booksPopModel.getBook_sub_name() == null ||
                booksPopModel.getBook_serial_name().equals("") ||
                booksPopModel.getBook_serial_name() == null ||
                author_id == -999 ||
                publisher_id == -999 ||
                booksPopModel.getBook_isbn().equals("") ||
                booksPopModel.getBook_isbn() == null ||
                booksPopModel.getBook_desc().equals("") ||
                booksPopModel.getBook_desc() == null) {
            booksList = booksDao.findByRange((currentPage - 1) * rowsPerPage, rowsPerPage, searchText);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "There are empty spaces", "There are empty spaces"));
        } else {
            booksPopModel.setBook_author(authorsDao.findById(author_id));
            booksPopModel.setBook_publisher(publishersDao.findById(publisher_id));
            if (booksDao.updateBook(booksPopModel)) {
                booksList.set(booksList.indexOf(booksPopModel), booksPopModel);
                isListEmpty();
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Successful", "Successful"));
            } else {
                booksList = booksDao.findByRange((currentPage - 1) * rowsPerPage, rowsPerPage, searchText);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Unexpected error occurred!", "Unexpected error occurred!"));
            }
        }

        popup = false;
    }

    public void cancelEdit() {
        popup = false;
    }

    public void remove(Books book) {
        if (booksDao.deleteBook(book)) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Successful", "Successful"));
            booksList.remove(book);
            isListEmpty();
            totalRowSize--;
            totalPageSize = (int) Math.ceil((double) totalRowSize / (double) rowsPerPage);

        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Unexpected error occurred!", "Unexpected error occurred!"));
        }
    }

    public void next() {
        if (currentPage != totalPageSize) {
            currentPage++;
            booksList = booksDao.findByRange((currentPage - 1) * rowsPerPage, rowsPerPage, searchText);
        }
    }

    public void prev() {
        if (currentPage != 1) {
            currentPage--;
            booksList = booksDao.findByRange((currentPage - 1) * rowsPerPage, rowsPerPage, searchText);
        }
    }

    public void first() {
        if (currentPage != 1) {
            currentPage = 1;
            booksList = booksDao.findByRange((currentPage - 1) * rowsPerPage, rowsPerPage, searchText);
        }
    }

    public void last() {
        currentPage = totalPageSize;
        booksList = booksDao.findByRange((currentPage - 1) * rowsPerPage, rowsPerPage, searchText);
    }

    public void searchResultList() {
        totalRowSize = booksDao.findAll(searchText).size();
        totalPageSize = (int) Math.ceil((double) totalRowSize / (double) rowsPerPage);
        if (totalPageSize != 0) {
            currentPage = 1;
            booksList = booksDao.findByRange((currentPage - 1) * rowsPerPage, rowsPerPage, searchText);
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "There aren't any book like " + searchText + " in the database. ", "There aren't any book like " + searchText + " in the database. "));
            searchText = "";
            init();
        }
    }

    public void isListEmpty(){
        if (booksList.isEmpty() && currentPage != 1) {
            currentPage--;
            totalPageSize--;
            booksList = booksDao.findByRange((currentPage - 1) * rowsPerPage, rowsPerPage, searchText);
        } else if (booksList.isEmpty() && currentPage == 1) {
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

    public long getAuthor_id() {
        return author_id;
    }

    public void setAuthor_id(long author_id) {
        this.author_id = author_id;
    }

    public long getPublisher_id() {
        return publisher_id;
    }

    public void setPublisher_id(long publisher_id) {
        this.publisher_id = publisher_id;
    }

    public List<Authors> getAuthorsList() {
        return authorsList;
    }

    public void setAuthorsList(List<Authors> authorsList) {
        this.authorsList = authorsList;
    }

    public List<Publishers> getPublishersList() {
        return publishersList;
    }

    public void setPublishersList(List<Publishers> publishersList) {
        this.publishersList = publishersList;
    }
}

