package com.mhmtnasif.library_app.dao;

import com.mhmtnasif.library_app.entities.Publishers;

import java.util.List;

public interface PublishersDao {

    boolean addPublisher(Publishers publisher);
    boolean updatePublisher(Publishers publisher);
    boolean deletePublisher(Publishers publisher);
    List<Publishers> findAll(String searchText);
    List<Publishers> findByRange(int first,int max,String searchText);
    Publishers findById(long id);
}
