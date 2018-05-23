package com.mhmtnasif.library_app.entities;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

@NamedQueries({
        @NamedQuery(name = "Authors.findAllBySearch",query = "select a from Authors a where LOWER(a.authors_name) LIKE CONCAT('%',:param,'%') or LOWER(a.authors_desc) LIKE CONCAT('%',:param,'%') "),
        @NamedQuery(name="Authors.findAll",query = "select a from Authors a")
})

@Entity
public class Authors {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String authors_name;
    @Column(length = 2000)
    private String authors_desc;

    @OneToMany(mappedBy = "book_author" ,cascade ={CascadeType.ALL},orphanRemoval = true)
    private List<Books> bookList=new LinkedList<Books>();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAuthors_name() {
        return authors_name;
    }

    public void setAuthors_name(String authors_name) {
        this.authors_name = authors_name;
    }

    public String getAuthors_desc() {
        return authors_desc;
    }

    public void setAuthors_desc(String authors_desc) {
        this.authors_desc = authors_desc;
    }
}
