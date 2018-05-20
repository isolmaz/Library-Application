package com.mhmtnasif.library_app.dao;

import com.mhmtnasif.library_app.entities.Authors;

import java.util.List;

public interface AuthorsDao {

    boolean addAuthor(Authors authors);
    List<Authors> findAll();
    Authors findById(long id);
}
