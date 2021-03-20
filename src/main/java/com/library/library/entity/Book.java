package com.library.library.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    private String authorName;
    private String authorSurName;
    private boolean isBookFree = true;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthorSurName() {
        return authorSurName;
    }

    public void setAuthorSurName(String surNameAuthor) {
        this.authorSurName = surNameAuthor;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public boolean isBookFree() {
        return isBookFree;
    }

    public void setBookFree(boolean bookFree) {
        isBookFree = bookFree;
    }

    @Override
    public boolean equals(Object o) {
        Book book = (Book) o;
        if(this.getId() == book.getId() && this.getAuthorName().equals(book.getAuthorName()) &&
        this.getAuthorSurName().equals(book.getAuthorSurName())){
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, authorName, authorSurName, isBookFree);
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", nameAuthor='" + authorName + '\'' +
                ", surNameAuthor='" + authorSurName + '\'' +
                ", isBookFree=" + isBookFree +
                '}';
    }
}
