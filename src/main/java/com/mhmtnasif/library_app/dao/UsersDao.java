package com.mhmtnasif.library_app.dao;

import com.mhmtnasif.library_app.entities.Users;

public interface UsersDao {

    long checkUserName(String param);
    Users addUser(Users users);
    long login(String username,String password);
    Users findUserByUserName(String username);
}
