package com.library.library.interfaceDao;


import com.library.library.entity.Book;
import com.library.library.entity.User;

import java.util.List;

public interface BookDao {
    List<Book> findAll();
    Object getUserByBookId(long id);
    String createBook(Book book);
    String deleteBookById(long id);
    Book getBookById(long id);
    Book editBook(Book book);
    Book findByBookNameAndAuthor(String authorName, String authorSurName, String bookName);
}
