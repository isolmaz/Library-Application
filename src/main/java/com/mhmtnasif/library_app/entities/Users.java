package com.mhmtnasif.library_app.entities;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

@NamedQueries({
        @NamedQuery(name = "Users.CheckUserName", query = "select count(user) from Users user where user.username=:param"),
        @NamedQuery(name = "Users.login", query = "select count(user)from Users user where user.username=:username and user.password=:password"),
        @NamedQuery(name = "Users.findUserByUserName", query = "SELECT user from Users user where user.username=:username")


})
@Entity
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String username;
    private String password;
    private boolean isAdmin;
    @OneToMany(mappedBy = "book_user", cascade = {CascadeType.ALL}, orphanRemoval = true)
    private List<Books> bookList = new LinkedList<Books>();

    public Users(String username, String password, boolean isAdmin) {
        this.username = username;
        this.password = password;
        this.isAdmin = isAdmin;
    }

    public Users() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<Books> getBookList() {
        return bookList;
    }

    public void setBookList(List<Books> bookList) {
        this.bookList = bookList;
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

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }
}
