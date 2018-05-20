package com.mhmtnasif.library_app.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JpaFactory {

    public static JpaFactory mJpaFactory;
    public static final Object OBJECT = new Object();
    private EntityManagerFactory mEntityManagerFactory;

    private JpaFactory() {
        mEntityManagerFactory = Persistence.createEntityManagerFactory("LibraryPersistenceUnit");
    }

    public static JpaFactory getInstance() {
        if (mJpaFactory == null) {
            synchronized (OBJECT) {
                if (mJpaFactory == null)
                    return new JpaFactory();
            }
        }
        return mJpaFactory;
    }

    public EntityManager getEntityManager() {
        return mEntityManagerFactory.createEntityManager();
    }

    public void CloseFactory() {
        mEntityManagerFactory.close();
    }
}

