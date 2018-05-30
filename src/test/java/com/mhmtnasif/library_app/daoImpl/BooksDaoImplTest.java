package com.mhmtnasif.library_app.daoImpl;

import com.mhmtnasif.library_app.dao.AuthorsDao;
import com.mhmtnasif.library_app.dao.BooksDao;
import com.mhmtnasif.library_app.dao.PublishersDao;
import com.mhmtnasif.library_app.dao.UsersDao;
import com.mhmtnasif.library_app.entities.Books;
import com.mhmtnasif.library_app.entities.Users;
import org.junit.Before;
import org.junit.Test;

import java.awt.print.Book;
import java.util.List;

import static org.junit.Assert.*;

public class BooksDaoImplTest {

    BooksDao booksDao;
    AuthorsDao authorsDao;
    PublishersDao publishersDao;
    UsersDao usersDao;
    private int size;
    private int sizeListOfAuthorBooks;
    private int sizeListOfPublisherBooks;
    private int sizeListOfUserBooks;

    @Before
    public void setUp() {
        booksDao = new BooksDaoImpl();
        authorsDao=new AuthorsDaoImpl();
        publishersDao=new PublishersDaoImpl();
        usersDao=new UserDaoImpl();
        size=booksDao.findAll("").size();
        sizeListOfAuthorBooks=booksDao.findByAuthorId(authorsDao.findById(1)).size();
        sizeListOfPublisherBooks=booksDao.findByPublisherId(publishersDao.findById(1)).size();
        sizeListOfUserBooks=booksDao.findAllByUserId("",usersDao.findUserByUserName("furkan")).size();
    }

    @Test
    public void addBook() {
        Books book = new Books("example", "example","example",authorsDao.findById(1),publishersDao.findById(1),usersDao.findUserByUserName("furkan"),"123123","example");
        assertTrue("addBook method failed result of this method should be true", booksDao.addBook(book));
    }

    @Test
    public void updateBook() {
        List<Books> books=booksDao.findByAuthorId(authorsDao.findById(1));
        books.get(books.size()-1).setBook_desc("updated");
        booksDao.updateBook(books.get(books.size()-1));
        Books Updatedbook=booksDao.findByAuthorId(authorsDao.findById(1)).get(books.size()-1);
        assertTrue("updateBook method failed book description  should  be 'updated' ", Updatedbook.getBook_desc().equals("updated"));
    }

    @Test
    public void deleteBook() {
        Books book = new Books("example", "example","example",authorsDao.findById(1),publishersDao.findById(1),usersDao.findUserByUserName("furkan"),"123123","example");
        booksDao.addBook(book);
        assertTrue("deletePublisher method failed result of this method should  be true", booksDao.deleteBook(book));
    }

    @Test
    public void findAll() {
        addBook();
        assertTrue("findAll method failed size of the list should  be "+size+1, booksDao.findAll("").size()==size+1);
        assertTrue("findAll method failed size of the list with search text(ex) should  be >=1", booksDao.findAll("ex").size()>=1);
        assertTrue("findAll method failed size of the list with text(asd) should  be 0", booksDao.findAll("asd").size()==0);
    }

    @Test
    public void findByRange() {
        assertTrue("findByRange(first:0,max:10) method failed size of the list should  be <=10"+size, booksDao.findByRange(0,10,"").size()<=10);
        assertTrue("findByRange(first:10,max:10) method failed size of the list should  be <=20", booksDao.findByRange(10,10,"").size()<=20);
        assertTrue("findByRange(first:0,max:2) method failed size of the list should  be <=2", booksDao.findByRange(0,2,"").size()<=2);
        assertTrue("findByRange(first:0,max:10,text:ex) method failed size of the list should  be <=10", booksDao.findByRange(0,10,"ex").size()<=10);
        assertTrue("findByRange(first:10,max:10,text:ex) method failed size of the list should  be <=20", booksDao.findByRange(10,10,"ex").size()<=20);
        assertTrue("findByRange(first:0,max:10,text:asd) method failed size of the list should  be 0", booksDao.findByRange(0,10,"asd").size()==0);
    }

    @Test
    public void findByAuthorId() {
        assertTrue("findByAuthorId(1) method failed size of the list should  be "+sizeListOfAuthorBooks, booksDao.findByAuthorId(authorsDao.findById(1)).size()==sizeListOfAuthorBooks);

    }

    @Test
    public void findByPublisherId() {
        assertTrue("findByPublisherId(1) method failed size of the list should  be "+sizeListOfPublisherBooks, booksDao.findByPublisherId(publishersDao.findById(1)).size()==sizeListOfPublisherBooks);
    }

    @Test
    public void findAllByUserId() {
        assertTrue("findAllByUserId('',furkan) method failed size of the list should  be "+sizeListOfUserBooks, booksDao.findAllByUserId("",usersDao.findUserByUserName("furkan")).size()==sizeListOfUserBooks);
    }

    @Test
    public void findByRangeForSpecificUser() {
        assertTrue("findByRange(first:0,max:10) method failed size of the list should  be <=10"+size, booksDao.findByRangeForSpecificUser(0,10,"",usersDao.findUserByUserName("furkan")).size()<=10);
        assertTrue("findByRange(first:10,max:10) method failed size of the list should  be <=20", booksDao.findByRangeForSpecificUser(10,10,"",usersDao.findUserByUserName("furkan")).size()<=20);
        assertTrue("findByRange(first:0,max:2) method failed size of the list should  be <=2", booksDao.findByRangeForSpecificUser(0,2,"",usersDao.findUserByUserName("furkan")).size()<=2);
        assertTrue("findByRange(first:0,max:10,text:ex) method failed size of the list should  be <=10", booksDao.findByRangeForSpecificUser(0,10,"ex",usersDao.findUserByUserName("furkan")).size()<=10);
        assertTrue("findByRange(first:10,max:10,text:ex) method failed size of the list should  be <=20", booksDao.findByRangeForSpecificUser(10,10,"ex",usersDao.findUserByUserName("furkan")).size()<=20);
        assertTrue("findByRange(first:0,max:10,text:asd) method failed size of the list should  be 0", booksDao.findByRangeForSpecificUser(0,10,"asd",usersDao.findUserByUserName("furkan")).size()==0);
    }

    @Test
    public void deleteBooks() {
        List<Books> books=booksDao.findAllByUserId("",usersDao.findUserByUserName("furkan"));
        assertTrue("deletePublisher method failed result of this method should  be true", booksDao.deleteBooks(books));
    }
}