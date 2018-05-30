package com.mhmtnasif.library_app.daoImpl;

import com.mhmtnasif.library_app.dao.PublishersDao;
import com.mhmtnasif.library_app.entities.Publishers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PublishersDaoImplTest {

    PublishersDao publishersDao;
    private int size;

    @Before
    public void setUp() {
        publishersDao = new PublishersDaoImpl();
        size=publishersDao.findAll("").size();
    }

    @Test
    public void addPublisher() {
        Publishers publisher = new Publishers("example", "example");
        assertTrue("addPublisher method failed result of this method should be true", publishersDao.addPublisher(publisher));
    }

    @Test
    public void updatePublisher() {
        Publishers publishers=publishersDao.findById(4);
        publishers.setPublisher_name("updated");
        publishersDao.updatePublisher(publishers);
        Publishers publishersUpdated=publishersDao.findById(4);
        assertTrue("updatePublisher method failed publisher name should  be 'updated' ", publishersUpdated.getPublisher_name().equals("updated"));
    }

    @Test
    public void deletePublisher() {
        Publishers publisher=new Publishers("example","example");
        publishersDao.addPublisher(publisher);
        assertTrue("deletePublisher method failed result of this method should  be true", publishersDao.deletePublisher(publisher));
    }


    @Test
    public void findAll() {
        assertEquals("findAll method failed size of the list should  be "+size, publishersDao.findAll("").size(),size);
        assertEquals("findAll method failed size of the list with search text(ex) should  be "+(size-3), publishersDao.findAll("ex").size(),(size-3));
        assertEquals("findAll method failed size of the list with text(asd) should  be 0", publishersDao.findAll("asd").size(),0);
    }

    @Test
    public void findByRange() {
        assertTrue("findByRange(first:0,max:10) method failed size of the list should  be <=10"+size, publishersDao.findByRange(0,10,"").size()<=10);
        assertTrue("findByRange(first:10,max:10) method failed size of the list should  be <=20", publishersDao.findByRange(10,10,"").size()<=20);
        assertTrue("findByRange(first:0,max:2) method failed size of the list should  be <=2", publishersDao.findByRange(0,2,"").size()<=2);
        assertTrue("findByRange(first:0,max:10,text:ex) method failed size of the list should  be <=10", publishersDao.findByRange(0,10,"ex").size()<=10);
        assertTrue("findByRange(first:10,max:10,text:ex) method failed size of the list should  be <=20", publishersDao.findByRange(10,10,"ex").size()<=20);
        assertEquals("findByRange(first:0,max:10,text:asd) method failed size of the list should  be 0", publishersDao.findByRange(0,10,"asd").size(),0);
    }

    @Test
    public void findById() {
        assertTrue("finById method failed. the publisher which get with id(4) should has id==4", publishersDao.findById(4).getId()==4);
        Assert.assertNull("findById method failed there isn't any publisher which has id=100", publishersDao.findById(100));

    }

}