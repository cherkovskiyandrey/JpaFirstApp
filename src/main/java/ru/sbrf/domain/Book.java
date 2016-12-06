package ru.sbrf.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

//@Table(name = "USERS", uniqueConstraints = @UniqueConstraint(columnNames = {"TENANT_ID", "username"}))
@Entity
public class Book implements Serializable {

    private static final long serialVersionUID = -8568681083447054748L;

    @Id
    @GeneratedValue
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Temporal(TemporalType.DATE)
    @Column(name = "DATE")
    private Date date;

    @ManyToMany(mappedBy = "bookList")
    private List<Author> authorList = new LinkedList<>();

    public Book() {
    }

    public Book(String name, Date date) {
        this.name = name;
        this.date = date;
        this.authorList = authorList;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<Author> getAuthorList() {
        return authorList;
    }

    public void setAuthorList(List<Author> authorList) {
        this.authorList = authorList;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", date=" + date +
                '}';
    }
}
