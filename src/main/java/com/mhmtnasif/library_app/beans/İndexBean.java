package com.mhmtnasif.library_app.beans;

import com.mhmtnasif.library_app.dao.BooksDao;
import com.mhmtnasif.library_app.dao.UsersDao;
import com.mhmtnasif.library_app.daoImpl.BooksDaoImpl;
import com.mhmtnasif.library_app.daoImpl.UserDaoImpl;
import com.mhmtnasif.library_app.entities.Books;
import com.mhmtnasif.library_app.entities.Users;
import com.mhmtnasif.library_app.util.LinkedInProfile;
import com.mhmtnasif.library_app.util.LinkedinPostGet;
import com.mhmtnasif.library_app.util.Util;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@ManagedBean
@ViewScoped
public class İndexBean {


    private BooksDao booksDao = new BooksDaoImpl();
    UsersDao usersDao=new UserDaoImpl();
    LinkedinPostGet linkedinPost_get =new LinkedinPostGet();
    LinkedInProfile linkedInProfile=new LinkedInProfile();
    private List<Books> booksList;
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
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String code = request.getParameter("code");
        String state = request.getParameter("state");
        if (booksList != null) {
            booksList.clear();
        }
        rowsPerPage = 5;
        currentPage = 1;
        if (code != null && state != null){
            try {
                linkedInProfile=linkedinPost_get.sendPost(code);
                String passwordHash=Util.hashMD5(linkedInProfile.getId());
                if (usersDao.login(linkedInProfile.getId(),passwordHash)==0){
                    Users user=usersDao.addUser(new Users(linkedInProfile.getId(),passwordHash,false));
                    FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("valid_user",user);
                    //FacesContext.getCurrentInstance().getExternalContext().redirect("user.xhtml");
                }else{
                    Users users=usersDao.findUserByUserName(linkedInProfile.getId());
                    FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("valid_user",users);
                    //FacesContext.getCurrentInstance().getExternalContext().redirect("user.xhtml");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public void show(Books book) {
        booksPopModel = book;
        author_id = book.getBook_author().getId();
        publisher_id = book.getBook_publisher().getId();
        this.popup = true;
    }


    public void cancel() {
        popup = false;
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
        if (searchText.length() > 0) {
            totalRowSize = booksDao.findAll(searchText).size();
            totalPageSize = (int) Math.ceil((double) totalRowSize / (double) rowsPerPage);
            if (totalPageSize != 0) {
                currentPage = 1;
                booksList = booksDao.findByRange((currentPage - 1) * rowsPerPage, rowsPerPage, searchText);
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "There aren't any book like " + searchText + " in the database. ", "There aren't any publisher like " + searchText + " in the database. "));
                searchText = "";
                init();
            }
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
}
