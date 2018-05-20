package com.mhmtnasif.library_app.beans;

import com.mhmtnasif.library_app.dao.AuthorsDao;
import com.mhmtnasif.library_app.dao.BooksDao;
import com.mhmtnasif.library_app.dao.PublishersDao;
import com.mhmtnasif.library_app.dao.daoImpl.AuthorsDaoImpl;
import com.mhmtnasif.library_app.dao.daoImpl.BooksDaoImpl;
import com.mhmtnasif.library_app.dao.daoImpl.PublishersDaoImpl;
import com.mhmtnasif.library_app.entities.Authors;
import com.mhmtnasif.library_app.entities.Books;
import com.mhmtnasif.library_app.entities.Publishers;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.util.List;

@ManagedBean
@ViewScoped
public class BooksBean {

    private long author_id;
    private long publisher_id;
    private Books books = new Books();
    private BooksDao booksDao = new BooksDaoImpl();
    private AuthorsDao authorsDao = new AuthorsDaoImpl();
    private PublishersDao publishersDao = new PublishersDaoImpl();
    private List<Authors> authors;
    private List<Publishers> publishers;

    @PostConstruct
    public void init() {
        authors = authorsDao.findAll();
        publishers = publishersDao.findAll();
    }

    public void addBook() {
        if (this.books.getBook_name().equals("") ||
                this.books.getBook_name() == null ||
                this.books.getBook_sub_name().equals("") ||
                this.books.getBook_sub_name() == null ||
                this.books.getBook_serial_name().equals("") ||
                this.books.getBook_serial_name() == null ||
                author_id == -999 ||
                publisher_id == -999 ||
                this.books.getBook_isbn().equals("") ||
                this.books.getBook_isbn() == null ||
                this.books.getBook_desc().equals("") ||
                this.books.getBook_desc() == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_ERROR, "There are empty spaces", "There are empty spaces"
            ));
        } else {
            this.books.setBook_author(authorsDao.findById(author_id));
            this.books.setBook_publisher(publishersDao.findById(publisher_id));
            if (booksDao.addBook(this.books)) {
                this.books.setBook_name("");
                this.books.setBook_sub_name("");
                this.books.setBook_serial_name("");
                this.books.setBook_isbn("");
                this.books.setBook_desc("");
                author_id=-999;
                publisher_id=-999;
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Successful", "Successful"));
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Unexpected error occurred!", "Unexpected error occurred!"));
            }
        }
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

    public Books getBooks() {
        return books;
    }

    public void setBooks(Books books) {
        this.books = books;
    }

    public List<Authors> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Authors> authors) {
        this.authors = authors;
    }

    public List<Publishers> getPublishers() {
        return publishers;
    }

    public void setPublishers(List<Publishers> publishers) {
        this.publishers = publishers;
    }
}
