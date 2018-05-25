package com.mhmtnasif.library_app.dao;

import com.mhmtnasif.library_app.entities.Authors;
import com.mhmtnasif.library_app.entities.Books;
import com.mhmtnasif.library_app.entities.Publishers;
import com.mhmtnasif.library_app.entities.Users;

import java.awt.print.Book;
import java.util.List;

public interface BooksDao {

    boolean addBook(Books book);
    boolean updateBook(Books book);
    boolean deleteBook(Books book);
    List<Books> findAll(String searchText);
    List<Books> findByRange(int first,int max,String searchText);
    List<Books> findByAuthorId(Authors author);
    List<Books> findByPublisherId(Publishers publisher);
    List<Books> findAllByUserId(String searchText,Users user);
    List<Books> findByRangeForSpecificUser(int first,int max,String searchText,Users user);
    boolean deleteBooks(List<Books> booksList);
}
