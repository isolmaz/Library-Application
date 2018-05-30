package com.mhmtnasif.library_app.daoImpl;

import com.mhmtnasif.library_app.dao.UsersDao;
import com.mhmtnasif.library_app.entities.Users;
import com.mhmtnasif.library_app.util.JpaFactory;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

public class UserDaoImpl implements UsersDao {

    public long checkUserName(String param) {
        EntityManager entityManager = JpaFactory.getInstance().getEntityManager();
        TypedQuery<Long> longTypedQuery = entityManager.createNamedQuery("Users.CheckUserName", Long.class);
        longTypedQuery.setParameter("param", param);
        long status = longTypedQuery.getSingleResult();
        entityManager.close();
        JpaFactory.getInstance().CloseFactory();
        return status;
    }

    public Users addUser(Users user) {
        EntityManager entityManager = JpaFactory.getInstance().getEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(user);
        entityManager.flush();
        entityManager.getTransaction().commit();
        entityManager.close();
        JpaFactory.getInstance().CloseFactory();
        return user;
    }

    public long login(String username, String password) {
        EntityManager entityManager = JpaFactory.getInstance().getEntityManager();
        TypedQuery<Long> longTypedQuery = entityManager.createNamedQuery("Users.login", Long.class);
        longTypedQuery.setParameter("username", username);
        longTypedQuery.setParameter("password", password);
        long status = longTypedQuery.getSingleResult();
        entityManager.close();
        JpaFactory.getInstance().CloseFactory();
        return status;
    }

    public Users findUserByUserName(String username) {
        EntityManager entityManager = JpaFactory.getInstance().getEntityManager();
        TypedQuery<Users> longTypedQuery = entityManager.createNamedQuery("Users.findUserByUserName", Users.class);
        longTypedQuery.setParameter("username", username);
        Users user =null;
        try {
            user=longTypedQuery.getSingleResult();
        }catch (NoResultException e){
            throw new NoResult("There aren't any result like "+username);
        }
        entityManager.close();
        JpaFactory.getInstance().CloseFactory();
        return user;
    }
}
