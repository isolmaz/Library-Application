package com.mhmtnasif.library_app.dao;

import com.mhmtnasif.library_app.entities.Publishers;

import java.util.List;

public interface PublishersDao {

    boolean addPublisher(Publishers publishers);
    List<Publishers> findAll();
    Publishers findById(long id);
}
