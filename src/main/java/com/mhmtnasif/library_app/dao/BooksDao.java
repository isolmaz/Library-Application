package com.mhmtnasif.library_app.dao;

import com.mhmtnasif.library_app.entities.Authors;
import com.mhmtnasif.library_app.entities.Books;
import com.mhmtnasif.library_app.entities.Publishers;

import java.util.List;

public interface BooksDao {

    boolean addBook(Books books);
    List<Books> findByAuthorId(Authors author);
    List<Books> findPublisherId(Publishers publisher);
    boolean deleteBooks(List<Books> booksList);
}
