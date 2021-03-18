package com.library.library.interfaceDao;

import com.library.library.entity.Book;
import com.library.library.entity.User;
import org.springframework.http.ResponseEntity;

public interface UserDao {
    ResponseEntity createUser(User user);
    User findUserById(long id);
    ResponseEntity deleteUserById(long id);
    void removeBookByIdInUser(User user, Book book);
    ResponseEntity editUser(User user);
}
