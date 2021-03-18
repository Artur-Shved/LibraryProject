package com.library.library.entity;

import com.library.library.entity.Book;

import javax.persistence.*;
import java.util.Set;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String userName;
    private String surName;

    @ElementCollection(targetClass = Book.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_book", joinColumns = @JoinColumn(name = "user_id"))
    private Set<Book> books;

    public Set<Book> getBooks() {
        return books;
    }

    public void setBooks(Set<Book> books) {
        this.books = books;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String name) {
        this.userName = name;
    }

    public String getSurName() {
        return surName;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + userName + '\'' +
                ", surName='" + surName + '\'' +
                ", book=" + getBooks() +
                '}';
    }
}
