package com.library.library.interfaceDao;

import com.library.library.entity.Book;
import com.library.library.entity.User;
import org.springframework.http.ResponseEntity;

public interface UserDao {
    ResponseEntity createUser(User user);
    User findUser(long id);
    User findUser(String name, String surName);
    User findUser(Book book);
    ResponseEntity deleteUserById(long id);
    ResponseEntity editUser(User user);
    ResponseEntity removeBook(Book book);
    ResponseEntity takeBook(long bookId, long userId);
    ResponseEntity takeBook(String userName, String userLastName , String bookName, String authorName, String authorSurName);
}
