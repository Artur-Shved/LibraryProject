package com.library.library.interfaceDao;


import com.library.library.entity.Book;
import com.library.library.entity.User;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface BookDao {
    List<Book> findAll();
    User getUserByBookId(long id);
    ResponseEntity createBook(Book book);
    ResponseEntity deleteBookById(long id);
    Book getBookById(long id);
    Book editBook(Book book);
    Book findByBookNameAndAuthor(String authorName, String authorSurName, String bookName);
}
