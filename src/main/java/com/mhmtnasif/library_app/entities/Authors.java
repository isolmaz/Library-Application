package com.mhmtnasif.library_app.entities;

import javax.persistence.*;

@Entity
public class Authors {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String authors_name;
    @Column(length = 2000)
    private String authors_desc;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAuthors_desc() {
        return authors_desc;
    }

    public void setAuthors_desc(String authors_desc) {
        this.authors_desc = authors_desc;
    }

    public String getAuthors_name() {
        return authors_name;
    }

    public void setAuthors_name(String authors_name) {
        this.authors_name = authors_name;
    }
}
