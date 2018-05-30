package com.mhmtnasif.library_app.entities;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

@NamedQueries({
        @NamedQuery(name = "Publishers.findAllBySearch",query = "select p from Publishers p where LOWER(p.publisher_name) LIKE CONCAT('%',:param,'%') or LOWER(p.publisher_desc) LIKE CONCAT('%',:param,'%') "),
        @NamedQuery(name="Publishers.findAll",query = "select p from  Publishers p")
})
@Entity
public class Publishers {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String publisher_name;
    @Column(length = 2000)
    private String publisher_desc;
    @OneToMany(orphanRemoval = true,cascade ={CascadeType.ALL},mappedBy = "book_publisher")
    private List<Books> bookList=new LinkedList<Books>();

    public Publishers(String publisher_name, String publisher_desc) {
        this.publisher_name = publisher_name;
        this.publisher_desc = publisher_desc;
    }

    public Publishers() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPublisher_name() {
        return publisher_name;
    }

    public void setPublisher_name(String publisher_name) {
        this.publisher_name = publisher_name;
    }

    public String getPublisher_desc() {
        return publisher_desc;
    }

    public void setPublisher_desc(String publisher_desc) {
        this.publisher_desc = publisher_desc;
    }
}
