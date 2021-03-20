package com.library.library.interfaceDao;


import com.library.library.entity.Book;
import com.library.library.entity.User;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface BookDao {
    List<Book> findAll();
    void saveBook(Book book);
    ResponseEntity createBook(Book book);
    ResponseEntity deleteBookById(long id);
    Book getBookById(long id);
    ResponseEntity editBook(Book book);
    Book findByBookNameAndAuthor(String authorName, String authorSurName, String bookName);
}
