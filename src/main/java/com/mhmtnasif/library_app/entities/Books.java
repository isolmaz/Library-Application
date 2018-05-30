package com.mhmtnasif.library_app.entities;

import javax.persistence.*;

@NamedQueries({
        @NamedQuery(name = "Books.findByAuthorId", query = "select book from Books book where book.book_author.id=:param"),
        @NamedQuery(name = "Books.findByPublisherId", query = "select book from Books book where book.book_publisher.id=:param"),
        @NamedQuery(name = "Books.findAllBySearch", query = "select b from Books b where LOWER(b.book_name) LIKE CONCAT('%',:param,'%') or " +
                " LOWER(b.book_sub_name) LIKE CONCAT('%',:param,'%') or LOWER(b.book_serial_name) LIKE CONCAT('%',:param,'%')" +
                " or LOWER(b.book_isbn) LIKE CONCAT('%',:param,'%') or LOWER(b.book_desc) LIKE CONCAT('%',:param,'%')" +
                " or LOWER(b.book_author.authors_name) LIKE CONCAT('%',:param,'%')" +
                " or LOWER(b.book_publisher.publisher_name) LIKE CONCAT('%',:param,'%')"),
        @NamedQuery(name = "Books.findAll", query = "select b from Books b"),
        @NamedQuery(name = "Books.findAllByUserId", query = "select b from Books b where b.book_user.id=:param"),
        @NamedQuery(name = "Books.findAllByUserIdSearch", query = "select b from Books b where b.book_user.id=:user and (LOWER(b.book_name) LIKE CONCAT('%',:param,'%') or " +
                " LOWER(b.book_sub_name) LIKE CONCAT('%',:param,'%') or LOWER(b.book_serial_name) LIKE CONCAT('%',:param,'%')" +
                " or LOWER(b.book_isbn) LIKE CONCAT('%',:param,'%') or LOWER(b.book_desc) LIKE CONCAT('%',:param,'%')" +
                " or LOWER(b.book_author.authors_name) LIKE CONCAT('%',:param,'%')" +
                " or LOWER(b.book_publisher.publisher_name) LIKE CONCAT('%',:param,'%'))"),
})
@Entity
public class Books {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String book_name;
    private String book_sub_name;
    private String book_serial_name;
    @ManyToOne
    private Authors book_author;
    @ManyToOne
    private Publishers book_publisher;
    @ManyToOne
    private Users book_user;

    private String book_isbn;
    @Column(length = 2000)
    private String book_desc;

    public Books(String book_name, String book_sub_name, String book_serial_name, Authors book_author, Publishers book_publisher, Users book_user, String book_isbn, String book_desc) {
        this.book_name = book_name;
        this.book_sub_name = book_sub_name;
        this.book_serial_name = book_serial_name;
        this.book_author = book_author;
        this.book_publisher = book_publisher;
        this.book_user = book_user;
        this.book_isbn = book_isbn;
        this.book_desc = book_desc;
    }

    public Books() {
    }

    public Users getBook_user() {
        return book_user;
    }

    public void setBook_user(Users book_user) {
        this.book_user = book_user;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getBook_name() {
        return book_name;
    }

    public void setBook_name(String book_name) {
        this.book_name = book_name;
    }

    public String getBook_sub_name() {
        return book_sub_name;
    }

    public void setBook_sub_name(String book_sub_name) {
        this.book_sub_name = book_sub_name;
    }

    public String getBook_serial_name() {
        return book_serial_name;
    }

    public void setBook_serial_name(String book_serial_name) {
        this.book_serial_name = book_serial_name;
    }

    public Authors getBook_author() {
        return book_author;
    }

    public void setBook_author(Authors book_author) {
        this.book_author = book_author;
    }

    public Publishers getBook_publisher() {
        return book_publisher;
    }

    public void setBook_publisher(Publishers book_publisher) {
        this.book_publisher = book_publisher;
    }

    public String getBook_isbn() {
        return book_isbn;
    }

    public void setBook_isbn(String book_isbn) {
        this.book_isbn = book_isbn;
    }

    public String getBook_desc() {
        return book_desc;
    }

    public void setBook_desc(String book_desc) {
        this.book_desc = book_desc;
    }
}
