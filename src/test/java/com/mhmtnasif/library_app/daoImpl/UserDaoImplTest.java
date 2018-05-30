package com.mhmtnasif.library_app.daoImpl;

import com.mhmtnasif.library_app.dao.UsersDao;
import com.mhmtnasif.library_app.entities.Users;
import com.mhmtnasif.library_app.util.Util;
import org.junit.Before;
import org.junit.Test;

import java.security.NoSuchAlgorithmException;

import static org.junit.Assert.assertTrue;


public class UserDaoImplTest {

    public UsersDao usersDao;

    @Before
    public void init() {
        usersDao = new UserDaoImpl();
    }

    @Test
    public void checkUserName() {
        assertTrue("checkUserName(furkan) method failed result of this method  should be 1", usersDao.checkUserName("furkan") == 1);
        assertTrue("checkUserName(FURKAN) method failed result of this method  should be 1", usersDao.checkUserName("FURKAN") == 1);
        assertTrue("checkUserName(asdasd) method failed result of this method  should be  0", usersDao.checkUserName("asdasd") == 0);
        assertTrue("checkUserName() method failed result of this method  should be  0", usersDao.checkUserName("") == 0);
    }

    @Test
    public void addUser() throws NoSuchAlgorithmException {
        Users user = new Users("example", Util.hashMD5("example"), false);
        Users addedUser = usersDao.addUser(user);
        assertTrue("addUser method failed the user's id who added should not be 0", addedUser.getId() != 0);
        assertTrue("addUser method failed objects should be same", user == addedUser);
    }

    @Test
    public void login() throws NoSuchAlgorithmException {
        assertTrue("login(furkan,123123123) method failed the result of this method should be 1", usersDao.login("furkan", Util.hashMD5("123123123")) == 1);
        assertTrue("login(FURKAN,123123123) method failed the result of this method should be 1", usersDao.login("FURKAN", Util.hashMD5("123123123")) == 1);
        assertTrue("login(asdasd,123123123) method failed the result of this method should be 0", usersDao.login("asdasd", Util.hashMD5("123123123")) == 0);
        assertTrue("login(FURKAN,12312312) method failed the result of this method should be 0", usersDao.login("FURKAN", Util.hashMD5("12312312")) == 0);
    }

    @Test
    public void findUserByUserName() {
        assertTrue("wrong user", usersDao.findUserByUserName("furkan").getId() == 1);
    }

    @Test(expected = UsersDao.NoResult.class)
    public void findUserByUserNameTestException() {
        usersDao.findUserByUserName("");
    }

}