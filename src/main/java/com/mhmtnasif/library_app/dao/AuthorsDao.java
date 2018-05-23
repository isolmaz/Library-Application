package com.mhmtnasif.library_app.dao;

import com.mhmtnasif.library_app.entities.Authors;

import java.util.List;

public interface AuthorsDao {

    boolean addAuthor(Authors author);
    boolean updateAuthor(Authors author);
    boolean deleteAuthor(Authors author);
    List<Authors> findAll(String searchText);
    List<Authors> findByRange(int first,int max,String searchText);
    Authors findById(long id);
}
